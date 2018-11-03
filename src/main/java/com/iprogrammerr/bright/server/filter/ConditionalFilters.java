package com.iprogrammerr.bright.server.filter;

import java.util.ArrayList;
import java.util.List;

import com.iprogrammerr.bright.server.initialization.Initialization;
import com.iprogrammerr.bright.server.initialization.MappedInitialization;
import com.iprogrammerr.bright.server.initialization.SolidInitialization;
import com.iprogrammerr.bright.server.initialization.StickyInitialization;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.BlockedResponse;
import com.iprogrammerr.bright.server.response.IntermediateResponse;
import com.iprogrammerr.bright.server.response.ToForwardResponse;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;

public final class ConditionalFilters implements Filters {

	private final Initialization<Iterable<ConditionalFilter>> filters;

	private ConditionalFilters(Initialization<Iterable<ConditionalFilter>> filters) {
		this.filters = filters;
	}

	public ConditionalFilters(Iterable<ConditionalFilter> filters) {
		this(new StickyInitialization<>(
				new MappedInitialization<Iterable<ConditionalFilter>, Iterable<ConditionalFilter>>(
						filters, cfs -> {
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
	public IntermediateResponse response(Request request) {
		IntermediateResponse response = new ToForwardResponse();
		try {
			for (ConditionalFilter filter : this.filters.value()) {
				if (!filter.areConditionsMet(request)) {
					continue;
				}
				response = filter.response(request);
				if (!response.canForward()) {
					break;
				}
			}
		} catch (Exception e) {
			response = new BlockedResponse(new ForbiddenResponse());
		}
		return response;
	}
}
