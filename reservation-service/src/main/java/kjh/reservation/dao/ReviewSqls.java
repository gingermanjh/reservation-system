package kjh.reservation.dao;

public class ReviewSqls {

	static final String SELECT_COMMENT_BY_ID_FIRST = "select id, product_id, user_id, score, comment, modify_date from reservation_user_comment where product_id =:product_id limit 3";
	static final String SELECT_COMMENT_BY_ID = "select user_id, score, comment, modify_date from reservation_user_comment where product_id =:product_id";
	static final String SELECT_DISPLAYINFO_BY_ID = "select place_name, place_lot, place_street, tel, homepage, email from display_info where product_id =:product_id";
	static final String SELECT_PRODUCTNAME = "select name from product where id =:id";
	static final String SELECT_USERNAME = "select username from users where id=:id";

}
