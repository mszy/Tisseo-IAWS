package tisseo

import java.beans.MetaData;
import java.text.SimpleDateFormat

import grails.artefact.ApiDelegate;
import groovy.json.JsonSlurper;

import org.springframework.dao.DataIntegrityViolationException

import com.sun.org.apache.xml.internal.serialize.LineSeparator;

class LineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		def alreadyRetrieved = ExpirationDates.findByApiName( "linesList" ) == null
		if( alreadyRetrieved || ExpirationDates.findByApiName( "linesList" ).expirationDate < new Date() ) {
			JsonSlurper json =  new JsonSlurper()
			def rawText = new URL( "http://pt.data.tisseo.fr/linesList?format=json&key=a03561f2fd10641d96fb8188d209414d8" ).text
			def jsonObj = json.parseText( rawText )
			
			def extractedDate = new SimpleDateFormat("yyyy-MM-dd h:m").parse( jsonObj.expirationDate )
			if( alreadyRetrieved ) {
				def newDate = ExpirationDates.findByApiName( "linesList" )
				newDate.expirationDate = extractedDate
				newDate.save()
			} else {
				new ExpirationDates( apiName: "linesList", expirationDate = extractedDate ).save()
			}
		
			def extractedLines = jsonObj.lines.line
			extractedLines.each {
				def line = Line.findByLineId( it.id )
				println line
				println " /// " + it.name + " --/-- " + it.shortName + " --/-- " + it.id + " --/-- " + it.transportMode?.name
				if( line == null ) {
					line = new Line( name: it.name,
						  	  		 shortName: it.shortName,
									 lineId: it.id,
									 transportMode: it.transportMode?.name,
									 likesCount: 0,
									 dislikesCount: 0 )
					println "J'ajoute la nouvelle ligne ${line.shortName}"
				} else {
					line.name = it.name
					line.shortName = it.shortName
					line.lineId = it.lineId
					line.transportMode = it.transportMode?.name
					line.likesCount = 2
					line.dislikesCount = 1
					println "Je modifie la ligne ${line.shortName}"
				}
				line.save()
			}
		} else {
			println "No need te retrieve datas"
		}
		
        params.max = Math.min(max ?: 10, 100)
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

        [lineInstance: lineInstance]
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
}
