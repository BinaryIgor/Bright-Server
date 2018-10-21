package com.iprogrammerr.bright.server.filter;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.MappedInitialization;
import com.iprogrammerr.bright.server.initialization.SolidInitialization;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class ConditionalFilters implements Filters {

    private final Initialization<Iterable<ConditionalFilter>> filters;

    private ConditionalFilters(Initialization<Iterable<ConditionalFilter>> filters) {
	this.filters = filters;
    }

    public ConditionalFilters(Iterable<ConditionalFilter> filters) {
	this(new SolidInitialization<>(
		new MappedInitialization<Iterable<ConditionalFilter>, Iterable<ConditionalFilter>>(filters, cfs -> {
		    List<ConditionalFilter> sorted = new ArrayList<>();
		    for (ConditionalFilter cf : cfs) {
			if (cf.isPrimary()) {
			    sorted.add(cf);
			}
		    }
		    for (ConditionalFilter cf : cfs) {
			if (!cf.isPrimary()) {
			    sorted.add(cf);
			}
		    }
		    return sorted;
		})));
    }

    public ConditionalFilters() {
	this(new SolidInitialization<>(() -> new ArrayList<>()));
    }

    @Override
    public Response response(Request request) {
	try {
	    Iterable<ConditionalFilter> filters = matchedFilters(request);
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

    private Iterable<ConditionalFilter> matchedFilters(Request request) {
	List<ConditionalFilter> filters = new ArrayList<>();
	for (ConditionalFilter filter : this.filters.value()) {
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
