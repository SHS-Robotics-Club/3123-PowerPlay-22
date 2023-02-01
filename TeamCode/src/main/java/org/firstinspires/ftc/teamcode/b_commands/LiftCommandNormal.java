package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystemNormal;


@Disabled
public class LiftCommandNormal extends CommandBase {
	public static double kP = 0.01, kI = 0.0, kD = 0.0001, kS = 0.0, kV = 0.0, kA = 0.0;
	public static double     positionTolerance = 1.0;
	public static int        lowerAmmount      = 100;
	public static LiftLevels liftLevels        = LiftLevels.FLOOR;
	LiftSubsystemNormal lift;
	LiftStates    liftStates;

	public LiftCommandNormal(LiftSubsystemNormal liftSubsystem) {
		lift           = liftSubsystem;
		addRequirements(liftSubsystem);
	}

	@Override
	public void initialize() {
		liftStates = LiftStates.READY;
		liftLevels = LiftLevels.FLOOR;
		lift.setTolerance(positionTolerance);
		lift.setCoeff(kP);
		lift.setTargetPosition(0);
		lift.stop();
	}

	public void setLiftLevels(LiftLevels liftLevels) {
		LiftCommandNormal.liftLevels = liftLevels;
	}

	@Override
	public void execute() {
		lift.getPosition();
		lift.setTargetPosition(liftLevels.getLevelPos(lift.getLower()));
		switch (liftStates) {
			case READY:
				if (lift.atTargetPosition()) {
					lift.stop();
				}
				if (!lift.atTargetPosition()) {
					liftStates = LiftStates.MOVING;
				}
			case MOVING:
				if (!lift.atTargetPosition()) {
					//TODO: Set tp velocity control?
					lift.set(1);
				}
				if (lift.atTargetPosition()) {
					liftStates = LiftStates.READY;
				}
		}
	}

	enum LiftStates {
		READY, MOVING
	}

	public enum LiftLevels {
		FLOOR(0, 1.0), LOW(2000, 1.0), MED(2990, 0.9), HIGH(3265, 0.8);

		private final int    levelPos;
		private final double driveMult;

		LiftLevels(int levelPos, double driveMult) {
			this.levelPos  = levelPos;
			this.driveMult = driveMult;
		}

		public int getLevelPos(boolean lower) {
			if (lower) {
				return levelPos - lowerAmmount;
			} else {
				return levelPos;
			}
		}

		public double getDriveMult() {
			return driveMult;
		}
	}
}
