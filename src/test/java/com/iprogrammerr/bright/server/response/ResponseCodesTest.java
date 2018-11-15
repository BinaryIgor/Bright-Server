package com.iprogrammerr.bright.server.response;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.iprogrammerr.bright.server.response.template.BadRequestResponse;
import com.iprogrammerr.bright.server.response.template.CreatedResponse;
import com.iprogrammerr.bright.server.response.template.ForbiddenResponse;
import com.iprogrammerr.bright.server.response.template.InternalServerErrorResponse;
import com.iprogrammerr.bright.server.response.template.NoContentResponse;
import com.iprogrammerr.bright.server.response.template.NotFoundResponse;
import com.iprogrammerr.bright.server.response.template.OkResponse;
import com.iprogrammerr.bright.server.response.template.SeeOtherResponse;
import com.iprogrammerr.bright.server.response.template.UnauthenticatedResponse;

public final class ResponseCodesTest {

	@Test
	public void shouldHaveProperCodes() {
		assertThat(
				Arrays.asList(new BadRequestResponse(), new CreatedResponse(), new ForbiddenResponse(),
						new InternalServerErrorResponse(), new NoContentResponse(), new NotFoundResponse(),
						new OkResponse(), new SeeOtherResponse("www.mock.com"), new UnauthenticatedResponse()),
				new ResponsesThatHaveProperCodes(Arrays.asList(400, 201, 403, 500, 204, 404, 200, 303, 401)));
	}
}
