package pl.app.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@AllArgsConstructor
class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().disable();
		http.authorizeRequests()
				.antMatchers("/jpr-bank-app/**").authenticated()
				.and()
				.formLogin().defaultSuccessUrl("/jpr-bank-app");
	}
}
