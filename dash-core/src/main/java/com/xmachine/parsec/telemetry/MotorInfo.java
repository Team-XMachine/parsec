package com.xmachine.parsec.telemetry;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.json.JSONException;
import org.json.JSONObject;

public class MotorInfo {
    private final DcMotorEx motor;

    public double power;
    public double velocity;
    public double position;

    public MotorInfo(DcMotorEx motor) {
        this.motor = motor;
    }

    public void update() {
        this.power = motor.getPower();
        this.velocity = motor.getVelocity(); // Requires RUN_USING_ENCODER
        this.position = motor.getCurrentPosition();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("power", motor.getPower());
        json.put("velocity", motor.getVelocity());
        json.put("position", motor.getCurrentPosition());
        return json;
    }
}
