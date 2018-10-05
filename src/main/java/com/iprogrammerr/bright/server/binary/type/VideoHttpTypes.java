package com.iprogrammerr.bright.server.binary.type;

public final class VideoHttpTypes implements HttpTypes {

    private static final String[] VIDEO_TYPES = new String[] { "avi", "mpeg", "ogv", "webm", "3gp" };

    @Override
    public String type(String type) {
	if (isKnown(type)) {
	    type = "video/" + type;
	}
	return type;
    }

    @Override
    public boolean isKnown(String type) {
	for (String vt : VIDEO_TYPES) {
	    if (vt.equals(type)) {
		return true;
	    }
	}
	return false;
    }
}
