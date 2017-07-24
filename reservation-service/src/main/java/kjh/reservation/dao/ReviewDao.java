package kjh.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kjh.reservation.domain.DisplayInfo;
import kjh.reservation.domain.FileDomain;
import kjh.reservation.domain.ReservationUserComment;
import kjh.reservation.domain.ReservationUserCommentImage;

@Repository
public class ReviewDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationUserComment> rowMapper = BeanPropertyRowMapper.newInstance(ReservationUserComment.class);
	private RowMapper<DisplayInfo> rowMapperDetailPath = BeanPropertyRowMapper.newInstance(DisplayInfo.class);
	private RowMapper<ReservationUserCommentImage> rowMapperCommentImage = BeanPropertyRowMapper.newInstance(ReservationUserCommentImage.class);
	private RowMapper<FileDomain> rowMapperFile = BeanPropertyRowMapper.newInstance(FileDomain.class);
	
	public ReviewDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}


	public List<ReservationUserComment> getComment(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.query(ReviewSqls.SELECT_COMMENT_BY_ID_FIRST, params, rowMapper);
	}


	public List<ReservationUserComment> getAllComments(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.query(ReviewSqls.SELECT_COMMENT_BY_ID, params, rowMapper);
	}


	public DisplayInfo getDispalyInfo(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("product_id", id);
		return jdbc.queryForObject(ReviewSqls.SELECT_DISPLAYINFO_BY_ID, params, rowMapperDetailPath);
	}
	
	public String getProductName(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(ReviewSqls.SELECT_PRODUCTNAME, params, String.class);
	}
	
	public String getUserName(Integer userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", userId);
		return jdbc.queryForObject(ReviewSqls.SELECT_USERNAME, params, String.class);
	}


	public List<ReservationUserCommentImage> getFileId(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.query(FileSqls.SELECT_COMMENT_FILE_ID, params, rowMapperCommentImage);
	}


	public FileDomain getSavedFileName(Integer fileId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", fileId);
		return jdbc.queryForObject(FileSqls.SELECT_IMG_BY_FILE_ID, params, rowMapperFile);
	}

}
