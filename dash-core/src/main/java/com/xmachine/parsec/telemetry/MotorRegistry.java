package com.xmachine.parsec.telemetry;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MotorRegistry {
    private static final List<MotorTelemetry> motors = new ArrayList<>();

    public void addMotor(DcMotorEx motor) {
        motors.add(new MotorTelemetry(motor));
    }

    public static JSONArray toJsonArray() throws JSONException {
        JSONArray array = new JSONArray();
        for (MotorTelemetry m : motors) {
            array.put(m.toJson());
        }
        return array;
    }
}
