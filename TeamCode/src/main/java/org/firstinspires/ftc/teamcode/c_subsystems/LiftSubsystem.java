package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;

@Config
public class LiftSubsystem extends SubsystemBase {

	//Define Whatever These Mean
	public static final double kS = 0, kG = 0, kV = 0, kA = 0;
	public static final double kP = 0, kI = 0, kD = 0, kF = 0;
	// Create a new ElevatorFeedforward
	ElevatorFeedforward feedforward = new ElevatorFeedforward(kS, kG, kV, kA);
	PIDFController      pidf        = new PIDFController(kP, kI, kD, kF);

	//Froot Loop

	@Override
	public void periodic() {
		feedforward.calculate(10, 20);
	}
}

