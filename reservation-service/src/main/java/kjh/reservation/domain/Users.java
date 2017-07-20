package kjh.reservation.domain;

import java.util.Date;

public class Users {
	private Integer id;
	private String username;
	private String email;
	private String tel;
	private String nickname;
	private String snsId;
	private String snsType;
	private String snsProfile;
	private Integer adminFlag;
	private Date createDate;
	private Date modifyDate;
	
	public Users() {
		
	}
	public Users(String username, String email, String nickname, String snsId) {
		this.username = username;
		this.email = email;
		this.nickname = nickname;
		this.snsId = snsId;
		tel = "123-1234";
		snsType = "naver";
		snsProfile = "haha";
		adminFlag = 0;
		createDate = new Date();
		modifyDate = new Date();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSnsId() {
		return snsId;
	}
	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}
	public String getSnsType() {
		return snsType;
	}
	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}
	public String getSnsProfile() {
		return snsProfile;
	}
	public void setSnsProfile(String snsProfile) {
		this.snsProfile = snsProfile;
	}
	public Integer getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(Integer adminFlag) {
		this.adminFlag = adminFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", email=" + email + ", tel=" + tel + ", nickname="
				+ nickname + ", snsId=" + snsId + ", snsType=" + snsType + ", snsProfile=" + snsProfile
				+ ", adminFlag=" + adminFlag + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}
	
}
