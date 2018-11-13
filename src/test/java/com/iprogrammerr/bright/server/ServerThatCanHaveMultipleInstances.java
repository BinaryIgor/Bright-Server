package com.iprogrammerr.bright.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.iprogrammerr.bright.server.test.ConditionalWait;

public final class ServerThatCanHaveMultipleInstances extends TypeSafeMatcher<BrightServer> {

	private static final Connection MOCKED_CONNECTION = s -> {
		try {
			s.close();
		} catch (Exception e) {

		}
	};
	private final Random random;

	public ServerThatCanHaveMultipleInstances(Random random) {
		this.random = random;
	}

	public ServerThatCanHaveMultipleInstances() {
		this(new Random());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(getClass().getSimpleName());
	}

	@Override
	protected boolean matchesSafely(BrightServer item) {
		boolean matched;
		try {
			int instances = 3 + this.random.nextInt(10);
			int count = 0;
			List<Server> servers = new ArrayList<>();
			servers.add(item);
			ConditionalWait wait = new ConditionalWait(100);
			for (int i = 0; i < instances; ++i) {
				Server server = new BrightServer(0, MOCKED_CONNECTION);
				servers.add(server);
				server.start();
				wait.waitUntil(() -> server.isRunning());
			}
			for (Server s : servers) {
				if (s.isRunning()) {
					++count;
				}
				s.stop();
			}
			matched = count == instances;
		} catch (Exception e) {
			matched = false;
		}
		return matched;
	}
}
