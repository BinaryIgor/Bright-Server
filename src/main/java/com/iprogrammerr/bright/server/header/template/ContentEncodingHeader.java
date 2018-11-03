package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class ContentEncodingHeader extends HeaderEnvelope {

	public ContentEncodingHeader(String encoding) {
		super(new HttpHeader("Content-Encoding", encoding));
	}
}
