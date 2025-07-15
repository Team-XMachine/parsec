package com.xmachine.parsec.core;

import com.xmachine.parsec.telemetry.RobotPose;
import com.xmachine.parsec.telemetry.MotorRegistry;

import org.json.JSONObject;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

import java.io.IOException;

public class Socket extends NanoWSD.WebSocket {

    private final Server server;

    public Socket(NanoHTTPD.IHTTPSession session, Server server) {
        super(session);
        this.server = server;
    }

    @Override
    protected void onOpen() {
        new Thread(() -> {
            while (isOpen()) {
                try {
                    JSONObject telemetry = new JSONObject();
                    telemetry.put("type", "telemetry");
                    telemetry.put("pose", RobotPose.toJson());
                    telemetry.put("motors", MotorRegistry.toJsonArray());
                    send(telemetry.toString());
                    Thread.sleep(200); // 5 FPS
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void onClose(NanoWSD.WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
        System.out.println("🔴 Parsec WebSocket Closed: " + reason);
        server.removeSocket(this);
    }

    @Override
    protected void onMessage(NanoWSD.WebSocketFrame message) {
        String msg = message.getTextPayload();
        System.out.println("📨 From client: " + msg);

        // Here you can handle client messages if needed
        try {
            send("Echo: " + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String json) {
        try {
            send(json);
        } catch (IOException e) {
            System.err.println("❌ Failed to send data to client");
            e.printStackTrace();
        }
    }

    @Override public void onPong(NanoWSD.WebSocketFrame pong) {}
    @Override public void onException(IOException e) { e.printStackTrace(); }
}
