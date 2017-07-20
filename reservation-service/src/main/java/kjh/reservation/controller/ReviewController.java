package kjh.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kjh.reservation.dto.ReviewContentDto;
import kjh.reservation.dto.ReviewStatDto;
import kjh.reservation.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService commentService;
	
	@GetMapping("/{id}")
	public String reviewPage() {
		return "review";
	}
	
	@GetMapping("/api/{id}")
	@ResponseBody
	public ReviewStatDto getStats(@PathVariable Integer id) {
		return commentService.getStats(id);
	}
	
	@GetMapping("/api/comments/{id}")
	@ResponseBody
	public List<ReviewContentDto> getUserComment(@PathVariable Integer id) {
		return commentService.getComment(id);
	}

}
