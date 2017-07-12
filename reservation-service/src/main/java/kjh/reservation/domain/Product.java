package kjh.reservation.domain;

import java.util.Date;

public class Product {
	private Integer id;
	private String name;
	private Integer categoryId;
	private String description;
	private Date salesStart;
	private Date salesEnd;
	private Integer salesFlag;
	private String event;
	private Date createDate;
	private Date modifyDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getSalesStart() {
		return salesStart;
	}
	public void setSalesStart(Date salesStart) {
		this.salesStart = salesStart;
	}
	public Date getSalesEnd() {
		return salesEnd;
	}
	public void setSalesEnd(Date salesEnd) {
		this.salesEnd = salesEnd;
	}
	public Integer getSalesFlag() {
		return salesFlag;
	}
	public void setSalesFlag(Integer salesFlag) {
		this.salesFlag = salesFlag;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
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
		return "Product [id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", description=" + description
				+ ", salesStart=" + salesStart + ", salesEnd=" + salesEnd + ", salesFlag=" + salesFlag + ", event="
				+ event + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}
	
}
