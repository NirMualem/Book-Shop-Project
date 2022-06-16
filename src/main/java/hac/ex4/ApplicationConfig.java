package hac.ex4;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security class
 * for ADMIN by username "admin" password "password"
 * and "USER" by username "user1" / "user2" /"user3" password "user"
 */
@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    /**
     * configure function
     * @param auth check Auth for user
     * @throws Exception if failed
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth
                .inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("password")).roles("ADMIN").and()
                .withUser("user1").password(encoder.encode("user")).roles("USER").and()
                .withUser("user2").password(encoder.encode("user")).roles("USER").and()
                .withUser("user3").password(encoder.encode("user")).roles("USER");
    }

    /**
     * @param http http
     * @throws Exception when access dined 403
     * set all access in shop
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/confirmOrder").hasAnyRole("ADMIN", "USER")
                .antMatchers("/loginForPay").hasAnyRole("ADMIN", "USER")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403.html");
    }

}
