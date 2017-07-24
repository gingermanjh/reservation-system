package kjh.reservation.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kjh.reservation.service.LoginService;

@Controller
@RequestMapping("/member")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping
	public String loginPage(HttpSession session) {
		return loginService.check(session);
	}
	
	@GetMapping("/oauth2c")
	public String naverLoginCallback(HttpServletRequest request) throws UnsupportedEncodingException {
		Integer userId = loginService.getNaverToken(request);
		request.getSession().setAttribute("userId", userId);
		return "redirect:/reservations";
	}
	
	@GetMapping("/naverlogin")
	public String naverLogin(HttpSession session) throws UnsupportedEncodingException {
		String url = loginService.naverLogin(session);
		return "redirect:"+url;
	} 
	
}
