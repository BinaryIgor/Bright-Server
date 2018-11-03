package com.iprogrammerr.bright.server.model;

public interface DynamicMapping<From> {
	<To> To value(From from, Class<To> clazz) throws Exception;
}
