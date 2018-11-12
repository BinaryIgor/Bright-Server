package com.iprogrammerr.bright.server.respondent;

import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import com.iprogrammerr.bright.server.binary.type.HttpTypes;
import com.iprogrammerr.bright.server.binary.type.StaticHttpTypes;
import com.iprogrammerr.bright.server.binary.type.TypedBinary;
import com.iprogrammerr.bright.server.binary.type.TypedFile;
import com.iprogrammerr.bright.server.response.body.TypedResponseBody;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public final class RawFileRespondentTest {

	@Test
	public void canRespond() throws Exception {
		TypedBinary binary = new TypedFile(new File(
				String.format("src%stest%sresources%sindex.html", File.separator, File.separator, File.separator)));
		byte[] content = binary.content();
		HttpTypes types = new StaticHttpTypes();
		assertThat(new RawFileRespondent(types), new FileRespondentThatCanRespond(binary,
				new OkResponse(new TypedResponseBody(types.type(binary.type()), content))));
	}
}
