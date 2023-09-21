package ma.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.payment.auth.auth.ApplicationUser;
import ma.payment.auth.auth.ApplicationUserDaoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static ma.payment.auth.Security.UserRoles.ADMIN;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication {
	@Value("admin.default.username") String email;
	@Value("admin.default.password") String password;
	private final ApplicationUserDaoService userDaoService;
	public static void main(String[] args) {

		SpringApplication.run(BackendApplication.class, args);
	}




	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

	@EventListener(ContextRefreshedEvent.class)
	public void seeders(){
		userDaoService.selectApplicationUserByUsername("admin@admin.com").or(() ->  userDaoService.saveApplicationUser( new ApplicationUser(
					ADMIN.getGrantedAuthorities(),
					"123456",
					"admin@admin.com",
					true,
					true,
					true,
					true
			)).map(e-> null));


	}

}
