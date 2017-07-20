package kjh.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kjh.reservation.domain.Product;
import kjh.reservation.dto.DetailPathDto;
import kjh.reservation.dto.ReviewContentDto;
import kjh.reservation.service.ProductService;
import kjh.reservation.service.ReviewService;

@Controller
@RequestMapping("/detail")
public class DetailController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/{id}")
	public String detailPage() {
		return "detail";
	}
	
	@GetMapping("/api/{id}")
	@ResponseBody
	public Product getProductDetail(@PathVariable Integer id) {
		return productService.getProduct(id);
	}
	
	@GetMapping("/api/comments/{id}")
	@ResponseBody
	public List<ReviewContentDto> getUserComment(@PathVariable Integer id) {
		return reviewService.getComment(id);
	}
	
	@GetMapping("/api/info/{id}")
	@ResponseBody
	public String getInfo(@PathVariable Integer id) {
		return null;
	}
	
	@GetMapping("/api/path/{id}")
	@ResponseBody
	public DetailPathDto getPath(@PathVariable Integer id) {
		return reviewService.getPath(id);
	}

}
