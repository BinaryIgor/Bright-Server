package com.iprogrammerr.bright.server.binary.type;

public final class VideoHttpTypes implements HttpTypes {

    private static final String[] VIDEO_TYPES = new String[] { "avi", "mpeg", "ogv", "webm", "3gp" };

    @Override
    public String type(String type) {
	if (know(type)) {
	    type = "video/" + type;
	}
	return type;
    }

    @Override
    public boolean know(String type) {
	for (String videoType : VIDEO_TYPES) {
	    if (videoType.equals(type)) {
		return true;
	    }
	}
	return false;
    }
}
