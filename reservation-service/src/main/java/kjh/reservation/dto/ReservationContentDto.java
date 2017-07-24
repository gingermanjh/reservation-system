package kjh.reservation.dto;

import java.util.Date;
import java.util.List;

import kjh.reservation.domain.ProductPrice;
import kjh.reservation.domain.Users;

public class ReservationContentDto {
	private String name;
	private String placeStreet;
	private String placeLot;
	private Date displayStart;
	private Date displayEnd;
	private String observationTime;
	private List<ProductPrice> priceList;
	private Users users;
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
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
	public Date getDisplayStart() {
		return displayStart;
	}
	public void setDisplayStart(Date displayStart) {
		this.displayStart = displayStart;
	}
	public Date getDisplayEnd() {
		return displayEnd;
	}
	public void setDisplayEnd(Date displayEnd) {
		this.displayEnd = displayEnd;
	}
	public String getObservationTime() {
		return observationTime;
	}
	public void setObservationTime(String observationTime) {
		this.observationTime = observationTime;
	}
	public List<ProductPrice> getPriceList() {
		return priceList;
	}
	public void setPriceList(List<ProductPrice> priceList) {
		this.priceList = priceList;
	}
	@Override
	public String toString() {
		return "ReservationContentDto [name=" + name + ", placeStreet=" + placeStreet + ", placeLot=" + placeLot
				+ ", displayStart=" + displayStart + ", displayEnd=" + displayEnd + ", observationTime="
				+ observationTime + ", priceList=" + priceList + ", users=" + users + "]";
	}
	
}
