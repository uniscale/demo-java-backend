package com.uniscale.messages;

import com.uniscale.sdk.Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessagesApplication {

  public static void main(String[] args) {
    // Set up our session
    // Register the timeline functionality interceptor with access to the
    // forwarding session
    var sessionFuture =
        Platform.builder().withInterceptors(TimelineInterceptors::registerInterceptors).build();

    var session = sessionFuture.join();

    // Accept gateway stuff from api.

    SpringApplication.run(com.uniscale.messages.MessagesApplication.class, args);
  }
}
