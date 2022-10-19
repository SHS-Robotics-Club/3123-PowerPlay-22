package org.firstinspires.ftc.teamcode.c_subsystems.teleop;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class TestDriveSubsystem extends SubsystemBase {
    private MotorEx frontLeft, frontRight, backLeft, backRight;
    private double frontLeftPower, frontRightPower, backLeftPower, backRightPower;
    private MecanumDrive mecanumDrive;

    public TestDriveSubsystem(MotorEx fL, MotorEx fR, MotorEx bL, MotorEx bR, RevIMU imu){
        frontLeft = fL;
        frontRight = fR;
        backLeft = bL;
        backRight = bR;

        //mecanumDrive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

    }

    // Method which runs the subsystem
    public void drive(double strafe, double forward, double turn) {
        //mecanumDrive.driveRobotCentric(-strafeSpeed, -forwardSpeed, -turnSpeed, true);
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(turn), 1);
        frontLeftPower = (forward + strafe + turn) / denominator;
        backLeftPower = (forward - strafe + turn) / denominator;
        frontRightPower = (forward - strafe - turn) / denominator;
        backRightPower = (forward + strafe - turn) / denominator;

        frontLeft.set(frontLeftPower);
        backLeft.set(backLeftPower);
        frontRight.set(frontRightPower);
        backRight.set(backRightPower);

    }
}
