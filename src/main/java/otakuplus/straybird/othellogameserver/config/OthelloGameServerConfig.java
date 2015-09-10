package otakuplus.straybird.othellogameserver.config;

import otakuplus.straybird.othellogameserver.network.OthelloGameSocketIOServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OthelloGameServerConfig {

	@Bean(initMethod = "startServer", destroyMethod = "stopServer")
	public OthelloGameSocketIOServer othelloGameSocketIOServer() {
		return new OthelloGameSocketIOServer(configuration());
	}

	@Bean
	public com.corundumstudio.socketio.Configuration configuration() {
		return new com.corundumstudio.socketio.Configuration();
	}

}
