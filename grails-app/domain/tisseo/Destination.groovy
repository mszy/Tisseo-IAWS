package tisseo

class Destination {
	String name
	String destinationId
	
	static hasMany = [terminusOf: Line]
	
    static constraints = {
    }
}
