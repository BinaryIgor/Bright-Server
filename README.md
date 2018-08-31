# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework. Explanatory code is present in example package, so let's examples speak for themselves.
## Starting Server`
 Server server = new Server(serverConfiguration, requestResolvers, requestFilters);

## Configuration
#defaults to empty string  
contextPath=  
#defaults to 8080  
port=
#wheter to add cors specific headers in response and handle JavaScript OPTIONS request  
addCorsHeaders=true  
#All defaults to * and are used only if addCorsHeaders is set to true.  
allowedOrigin=*  
allowedMethods=*  
allowedHeaders=*  

Above is needed as properties file:  
 ServerConfiguration serverConfiguration = new ServerConfiguration(properties);
 
 ## Request Resolver
 
 ```java
List<RequestResolver> requestResolvers = new ArrayList<>();
RequestResolver helloResolver = new RequestResolver("hello/{id:int}", RequestMethod.GET, urlPatternParser, new HelloHandler());
requestResolvers.add(helloResolver);
 ```
 First argument is urlPattern which, together with request method will be used to resolve request by UrlPatternParser interface. If it will be successfully resolved RequestHandler, in example HelloHandler, will handle request. Bright Server provide TypedUrlPatternParser, which works as follows. For pathVariables {key:value} in any part of the url where you expect variable of certain types. Valid types are defined by UrlPatternType and are basically java primitives. It works similarly for query parameters. 
 ```java
 RequestResolver helloResolver = new RequestResolver("hello?message=string", RequestMethod.GET, urlPatternParser, new HelloHandler());
 ```
 Above will require message parameter. TypedUrlPatterParser guarantee that you will have all required by urlPattern pathVariables and parameters at the moment of handling request.
 ```java
 public class HelloHandler implements RequestHandler {

    @Override
    public Response handle(ResolvedRequest request) {
	int id = request.getPathVariable("id", Integer.class);
	String message = request.getParameter("message", String.class);
	message = "Received " + message + " from " + id;
	return new PlainTextResponse(ResponseCode.OK, message);
    }

}
```
 
 
