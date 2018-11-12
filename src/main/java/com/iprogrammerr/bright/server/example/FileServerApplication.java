package com.iprogrammerr.bright.server.example;

import java.util.Optional;

import com.iprogrammerr.bright.server.BrightServer;
import com.iprogrammerr.bright.server.Connection;
import com.iprogrammerr.bright.server.RequestResponseConnection;
import com.iprogrammerr.bright.server.application.Application;
import com.iprogrammerr.bright.server.binary.type.AudioHttpTypes;
import com.iprogrammerr.bright.server.binary.type.StaticHttpTypes;
import com.iprogrammerr.bright.server.protocol.HttpOneProtocol;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.respondent.FilesRespondent;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.InternalServerErrorResponse;

public final class FileServerApplication implements Application {

	private final ConditionalRespondent fileRespondent;

	public FileServerApplication(ConditionalRespondent fileRespondent) {
		this.fileRespondent = fileRespondent;
	}

	public static void main(String[] args) throws Exception {
		ServerConfiguration configuration = new ServerConfiguration(args);
		System.out.println("Starting with: " + configuration.print());
		ConditionalRespondent fileRespondent = new FilesRespondent(configuration.rootDirectory(),
				new ExampleFileRespondent(new StaticHttpTypes(), new AudioHttpTypes()));
		Connection connection = new RequestResponseConnection(new HttpOneProtocol(),
				new FileServerApplication(fileRespondent));
		BrightServer server = new BrightServer(configuration.port(), configuration.timeout(), connection);
		server.start();
	}

	@Override
	public Optional<Response> response(Request request) {
		Optional<Response> response;
		if (this.fileRespondent.areConditionsMet(request)) {
			try {
				response = Optional.of(this.fileRespondent.response(request));
			} catch (Exception e) {
				response = Optional.of(new InternalServerErrorResponse());
			}
		} else {
			response = Optional.empty();
		}
		return response;
	}
}
