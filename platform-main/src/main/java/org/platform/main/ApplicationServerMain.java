package org.platform.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author lou
 */
@SpringBootApplication(scanBasePackages = {"com.platform.server.*"})
//@ImportResource(locations={"classpath:application.properties"})
public class ApplicationServerMain {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationServerMain.class, args);
	}
}
