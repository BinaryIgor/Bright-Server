package com.iprogrammerr.bright.server.response;

public interface IntermediateResponse {

	boolean canForward();

	Response error() throws Exception;
}
