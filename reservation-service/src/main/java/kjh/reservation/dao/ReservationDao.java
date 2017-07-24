package kjh.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kjh.reservation.domain.DisplayInfo;
import kjh.reservation.domain.Product;
import kjh.reservation.domain.ProductPrice;
import kjh.reservation.domain.ReservationInfo;
import kjh.reservation.domain.Users;

@Repository
public class ReservationDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Product> rowMapperProduct = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<DisplayInfo> rowMapperDisplayInfo = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<ProductPrice> rowMapperProductPrice = BeanPropertyRowMapper.newInstance(ProductPrice.class);
	private RowMapper<Users> rowMapperUsers = BeanPropertyRowMapper.newInstance(Users.class);
	private RowMapper<ReservationInfo> rowMapperReservationInfo = BeanPropertyRowMapper.newInstance(ReservationInfo.class);

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource) 
				.withTableName("reservation_info")
				.usingGeneratedKeyColumns("id");
	}
	
	public Product getInfoFromProduct(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(ReservationSqls.SELECT_INFO_FROM_PRODUCT, params, rowMapperProduct);
	}
	
	public DisplayInfo getInfoFromDisplayInfo(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(ReservationSqls.SELECT_INFO_FROM_DISPLAY_INFO, params, rowMapperDisplayInfo);
	}

	public List<ProductPrice> getPriceList(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.query(ReservationSqls.SELECT_INFO_FROM_PRODUCT_PRICE, params, rowMapperProductPrice);
	}

	public Users getInfoFromUsers(Integer userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);
		return jdbc.queryForObject(ReservationSqls.SELECT_INFO_FROM_USERS, params, rowMapperUsers);
	}

	public Integer insert(ReservationInfo reservationInfo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public ReservationInfo selectByid(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(ReservationSqls.SELECT_BY_ID, params, rowMapperReservationInfo);
	}

}
