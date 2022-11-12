package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;


public class ArmSubsystem extends SubsystemBase {
	private MotorEx arm;
	private static final double gbTicksDeg = (360.0 / 537.7);

	public ArmSubsystem(MotorEx arm) {
		this.arm  = arm;
	}

	public void armPit(double posCoef, int pos, double tolerance) {
		// set and get the position coefficient
		arm.setPositionCoefficient(posCoef); //Default: 0.05
		double kP = arm.getPositionCoefficient();

		// set the target position
		arm.setTargetPosition((int) (pos / gbTicksDeg));      // an integer representing
		// desired tick count Default: 1200

		arm.set(0);

		// set the tolerance
		arm.setPositionTolerance(tolerance);   // allowed maximum error Default: 13.6

		// perform the control loop
		while (!arm.atTargetPosition()) {
			arm.set(0.75);
		}
		arm.stopMotor(); // stop the motor
	}
}
