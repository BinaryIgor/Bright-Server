package com.iprogrammerr.bright.server.protocol;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.iprogrammerr.bright.server.header.Header;
import com.iprogrammerr.bright.server.header.HttpHeader;
import com.iprogrammerr.bright.server.header.template.ContentLengthHeader;
import com.iprogrammerr.bright.server.header.template.ContentTypeHeader;
import com.iprogrammerr.bright.server.mock.MockedBinary;
import com.iprogrammerr.bright.server.mock.MockedMultipartBinary;
import com.iprogrammerr.bright.server.mock.RequestBinary;
import com.iprogrammerr.bright.server.mock.ResponseBinary;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.CreatedResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class HttpOneProtocolTest {

    private static final String HOST = "www.iprogrammerr.com:8080";
    private static final String PATH = "bright";

    @Test
    public void canReadSimple() throws Exception {
	List<Header> headers = new ArrayList<>();
	headers.add(new HttpHeader("host", HOST));
	Request request = new ParsedRequest("get", PATH, headers);
	HttpOneProtocol protocol = new HttpOneProtocol();
	Request read = protocol.request(new ByteArrayInputStream(new RequestBinary(request).content()));
	assertTrue(request.equals(read));
	byte[] body = new MockedBinary().content();
	headers.add(new ContentTypeHeader("image/jpeg"));
	headers.add(new ContentLengthHeader(body.length));
	request = new ParsedRequest("POST", PATH, headers, body);
	byte[] binary = new RequestBinary(request).content();
	read = protocol.request(new ByteArrayInputStream(binary));
	assertTrue(request.equals(read));
    }

    @Test
    public void canReadComplex() throws Exception {
	List<Header> headers = new ArrayList<>();
	headers.add(new HttpHeader("host", HOST));
	byte[] body = new MockedMultipartBinary().content();
	headers.add(new ContentTypeHeader("multipart/mock-data"));
	headers.add(new ContentLengthHeader(body.length));
	Request request = new ParsedRequest("post", PATH, headers, body);
	byte[] raw = new RequestBinary(request).content();
	Request read = new HttpOneProtocol().request(new ByteArrayInputStream(raw));
	assertTrue(request.equals(read));
    }

    @Test
    public void canWrite() throws Exception {
	HttpOneProtocol protocol = new HttpOneProtocol();
	Response response = new CreatedResponse("super");
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	protocol.write(baos, response);
	assertTrue(Arrays.equals(new ResponseBinary(response).content(), baos.toByteArray()));
	response = new OkResponse(new TypedResponseBody("image/jpeg", new MockedBinary().content()));
	baos = new ByteArrayOutputStream();
	protocol.write(baos, response);
	assertTrue(Arrays.equals(new ResponseBinary(response).content(), baos.toByteArray()));
    }

    @Test
    public void canDetermineIfClose() {
	List<Header> headers = new ArrayList<>();
	headers.add(new HttpHeader("Connection", "Keep-alive"));
	Request request = new ParsedRequest("put", PATH, headers);
	HttpOneProtocol protocol = new HttpOneProtocol();
	assertFalse(protocol.shouldClose(request));
	headers.set(headers.size() - 1, new HttpHeader("Connection", "close"));
	assertTrue(protocol.shouldClose(request));
	headers.remove(headers.size() - 1);
	assertTrue(protocol.shouldClose(request));
    }

}
