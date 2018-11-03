package com.iprogrammerr.bright.server.respondent;

import java.io.File;

import com.iprogrammerr.bright.server.binary.type.StaticHttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedFile;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.method.RequestMethod;
import com.iprogrammerr.bright.server.pattern.FileUrlPattern;
import com.iprogrammerr.bright.server.pattern.IndexHtmlFileUrlPattern;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.SeeOtherResponse;

public final class FilesRespondent implements ConditionalRespondent {

	private final RequestMethod requestMethod;
	private final FileUrlPattern urlPattern;
	private final FileRespondent respondent;

	public FilesRespondent(RequestMethod requestMethod, FileUrlPattern urlPattern,
			FileRespondent respondent) {
		this.requestMethod = requestMethod;
		this.urlPattern = urlPattern;
		this.respondent = respondent;
	}

	public FilesRespondent(String rootDirectory, FileRespondent respondent) {
		this(new GetMethod(), new IndexHtmlFileUrlPattern(rootDirectory), respondent);
	}

	public FilesRespondent(String rootDirectory) {
		this(new GetMethod(), new IndexHtmlFileUrlPattern(rootDirectory),
				new RawFileRespondent(new StaticHttpTypes()));
	}

	@Override
	public boolean areConditionsMet(Request request) {
		return this.requestMethod.is(request.method()) && this.urlPattern.isMatched(request.url());
	}

	@Override
	public Response response(Request request) throws Exception {
		if (!areConditionsMet(request)) {
			throw new Exception("Given request does not match respondent requirements");
		}
		Response response;
		File file = new File(this.urlPattern.filePath(request.url()));
		if (!file.exists()) {
			response = new NotFoundResponse();
		} else if (file.isDirectory()) {
			response = new SeeOtherResponse(request.url() + "/");
		} else {
			response = this.respondent.response(new TypedFile(file));
		}
		return response;
	}
}
