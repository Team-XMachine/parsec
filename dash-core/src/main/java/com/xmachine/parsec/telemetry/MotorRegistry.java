package com.xmachine.parsec.telemetry;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MotorRegistry {
    private static final Map<String, MotorInfo> motors = new HashMap<>();

    public void addMotor(DcMotorEx motor) {
        String name = motor.getDeviceName();
        motors.put(name, new MotorInfo(motor));
    }

    public static JSONArray toJsonArray() throws JSONException {
        JSONArray array = new JSONArray();
        for (MotorInfo info : motors.values()) {
            array.put(info.toJson());
        }
        return array;
    }

    public Map<String, MotorInfo> getMotors() {
        return motors;
    }
}
