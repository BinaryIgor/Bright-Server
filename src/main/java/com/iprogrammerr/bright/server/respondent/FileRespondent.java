package com.iprogrammerr.bright.server.respondent;

import java.io.File;

import com.iprogrammerr.bright.server.binary.BinaryFile;
import com.iprogrammerr.bright.server.binary.HttpTypedFile;
import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.pattern.FileUrlPattern;
import com.iprogrammerr.bright.server.pattern.IndexHtmlFileUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.NotFoundResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;

public class FileRespondent implements ConditionalRespondent {

    private static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    private final RequestMethod requestMethod;
    private final FileUrlPattern urlPattern;

    public FileRespondent(RequestMethod requestMethod, FileUrlPattern urlPattern) {
	this.requestMethod = requestMethod;
	this.urlPattern = urlPattern;
    }

    public FileRespondent() {
	this(new GetMethod(), new IndexHtmlFileUrlPattern());
    }

    @Override
    public boolean conditionsMet(Request request) {
	if (!requestMethod.is(request.method())) {
	    return false;
	}
	return urlPattern.match(request.url());
    }

    @Override
    public Response respond(Request request) throws Exception {
	if (!conditionsMet(request)) {
	    throw new PreConditionRequiredException("Given request does not match respondent requirements");
	}
	try {
	    File file = new File(WORKING_DIRECTORY + File.separator + urlPattern.filePath(request.url()));
	    if (!file.exists()) {
		return new NotFoundResponse();
	    }
	    BinaryFile binaryFile = new HttpTypedFile(file);
	    return new OkResponse(new TypedResponseBody(binaryFile.type(), binaryFile.content()));
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new NotFoundResponse();
	}
    }

}
