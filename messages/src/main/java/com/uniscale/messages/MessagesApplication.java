package com.uniscale.messages;

import com.uniscale.sdk.Platform;
import com.uniscale.sdk.core.PlatformSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagesApplication {

  public static void main(String[] args) {
    SpringApplication.run(com.uniscale.messages.MessagesApplication.class, args);
  }

  @Bean
  public PlatformSession platformSession() {
    // Set up our session
    // Register the timeline functionality interceptor with access to the
    // forwarding session
    var sessionFuture =
        Platform.builder().withInterceptors(TimelineInterceptors::registerInterceptors).build();

    return sessionFuture.join();
  }
}
