package spring.emailverification.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;
import spring.emailverification.appuser.AppUserService;

/**
 * 
 * @Configuration: Spring Configuration annotation indicates that the class
 *                 has @Bean definition methods. So Spring container can process
 *                 the class and generate Spring Beans to be used in the
 *                 application. Spring @Configuration annotation allows us to
 *                 use annotations for dependency injection.
 * 
 * @bean: Spring @Bean annotation tells that a method produces a bean to be
 *        managed by the Spring container. It is a method-level annotation.
 *        During Java configuration ( @Configuration ), the method is executed
 *        and its return value is registered as a bean within a BeanFactory.
 * 
 */

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final AppUserService appUserService;
   private final BCryptPasswordEncoder bCryptPasswordEncoder;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().authorizeRequests().antMatchers("/api/v*/registration/**").permitAll().anyRequest()
            .authenticated().and().formLogin();
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.authenticationProvider(daoAuthenticationProvider());
   }

   @Bean
   public DaoAuthenticationProvider daoAuthenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setPasswordEncoder(bCryptPasswordEncoder);
      provider.setUserDetailsService(appUserService);
      return provider;
   }

}
