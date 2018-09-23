package com.iprogrammerr.bright.server.example;

public class ServerConfiguration {

    private final String[] args;

    public ServerConfiguration(String[] args) {
	this.args = args;
    }

    public int port() {
	if (args.length < 1) {
	    return 8080;
	}
	try {
	    String value = value("port");
	    if (value.isEmpty()) {
		return 8080;
	    }
	    return Integer.parseInt(value.trim());
	} catch (Exception exception) {
	    return 8080;
	}
    }

    public int timeout() {
	if (args.length < 1) {
	    return 5000;
	}
	try {
	    String value = value("timeout");
	    if (value.isEmpty()) {
		return 5000;
	    }
	    return Integer.parseInt(value.trim());
	} catch (Exception exception) {
	    return 5000;
	}
    }

    public String rootDirectory() throws Exception {
	if (args.length < 1) {
	    throw new Exception("root directory is required!");
	}
	String rootDirectory = value("root_directory");
	if (rootDirectory.isEmpty()) {
	    throw new Exception("root directory can not be empty!");
	}
	return rootDirectory;
    }

    private String value(String key) {
	String keyValue = keyValue(key);
	if (!keyValue.contains(key) || !keyValue.contains("=")) {
	    return "";
	}
	String[] keyAndValue = keyValue.split("=");
	if (keyAndValue.length < 2) {
	    return "";
	}
	return keyAndValue[1];
    }

    private String keyValue(String key) {
	for (String arg : args) {
	    if (arg.contains(key)) {
		return arg;
	    }
	}
	return "";
    }

    public String print() throws Exception {
	return "port=" + port() + ", timeout=" + timeout() + ", rootDirectory=" + rootDirectory();
    }

}
