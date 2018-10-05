package com.iprogrammerr.bright.server.application;

import java.util.List;
import java.util.Optional;

import com.iprogrammerr.bright.server.cors.Cors;
import com.iprogrammerr.bright.server.exception.NotFoundException;
import com.iprogrammerr.bright.server.filter.ConditionalFilter;
import com.iprogrammerr.bright.server.filter.ConditionalFilters;
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
    private final String context;
    private final Cors cors;
    private final List<ConditionalRespondent> respondents;
    private final ConditionalFilters filters;

    public HttpApplication(String context, Cors cors, List<ConditionalRespondent> respondents,
	    ConditionalFilters filters) {
	this.context = context;
	this.cors = cors;
	this.respondents = respondents;
	this.filters = filters;
    }

    public HttpApplication(String contextPath, Cors cors, List<ConditionalRespondent> respondents,
	    List<ConditionalFilter> filters) {
	this(contextPath, cors, respondents, new ConditionalFilters(filters));
    }

    public HttpApplication(Cors cors, List<ConditionalRespondent> respondents, List<ConditionalFilter> filters) {
	this("", cors, respondents, new ConditionalFilters(filters));
    }

    public HttpApplication(String contextPath, Cors cors, List<ConditionalRespondent> respondents) {
	this(contextPath, cors, respondents, new ConditionalFilters());
    }

    public HttpApplication(Cors cors, List<ConditionalRespondent> respondents) {
	this("", cors, respondents, new ConditionalFilters());
    }

    @Override
    public Optional<Response> response(Request request) {
	Optional<Response> response;
	if (!this.context.isEmpty() && !request.url().startsWith(this.context)) {
	    response = Optional.empty();
	} else if (OPTIONS_METHOD.is(request.method())) {
	    response = Optional.of(optionsResponse(request));
	} else {
	    response = Optional.of(validRequestResponse(request));
	}
	return response;
    }

    private Response validRequestResponse(Request request) {
	Response response;
	try {
	    request.removeContext(this.context);
	    ConditionalRespondent cr = respondent(request);
	    response = this.filters.filtered(request);
	    if (hasProperCode(response.code())) {
		response = this.cors.toAddHeaders().isEmpty() ? cr.response(request)
			: new WithAdditionalHeadersResponse(cr.response(request), this.cors.toAddHeaders());
	    }
	} catch (NotFoundException e) {
	    e.printStackTrace();
	    response = new NotFoundResponse();
	} catch (Exception e) {
	    e.printStackTrace();
	    response = new InternalServerErrorResponse();
	}
	return response;
    }

    private ConditionalRespondent respondent(Request request) throws Exception {
	for (ConditionalRespondent cr : this.respondents) {
	    if (cr.areConditionsMet(request)) {
		return cr;
	    }
	}
	throw new NotFoundException(
		String.format("There is no respondent for %s method and url: %s", request.method(), request.url()));
    }

    private Response optionsResponse(Request request) {
	Response response;
	if (!this.cors.isValid(request)) {
	    response = new ForbiddenResponse();
	} else {
	    response = new OkResponse(this.cors.toAddHeaders());
	}
	return response;
    }

    private boolean hasProperCode(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

}
