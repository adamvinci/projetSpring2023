package be.vinci.ipl.vsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ExecuteApplication {

  /**
   * Entry point of the order execute application
   *
   * @param args Command line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(ExecuteApplication.class, args);
  }

}
