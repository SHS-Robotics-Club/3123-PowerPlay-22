package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

@Config
public class LiftSubsystemOther extends SubsystemBase {

	List<Double> liftVelo, liftPos;
	MotorGroup lift;

	//Define Whatever These Mean
	public static double kS = 0.0, kG = 0.0, kV = 0.0, kA = 0.0;
	public static double kP = 0.0, kI = 0.0, kD = 0.0, kF = 0.0;
	public static double tolerance = 10.0, velo = 10.0, accel = 20.0;

	// Create a new ElevatorFeedforward
	ElevatorFeedforward feedforward = new ElevatorFeedforward(kS, kG, kV, kA);
	PIDFController      pidf        = new PIDFController(kP, kI, kD, kF);
	double              ffOut, pidfOut;

	public LiftSubsystemOther(MotorGroup lift) {
		this.lift = lift;
		liftVelo  = lift.getVelocities();
		liftPos   = lift.getPositions();
	}

	public double calculate(double velocitySetpoint, double accelSetpoint, double targetPosition) {
		ffOut = feedforward.calculate(velocitySetpoint, accelSetpoint);
		pidf.setF(ffOut);
		pidfOut = pidf.calculate(liftVelo.get(0), targetPosition);
		return pidfOut;
	}

	public void set(double targetPosition) {
		double lowPos = targetPosition - tolerance, highPos = targetPosition + tolerance;
		while (liftPos.get(0) >= lowPos && liftPos.get(0) <= highPos) {
			double thong = calculate(velo, accel, targetPosition);
			lift.set(thong);
		}
	}

	public void stop() {
		lift.set(0);
		lift.stopMotor();
	}
}

