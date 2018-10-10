package com.iprogrammerr.bright.server.application;

import java.util.Optional;

import com.iprogrammerr.bright.server.cors.Cors;
import com.iprogrammerr.bright.server.filter.ConditionalFilter;
import com.iprogrammerr.bright.server.filter.ConditionalFilters;
import com.iprogrammerr.bright.server.filter.Filters;
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
    private final Iterable<ConditionalRespondent> respondents;
    private final Filters filters;

    public HttpApplication(String context, Cors cors, Iterable<ConditionalRespondent> respondents, Filters filters) {
	this.context = context;
	this.cors = cors;
	this.respondents = respondents;
	this.filters = filters;
    }

    public HttpApplication(String context, Cors cors, Iterable<ConditionalRespondent> respondents,
	    Iterable<ConditionalFilter> filters) {
	this(context, cors, respondents, new ConditionalFilters(filters));
    }

    public HttpApplication(Cors cors, Iterable<ConditionalRespondent> respondents,
	    Iterable<ConditionalFilter> filters) {
	this("", cors, respondents, new ConditionalFilters(filters));
    }

    public HttpApplication(String context, Cors cors, Iterable<ConditionalRespondent> respondents) {
	this(context, cors, respondents, new ConditionalFilters());
    }

    public HttpApplication(Cors cors, Iterable<ConditionalRespondent> respondents) {
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
	    try {
		request.removeContext(this.context);
		ConditionalRespondent cr = respondent(request);
		response = Optional.of(validRequestResponse(request, cr));
	    } catch (Exception e) {
		e.printStackTrace();
		response = Optional.of(new NotFoundResponse());
	    }
	}
	return response;
    }

    private Response validRequestResponse(Request request, ConditionalRespondent respondent) {
	Response response;
	try {
	    response = this.filters.response(request);
	    if (isProperCode(response.code())) {
		response = this.cors.toAddHeaders().isEmpty() ? respondent.response(request)
			: new WithAdditionalHeadersResponse(respondent.response(request), this.cors.toAddHeaders());
	    }
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
	throw new Exception(
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

    private boolean isProperCode(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

}
