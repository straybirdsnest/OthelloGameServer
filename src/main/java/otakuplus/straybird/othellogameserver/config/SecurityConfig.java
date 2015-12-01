package otakuplus.straybird.othellogameserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import otakuplus.straybird.othellogameserver.security.CsrfHeaderFilter;
import otakuplus.straybird.othellogameserver.services.OthelloUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OthelloUserDetailsService othelloUserDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(othelloUserDetailsService);
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/api/csrftoken").permitAll()
                .antMatchers("/api/authorization").permitAll()
                .antMatchers("/api/crsftoken").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/signin").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/api/**").hasAnyRole("USER","ADMIN").anyRequest().authenticated()
        // @formatter:on

                .and().
                formLogin()
                .loginPage("/api/signin")
                .loginProcessingUrl("/api/signin")
                .permitAll();

        // CSRF tokens handling
        http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
        http.csrf().csrfTokenRepository(csrfTokenRepository());

        http.logout().logoutUrl("/logout");
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}

