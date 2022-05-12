package enums;

//All the constants will reside here
public enum APIResources {
	
	AddPlaceAPi("maps/api/place/add/json"),
	GetPlaceAPi("maps/api/place/get/json"),
	DeletePlaceAPi("maps/api/place/delete/json");


	private String resource;
	APIResources(String resource) {
		this.resource=resource;
	}
	public String getResource() {
		return resource;
	}

}

