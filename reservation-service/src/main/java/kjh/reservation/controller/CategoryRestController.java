package kjh.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kjh.reservation.service.CategoryService;

@RestController
@RequestMapping("/categorys/api")
public class CategoryRestController {

	private final CategoryService categoryService;

	@Autowired
	public CategoryRestController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// @GetMapping("/list")
	// public Collection<Category> list(HttpServletRequest request) {
	// return categoryService.getAll();
	// }

	@DeleteMapping("/remove/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		categoryService.delete(id);
	}
}
