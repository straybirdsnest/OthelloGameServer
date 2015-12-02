package otakuplus.straybird.othellogameserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/partials/home").setViewName("home");
        registry.addViewController("/partials/register").setViewName("register");
        registry.addViewController("/partials/login").setViewName("login");
        registry.addViewController("/partials/user/profile").setViewName("updateProfile");
        //registry.addViewController("/signin").setViewName("signin");
    }

}
