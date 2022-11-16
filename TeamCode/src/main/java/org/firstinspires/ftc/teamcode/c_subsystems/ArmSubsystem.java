package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

@Config
public class ArmSubsystem extends SubsystemBase {
	private MotorEx arm;
	private static final double gearDeg = (360.0 / 537.7) / 0.22857;
	public static final double posCoef = 0.05, posTol = 13.6;
	public static final double kI = 0, kD = 0;
	
	public ArmSubsystem(MotorEx arm) {
		this.arm = arm;
	}
	
	public void armPit(int pos) {
		// set and get the position coefficient
		arm.setPositionCoefficient(posCoef); //Default: 0.05
		double kP = arm.getPositionCoefficient();
		
		arm.setVeloCoefficients(kP, kI, kD);
		
		// set the target position
		arm.setTargetPosition((int) (pos / gearDeg));      // an integer representing desired tick count Default: 1200
		
		arm.set(0);
		
		// set the tolerance
		arm.setPositionTolerance(posTol);   // allowed maximum error Default: 13.6
		
		// perform the control loop
		while (!arm.atTargetPosition()) {
			arm.set(0.25);
		}
		arm.stopMotor(); // stop the motor
	}
}
