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

import kjh.reservation.domain.FileDomain;
import kjh.reservation.domain.ProductImage;

@Repository
public class FileDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<FileDomain> rowMapperFile = BeanPropertyRowMapper.newInstance(FileDomain.class);
	private RowMapper<ProductImage> rowMapperProductImage = BeanPropertyRowMapper.newInstance(ProductImage.class);
	
	public FileDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
																
		this.insertAction = new SimpleJdbcInsert(dataSource) 
				.withTableName("file")
				.usingGeneratedKeyColumns("id");
	}

	public Integer insert(FileDomain fileDomain) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(fileDomain);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public FileDomain getFile(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(FileSqls.SELECT_IMG_BY_FILE_ID, params, rowMapperFile);
	}
	
	public ProductImage getFileId(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.queryForObject(FileSqls.SELECT_THUMB_FILE_ID, params, rowMapperProductImage);
	}

	public List<ProductImage> getFileIdList(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.query(FileSqls.SELECT_FILE_ID, params, rowMapperProductImage);
	}

}
