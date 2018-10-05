package com.iprogrammerr.bright.server.request;

public interface MatchedRequest extends Request {

    <T> boolean hasParameter(String key, Class<T> clazz);

    <T> T parameter(String key, Class<T> clazz) throws Exception;

    <T> boolean hasPathVariable(String key, Class<T> clazz);

    <T> T pathVariable(String key, Class<T> clazz) throws Exception;
}
