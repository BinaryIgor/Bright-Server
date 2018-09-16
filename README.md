# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework.
 ```java
RequestMethod get = new GetMethod();

List<ConditionalRespondent> respondents = new ArrayList<>();
ConditionalRespondent helloRespondent = new HttpRespondent("hello/{id:int}", get, new HelloRespondent());
respondents.add(helloRespondent);

List<ConditionalRequestFilter> requestFilters = new ArrayList<>();
ConditionalRequestFilter authorizationFilter = new HttpRequestFilter("*", new AnyRequestMethodRule(),
		new AuthorizationFilter());
requestFilters.add(authorizationFilter);

Connector connector = new RequestResponseConnector(new AllowAllCors(), respondents, new ConditionalRequestFilters(filters));
Server server = new Server(8080, 5000, connector);
server.start();
```
That's all. Now you have fully working http server which handles get request nad authorize it as follows:
```java
public class AuthorizationFilter implements RequestFilter {

    private static final String SECRET_TOKEN = "token";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Response filter(Request request) {
	if (!request.hasHeader(AUTHORIZATION_HEADER)) {
	    return new ForbiddenResponse();
	}
	try {
	    String token = request.header(AUTHORIZATION_HEADER);
	    boolean valid = token.equals(SECRET_TOKEN);
	    if (!valid) {
		return new ForbiddenResponse();
	    }
	    return new OkResponse();
	} catch (Exception exception) {
	    return new ForbiddenResponse();
	}
    }

}
```
```java
public class HelloRespondent implements Respondent {

    @Override
    public Response respond(MatchedRequest request) {
	try {
	    int id = request.pathVariable("id", Integer.class);
	    String message = "Hello number " + id;
	    return new OkResponse(message);
	} catch (Exception exception) {
	    return new BadRequestResponse(exception.getMessage());
	}
    }
}
```
For more, refer to [one page docs](https://github.com/Iprogrammerr/Bright-Server/wiki).
Project is under active development, so any feedback or opened issue is very welcome and appreciated.
Because of that, they might become outdated from time to time.

## Maven
  Api needs stabilization, so soon to be added.
  
## Example
  In development: https://github.com/Iprogrammerr/Riddle/

 
 
