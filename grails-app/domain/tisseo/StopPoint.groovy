package tisseo

class StopPoint {
	
	String name
	String stopPointId
	
	static hasMany = [destinations: Destination]
	static belongsTo = [stopArea: StopArea]
	
    static constraints = {
    }
}
