package com.iprogrammerr.bright.server.request;

public interface MatchedRequest extends Request {

    <T> T parameter(String key, Class<T> clazz) throws Exception;

    <T> T pathVariable(String key, Class<T> clazz) throws Exception;

}
