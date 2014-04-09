package velo

class LineVelo {
	
	String name
	String shortName
	String lineId
	
	Integer likesCount
	Integer dislikesCount

	static constraints = {
		likesCount min: 0
		dislikesCount min: 0
	}
	
	String toString() {
		shortName
	}
}
