package gov.usgs.wma.waterdata;

public class ResultObject {
	// Technically does not need to pass anything to another lambda right now... but
	// it may be expanded later? Only using it to LOG successful/failed update attempt...
	// Could probably just use the RequestObject for this?
	private String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
