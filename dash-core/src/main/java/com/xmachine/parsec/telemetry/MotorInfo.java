package com.xmachine.parsec.telemetry;

import com.qualcomm.robotcore.hardware.DcMotorEx;

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
}
