package com.xmachine.parsec.core;

public class Dashboard {
    private static Server server;

    public static void init() {
        try {
            server = new Server(8082); // change if needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
