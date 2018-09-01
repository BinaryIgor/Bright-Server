# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework. Explanatory code is present in example package, so let's examples speak for themselves.
 ```java
ServerConfiguration serverConfiguration = new ServerConfiguration(getServerProperties());

UrlPatternParser urlPatternParser = new TypedUrlPatternParser();
FilterUrlPatternParser filterUrlPatternParser = new StarSymbolFilterUrlPatternParser();

List<RequestResolver> requestResolvers = new ArrayList<>();
RequestResolver helloResolver = new RequestResolver("hello/{id:int}", RequestMethod.GET, urlPatternParser,
	new HelloHandler());
requestResolvers.add(helloResolver);

List<RequestFilter> requestFilters = new ArrayList<>();
RequestFilter authorizationFilter = new RequestFilter("*", new AnyRequestMethodRule(), filterUrlPatternParser,
	new AuthorizationHandler());
requestFilters.add(authorizationFilter);
	
Server server = new Server(serverConfiguration, requestResolvers, requestFilters);
Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    server.stop();
}));
server.start();
```
That's all. Now you have fully working http server which handles get request nad authorize it as follows:
```java
public class AuthorizationHandler implements ToFilterRequestHandler {

    private static final String SECRET_TOKEN = "token";

    @Override
    public Response handle(Request request) {
	if (!request.hasHeader(HeaderKey.AUTHORIZATION)) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	String token = request.getHeader(HeaderKey.AUTHORIZATION);
	boolean valid = token.equals(SECRET_TOKEN);
	if (!valid) {
	    return new EmptyResponse(ResponseCode.FORBIDDEN);
	}
	System.out.println("Secret header of " + token + " is valid");
	return new EmptyResponse(ResponseCode.OK);
    }

}

public class HelloHandler implements RequestHandler {

    @Override
    public Response handle(ResolvedRequest request) {
	int id = request.getPathVariable("id", Integer.class);
	String message = "Hello number " + id + "!";
	return new PlainTextResponse(ResponseCode.OK, message);
    }
}
```
For more, refer to [one page docs](https://github.com/Iprogrammerr/Bright-Server/wiki)
Project is still under development, so any feedback or opened issue is very welcome and appreciated.


 
 
