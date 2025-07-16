package com.xmachine.parsec.core;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.xmachine.parsec.telemetry.MotorRegistry;
import com.xmachine.parsec.telemetry.RobotPose;

public class Dashboard {

    static RobotPose robotPose;
    static MotorRegistry motorRegistry;

    public static void init(int port) {
        robotPose = new RobotPose();
        motorRegistry = new MotorRegistry();
        new Thread(() -> {
            try {
                new Server(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ).start();
    }

    public static void setRobotPose(double x, double y, double heading) {
        robotPose.setPose(x, y, heading);
    }

    public static void addMotor(DcMotorEx motor) {
        motorRegistry.addMotor(motor);
    }
}
