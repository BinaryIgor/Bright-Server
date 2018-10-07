package com.iprogrammerr.bright.server.filter;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class ConditionalFilters implements Filters {

    private final List<ConditionalFilter> filters;

    public ConditionalFilters(ConditionalFilter primaryFilter, List<ConditionalFilter> filters) {
	this.filters = filters;
	this.filters.add(0, primaryFilter);
    }

    public ConditionalFilters(List<ConditionalFilter> filters) {
	this.filters = filters;
    }

    public ConditionalFilters() {
	this.filters = new ArrayList<>();
    }

    @Override
    public Response response(Request request) {
	try {
	    List<ConditionalFilter> filters = matchedFilters(request);
	    for (ConditionalFilter filter : filters) {
		Response response = filter.response(request);
		if (!hasProperCode(response)) {
		    return response;
		}
	    }
	    return new OkResponse();
	} catch (Exception e) {
	    return new ForbiddenResponse();
	}
    }

    private List<ConditionalFilter> matchedFilters(Request request) {
	List<ConditionalFilter> filters = new ArrayList<>();
	for (ConditionalFilter filter : this.filters) {
	    if (filter.areConditionsMet(request)) {
		filters.add(filter);
	    }
	}
	return filters;
    }

    private boolean hasProperCode(Response response) {
	return response.code() >= 200 && response.code() < 300;
    }

}
