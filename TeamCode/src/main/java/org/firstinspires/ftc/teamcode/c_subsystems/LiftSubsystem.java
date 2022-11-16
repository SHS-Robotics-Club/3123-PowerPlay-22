package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

@Config
public class LiftSubsystem extends SubsystemBase {
	private MotorGroup lift;
	private static final double gearDeg = (360.0 / 537.7);
	public static final double posCoef = 0.05, posTol = 13.6;
	public static final double kI = 0, kD = 0;
	
	public LiftSubsystem(MotorGroup lift) {
		this.lift = lift;
	}
	
	public void liftPos(int pos) {
		// set and get the position coefficient
		lift.setPositionCoefficient(posCoef);   //Default: 0.05
		double kP = lift.getPositionCoefficient();
		
		lift.setVeloCoefficients(kP, kI, kD);
		
		// set the target position
		lift.setTargetPosition((int) (pos / gearDeg));  // an integer representing desired tick count
		
		lift.set(0);
		
		// set the tolerance
		lift.setPositionTolerance(posTol);  // allowed maximum error Default: 13.6
		
		// perform the control loop
		while (!lift.atTargetPosition()) {
			lift.set(0.5);
		}
		lift.stopMotor();   // stop the motor
	}
	
}
