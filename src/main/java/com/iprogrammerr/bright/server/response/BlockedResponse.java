package com.iprogrammerr.bright.server.response;

public final class BlockedResponse implements IntermediateResponse {

	private final Response response;

	public BlockedResponse(Response response) {
		this.response = response;
	}

	@Override
	public boolean canForward() {
		return false;
	}

	@Override
	public Response error() throws Exception {
		return this.response;
	}
}
