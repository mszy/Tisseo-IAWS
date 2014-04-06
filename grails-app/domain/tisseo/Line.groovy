package tisseo

class Line {
	String name
	String shortName
	String lineId
	String transportMode
	
	Integer likesCount
	Integer dislikesCount

    static constraints = {
		likesCount min: 0
		dislikesCount min: 0
		transportMode nullable: true  
	}
	
	String toString() {
		shortName
	}
}
