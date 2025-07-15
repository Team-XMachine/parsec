package com.xmachine.parsec.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.xmachine.parsec.telemetry.MotorRegistry;
import com.xmachine.parsec.telemetry.RobotPose;

public class Dashboard {

    static RobotPose robotPose;
    static MotorRegistry motorRegistry;
    public static void init() {
        robotPose = new RobotPose();
        motorRegistry = new MotorRegistry();
        try {
            new Server(8082);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setRobotPose(double x, double y, double heading) {
        robotPose.setPose(x, y, heading);
    }

    public static void addMotor(DcMotorEx motor) {
        motorRegistry.addMotor(motor);
    }
}
