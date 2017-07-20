package kjh.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kjh.reservation.domain.Category;
import kjh.reservation.dto.CountParam;
import kjh.reservation.dto.MainProductListDto;
import kjh.reservation.service.CategoryService;
import kjh.reservation.service.ProductService;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@GetMapping
	public String mainPage() {
		return "mainpage";
	}

	@GetMapping("/main/api/categorys")
	@ResponseBody
	public List<Category> getCategory() {
		return categoryService.getAll();
	}

	@GetMapping("/main/api/categorys/{id}")
	@ResponseBody
	public List<MainProductListDto> productByFilter(@PathVariable Integer id) {
		return productService.getListByCategory(id);
	}

	@GetMapping("/main/api/categorys/count/{id}")
	@ResponseBody
	public CountParam countByFilter(@PathVariable Integer id) {
		return productService.getCount(id);
	}

	@GetMapping("/main/api/categorys/{id}/offset/{offset}")
	@ResponseBody
	public List<MainProductListDto> getMoreProduct(@PathVariable Integer id, @PathVariable Integer offset) {
		return productService.getMoreProduct(id, offset);
	}

}
