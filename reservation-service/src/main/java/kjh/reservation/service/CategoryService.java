package kjh.reservation.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kjh.reservation.dao.CategoryDao;
import kjh.reservation.domain.Category;

@Service
public class CategoryService {
	
	@Autowired
	CategoryDao categoryDao;
	
	@Transactional(readOnly = true)
	public Category get(Integer id) {
		return categoryDao.selectById(id);
	}
	
	@Transactional(readOnly = true)
	public Collection<Category> getAll() {
		return categoryDao.getAll();
	}
	
	@Transactional(readOnly = false)
	public Category addCategory(Category category) {
		Integer insert = categoryDao.insert(category);
		category.setId(insert);
		return category;
	}
	
	@Transactional(readOnly = false)
	public int delete(Integer id) {
		return categoryDao.delete(id);
	}
	
	@Transactional(readOnly = false)
	public int update(Category category) {
		return categoryDao.update(category);
	}

}
