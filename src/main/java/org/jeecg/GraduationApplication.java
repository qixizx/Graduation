package org.jeecg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@EnableSwagger2
@SpringBootApplication
public class GraduationApplication {

  public static void main(String[] args) throws UnknownHostException {
    //System.setProperty("spring.devtools.restart.enabled", "true");

    ConfigurableApplicationContext application = SpringApplication.run(GraduationApplication.class, args);
    Environment env = application.getEnvironment();
    String ip = InetAddress.getLocalHost().getHostAddress();
    String port = env.getProperty("server.port");
    String path = env.getProperty("server.servlet.context-path");
    log.info("\n----------------------------------------------------------\n\t" +
        "Application tpsoft-Boot is running! Access URLs:\n\t" +
        "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
        "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
        "swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
        "Doc: \t\thttp://" + ip + ":" + port + path + "/doc.html\n" +
        "----------------------------------------------------------");

  }
  
}