# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework.
 ```java
public final class SimpleApplication {

    public static void main(String[] args) throws Exception {
        RequestMethod get = new GetMethod();
	
        List<ConditionalRespondent> respondents = new ArrayList<>();
        ConditionalRespondent helloRespondent = new HttpRespondent("hello/{id:int}", get, new HelloRespondent());
        respondents.add(helloRespondent);

        List<ConditionalRequestFilter> filters = new ArrayList<>();
        ConditionalRequestFilter authorizationFilter = new HttpRequestFilter("*", new AnyRequestMethodRule(),
           new AuthorizationFilter());
        filters.add(authorizationFilter);

        Application application = new HttpApplication(new AllowAllCors(), respondents, filters);
        Connector connection = new RequestResponseConnection(new HttpOneProtocol(), application);
        Server server = new Server(8080, 5000, connection);
        server.start();
    }
}
```
That's all. Now you have fully working http server which handles get request nad authorize it as follows:
```java
public final class AuthorizationFilter implements RequestFilter {

    private static final String SECRET_TOKEN = "token";
    private static final String AUTHORIZATION_HEADER = "Authorization";

   @Override
   public Response response(Request request) {
    	Response response;
    	if (request.hasHeader(AUTHORIZATION_HEADER)) {
    	    String token = request.header(AUTHORIZATION_HEADER);
    	    boolean valid = token.equals(SECRET_TOKEN);
    	    response = valid ? new OkResponse() : new ForbiddenResponse();
    	} else {
    	    response = new ForbiddenResponse();
    	}
    	return response;
    }
    
}
```
```java
public final class HelloRespondent implements Respondent {

    @Override
    public Response response(MatchedRequest request) {
        Response response;
	try {
	    int id = request.pathVariable("id", Integer.class);
	    String message = "Hello number " + id;
	    response = new OkResponse(message);
	} catch (Exception e) {
	    response = new BadRequestResponse(e.getMessage());
	}
	return response;
    }
}
```
For more, refer to [one page docs](https://github.com/Iprogrammerr/Bright-Server/wiki).
Be sure to check out [examples](https://github.com/Iprogrammerr/Bright-Server/tree/master/src/main/java/com/iprogrammerr/bright/server/example), which are always the best documentation.
Project is under active development, so any feedback or opened issue is very welcome and appreciated.
Because of that, they might become outdated from time to time.
## Maven
```xml
<dependency>
  <groupId>com.iprogrammerr</groupId>
  <artifactId>bright-server</artifactId>
  <version>1.0-beta-2</version>
</dependency>
```
## Gradle
```
compile 'com.iprogrammerr:bright-server:1.0-beta-2'
 ```
## Example
  In development: (https://github.com/Iprogrammerr/Riddle/)
