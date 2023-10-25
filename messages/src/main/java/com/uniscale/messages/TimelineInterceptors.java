package com.uniscale.messages;

import com.uniscale.sdk.core.PlatformInterceptorBuilder;
import com.uniscale.sdk.core.Result;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;
import uniscaledemo.messages.Patterns;
import uniscaledemo.messages.messages.MessageFull;
import uniscaledemo.messages.messages.UserTag;
import uniscaledemo.messages_1_0.ErrorCodes;

public class TimelineInterceptors {
  private static final HashMap<UUID, MessageFull> messages = new HashMap<>();

  public static void registerInterceptors(PlatformInterceptorBuilder builder) {
    var patterns = Patterns.messages;
    builder
        // Register an interceptor for the message feature SendMessage
        .interceptMessage(
            // Specify the AllMessageUsages pattern so that the implementation
            // picks up features for all use case instances this feature
            // is used in
            patterns.sendMessage.allMessageUsages,
            // Define a handler for the feature
            patterns.sendMessage.handle(
                (input, ctx) -> {
                  if (input.getMessage().length() < 3 || input.getMessage().length() > 60)
                    return Result.badRequest(ErrorCodes.messages.invalidMessageLength);
                  var msg =
                      MessageFull.builder()
                          .messageIdentifier(UUID.randomUUID())
                          .message(input.getMessage())
                          .created(UserTag.builder().by(input.getBy()).at(Instant.now()).build())
                          .build();
                  messages.put(msg.getMessageIdentifier(), msg);
                  return Result.ok();
                }))
        // Register an interceptor for the request/response feature GetMessageList
        .interceptRequest(
            patterns.getMessageList.allRequestUsages,
            patterns.getMessageList.handleDirect(
                (input, ctx) ->
                    messages.values().stream()
                        .sorted(Comparator.comparing(p -> p.getCreated().getAt()))
                        .limit(50)
                        .toList()));
  }
}
