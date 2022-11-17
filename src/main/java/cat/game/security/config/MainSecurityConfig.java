package cat.game.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.game.security.jwt.JwtEntryPoint;
import cat.game.security.jwt.JwtFilter;
import cat.game.security.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class MainSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailService userDetailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtEntryPoint jwtEntryPoint;
	
	@Autowired
	JwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests().antMatchers("/auth/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	
}
