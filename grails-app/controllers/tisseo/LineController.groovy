package tisseo

import java.beans.MetaData;
import java.text.SimpleDateFormat

import groovy.json.JsonSlurper;

import org.springframework.dao.DataIntegrityViolationException

import com.sun.org.apache.xml.internal.serialize.LineSeparator;

class LineController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		if( MetaDataAPI.instance.expirationDateLines == null || MetaDataAPI.instance.expirationDateLines < new Date() ) {
			JsonSlurper json =  new JsonSlurper()
			def rawText = new URL( "http://pt.data.tisseo.fr/linesList?format=json&key=a03561f2fd10641d96fb8188d209414d8" ).text
			def jsonObj = json.parseText( rawText )
			
			MetaDataAPI.instance.expirationDateLines = new SimpleDateFormat("yyyy-MM-dd h:m").parse( jsonObj.expirationDate )
		
			def extractedLines = jsonObj.lines.line
			extractedLines.each {
				//println it.name + " --/-- " + it.shortName + " --/-- " + it.id
				new Line( name: it.name, shortName: it.shortName, id: it.id, likesCount: 0, dislikesCount: 0 ).save()
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

        flash.message = message(code: 'default.created.message', args: [message(code: 'line.label', default: 'Line'), lineInstance.id])
        redirect(action: "show", id: lineInstance.id)
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

        flash.message = message(code: 'default.updated.message', args: [message(code: 'line.label', default: 'Line'), lineInstance.id])
        redirect(action: "show", id: lineInstance.id)
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
