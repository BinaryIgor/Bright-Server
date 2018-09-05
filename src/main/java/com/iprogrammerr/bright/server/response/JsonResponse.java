package com.iprogrammerr.bright.server.response;

import com.iprogrammerr.bright.server.constants.HeaderValue;
import com.iprogrammerr.bright.server.constants.ResponseCode;

public class JsonResponse extends ResponseEnvelope {


    public JsonResponse(int responseCode, String json) {
	super(new ContentResponse(responseCode, HeaderValue.JSON.getValue(), json.getBytes()));

    }
    
    public JsonResponse(ResponseCode responseCode, String json) {
	this(responseCode.getValue(), json);
    }
    

}
