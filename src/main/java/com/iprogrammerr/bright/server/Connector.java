package com.iprogrammerr.bright.server;

import java.net.Socket;

public interface Connector {
    Runnable plug(Socket socket);
}
