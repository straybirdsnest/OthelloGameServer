package otakuplus.straybird.othellogameserver;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class OthelloGameApp implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {

    public static final String CFG_SOCKETIO_HOST = "othellogameserver.socketio.host";
    public static final String CFG_SOCKETIO_PORT = "othellogameserver.socketio.port";

    private static final Logger logger = LoggerFactory.getLogger(OthelloGameApp.class);

	@Autowired
	private SocketIOServer socketIOServer;

	public static void main(String[] args) {
		SpringApplication.run(OthelloGameApp.class, args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environment env = applicationContext.getEnvironment();
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            logger.info("Spring 容器初始化完毕，开始启动SocketIO服务器");
            socketIOServer.start();
        } else if (event instanceof ContextStartedEvent) {
            logger.info("======== ContextStartedEvent ========");
        } else if (event instanceof ContextStoppedEvent) {
            logger.info("======== ContextStoppedEvent ========");
        } else if (event instanceof ContextClosedEvent) {
            logger.info("Spring 容器已经关闭，开始关闭SocketIO服务器");
            socketIOServer.stop();
        }
	}
}
