package com.iprogrammerr.bright.server.respondent;

import java.io.File;

import com.iprogrammerr.bright.server.binary.BinaryFile;
import com.iprogrammerr.bright.server.binary.CompressedBinary;
import com.iprogrammerr.bright.server.binary.DeflateCompressedBinary;
import com.iprogrammerr.bright.server.binary.HttpTypedFile;
import com.iprogrammerr.bright.server.exception.PreConditionRequiredException;
import com.iprogrammerr.bright.server.header.ContentEncodingHeader;
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
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
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

    @Override
    public Response respond(Request request) throws Exception {
	if (!canRespond(request)) {
	    throw new PreConditionRequiredException("Given request does not match respondent requirements");
	}
	try {
	    int indexOfQuestionMark = request.url().indexOf("?");
	    String fileName = indexOfQuestionMark > 0 ? request.url().substring(0, indexOfQuestionMark) : request.url();
	    System.out.println("Will read!" + fileName);
	    BinaryFile binaryFile = new HttpTypedFile(new File(WORKING_DIRECTORY + FILE_SEPARATOR + fileName));
	    CompressedBinary compressedBinary = new DeflateCompressedBinary(binaryFile.content());
	    byte[] content = compressedBinary.content();
	    System.out.println("Content = " + content.length);
	    return new OkResponse(new ContentTypeHeader(binaryFile.type()), content,
		    new ContentEncodingHeader(compressedBinary.algorithm()));
	} catch (Exception exception) {
	    exception.printStackTrace();
	    return new NotFoundResponse();
	}
    }

}
