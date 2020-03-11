package gov.usgs.wma.waterdata;

public class ResultObject {
	// Technically does not need to pass anything to another lambda right now... but
	// it may be expanded later? Only using it to LOG successful/failed update attempt...
	// Could probably just use the RequestObject for this?
	private String uniqueId;

	// look to dvstat repo to see counts, basically verifying the number of records grabbed vs number inserted
	private String status;


	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
