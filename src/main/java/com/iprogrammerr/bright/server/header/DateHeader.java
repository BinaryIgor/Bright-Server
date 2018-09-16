package com.iprogrammerr.bright.server.header;

import java.time.LocalDateTime;

public class DateHeader extends HeaderEnvelope {

    public DateHeader(LocalDateTime date) {
	super(new HttpHeader("Date", date.toString()));
    }

}
