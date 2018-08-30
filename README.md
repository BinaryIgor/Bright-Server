# Bright Server
 Bright Server is easy to use, robust, reliable and flexible standalone java http server and lightweight web framework.
## Starting Server`
Server server = new Server(serverConfiguration, requestResolvers, requestFilters);
## Configuration

>#default to ""
contextPath=
#default to 8080
port=
#Headers appended to every response
#Default to *
allowedOrigins=*
#Default to *
allowedMethods=*
#Default to *
>allowedHeaders=*

ServerConfiguration serverConfiguration = new ServerConfiguration(properties);