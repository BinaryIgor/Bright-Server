package com.iprogrammerr.bright.server.initialization;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.iprogrammerr.bright.server.model.Mapping;

public final class MappingInitializationTest {

	@Test
	public void canMap() {
		Mapping<Integer, String> mapping = from -> String.valueOf(from + 1);
		assertThat(mapping.value(1), Matchers.equalTo(new MappedInitialization<Integer, String>(1, mapping).value()));
	}
}
