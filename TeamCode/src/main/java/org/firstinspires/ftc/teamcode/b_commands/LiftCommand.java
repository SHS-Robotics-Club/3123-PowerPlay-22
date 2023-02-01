package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;


public class LiftCommand extends CommandBase {
	public static double     positionTolerance = 1.0;
	public static int        lowerAmmount      = 100;
	public static LiftLevels liftLevels        = LiftLevels.FLOOR;
	GamepadEx     gamepadEx;
	LiftSubsystem lift;
	LiftStates    liftStates;

	public LiftCommand(LiftSubsystem liftSubsystem, GamepadEx gamepadEx) {
		lift           = liftSubsystem;
		this.gamepadEx = gamepadEx;
		addRequirements(liftSubsystem);
	}

	@Override
	public void initialize() {
		liftStates = LiftStates.READY;
		liftLevels = LiftLevels.FLOOR;
		lift.setTolerance(positionTolerance);
		lift.setSetPoint(0);
		lift.stop();
	}

	public void setLiftLevels(LiftLevels liftLevels) {
		LiftCommand.liftLevels = liftLevels;
	}

	@Override
	public void execute() {
		lift.getPosition();
		lift.setSetPoint(liftLevels.getLevelPos(lift.getLower()));
		switch (liftStates) {
			case READY:
				if (lift.atSetPoint()) {
					lift.stop();
				}
				if (!lift.atSetPoint()) {
					liftStates = LiftStates.MOVING;
				}
			case MOVING:
				if (!lift.atSetPoint()) {
					//TODO: Set tp velocity control?
					lift.set(lift.calculate(50));
				}
				if (lift.atSetPoint()) {
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
