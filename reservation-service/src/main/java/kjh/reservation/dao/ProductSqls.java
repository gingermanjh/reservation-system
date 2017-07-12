package kjh.reservation.dao;

public class ProductSqls {
	final static int LIMIT_ROW_NUM = 4;
	final static String SELECT_BY_CATEGORY_FIRST = "select name, description from product where category_id = :category_id limit :rowNum";
	final static String SELECT_ALL_FIRST = "select name, description from product order by id asc limit :rowNum";
	final static String COUNT_BY_CATEGORY = "select count(*) count from product where category_id = :category_id";
	static final String COUNT_ALL = "select count(*) count from product";
	static final String SELECT_BY_CATEGORY = "select name, description from product where category_id = :category_id limit :offset, :rowNum";
	final static String SELECT_ALL = "select name, description from product order by id asc limit :offset, :rowNum";
}
