package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class AccessControlAllowHeadersHeader extends HeaderEnvelope {

	public AccessControlAllowHeadersHeader(String value) {
		super(new HttpHeader("Access-Control-Allow-Headers", value));
	}
}
