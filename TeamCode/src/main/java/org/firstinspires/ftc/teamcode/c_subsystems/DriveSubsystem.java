package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class DriveSubsystem extends SubsystemBase {
    private MotorEx frontLeft, frontRight, backLeft, backRight;
    private MecanumDrive mecanumDrive;

    public DriveSubsystem(MotorEx fL, MotorEx fR, MotorEx bL, MotorEx bR) {
        frontLeft = fL;
        frontRight = fR;
        backLeft = bL;
        backRight = bR;

        mecanumDrive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

    }

    // Method which runs the subsystem
    public void drive(double strafeSpeed, double forwardSpeed, double turnSpeed) {
        mecanumDrive.driveRobotCentric(-strafeSpeed, -forwardSpeed, -turnSpeed, true);
    }
}
