
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper

import org.json.simple.JSONArray
import tisseo.Place

class BootStrap {

    def init = { servletContext ->
		
		JsonSlurper json =  new JsonSlurper()
		def rawText = new URL( "http://pt.data.tisseo.fr/linesList?format=json&key=a03561f2fd10641d96fb8188d209414d8" ).text
		def jsonObj = json.parseText( rawText )
		
		def lines = jsonObj.lines.line
		lines.each { 
			println it.name + " --/-- " + it.shortName + " --/-- " + it.id
			new Place( name: it.name, shortName: it.shortName, id: it.id).save() }
		
    }
    def destroy = {
    }
}
