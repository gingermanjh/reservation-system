package kjh.reservation.dto;

import java.math.BigDecimal;

public class ReviewStatDto {
	private Integer count;
	private BigDecimal score;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "ReviewStatDto [count=" + count + ", score=" + score + "]";
	}
	
}
