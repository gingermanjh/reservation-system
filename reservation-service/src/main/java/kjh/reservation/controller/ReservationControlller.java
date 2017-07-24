package kjh.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import kjh.reservation.domain.ReservationInfo;
import kjh.reservation.dto.ReservationContentDto;
import kjh.reservation.service.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationControlller {
	
	@Autowired
	private ReservationService reservationService;
	
	@GetMapping
	public String MyReservationPage() {
		return "myreservation";
	}
	
	@GetMapping("/list")
	public String reservationList(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		return "myreservation";
	}
	
	@GetMapping("/book/{id}")
	public String reservationBook(@PathVariable Integer id) {
		return "reserve";
	}
	
	@GetMapping("/info/{id}")
	@ResponseBody
	public ReservationContentDto getInfo(@PathVariable Integer id, HttpSession session) {
		Integer userId= (Integer) session.getAttribute("userId");
		return reservationService.getInfo(id, userId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ReservationInfo submitReservation(@RequestBody ReservationInfo receivedData) {
		return reservationService.submitReservation(receivedData);
	}
}
