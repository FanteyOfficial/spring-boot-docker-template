package it.paleocapa.mastroiannim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication(scanBasePackages={"it.paleocapa.mastroiannim"})
public class DockerServer {

	private static final Logger LOG = LoggerFactory.getLogger(DockerServer.class);

    public static void main(String[] args) {
        SpringApplication.run(DockerServer.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        LOG.info("hello world, I have just started up");
    }
}
