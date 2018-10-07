package com.iprogrammerr.bright.server.rule.filter;

import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.rule.Rule;

public interface FilterRule extends Rule<Request> {
    boolean isPrimary();
}
