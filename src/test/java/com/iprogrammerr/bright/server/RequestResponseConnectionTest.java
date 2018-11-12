package com.iprogrammerr.bright.server;

import static org.junit.Assert.assertThat;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.application.HttpApplication;
import com.iprogrammerr.bright.server.method.PostMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.mock.RequestBinary;
import com.iprogrammerr.bright.server.request.ParsedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.PotentialRespondent;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class RequestResponseConnectionTest {

	@Test
	public void canConnect() throws Exception {
		RequestMethod post = new PostMethod();
		Response response = new OkResponse();
		try (ServerSocket s = new ServerSocket(0)) {
			writeRequestThread(new ParsedRequest("user/1", "post"), s.getLocalPort()).start();
			assertThat(
					new RequestResponseConnection(new HttpApplication(
							Arrays.asList(new PotentialRespondent("user/{id:int}", post, r -> response),
									new PotentialRespondent("users", post, r -> response)))),
					new ConnectionThatCanConnect(s.accept()));
		}
	}

	private Thread writeRequestThread(Request request, int port) {
		return new Thread(() -> {
			try (Socket socket = new Socket(InetAddress.getLocalHost(), port)) {
				socket.getOutputStream().write(new RequestBinary(request).content());
			} catch (Exception e) {

			}
		});
	}
}
