package com.iprogrammerr.bright.server.application;

import java.util.Optional;

import com.iprogrammerr.bright.server.cors.Cors;
import com.iprogrammerr.bright.server.filter.ConditionalFilter;
import com.iprogrammerr.bright.server.filter.ConditionalFilters;
import com.iprogrammerr.bright.server.filter.Filters;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.respondent.ConditionalRespondent;
import com.iprogrammerr.bright.server.response.IntermediateResponse;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.WithAdditionalHeadersResponse;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class HttpApplication implements Application {

	private final String context;
	private final Cors cors;
	private final Iterable<ConditionalRespondent> respondents;
	private final Filters filters;

	public HttpApplication(String context, Cors cors, Iterable<ConditionalRespondent> respondents,
			Filters filters) {
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
		} else if (this.cors.is(request)) {
			response = Optional.of(corsResponse(request));
		} else {
			try {
				request.removeContext(this.context);
				ConditionalRespondent cr = respondent(request);
				response = Optional.of(validRequestResponse(request, cr));
			} catch (Exception e) {
				response = Optional.of(new NotFoundResponse());
			}
		}
		return response;
	}

	private Response validRequestResponse(Request request, ConditionalRespondent respondent) {
		Response response;
		try {
			IntermediateResponse ir = this.filters.response(request);
			if (ir.canForward()) {
				response = this.cors.toAddHeaders().isEmpty() ? respondent.response(request)
						: new WithAdditionalHeadersResponse(respondent.response(request),
								this.cors.toAddHeaders());
			} else {
				response = ir.error();
			}
		} catch (Exception e) {
			response = new InternalServerErrorResponse(e.getMessage());
		}
		return response;
	}

	private ConditionalRespondent respondent(Request request) throws Exception {
		for (ConditionalRespondent cr : this.respondents) {
			if (cr.areConditionsMet(request)) {
				return cr;
			}
		}
		throw new Exception(String.format("There is no respondent for %s method and url: %s",
				request.method(), request.url()));
	}

	private Response corsResponse(Request request) {
		Response response;
		if (this.cors.isValid(request)) {
			response = new OkResponse(this.cors.toAddHeaders());
		} else {
			response = new ForbiddenResponse();
		}
		return response;
	}
}
