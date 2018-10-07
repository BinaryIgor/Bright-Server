package com.iprogrammerr.bright.server.header.template;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class DateHeader extends HeaderEnvelope {

    public DateHeader(String date) {
	super(new HttpHeader("Date", date));
    }

}
