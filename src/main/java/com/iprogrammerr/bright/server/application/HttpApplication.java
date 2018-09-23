package com.iprogrammerr.bright.server.application;

import java.util.List;
import java.util.Optional;

import com.iprogrammerr.bright.server.cors.Cors;
import com.iprogrammerr.bright.server.exception.NotFoundException;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilter;
import com.iprogrammerr.bright.server.filter.ConditionalRequestFilters;
import com.iprogrammerr.bright.server.method.OptionsMethod;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.WithAdditionalHeadersResponse;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

//TODO is this good enough cors request recognition?
public final class HttpApplication implements Application {

    private static final OptionsMethod OPTIONS_METHOD = new OptionsMethod();
    private final String contextPath;
    private final Cors cors;
    private final List<ConditionalRespondent> respondents;
    private final ConditionalRequestFilters filters;

    public HttpApplication(String contextPath, Cors cors, List<ConditionalRespondent> respondents,
	    ConditionalRequestFilters filters) {
	this.contextPath = contextPath;
	this.cors = cors;
	this.respondents = respondents;
	this.filters = filters;
    }

    public HttpApplication(String contextPath, Cors cors, List<ConditionalRespondent> respondents,
	    List<ConditionalRequestFilter> filters) {
	this(contextPath, cors, respondents, new ConditionalRequestFilters(filters));
    }

    public HttpApplication(Cors cors, List<ConditionalRespondent> respondents, List<ConditionalRequestFilter> filters) {
	this("", cors, respondents, new ConditionalRequestFilters(filters));
    }

    public HttpApplication(String contextPath, Cors cors, List<ConditionalRespondent> respondents) {
	this(contextPath, cors, respondents, new ConditionalRequestFilters());
    }

    public HttpApplication(Cors cors, List<ConditionalRespondent> respondents) {
	this("", cors, respondents, new ConditionalRequestFilters());
    }

    @Override
    public Optional<Response> respond(Request request) {
	Optional<Response> response;
	if (!contextPath.isEmpty() && !request.url().startsWith(contextPath)) {
	    response = Optional.empty();
	} else if (OPTIONS_METHOD.is(request.method())) {
	    response = Optional.of(respondToOptions(request));
	} else {
	    response = Optional.of(findResponse(request));
	}
	return response;
    }

    private Response findResponse(Request request) {
	Response response;
	try {
	    request.removeContextPath(contextPath);
	    ConditionalRespondent respondent = findRespondent(request);
	    response = filters.run(request);
	    if (properResponseCode(response.code())) {
		response = cors.toAddHeaders().isEmpty() ? respondent.respond(request)
			: new WithAdditionalHeadersResponse(respondent.respond(request), cors.toAddHeaders());
	    }
	} catch (NotFoundException exception) {
	    exception.printStackTrace();
	    response = new NotFoundResponse();
	} catch (Exception exception) {
	    exception.printStackTrace();
	    response = new InternalServerErrorResponse();
	}
	return response;
    }

    private ConditionalRespondent findRespondent(Request request) throws Exception {
	for (ConditionalRespondent respondent : respondents) {
	    if (respondent.conditionsMet(request)) {
		return respondent;
	    }
	}
	throw new NotFoundException(
		String.format("There is no respondent for %s method and url: %s", request.method(), request.url()));
    }

    private Response respondToOptions(Request request) {
	Response response;
	if (!cors.validate(request)) {
	    response = new ForbiddenResponse();
	} else {
	    response = new OkResponse(cors.toAddHeaders());
	}
	return response;
    }

    private boolean properResponseCode(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

}
