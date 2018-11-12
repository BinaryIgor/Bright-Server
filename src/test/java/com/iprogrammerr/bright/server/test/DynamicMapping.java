package com.iprogrammerr.bright.server.test;

import com.iprogrammerr.bright.server.model.TypedMap;

public interface DynamicMapping<From> {
	<To> To value(TypedMap from, Class<To> clazz) throws Exception;
}
