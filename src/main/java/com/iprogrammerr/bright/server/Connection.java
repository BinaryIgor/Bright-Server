package com.iprogrammerr.bright.server;

import java.net.Socket;

public interface Connection {

    Runnable handle(Socket socket);
}
