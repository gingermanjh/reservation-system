package kjh.reservation.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kjh.reservation.dao.ProductDao;
import kjh.reservation.domain.DisplayInfo;
import kjh.reservation.domain.Product;
import kjh.reservation.dto.CountParam;
import kjh.reservation.dto.MainProductListDto;

@Service
public class ProductService {
	
	@Autowired
	ProductDao productDao;
	
	@Transactional(readOnly = true)
	public Collection<MainProductListDto> getListByCategory(Integer id) {
		Collection<MainProductListDto> mpdList = new ArrayList<MainProductListDto>();
		Collection<Product> pdList = productDao.selectByCategory(id);
		
		for(Product pd : pdList) {
			MainProductListDto mpd = new MainProductListDto();
			DisplayInfo di = productDao.selectByProductId(pd.getId());
			mpd.setId(pd.getId());
			mpd.setName(pd.getName());
			mpd.setDescription(pd.getDescription());
			mpd.setPlaceName(di.getPlaceName());
			mpdList.add(mpd);
		}
		return mpdList;
	}

	@Transactional(readOnly = true)
	public CountParam getCount(Integer id) {
		return productDao.countByCategory(id);
	}

	@Transactional(readOnly = true)
	public Collection<MainProductListDto> getMoreProduct(Integer id, Integer offset) {
		Collection<MainProductListDto> mpdList = new ArrayList<MainProductListDto>();
		Collection<Product> pdList = productDao.getMoreProduct(id, offset);
		
		for(Product pd : pdList) {
			MainProductListDto mpd = new MainProductListDto();
			DisplayInfo di = productDao.selectByProductId(pd.getId());
			mpd.setId(pd.getId());
			mpd.setName(pd.getName());
			mpd.setDescription(pd.getDescription());
			mpd.setPlaceName(di.getPlaceName());
			mpdList.add(mpd);
		}
		
		return mpdList;
	}

	@Transactional(readOnly = true)
	public Product getProduct(Integer id) {
		return productDao.getProduct(id);
	}
	
}
