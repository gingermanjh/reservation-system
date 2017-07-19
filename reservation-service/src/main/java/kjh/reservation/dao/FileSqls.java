package kjh.reservation.dao;

public class FileSqls {
	static final String SELECT_THUMB_FILE_ID = "select file_id from product_image where type=1 and product_id=:product_id";
	static final String SELECT_FILE_ID = "select file_id from product_image where product_id=:product_id order by type desc";
	static final String SELECT_IMG_BY_FILE_ID = "select save_file_name, content_type from file where id=:id";
	static final String SELECT_COMMENT_FILE_ID = "select file_id from reservation_user_comment_image where reservation_user_comment_id=:id";
}
