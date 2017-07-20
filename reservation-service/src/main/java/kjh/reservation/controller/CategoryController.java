package kjh.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kjh.reservation.domain.Category;
import kjh.reservation.service.CategoryService;

@Controller
@RequestMapping("/categorys")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String main(HttpServletRequest request) {
		List<Category> list = categoryService.getAll();
		request.setAttribute("listInfo", list);
		return "categoryPage";
	}

	@RequestMapping(method = { RequestMethod.PUT })
	public String update(@ModelAttribute Category categoryParam) {
		Category category = new Category();
		category.setId(categoryParam.getId());
		category.setName(categoryParam.getName());
		int updated = categoryService.update(category);
		if (updated != 0) {
			System.out.println("updated");
		} else {
			System.out.println("not updated");
		}
		return "redirect:/categorys";
	}

	@PostMapping
	public String create(@ModelAttribute Category categoryParam) {
		Category category = new Category();
		category.setName(categoryParam.getName());
		// Category added = categoryService.addCategory(category);
		categoryService.addCategory(category);
		return "redirect:/categorys";
	}
}
