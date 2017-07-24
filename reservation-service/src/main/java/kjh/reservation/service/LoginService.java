package kjh.reservation.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import kjh.reservation.dao.LoginDao;
import kjh.reservation.domain.Users;
import kjh.reservation.dto.NaverAccessToken;
import kjh.reservation.dto.NaverLoginUser;
import kjh.reservation.dto.NaverLoginUserResult;

@Service
public class LoginService {
	
	@Autowired
	private LoginDao loginDao;

	public String check(HttpSession session) {
		if (session.getAttribute("state") != null) {
			return "myreservation";
		} else {
			return "loginPage";
		}
	}

	public String naverLogin(HttpSession session) throws UnsupportedEncodingException {
		return getNaverLoginUrl(session);
	}

	public Integer getNaverToken(HttpServletRequest request) throws UnsupportedEncodingException {

		String apiURL = getNaverAccessTokenUrl(request);
		String accessToken = "";

		RestTemplate restTemplate = new RestTemplate();
		NaverAccessToken nat = restTemplate.getForObject(apiURL, NaverAccessToken.class);
		accessToken = nat.getAccessToken();

		String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", header);
		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<NaverLoginUserResult> responseEntity = restTemplate.exchange(
				"https://openapi.naver.com/v1/nid/me", HttpMethod.GET, entity,
				new ParameterizedTypeReference<NaverLoginUserResult>() {
				});
		NaverLoginUser response = responseEntity.getBody().getResponse();
		
		Users user = new Users(response.getName(), response.getEmail(), response.getNickname(), response.getEmail());

		Users chk = loginDao.selectByEmail(response.getEmail());
		if(chk == null) {
			return loginDao.insert(user);
		} else {
			System.out.println("이미 있는 유저");
			return chk.getId();
		}
	}

	private String getNaverLoginUrl(HttpSession session) throws UnsupportedEncodingException {
		String clientId = "xG6IjQpP8c8ePRi3w__i";// 애플리케이션 클라이언트 아이디값";
		String redirectURI = URLEncoder.encode("http://localhost:8080/member/oauth2c", "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state;
		session.setAttribute("state", state);
		return apiURL;
	}

	private String getNaverAccessTokenUrl(HttpServletRequest request) throws UnsupportedEncodingException {
		String clientId = "xG6IjQpP8c8ePRi3w__i";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "h73kuGutrv";// 애플리케이션 클라이언트 시크릿값";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		String redirectURI = URLEncoder.encode("http://localhost:8080/member/oauth2c", "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		return apiURL;
	}

}
