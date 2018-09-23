package com.iprogrammerr.bright.server.binary.type;

public final class StaticHttpTypes implements HttpTypes {

    private static final String[] APPLICATION_TYPES = new String[] { "json", "pdf", "rar", "tar", "zip", "sh", "xhtml",
	    "xml", "abw", "csh", "doc", "epub", "jar", "ods", "ogx", "ppt", "xls" };
    private static final String[] IMAGE_TYPES = new String[] { "jpg", "png", "gif", "svg", "bmp", "ico", "tif", "tiff",
	    "webp" };

    @Override
    public String type(String type) {
	if (type.equals("html") || type.equals("css")) {
	    type = "text/" + type;
	} else if (type.equals("txt")) {
	    type = "text/plain";
	} else if (type.equals("js")) {
	    type = "application/javascript";
	} else if (application(type)) {
	    type = "application/" + type;
	} else if (image(type)) {
	    type = "image/" + type;
	}
	return type;
    }

    private boolean application(String type) {
	for (String applicationType : APPLICATION_TYPES) {
	    if (applicationType.equals(type)) {
		return true;
	    }
	}
	return false;
    }

    private boolean image(String type) {
	for (String imageType : IMAGE_TYPES) {
	    if (imageType.equals(type)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean know(String type) {
	return type.equals("html") || type.equals("css") || type.equals("txt") || type.equals("js") || application(type)
		|| image(type);
    }

}
