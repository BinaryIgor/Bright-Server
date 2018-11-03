package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class ContentTypeHeader extends HeaderEnvelope {

	public ContentTypeHeader(String contentType) {
		super(new HttpHeader("Content-Type", contentType));
	}
}
