package com.xmachine.parsec.core;

import com.xmachine.parsec.telemetry.MotorInfo;
import com.xmachine.parsec.telemetry.MotorRegistry;
import com.xmachine.parsec.telemetry.RobotPose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TelemetryBuilder {

    public static String build(MotorRegistry motorRegistry, RobotPose pose) throws JSONException {
        JSONObject telemetry = new JSONObject();

        JSONArray motorsArray = new JSONArray();
        for (String name : motorRegistry.getMotors().keySet()) {
            MotorInfo mi = motorRegistry.getMotors().get(name);
            JSONObject motor = new JSONObject();
            motor.put("name", name);
            motor.put("power", mi.power);
            motor.put("velocity", mi.velocity);
            motor.put("position", mi.position);
            motorsArray.put(motor);
        }
        telemetry.put("motors", motorsArray);

        JSONObject robot = new JSONObject();
        robot.put("x", pose.x);
        robot.put("y", pose.y);
        robot.put("heading", pose.heading);
        telemetry.put("pose", robot);

        return telemetry.toString();
    }
}
