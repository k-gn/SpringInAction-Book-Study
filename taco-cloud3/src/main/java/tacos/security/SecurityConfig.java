package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 스프링 부트는 자동으로 설정한 DB에 대한 DataSource 빈을 구성해준다.
	// JavaConfig 클래스로 dataSource 빈 생성을 하지 않아도 예약된 키에 프로퍼티 설정을 하면 자동으로 jpa 설정과 dataSource 빈을 구성해준다
	// 내장 DB는 아예 자동으로 되고 그 외 DB는 yml이나 properties 파일에 datasource를 설정해야한다.  yml이나 properties 파일에 설정 안하면 @bean으로 만들어줘야 함
	@Autowired
    DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { // Http 보안을 구성하는 메서드
		http
			.authorizeRequests()
				.antMatchers("/design", "/orders").hasRole("USER")
				.antMatchers("/", "/**").permitAll()
			.and()
//				.httpBasic(); //  Basic Authentication을 사용 (요청해더에 username, password를 실어 보내면 브라우저 또는 서버가 그 값을 읽어서 인증)
				.formLogin()
					.loginPage("/login") // 커스텀 로그인 페이지 요청url
			.and()
				.logout()
					.logoutSuccessUrl("/")
			.and()
				.csrf();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 사용자 인증 정보를 구성하는 메서드
		
		auth
			.userDetailsService(userDetailsService) // 커스텀 사용자 스토어
			.passwordEncoder(encoder());
		
		
				/* LDAP 기반 사용자 스토어 
			auth
				.ldapAuthentication()
				.userSearchBase("ou=people") // 사용자를 찾기위한 기준점을 제공
				.userSearchFilter("(uid={0})")
				.groupSearchBase("ou=groups") // 그룹을 찾기위한 기준점을 제공
				.groupSearchFilter("member={0}")
				.contextSource() // LDAP 서버의 위치 구성가능
				.root("dc=tacocloud,dc=com") // 내장 LDAP 서버의 루트 경로 지정
				.ldif("classpath:users.ldif")
				.and()
				.passwordCompare()
				.passwordEncoder(new BCryptPasswordEncoder())
				.passwordAttribute("userPasscode");*/
		
		/* JDBC 기반 사용자 스토어 예제 코드입니다.
			auth
				.jdbcAuthentication()
				.dataSource(dataSource)
//				.usersByUsernameQuery( // 없어도 기본적으로 시큐리티가 생성하는 SELECT SQL을 사용한다. (사용자와 권한 테이블 자체는 직접 만들어야한다)
//					"select username, password, enabled from users " +
//					"where username=?")
//				.authoritiesByUsernameQuery(
//					"select username, authority from authorities " +
//					"where username=?")
				.passwordEncoder(new NoEncodingPasswordEncoder());*/
		
		/* # 인메모리 기반 사용자 스토어 예제 코드
		auth.inMemoryAuthentication() // 사용자 정보를 메모리에서 유지 관리 (테스트 목적이나 간단한 애플리케이션에는 편리하나 사용자 정보의 추가나 변경이 어렵다)
			.withUser("user1")
			.password("{noop}password1")
			.authorities("ROLE_USER") // = roles("USER")
			.and()
			.withUser("user2")
			.password("{noop}password2")
			.authorities("ROLE_USER");*/
	}
}
