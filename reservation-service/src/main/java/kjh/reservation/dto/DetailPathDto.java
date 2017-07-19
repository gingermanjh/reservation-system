package kjh.reservation.dto;

public class DetailPathDto {
	private String name;
	private String placeStreet;
	private String placeLot;
	private String placeName;
	private String tel;
	private String homepage;
	private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlaceStreet() {
		return placeStreet;
	}
	public void setPlaceStreet(String placeStreet) {
		this.placeStreet = placeStreet;
	}
	public String getPlaceLot() {
		return placeLot;
	}
	public void setPlaceLot(String placeLot) {
		this.placeLot = placeLot;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "DetailPathDto [name=" + name + ", placeStreet=" + placeStreet + ", placeLot=" + placeLot
				+ ", placeName=" + placeName + ", tel=" + tel + ", homepage=" + homepage + ", email=" + email + "]";
	}
	
}
