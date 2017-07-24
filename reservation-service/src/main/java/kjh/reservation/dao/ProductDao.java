package kjh.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kjh.reservation.domain.DisplayInfo;
import kjh.reservation.domain.Product;
import kjh.reservation.dto.CountParam;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<DisplayInfo> rowMapperMpd = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<CountParam> rowMapperCount = BeanPropertyRowMapper.newInstance(CountParam.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<Product> selectByCategory(Integer categoryId) {
		if (categoryId == 1) {
			// Map<String, Object> params = Collections.emptyMap();
			Map<String, Object> params = new HashMap<>();
			params.put("rowNum", ProductSqls.LIMIT_ROW_NUM);
			return jdbc.query(ProductSqls.SELECT_ALL_FIRST, params, rowMapper);
		} else {
			Map<String, Object> params = new HashMap<>();
			params.put("category_id", categoryId);
			params.put("rowNum", ProductSqls.LIMIT_ROW_NUM);
			return jdbc.query(ProductSqls.SELECT_BY_CATEGORY_FIRST, params, rowMapper);
		}
	}

	public List<Product> getAll() {
		Map<String, Object> params = new HashMap<>();
		params.put("rowNum", ProductSqls.LIMIT_ROW_NUM);
		return jdbc.query(ProductSqls.SELECT_ALL_FIRST, params, rowMapper);
	}

	public CountParam countByCategory(Integer categoryId) {
		try {
			if (categoryId == 1) {
				Map<String, Object> params = Collections.emptyMap();
				return jdbc.queryForObject(ProductSqls.COUNT_ALL, params, rowMapperCount);
			} else {
				Map<String, Object> params = new HashMap<>();
				params.put("category_id", categoryId);
				return jdbc.queryForObject(ProductSqls.COUNT_BY_CATEGORY, params, rowMapperCount);
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public List<Product> getMoreProduct(Integer categoryId, Integer offset) {
		if (categoryId == 1) {
			Map<String, Object> params = new HashMap<>();
			params.put("offset", offset * ProductSqls.LIMIT_ROW_NUM);
			params.put("rowNum", ProductSqls.LIMIT_ROW_NUM);
			return jdbc.query(ProductSqls.SELECT_ALL, params, rowMapper);
		}
		Map<String, Object> params = new HashMap<>();
		params.put("category_id", categoryId);
		params.put("offset", offset * ProductSqls.LIMIT_ROW_NUM);
		params.put("rowNum", ProductSqls.LIMIT_ROW_NUM);
		return jdbc.query(ProductSqls.SELECT_BY_CATEGORY, params, rowMapper);
	}

	public Product getProduct(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(ProductSqls.SELECT_BY_ID_DETAIL, params, rowMapper);
	}

	public DisplayInfo selectByProductId(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.queryForObject(ProductSqls.SELECT_PLACE_NAME_BY_ID, params, rowMapperMpd);
		
	}

}
