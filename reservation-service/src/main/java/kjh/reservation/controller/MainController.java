package kjh.reservation.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kjh.reservation.domain.Category;
import kjh.reservation.domain.Product;
import kjh.reservation.dto.CountParam;
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
	public Collection<Category> getCategory() {
		return categoryService.getAll();
	}

	@GetMapping("/myreservation")
	public String myReserve() {
		return "loginPage";
	}
	
//	@GetMapping("/detail")
//	public String check() {
//		return "myreservation";
//	}
	
	@GetMapping("/check")
	public String check(HttpSession session) {
		if("ok".equals(session.getAttribute("loginInfo"))) {
			return "myreservation";
		}
		return "redirect:/myreservation";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(name="name") String id, @RequestParam(name="passwd") String passwd, HttpServletRequest request) {
		HttpSession session = request.getSession();
		 if("kang".equals(id) && "1234".equals(passwd)) {
			session.setAttribute("loginInfo", "ok");
			return "myreservation";
		} else {
			return "redirect:/myreservation";
		}
		
	}

	@GetMapping("/main/api/categorys/{id}")
	@ResponseBody
	public Collection<Product> productByFilter(@PathVariable Integer id) {
		return productService.get(id);
	}

	@GetMapping("/main/api/categorys/count/{id}")
	@ResponseBody
	public CountParam countByFilter(@PathVariable Integer id) {
		return productService.getCount(id);
	}

	@GetMapping("/main/api/categorys/{id}/offset/{offset}")
	@ResponseBody
	public Collection<Product> getMoreProduct(@PathVariable Integer id, @PathVariable Integer offset) {
		return productService.getMoreProduct(id, offset);
	}
}
