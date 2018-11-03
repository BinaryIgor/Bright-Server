package com.iprogrammerr.bright.server.response;

public final class ToForwardResponse implements IntermediateResponse {

	@Override
	public boolean canForward() {
		return true;
	}

	@Override
	public Response error() throws Exception {
		throw new Exception("There is no error, request need to be forwarded");
	}
}
