package org.firstinspires.ftc.teamcode.c_subsystems.archive;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

import java.util.List;

@Config
public class LiftSubsystemOtherOther extends SubsystemBase {

	List<Double> liftVelo, liftPos;
	MotorGroup lift;

	//Define Whatever These Mean
	public static double kP = 0.0, kI = 0.0, kD = 0.0;
	public static double tolerance = 10.0, velo = 10.0, accel = 20.0;

	// Create a new ElevatorFeedforward
	PIDFController pidf = new PIDController(kP, kI, kD);
	double         pidfOut;

	public LiftSubsystemOtherOther(MotorGroup lift) {
		this.lift = lift;
		liftVelo  = lift.getVelocities();
		liftPos   = lift.getPositions();
	}

	public double calculate(double targetPosition) {
		pidfOut = pidf.calculate(liftPos.get(0), targetPosition);
		return pidfOut;
	}

	public void set(double targetPosition) {
		lift.set(0);
		while (!pidf.atSetPoint()) {
			lift.set(calculate(targetPosition));
		}
		lift.stopMotor();
	}
}

