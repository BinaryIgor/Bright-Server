# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework.
## Starting Server`
 Server server = new Server(serverConfiguration, requestResolvers, requestFilters);

## Configuration
 #default to ""  
 contextPath=  
 #default to 8080  
 port=  
 #Comma separated headers appended to every response   
 #All defaults to * 
 allowedOrigins=*  
 allowedMethods=*  
 allowedHeaders=*  

 ServerConfiguration serverConfiguration = new ServerConfiguration(properties);
 
 ## RequestResolvers
 
 List<RequestResolver> requestResolvers = new ArrayList<>();
	RequestResolver helloResolver = new RequestResolver("hello/{id:int}", RequestMethod.GET,
		resolverUrlPatternParser, new HelloHandler());
	requestResolvers.add(helloResolver);
 
 
