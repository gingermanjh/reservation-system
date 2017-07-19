package kjh.reservation.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ReservationUserComment {
	private Integer id;
	private Integer productId;
	private Integer userId;
	private BigDecimal score;
	private String comment;
	private Date create_date;
	private Date modify_date;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	@Override
	public String toString() {
		return "ReservationUserComment [id=" + id + ", productId=" + productId + ", userId=" + userId + ", score="
				+ score + ", comment=" + comment + ", create_date=" + create_date + ", modify_date=" + modify_date
				+ "]";
	}
	
	
}
