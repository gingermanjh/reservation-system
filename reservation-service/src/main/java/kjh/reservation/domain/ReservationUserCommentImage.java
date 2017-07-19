package kjh.reservation.domain;

public class ReservationUserCommentImage {
	private Integer id;
	private Integer ReservationUserCommentId;
	private Integer fileId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReservationUserCommentId() {
		return ReservationUserCommentId;
	}
	public void setReservationUserCommentId(Integer reservationUserCommentId) {
		ReservationUserCommentId = reservationUserCommentId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	@Override
	public String toString() {
		return "ReservationUserCommentImage [id=" + id + ", ReservationUserCommentId=" + ReservationUserCommentId
				+ ", fileId=" + fileId + "]";
	}
	
}
