package io.pivotal.gemfire.sdg.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring/config/sdg-server-cache.xml")
public class SdgServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdgServerApplication.class, args);
	}
}
