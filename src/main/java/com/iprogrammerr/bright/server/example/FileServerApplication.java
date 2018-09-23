package com.iprogrammerr.bright.server.example;

import java.util.Optional;

import com.iprogrammerr.bright.server.Connector;
import com.iprogrammerr.bright.server.RequestResponseConnector;
import com.iprogrammerr.bright.server.Server;
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
	Connector connector = new RequestResponseConnector(new HttpOneProtocol(),
		new FileServerApplication(fileRespondent));
	Server server = new Server(configuration.port(), configuration.timeout(), connector);
	server.start();
    }

    @Override
    public Optional<Response> respond(Request request) {
	Optional<Response> response;
	if (fileRespondent.conditionsMet(request)) {
	    try {
		response = Optional.of(fileRespondent.respond(request));
	    } catch (Exception exception) {
		exception.printStackTrace();
		response = Optional.of(new InternalServerErrorResponse());
	    }
	} else {
	    response = Optional.empty();
	}
	return response;
    }

}
