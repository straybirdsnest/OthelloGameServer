package otakuplus.straybird.othellogameserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import otakuplus.straybird.othellogameserver.services.OthelloUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private OthelloUserDetailsService othelloUserDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(othelloUserDetailsService);
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/api/**").hasRole("USER").anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/api/logout/success").permitAll()
                .and()
                .formLogin().loginProcessingUrl("/api/login").failureUrl("/api/login/failure").permitAll();
        // @formatter:on
    }
}

