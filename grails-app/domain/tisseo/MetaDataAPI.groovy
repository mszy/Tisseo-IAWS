package tisseo

class MetaDataAPI {
	Date expirationDateLines
	
	private static final INSTANCE = new MetaDataAPI()
	static getInstance() { return INSTANCE }
	
	private MetaDataAPI() {}
	
    static constraints = {
    }
}
