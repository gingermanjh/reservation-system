package kjh.reservation.domain;

public class ProductImage {
	private Integer id;
	private Integer product_id;
	private Integer file_id;
	private Integer type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public Integer getFile_id() {
		return file_id;
	}
	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ProductImage [id=" + id + ", product_id=" + product_id + ", file_id=" + file_id + ", type=" + type
				+ "]";
	}
	
}
