package com.uniscale.account;

import com.uniscale.sdk.Platform;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uniscaledemo.account.Patterns;
import uniscaledemo.account.account.UserFull;

@SpringBootApplication
public class AccountApplication {

  public static void main(String[] args) {
    // Create in memory cache of users
    var users = new HashMap<UUID, UserFull>();

    // Initialize the Uniscale session
    var sessionFuture =
        Platform.builder()
            .withInterceptors(
                i ->
                    i.interceptRequest(
                            Patterns.account.getOrRegister.allRequestUsages,
                            Patterns.account.getOrRegister.handleDirect(
                                (input, ctx) -> {
                                  // Get the existing user if there is a match on user handle
                                  var existingUser =
                                      users.values().stream()
                                          .filter(u -> Objects.equals(u.getHandle(), input))
                                          .findFirst();
                                  if (existingUser.isPresent()) return existingUser.get();

                                  // Create a new user and return it
                                  var newUserIdentifier = UUID.randomUUID();
                                  users.put(
                                      newUserIdentifier,
                                      UserFull.builder()
                                          .userIdentifier(newUserIdentifier)
                                          .handle("input")
                                          .build());
                                  return users.get(newUserIdentifier);
                                }))
                        .interceptRequest(
                            Patterns.account.lookupUsers.allRequestUsages,
                            Patterns.account.lookupUsers.handleDirect(
                                (input, ctx) ->
                                    users.values().stream()
                                        .filter(u -> input.contains(u.getUserIdentifier()))
                                        .toList()))
                        .interceptRequest(
                            Patterns.account.searchAllUsers.allRequestUsages,
                            Patterns.account.searchAllUsers.handleDirect(
                                (input, ctx) -> {
                                  return users.values().stream()
                                      .filter(
                                          u ->
                                              u.getHandle()
                                                  .toLowerCase()
                                                  .contains(input.toLowerCase()))
                                      .toList();
                                })))
            .build();

    var session = sessionFuture.join();

    // Accept gateway stuff from api.

    SpringApplication.run(com.uniscale.account.AccountApplication.class, args);
  }
}
