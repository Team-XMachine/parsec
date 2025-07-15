package com.xmachine.parsec.telemetry;

import org.json.JSONException;
import org.json.JSONObject;

public class RobotPose {
    public static double x, y, heading;

    public void setPose(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public static JSONObject toJson() throws JSONException {
        JSONObject pose = new JSONObject();
        pose.put("x", x);
        pose.put("y", y);
        pose.put("heading", heading);
        return pose;
    }
}