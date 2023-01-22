package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

import java.util.List;

@Config
public class LiftSubsystem extends SubsystemBase {

	List<Double> liftVelo;
	Double targetPosition;
	MotorGroup lift;

	//Define Whatever These Mean
	public static final double kS = 0, kG = 0, kV = 0, kA = 0;
	public static final double kP = 0, kI = 0, kD = 0, kF = 0;
	public static final double velocitySetpoint = 10, accelSetpoint = 20;

	// Create a new ElevatorFeedforward
	ElevatorFeedforward feedforward = new ElevatorFeedforward(kS, kG, kV, kA);
	PIDFController      pidf        = new PIDFController(kP, kI, kD, kF);
	double ffOut, pidfOut;

	public LiftSubsystem(MotorGroup lift) {
		this.lift = lift;
		liftVelo = lift.getVelocities();
	}

	//Froot Loop
	@Override
	public void periodic() {
		ffOut = feedforward.calculate(velocitySetpoint, accelSetpoint);
		pidf.setF(ffOut);
		pidfOut = pidf.calculate(liftVelo.get(0), targetPosition);
	}

	public void liftSet (double targetPosition) {
		this.targetPosition = targetPosition;
	}

	public void liftStop (){
		lift.set(0);
		lift.stopMotor();
	}
}

