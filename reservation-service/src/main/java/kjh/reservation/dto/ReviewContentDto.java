package kjh.reservation.dto;

import java.math.BigDecimal;
import java.util.List;

import kjh.reservation.domain.FileDomain;

public class ReviewContentDto {
	
	private Integer commentId;
	private String productName;
	private String userName;
	private String comment;
	private BigDecimal score; 
	private String modify_date;
	private List<FileDomain> imgList;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public List<FileDomain> getImgList() {
		return imgList;
	}
	public void setImgList(List<FileDomain> imgList) {
		this.imgList = imgList;
	}
	@Override
	public String toString() {
		return "ReviewContentDto [commentId=" + commentId + ", productName=" + productName + ", userName=" + userName
				+ ", comment=" + comment + ", score=" + score + ", modify_date=" + modify_date + ", imgList=" + imgList
				+ "]";
	}
	
}
