package com.iprogrammerr.simpleserver.resolver;

import com.iprogrammerr.simpleserver.model.Request;
import com.iprogrammerr.simpleserver.model.Response;

public abstract class RequestResolver {

    private String mainPath;

    public RequestResolver(String mainPath) {
	this.mainPath = mainPath;
    }

    public abstract void resolve(Request request, Response response);

    public boolean canResolve(String path) {
	return path.startsWith(mainPath);
    }
}
