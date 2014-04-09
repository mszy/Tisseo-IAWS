package tisseo

class StopPoint {
	
	String name
	String stopPointId
	
	Date nextBus
	
	static hasMany = [destinations: Destination, onLines: Line]
	static belongsTo = [stopArea: StopArea]
	
    static constraints = {
    }
	
	String toString() {
		name + " : " + stopPointId
	}
}
