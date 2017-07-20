package kjh.reservation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kjh.reservation.dao.ReviewDao;
import kjh.reservation.domain.DisplayInfo;
import kjh.reservation.domain.FileDomain;
import kjh.reservation.domain.ReservationUserComment;
import kjh.reservation.domain.ReservationUserCommentImage;
import kjh.reservation.dto.DetailPathDto;
import kjh.reservation.dto.ReviewContentDto;
import kjh.reservation.dto.ReviewStatDto;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDao reviewDao;

	@Transactional(readOnly = true)
	public List<ReviewContentDto> getComment(Integer id) {
		List<ReservationUserComment> rucList = reviewDao.getComment(id);
		List<ReviewContentDto> reviewList = new ArrayList<ReviewContentDto>();
		String productName = reviewDao.getProductName(id);
		for(ReservationUserComment ruc : rucList) {
			ReviewContentDto rcd = new ReviewContentDto();
			ArrayList<FileDomain> fdList = new ArrayList<FileDomain>();
			String userName = reviewDao.getUserName(ruc.getUserId());
			List<ReservationUserCommentImage> ruciList = reviewDao.getFileId(ruc.getId());
			for(ReservationUserCommentImage ruci : ruciList) {
				FileDomain fd = reviewDao.getSavedFileName(ruci.getFileId());
				fdList.add(fd);
			}
			Calendar cal = Calendar.getInstance();
			Date date = ruc.getModify_date();
			cal.setTime(date);
			cal.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
			String parsedDate = cal.get(Calendar.YEAR) +"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.DATE)+".";
			
			rcd.setProductName(productName);
			rcd.setCommentId(ruc.getId());
			rcd.setModify_date(parsedDate);
			rcd.setComment(ruc.getComment());
			rcd.setScore(ruc.getScore());
			rcd.setUserName(userName);
			rcd.setImgList(fdList);
			reviewList.add(rcd);
		}
		return reviewList;
	}

	@Transactional(readOnly = true)
	public ReviewStatDto getStats(Integer id) {
		List<ReservationUserComment> rucList = reviewDao.getAllComments(id);
		BigDecimal bd = new BigDecimal(0.0);
		ReviewStatDto rsd = new ReviewStatDto();
		for(ReservationUserComment ruc : rucList) {
			bd = bd.add(ruc.getScore());
		}
		if(rucList.size() == 0) {
			rsd.setScore(bd);
		} else {
			rsd.setScore(bd.divide(new BigDecimal(rucList.size())));
		}
		rsd.setCount(rucList.size());
		
		return rsd;
	}

	@Transactional(readOnly = true)
	public DetailPathDto getPath(Integer id) {
		DetailPathDto detailPathDto = new DetailPathDto();
		String productName = reviewDao.getProductName(id);
		DisplayInfo displayInfo = reviewDao.getDispalyInfo(id);
		detailPathDto.setName(productName);
		detailPathDto.setPlaceStreet(displayInfo.getPlaceStreet());
		detailPathDto.setPlaceLot(displayInfo.getPlaceLot());
		detailPathDto.setPlaceName(displayInfo.getPlaceName());
		detailPathDto.setTel(displayInfo.getTel());
		detailPathDto.setHomepage(displayInfo.getHomepage());
		detailPathDto.setEmail(displayInfo.getEmail());
		return detailPathDto;
	}
}
