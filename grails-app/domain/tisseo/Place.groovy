package tisseo

import groovy.json.JsonSlurper

class Place {
	String name
	String shortName
	
	
    static constraints = {
    }
	
	String toString() {
		shortName
	}
}
