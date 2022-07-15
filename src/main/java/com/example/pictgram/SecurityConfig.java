package com.example.pictgram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.pictgram.filter.FormAuthenticationProvider;
import com.example.pictgram.repository.UserRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	protected static Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	UserDetailsService service;
	
	@Autowired
	private FormAuthenticationProvider authenticationProvider;
	
	private static final String[] URLSOTHER = {
			"/css/**", "/images/**", "/scripts/**" //, "/h2-console/**"
	};
	
	//認証からの除外
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(URLSOTHER);
	}
	
	//認証の設定
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		/* 記述誤りの箇所：改行により修飾が違うアドレスに適用
		http.authorizeRequests().antMatchers("/login", "/logout-complete", "users/new", "/user")
			.permitAll().anyRequest().authenticated()
			//ログアウト処理
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout-complete").clearAuthentication(true)
			.deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll()
			.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			//form
			.and().formLogin().loginPage("/login").defaultSuccessUrl("/topics").failureUrl("/login-failure")
			.permitAll();
			*/
		http.authorizeRequests().antMatchers("/", "/login", "/logout-complete", "/users/new", "/user", "/h2-console/**").permitAll()//特に制限なし
			.anyRequest().authenticated()//ログイン済みに制限
			//ログアウト処理
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logout-complete").clearAuthentication(true)
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true).permitAll().and().csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			//form
			.and().formLogin().loginPage("/login").defaultSuccessUrl("/topics").failureUrl("/login-failure")
			.permitAll();
		//追記(for h2-console-usage-only
		//http.headers().frameOptions().disable();
			//@formatter:on
		//Configメソッドの記述は、フォーマット機能をオフにすること！！
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
