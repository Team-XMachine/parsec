package com.xmachine.parsec.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.json.JSONException;
import org.json.JSONObject;

public class MotorTelemetry {
    private final DcMotorEx motor;

    public MotorTelemetry(DcMotorEx motor) {
        this.motor = motor;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", motor.getDeviceName());
        json.put("power", motor.getPower());
        json.put("velocity", motor.getVelocity()); // se disponível
        json.put("position", motor.getCurrentPosition());
        return json;
    }
}
