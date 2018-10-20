package com.iprogrammerr.bright.server.request;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.iprogrammerr.bright.server.header.Header;

public final class ParsedRequest implements Request {

    private final String method;
    private String url;
    private final List<Header> headers;
    private final byte[] body;

    public ParsedRequest(String method, String url, List<Header> headers, byte[] body) {
	this.method = method;
	this.url = url;
	this.headers = headers;
	this.body = body;
    }

    public ParsedRequest(String method, String url, List<Header> headers) {
	this(method, url, headers, new byte[0]);
    }

    @Override
    public String url() {
	return this.url;
    }

    @Override
    public String method() {
	return this.method;
    }

    @Override
    public List<Header> headers() {
	return this.headers;
    }

    @Override
    public byte[] body() {
	return this.body;
    }

    @Override
    public boolean hasHeader(String key) {
	for (Header header : this.headers) {
	    if (header.is(key)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public String header(String key) {
	String header = "";
	for (Header h : this.headers) {
	    if (h.is(key)) {
		header = h.value();
		break;
	    }
	}
	return header;
    }

    @Override
    public void removeContext(String context) {
	if (this.url.startsWith(context) && !context.isEmpty()) {
	    url = url.replace(context + "/", "");
	}

    }

    @Override
    public boolean equals(Object object) {
	boolean equal;
	if (object == null || !getClass().equals(object.getClass())) {
	    equal = false;
	} else if (object == this) {
	    equal = true;
	} else {
	    ParsedRequest other = (ParsedRequest) object;
	    equal = this.url.equals(other.url) && this.method.equalsIgnoreCase(other.method)
		    && Arrays.equals(this.body, other.body);
	    if (equal && this.headers.size() == other.headers.size()) {
		Collections.sort(this.headers, (f, s) -> f.key().compareTo(s.key()));
		Collections.sort(other.headers, (f, s) -> f.key().compareTo(s.key()));
		for (int i = 0; i < this.headers.size(); ++i) {
		    if (!this.headers.get(i).equals(other.headers.get(i))) {
			equal = false;
			break;
		    }
		}
	    }
	}
	return equal;
    }

}
