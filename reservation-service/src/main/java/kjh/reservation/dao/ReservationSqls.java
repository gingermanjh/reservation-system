package kjh.reservation.dao;

public class ReservationSqls {
	
	static final String SELECT_INFO_FROM_PRODUCT = "select name from product where id=:id";
	static final String SELECT_INFO_FROM_DISPLAY_INFO = "select place_street, place_lot, display_start, display_end, observation_time from display_info where id =:id";
	static final String SELECT_INFO_FROM_PRODUCT_PRICE = "select price_type, price, discount_rate from product_price where product_id=:product_id";
	static final String SELECT_INFO_FROM_USERS = "select id, username, tel, email from users where id=:id";
	static final String SELECT_BY_ID = "select * from reservation_info where id=:id";

}
