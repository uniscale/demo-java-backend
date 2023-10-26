# Demo solution Java backend

## How to run

Get maven details for `ActorCharacter-Messagethreads` from `Demo company`'s solution in Uniscale and paste those to `~/.m2/settings.xml`.

 - Then you can run in IDE of choice
 - or in command line:
   - `mvn -pl account -s settings.xml -am spring-boot:run`
   - `mvn -pl messages -s settings.xml -am spring-boot:run`
     - Add `-s settings.xml` if you want to use custom settings file instead of `~/.m2/settings.xml`

These will run each service on their own servers.

## How to use

Send backend action request to `/api/service-to-module/{featureId}`. Port 5298 for account service and port 5192 for messages.