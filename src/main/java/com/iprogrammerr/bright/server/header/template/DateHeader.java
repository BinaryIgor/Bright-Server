package com.iprogrammerr.bright.server.header.template;

import java.time.LocalDateTime;

import com.iprogrammerr.bright.server.header.HeaderEnvelope;
import com.iprogrammerr.bright.server.header.HttpHeader;

public final class DateHeader extends HeaderEnvelope {

    public DateHeader(LocalDateTime date) {
	super(new HttpHeader("Date", date.toString()));
    }

}
