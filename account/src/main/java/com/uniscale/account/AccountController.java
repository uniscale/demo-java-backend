package com.uniscale.account;

import com.uniscale.sdk.core.PlatformSession;
import com.uniscale.sdk.core.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
  private final PlatformSession session;

  public AccountController(PlatformSession session) {
    this.session = session;
  }

  @PostMapping("/api/service-to-module/{featureId}")
  public Result<Object> handleGatewayRequest(
      @PathVariable("featureId") String featureId, @RequestBody String gatewayRequest) {
    return session.acceptGatewayRequest(gatewayRequest).join();
  }
}
