package kjh.reservation.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kjh.reservation.dao.ProductDao;
import kjh.reservation.domain.Product;
import kjh.reservation.dto.CountParam;

@Service
public class ProductService {
	
	@Autowired
	ProductDao productDao;
	
	@Transactional(readOnly = true)
	public Collection<Product> get(Integer id) {
		return productDao.selectByCategory(id);
	}

	@Transactional(readOnly = true)
	public CountParam getCount(Integer id) {
		return productDao.countByCategory(id);
	}

	@Transactional(readOnly = true)
	public Collection<Product> getMoreProduct(Integer id, Integer offset) {
		return productDao.getMoreProduct(id, offset);
	}
	
}
