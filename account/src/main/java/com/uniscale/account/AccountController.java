package com.uniscale.account;

import com.uniscale.sdk.core.PlatformSession;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
  private final PlatformSession session;

  public AccountController(PlatformSession session) {
    this.session = session;
  }

  @PostMapping("/api/service-to-module/{featureId}")
  @CrossOrigin(origins = "*")
  public String handleGatewayRequest(
      @PathVariable("featureId") String featureId, @RequestBody String gatewayRequest) {
    return session.acceptGatewayRequest(gatewayRequest).join().toJson();
  }
}
