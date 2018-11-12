package com.iprogrammerr.bright.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.protocol.RequestResponseProtocol;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;

public final class RequestResponseConnection implements Connection {

	private final RequestResponseProtocol protocol;
	private final List<Application> applications;

	public RequestResponseConnection(RequestResponseProtocol protocol, List<Application> applications) {
		this.protocol = protocol;
		this.applications = applications;
	}

	public RequestResponseConnection(RequestResponseProtocol protocol, Application application) {
		this(protocol, Collections.singletonList(application));
	}

	public RequestResponseConnection(List<Application> applications) {
		this(new HttpOneProtocol(), applications);
	}

	public RequestResponseConnection(Application application) {
		this(new HttpOneProtocol(), Collections.singletonList(application));
	}

	@Override
	public void connect(Socket socket) {
		try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()) {
			Request request = this.protocol.request(is);
			Response response = response(request);
			this.protocol.write(os, response);
			if (this.protocol.shouldClose(request)) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			close(socket);
		}
	};

	private void close(Socket socket) {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Response response(Request request) {
		for (Application a : this.applications) {
			Optional<Response> r = a.response(request);
			if (r.isPresent()) {
				return r.get();
			}
		}
		return new NotFoundResponse();
	}
}
