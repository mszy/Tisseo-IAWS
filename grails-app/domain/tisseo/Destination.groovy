package tisseo

class Destination {

	static hasMany = [terminusOf: Line]
	
    static constraints = {
    }
}
