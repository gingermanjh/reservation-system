package kjh.reservation.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kjh.reservation.dao.ReservationDao;
import kjh.reservation.domain.DisplayInfo;
import kjh.reservation.domain.Product;
import kjh.reservation.domain.ProductPrice;
import kjh.reservation.domain.ReservationInfo;
import kjh.reservation.domain.Users;
import kjh.reservation.dto.ReservationContentDto;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationDao reservationDao;

	public ReservationContentDto getInfo(Integer id, Integer userId) {
		ReservationContentDto reservationContent = new ReservationContentDto();
		Product product = reservationDao.getInfoFromProduct(id);
		DisplayInfo displayInfo = reservationDao.getInfoFromDisplayInfo(id);
		List<ProductPrice> priceList = reservationDao.getPriceList(id);
		if(userId != null) {
			Users user = reservationDao.getInfoFromUsers(userId);
			reservationContent.setUsers(user);
		}
		reservationContent.setName(product.getName());
		reservationContent.setPlaceStreet(displayInfo.getPlaceStreet());
		reservationContent.setPlaceLot(displayInfo.getPlaceLot());
		reservationContent.setDisplayStart(displayInfo.getDisplayStart());
		reservationContent.setDisplayEnd(displayInfo.getDisplayEnd());
		reservationContent.setObservationTime(displayInfo.getObservationTime());
		reservationContent.setPriceList(priceList);
		return reservationContent;
	}

	public ReservationInfo submitReservation(ReservationInfo receivedData) {
		ReservationInfo reservationInfo = new ReservationInfo();
		Date now = new Date();
		reservationInfo.setProductId(receivedData.getProductId());
		reservationInfo.setUserId(receivedData.getUserId());
		reservationInfo.setGeneralTicketCount(receivedData.getGeneralTicketCount());
		reservationInfo.setYouthTicketCount(receivedData.getYouthTicketCount());
		reservationInfo.setChildTicketCount(receivedData.getChildTicketCount());
		reservationInfo.setReservationName(receivedData.getReservationName());
		reservationInfo.setReservationTel(receivedData.getReservationTel());
		reservationInfo.setReservationEmail(receivedData.getReservationEmail());
		reservationInfo.setReservationDate(now);
		reservationInfo.setCreateDate(now);
		reservationInfo.setModifyDate(now);
		Integer id = reservationDao.insert(reservationInfo);
		return reservationDao.selectByid(id);
	}
	
}
