package com.iprogrammerr.bright.server.exception;

public final class ToCatchException {

	public boolean hasCatched(UnreliableFunction function) {
		boolean catched = false;
		try {
			function.apply();
		} catch (Exception e) {
			catched = true;
		}
		return catched;
	}
}
