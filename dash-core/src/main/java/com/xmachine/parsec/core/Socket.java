package com.xmachine.parsec.core;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

import java.io.IOException;

public class Socket extends NanoWSD.WebSocket {

    public Socket(NanoHTTPD.IHTTPSession session) {
        super(session);
    }

    @Override
    protected void onOpen() {
        System.out.println("🟢 Parsec WebSocket Connected");
    }

    @Override
    protected void onClose(NanoWSD.WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
        System.out.println("🔴 Parsec WebSocket Closed: " + reason);
    }

    @Override
    protected void onMessage(NanoWSD.WebSocketFrame message) {
        String msg = message.getTextPayload();
        System.out.println("📨 From client: " + msg);
        try {
            send("Echo: " + msg); // Replace this with your telemetry stream
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void onPong(NanoWSD.WebSocketFrame pong) {}
    @Override public void onException(IOException e) { e.printStackTrace(); }
}
