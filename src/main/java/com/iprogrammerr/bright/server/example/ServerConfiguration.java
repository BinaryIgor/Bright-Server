package com.iprogrammerr.bright.server.example;

public final class ServerConfiguration {

    private final String[] args;

    public ServerConfiguration(String[] args) {
	this.args = args;
    }

    public int port() {
	int port;
	if (this.args.length < 1) {
	    port = 8080;
	} else {
	    try {
		String value = value("port");
		port = value.isEmpty() ? 8080 : Integer.parseInt(value.trim());
	    } catch (Exception e) {
		port = 8080;
	    }
	}
	return port;
    }

    public int timeout() {
	int timeout;
	if (this.args.length < 1) {
	    timeout = 5000;
	} else {
	    try {
		String value = value("timeout");
		timeout = value.isEmpty() ? 5000 : Integer.parseInt(value.trim());
	    } catch (Exception e) {
		timeout = 5000;
	    }
	}
	return timeout;
    }

    public String rootDirectory() throws Exception {
	if (this.args.length < 1) {
	    throw new Exception("root directory is required!");
	}
	String directory = value("root_directory");
	if (directory.isEmpty()) {
	    throw new Exception("root directory can not be empty!");
	}
	return directory;
    }

    private String value(String key) {
	String keyValue = keyValue(key);
	String value;
	if (!keyValue.contains(key)) {
	    value = "";
	} else {
	    String[] split = keyValue.split("=");
	    value = split.length >= 2 ? split[1] : "";
	}
	return value;
    }

    private String keyValue(String key) {
	for (String arg : this.args) {
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
