package org.firstinspires.ftc.teamcode.linear;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@Config
public class HardwareTwo {
    public static final double KP = 0;
    public static final double KI = 0;
    public static final double KD = 0;
    public static double volt = Double.POSITIVE_INFINITY;
    public MotorEx frontLeft, frontRight, backLeft, backRight;

    public HardwareTwo(HardwareMap hwMap) {
        frontLeft = new MotorEx(hwMap, "frontLeft");
        frontRight = new MotorEx(hwMap, "frontRight");
        backLeft = new MotorEx(hwMap, "backLeft");
        backRight = new MotorEx(hwMap, "backRight");
        initMotors();
        initVoltageSensor(hwMap);
    }

    private void initMotors(){
        frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
        frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setVeloCoefficients(KP, KI, KD);
        
        frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
        frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setVeloCoefficients(KP, KI, KD);
        
        backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
        backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setVeloCoefficients(KP, KI, KD);
        
        backRight.setRunMode(MotorEx.RunMode.VelocityControl);
        backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setVeloCoefficients(KP, KI, KD);
    }

    private void initVoltageSensor(HardwareMap hwMap){
        for (VoltageSensor sensor : hwMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                volt = Math.min(volt, voltage);
            }
        }
    }
}