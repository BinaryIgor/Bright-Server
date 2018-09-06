package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.header.JsonContentTypeHeader;

public class JsonResponse extends ResponseEnvelope {


    public JsonResponse(int responseCode, String json) {
	super(new ContentResponse(responseCode, new JsonContentTypeHeader(), json.getBytes()));

    }
    
    

}
