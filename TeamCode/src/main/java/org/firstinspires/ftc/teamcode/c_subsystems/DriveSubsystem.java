package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

public class DriveSubsystem extends SubsystemBase {
	private MotorEx frontLeft, frontRight, backLeft, backRight;
	private MecanumDrive mecanumDrive;

	/**
	 * @param frontLeft  The Front Left drive motor object.
	 * @param frontRight The Front Right drive motor object.
	 * @param backLeft   The Back Left drive motor object.
	 * @param backRight  The Back Right drive motor object.
	 */
	public DriveSubsystem(MotorEx frontLeft, MotorEx frontRight, MotorEx backLeft, MotorEx backRight) {
		this.frontLeft  = frontLeft;
		this.frontRight = frontRight;
		this.backLeft   = backLeft;
		this.backRight  = backRight;

		mecanumDrive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
	}

	//TODO: Test roadrunner driving and FTClib trajectories in teleop
	// Method which runs the subsystem
	public void drive(double strafeSpeed, double forwardSpeed, double turnSpeed) {
		mecanumDrive.driveRobotCentric(-strafeSpeed, -forwardSpeed, -turnSpeed, true);
	}
}