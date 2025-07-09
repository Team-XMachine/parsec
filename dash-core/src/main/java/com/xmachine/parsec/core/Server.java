package com.xmachine.parsec.core;

import fi.iki.elonen.NanoWSD;
import fi.iki.elonen.NanoHTTPD;
import java.io.IOException;

public class Server extends NanoWSD {

    public Server(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("✅ Parsec Dashboard running on port " + port);
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake) {
        return new Socket(handshake);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String html = "<html><body><h1>Parsec Dashboard Online</h1></body></html>";
        return newFixedLengthResponse(html);
    }
}
