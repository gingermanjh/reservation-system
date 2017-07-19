package kjh.reservation.dao;

public class ProductSqls {
	final static int LIMIT_ROW_NUM = 4;
	final static String SELECT_BY_CATEGORY_FIRST = "select id, name, description from product where category_id = :category_id limit :rowNum";
	final static String SELECT_ALL_FIRST = "select id, name, description from product order by id asc limit :rowNum";
	final static String COUNT_BY_CATEGORY = "select count(*) count from product where category_id = :category_id";
	static final String COUNT_ALL = "select count(*) count from product";
	static final String SELECT_BY_CATEGORY = "select id, name, description from product where category_id = :category_id limit :offset, :rowNum";
	final static String SELECT_ALL = "select id, name, description from product order by id asc limit :offset, :rowNum";
//	static final String SELECT_BY_ID = "select id, name, description from product where id = :id";
	
	// detail controller
	static final String SELECT_BY_ID_DETAIL = "select id, name, description, sales_flag, event from product where id = :id";
	
	static final String SELECT_PLACE_NAME_BY_ID = "select place_name from display_info where product_id =:product_id";
}
