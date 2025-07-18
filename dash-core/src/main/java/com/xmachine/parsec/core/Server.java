package com.xmachine.parsec.core;

import android.annotation.SuppressLint;

import com.xmachine.parsec.telemetry.MotorRegistry;
import com.xmachine.parsec.telemetry.RobotPose;

import org.json.JSONException;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends NanoWSD {

    private final Set<Socket> sockets = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public Server(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("running on port " + port);
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake) {
        Socket socket = new Socket(handshake, this);
        sockets.add(socket);
        return socket;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        try {
            if (uri.equals("/")) {
                return serveFile("dashboard/index.html", "text/html");
            } else if (uri.endsWith(".css")) {
                return serveFile("dashboard/style.css", "text/css");
            } else if (uri.endsWith(".js")) {
                return serveFile("dashboard/script.js", "application/javascript");
            } else {
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "404 Not Found");
            }
        } catch (IOException e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "500 Internal Server Error");
        }
    }

    private Response serveFile(String path, String mimeType) throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(path);
        if (stream == null) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, mimeType, "404 Not Found");
        }

        @SuppressLint({"NewApi", "LocalSuppress"}) byte[] bytes = readAllBytes(stream);
        return newFixedLengthResponse(Response.Status.OK, mimeType, new ByteArrayInputStream(bytes), bytes.length);
    }


    protected void removeSocket(Socket socket) {
        sockets.remove(socket);
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
}
