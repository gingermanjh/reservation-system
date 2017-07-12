package kjh.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "kjh.reservation.dao", "kjh.reservation.service" })
@Import({ DBConfig.class }) // DB
public class RootApplicationContextConfig {

	// service나 dao관련(비즈니스) 설정
	// 데이터소스나 트랜잭션 관련 설정
	// 따로 설정하면 프레젠테이션 레이어가 바뀌어도 그대로 유지가능

}
