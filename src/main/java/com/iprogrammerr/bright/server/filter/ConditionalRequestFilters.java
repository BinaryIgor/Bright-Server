package com.iprogrammerr.bright.server.filter;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.OkResponse;
import com.iprogrammerr.bright.server.response.Response;

public class ConditionalRequestFilters implements Filters {

    private final List<ConditionalRequestFilter> filters;

    public ConditionalRequestFilters(ConditionalRequestFilter primaryFilter, List<ConditionalRequestFilter> filters) {
	this.filters = filters;
	this.filters.add(0, primaryFilter);
    }

    public ConditionalRequestFilters(List<ConditionalRequestFilter> filters) {
	this.filters = filters;
    }

    public ConditionalRequestFilters() {
	this.filters = new ArrayList<>();
    }

    @Override
    public Response run(Request request) {
	try {
	    List<ConditionalRequestFilter> requestFilters = matchFilters(request);
	    for (ConditionalRequestFilter filter : requestFilters) {
		Response response = filter.filter(request);
		if (!properResponseCode(response.responseCode())) {
		    return response;
		}
	    }
	    return new OkResponse();
	} catch (Exception exception) {
	    return new ForbiddenResponse();
	}
    }

    private List<ConditionalRequestFilter> matchFilters(Request request) {
	List<ConditionalRequestFilter> matchedFilters = new ArrayList<>();
	for (ConditionalRequestFilter filter : filters) {
	    if (filter.conditionsMet(request)) {
		matchedFilters.add(filter);
	    }
	}
	return matchedFilters;
    }

    private boolean properResponseCode(int responseCode) {
	return responseCode >= 200 && responseCode < 300;
    }

}
