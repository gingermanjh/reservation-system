package kjh.reservation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "kjh.reservation.controller" })
public class ServletContextConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Value("${spring.resources.static-locations}")
	private String staticResourceLocation;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// static file들 사용하기 위함(프로젝트 폴더 바깥의 resources - /webapp/WEB-INF/views아래에
		// jsp넣는 방법 안 쓸때)
		registry.addResourceHandler("/resources/**").addResourceLocations(staticResourceLocation);

		// /src/main/resources의 파일쓸 때
		// registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}
