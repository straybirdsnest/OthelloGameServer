package otakuplus.straybird.othellogameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import otakuplus.straybird.othellogameserver.config.MvcConfig;

@SpringBootApplication
@Import({ MvcConfig.class })
public class OthelloGameApp {

	public static void main(String[] args) {
		SpringApplication.run(OthelloGameApp.class, args);
	}
}
