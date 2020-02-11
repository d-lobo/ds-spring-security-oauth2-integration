package de.lobo.spring.security.config;

import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.spring.security.api.BearerSecurityContextRepository;
import com.auth0.spring.security.api.JwtAuthenticationEntryPoint;
import com.auth0.spring.security.api.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
  static final String PRIVILEGED_API = "/privileged";
  static final String USER_API = "/user";
  static final String ADMIN_API = "/admin";

  @Value("${auth0.audience}")
  private String audience;
  @Value("${auth0.issuer}")
  private String issuer;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .anonymous().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .securityContext()
        .securityContextRepository(new BearerSecurityContextRepository())
        .and()
        .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, USER_API).authenticated()
        .antMatchers(HttpMethod.POST, USER_API).authenticated()
        .antMatchers(HttpMethod.PUT, USER_API).authenticated()
        .antMatchers(HttpMethod.DELETE, USER_API).authenticated()
         //assume JWT permissions are interpreted roles
        .antMatchers(HttpMethod.GET, PRIVILEGED_API).hasAnyAuthority("privileged", "admin")
        .antMatchers(HttpMethod.POST, PRIVILEGED_API).hasAnyAuthority("privileged", "admin")
        .antMatchers(HttpMethod.PUT, PRIVILEGED_API).hasAnyAuthority("privileged", "admin")
        .antMatchers(HttpMethod.DELETE, PRIVILEGED_API).hasAnyAuthority("privileged", "admin")
        .antMatchers(HttpMethod.GET, ADMIN_API).hasAuthority("admin")
        .antMatchers(HttpMethod.POST, ADMIN_API).hasAuthority("admin")
        .antMatchers(HttpMethod.PUT, ADMIN_API).hasAuthority("admin")
        .antMatchers(HttpMethod.DELETE, ADMIN_API).hasAuthority("admin");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    var jwkProvider = new JwkProviderBuilder(issuer).build();
    auth.authenticationProvider(new JwtAuthenticationProvider(jwkProvider, issuer, audience));
  }
}
