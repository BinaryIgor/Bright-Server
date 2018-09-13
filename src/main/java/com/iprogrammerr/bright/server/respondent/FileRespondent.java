package com.iprogrammerr.bright.server.respondent;

import java.io.File;

import com.iprogrammerr.bright.server.binary.BinaryFile;
import com.iprogrammerr.bright.server.binary.HttpTypedFile;
import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.header.ContentTypeHeader;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.pattern.FileUrlPattern;
import com.iprogrammerr.bright.server.pattern.UrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.NotFoundResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class FileRespondent implements ConditionalRespondent {

    private static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    private final RequestMethod requestMethod;
    private final UrlPattern urlPattern;

    public FileRespondent(RequestMethod requestMethod, UrlPattern urlPattern) {
	this.requestMethod = requestMethod;
	this.urlPattern = urlPattern;
    }

    public FileRespondent() {
	this(new GetMethod(), new FileUrlPattern());
    }

    @Override
    public boolean canRespond(Request request) {
	if (!requestMethod.is(request.method())) {
	    return false;
	}
	return urlPattern.match(request.url());
    }

    // TODO images content length mismatch
    @Override
    public Response respond(Request request) throws Exception {
	if (!canRespond(request)) {
	    throw new PreConditionRequiredException("Given request does not match respondent requirements");
	}
	try {
	    int indexOfQuestionMark = request.url().indexOf("?");
	    String fileName = indexOfQuestionMark > 0 ? request.url().substring(0, indexOfQuestionMark) : request.url();
	    if (fileName.isEmpty()) {
		fileName = "index.html";
	    }
	    if (fileName.endsWith("/")) {
		fileName += "index.html";
	    }
	    BinaryFile binaryFile = new HttpTypedFile(new File(WORKING_DIRECTORY + File.separator + fileName));
	    return new OkResponse(new ContentTypeHeader(binaryFile.type()), binaryFile.content());
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new NotFoundResponse();
	}
    }

}
