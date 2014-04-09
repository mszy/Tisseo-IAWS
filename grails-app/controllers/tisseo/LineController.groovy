package tisseo

import groovy.json.JsonSlurper

import java.text.SimpleDateFormat

import org.springframework.dao.DataIntegrityViolationException

class LineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	static apiKey = "a03561f2fd10641d96fb8188d209414d8"
	static bbox = "1.461167%2C43.554955%2C1.482711%2C43.5738"

    def index() {
        redirect(action: "list", params: params)
    }
	
	def retrieveJSON(String apiName, String parameters) {
			JsonSlurper jsonSlurper =  new JsonSlurper()
			def rawText = new URL( "http://pt.data.tisseo.fr/${apiName}?format=json" + ( parameters == "" ? "" : "&" ) + "${parameters}&key=${apiKey}" ).text
//			println "http://pt.data.tisseo.fr/${apiName}?format=json" + ( parameters == "" ? "" : "&" ) + "${parameters}&key=${apiKey}"
			jsonSlurper.parseText( rawText )
	}
	
	def updateStopAreasAndLineApi() {
		def apiNameStopArea = "stopAreasList"
		def json = null
		def alreadyRetrieved = ExpirationDates.findByApiName( apiNameStopArea ) != null
		if( !alreadyRetrieved || ExpirationDates.findByApiName( apiNameStopArea ).expirationDate < new Date() ) {
		
			json = retrieveJSON( apiNameStopArea, "displayLines=1&bbox=${bbox}" )
			
			def extractedDate = new SimpleDateFormat("yyyy-MM-dd h:m").parse( json.expirationDate )
			if( alreadyRetrieved ) {
				def newDate = ExpirationDates.findByApiName( apiNameStopArea )
				newDate.expirationDate = extractedDate
				newDate.save()
			} else {
				new ExpirationDates( apiName: apiNameStopArea, expirationDate: extractedDate ).save()
			}
		}
		
//		println "json = " + json
		if ( json != null ) {
			json = json.stopAreas.stopArea
			json.each {
				def stopArea = StopArea.findByStopAreaId( it.id )
				if( stopArea == null ) {
					stopArea = new StopArea( name: it.name,
										 	 stopAreaId: it.id)
//						println "J'ajoute la nouvelle stopArea ${line.shortName}"
				} else {
					stopArea.name = it.name
					stopArea.stopAreaId = it.id
//						println "Je modifie la stopArea ${line.shortName}"
				}
				
				it.line.each {
					def lineJson = retrieveJSON( "linesList", "lineId=${it.id}" )
//					println "lineJson = " + lineJson
					if( lineJson != null ) {
						lineJson = lineJson.lines.line[0]
						def line = Line.findByLineId( it.id )
						if( line == null ) {
							line = new Line( name: lineJson.name,
											 shortName: lineJson.shortName,
											 lineId: lineJson.id,
											 transportMode: lineJson.transportMode?.name,
											 likesCount: 0,
											 dislikesCount: 0 )
	//						println "J'ajoute la nouvelle ligne ${line.shortName}"
						} else {
							line.name = lineJson.name
							line.shortName = lineJson.shortName
							line.lineId = lineJson.id
							line.transportMode = lineJson.transportMode?.name
	//						println "Je modifie la ligne ${line.shortName}"
						}
						line.save()
						stopArea.addToLines( line )
					}
				}
				stopArea.save()
			}
		}
	}
	
	def updateStopPointsApi() {
		def apiNameStopArea = "stopPointsList"
		def json = null
		def alreadyRetrieved = ExpirationDates.findByApiName( apiNameStopArea ) != null
		if( !alreadyRetrieved || ExpirationDates.findByApiName( apiNameStopArea ).expirationDate < new Date() ) {
		
			json = retrieveJSON( apiNameStopArea, "displayLines=1&bbox=${bbox}" )
			
			def extractedDate = new SimpleDateFormat("yyyy-MM-dd h:m").parse( json.expirationDate )
			if( alreadyRetrieved ) {
				def newDate = ExpirationDates.findByApiName( apiNameStopArea )
				newDate.expirationDate = extractedDate
				newDate.save()
			} else {
				new ExpirationDates( apiName: apiNameStopArea, expirationDate: extractedDate ).save()
			}
		}
		
		if( json != null ) {
			json = json.physicalStops.physicalStop
			json.each {
				def stopPoint = StopPoint.findByStopPointId( it.id )
				if ( stopPoint == null ) {
					stopPoint = new StopPoint()
				}
				stopPoint.name = it.name
				stopPoint.stopPointId = it.id
				stopPoint.nextBus = new Date()
				it.destinations.each {
					def destination = Destination.findByDestinationId( it.id )
					if( destination == null ) {
						destination = new Destination()
					}
					destination.name = it.name
					destination.destinationId = it.id
					it.line.each {
						println "Line : " + Line.findByLineId( it.id )
						destination.addToTerminusOf( Line.findByLineId( it.id ) )
						stopPoint.addToOnLines( Line.findByLineId( it.id ) )
					}
					destination.save()
					stopPoint.addToDestinations( destination )
				}
				stopPoint.stopArea = StopArea.findByStopAreaId( it.stopArea.id )
				stopPoint.save()
			}
		}
	}
	
	def updateApiInformationIfNecessary() {
		updateStopAreasAndLineApi()
		updateStopPointsApi()
	}
	
	def updateNextBus( Line line ) {
		def stopPoints = StopPoint.createCriteria().listDistinct {
			onLines {
				eq( "lineId", line.lineId )
			}
		}
		stopPoints.each {
			def json = retrieveJSON( "departureBoard", "stopPointId=${it.stopPointId}&lineId=${line.lineId}&number=1" )
			if ( json != null ) {
				json = json.departures.departure[0]
				it.nextBus = new SimpleDateFormat("yyyy-MM-dd h:m").parse( json.dateTime )
				it.save()
			}
		}
		
		stopPoints
	}

    def list(Integer max) {
		updateApiInformationIfNecessary()
		
        params.max = Math.min(max ?: 100, 100)
        [lineInstanceList: Line.list(params), lineInstanceTotal: Line.count()]
    }

    def create() {
		[lineInstance: new Line(params)]
    }

    def save() {
        def lineInstance = new Line(params)
        if (!lineInstance.save(flush: true)) {
            render(view: "create", model: [lineInstance: lineInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'line.label', default: 'Line'), lineInstance.lineId])
        redirect(action: "show", id: lineInstance.lineId)
    }

    def show(Long id) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }
		def stopPoints = updateNextBus( lineInstance )
		if(!stopPoints) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'stopPoint.label', default: 'StopPoint'), id])
            redirect(action: "list")
            return
		}

        [lineInstance: lineInstance, stopPoints: stopPoints]
    }

    def edit(Long id) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        [lineInstance: lineInstance]
    }

    def update(Long id, Long version) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (lineInstance.version > version) {
                lineInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'line.label', default: 'Line')] as Object[],
                          "Another user has updated this Line while you were editing")
                render(view: "edit", model: [lineInstance: lineInstance])
                return
            }
        }

        lineInstance.properties = params

        if (!lineInstance.save(flush: true)) {
            render(view: "edit", model: [lineInstance: lineInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'line.label', default: 'Line'), lineInstance.lineId])
        redirect(action: "show", id: lineInstance.lineId)
    }

    def delete(Long id) {
        def lineInstance = Line.get(id)
        if (!lineInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
            return
        }

        try {
            lineInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'line.label', default: 'Line'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def like(Long id) {
		def lineToUpdate = Line.get(id)
		lineToUpdate.likesCount++
		lineToUpdate.save()
		redirect(action: "list", id: id)
	}
	
	def dislike(Long id) {
		def lineToUpdate = Line.get(id)
		lineToUpdate.dislikesCount++
		lineToUpdate.save()
		redirect(action: "list", id: id)
	}
}
