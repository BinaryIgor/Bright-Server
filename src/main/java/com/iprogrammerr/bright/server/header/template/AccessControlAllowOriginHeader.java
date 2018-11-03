package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class AccessControlAllowOriginHeader extends HeaderEnvelope {

	public AccessControlAllowOriginHeader(String value) {
		super(new HttpHeader("Access-Control-Allow-Origin", value));
	}
}
