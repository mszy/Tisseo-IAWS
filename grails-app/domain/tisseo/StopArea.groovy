package tisseo

class StopArea {

	String name
	String stopAreaId
	
	static hasMany = [lines: Line]
	
    static constraints = {
    }
}
