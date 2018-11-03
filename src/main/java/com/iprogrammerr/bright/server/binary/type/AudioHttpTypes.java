package com.iprogrammerr.bright.server.binary.type;

public final class AudioHttpTypes implements HttpTypes {

	private static final String[] AUDIO_TYPES = new String[] { "aac", "mpeg", "mid", "midi", "oga",
			"wav", "weba", "3gp", "3g2" };

	@Override
	public String type(String type) {
		if (isKnown(type)) {
			type = "audio/" + type;
		}
		return type;
	}

	@Override
	public boolean isKnown(String type) {
		for (String at : AUDIO_TYPES) {
			if (at.equals(type)) {
				return true;
			}
		}
		return false;
	}
}
