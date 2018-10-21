package com.iprogrammerr.bright.server.respondent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.iprogrammerr.bright.server.exception.ToCatchException;
import com.iprogrammerr.bright.server.method.DeleteMethod;
import com.iprogrammerr.bright.server.method.GetMethod;
import com.iprogrammerr.bright.server.mock.MockedRequest;
import com.iprogrammerr.bright.server.request.Request;
import com.iprogrammerr.bright.server.response.Response;
import com.iprogrammerr.bright.server.response.template.OkResponse;

public class PotentialRespondentTest {

    @Test
    public void canRespond() throws Exception {
	String pattern = "user/{id:long}/game?minPoints=int";
	Response response = new OkResponse();
	ConditionalRespondent respondent = new PotentialRespondent(pattern, new GetMethod(), req -> response);
	Request request = new MockedRequest("user/1/game?minPoints=11", "get");
	assertTrue(respondent.areConditionsMet(request));
	assertTrue(respondent.response(request).equals(response));
	pattern = "user/search/{threshold:double}";
	respondent = new PotentialRespondent(pattern, new DeleteMethod(), req -> response);
	request = new MockedRequest("user/search/11.6", "delete");
	assertTrue(respondent.areConditionsMet(request));
	assertTrue(respondent.response(request).equals(response));
    }

    @Test
    public void canRejectResponse() {
	ToCatchException toCatch = new ToCatchException();
	String pattern = "user/{id:long}/game?minPoints=int";
	ConditionalRespondent respondent = new PotentialRespondent(pattern, new GetMethod(), req -> new OkResponse());
	Request fr = new MockedRequest("user/1/game", "get");
	assertFalse(respondent.areConditionsMet(fr));
	assertTrue(toCatch.hasCatched(() -> respondent.response(fr)));
	Request sr = new MockedRequest("user/1/game?minPoints=33", "post");
	assertFalse(respondent.areConditionsMet(sr));
	assertTrue(toCatch.hasCatched(() -> respondent.response(sr)));
	Request tr = new MockedRequest("user/scale/game?minPoints=33", "get");
	assertFalse(respondent.areConditionsMet(tr));
	assertTrue(toCatch.hasCatched(() -> respondent.response(tr)));
    }

}
