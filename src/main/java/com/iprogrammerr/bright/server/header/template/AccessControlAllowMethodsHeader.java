package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class AccessControlAllowMethodsHeader extends HeaderEnvelope {

	public AccessControlAllowMethodsHeader(String value) {
		super(new HttpHeader("Access-Control-Allow-Methods", value));
	}
}
