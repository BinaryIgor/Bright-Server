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
	} else if (isApplication(type)) {
	    type = "application/" + type;
	} else if (isImage(type)) {
	    type = "image/" + type;
	}
	return type;
    }

    private boolean isApplication(String type) {
	return isType(type, APPLICATION_TYPES);
    }

    private boolean isImage(String type) {
	return isType(type, IMAGE_TYPES);
    }

    private boolean isType(String type, String[] types) {
	for (String t : types) {
	    if (t.equals(type)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public boolean isKnown(String type) {
	return type.equals("html") || type.equals("css") || type.equals("txt") || type.equals("js")
		|| isApplication(type) || isImage(type);
    }

}
