package com.iprogrammerr.simpleserver.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;
import com.iprogrammerr.simpleserver.parser.RequestParser;
import com.iprogrammerr.simpleserver.resolver.RequestsResolver;

public class RequestHandler implements Runnable {

    private Socket socket;
    private RequestParser httpParser;

    public RequestHandler(Socket socket, RequestParser httpParser) {
	this.socket = socket;
	this.httpParser = httpParser;
    }

    @Override
    public void run() {
	try (InputStream inputStream = socket.getInputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
	    Request request = httpParser.getRequest(inputStream);
	    System.out.println(request);
	    Response response = RequestsResolver.getInstance().resolve(request);
	    writeResponse(writer, response);
	} catch (IOException exception) {
	    exception.printStackTrace();
	} finally {
	    try {
		socket.close();
	    } catch (IOException exception) {
		exception.printStackTrace();
	    }
	}
    }

    private void writeResponse(BufferedWriter writer, Response response) throws IOException {
	writer.write(response.getCode() == 404 ? getNotFoundResponse() : getAcceptedResponse());
	writer.newLine();
	writer.write("Access-Control-Allow-Origin: *");
    }

    private String getAcceptedResponse() {
	return "HTTP/1.1 200 OK";
    }

    private String getNotFoundResponse() {
	return "HTTP/1.1 400 NOT FOUND";
    }

}
