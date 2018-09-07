# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework.
 ```java
ServerConfiguration serverConfiguration = new BrightServerConfiguration(serverProperties());

RequestMethod get = new GetMethod();
RequestMethod post = new PostMethod();

List<ConditionalRespondent> respondents = new ArrayList<>();
ConditionalRespondent helloRespondent = new HttpRespondent("hello/{id:int}", get, new HelloRespondent());
respondents.add(helloRespondent);

List<ConditionalRequestFilter> requestFilters = new ArrayList<>();
ConditionalRequestFilter authorizationFilter = new HttpRequestFilter("*", new AnyRequestMethodRule(),
		new AuthorizationFilter());
requestFilters.add(authorizationFilter);

Server server = new Server(serverConfiguration, respondents, requestFilters);
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
   server.stop();
}));
server.start();
```
That's all. Now you have fully working http server which handles get request nad authorize it as follows:
```java
public class AuthorizationFilter implements RequestFilter {

    private static final String SECRET_TOKEN = "token";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Response filter(Request request) throws Exception {
	if (!request.hasHeader(AUTHORIZATION_HEADER)) {
	    return new ForbiddenResponse();
	}
	String token = request.header(AUTHORIZATION_HEADER);
	boolean valid = token.equals(SECRET_TOKEN);
	if (!valid) {
	    return new ForbiddenResponse();
	}
	return new OkResponse();
    }

}
```
```java
public class HelloRespondent implements Respondent {

    @Override
    public Response respond(MatchedRequest request) throws Exception {
	int id = request.pathVariable("id", Integer.class);
	return new OkResponse(new TextPlainContentTypeHeader(), message);
    }
}
```
For more, refer to [one page docs](https://github.com/Iprogrammerr/Bright-Server/wiki).
Project is under active development, so any feedback or opened issue is very welcome and appreciated.

 
 
