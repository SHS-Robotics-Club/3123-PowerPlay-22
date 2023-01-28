package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;


public class LiftCommand extends CommandBase {
	public static double positionTolerance = 1.0, velo = 0.0, accel = 0.0;
	GamepadEx     gamepadEx;
	LiftSubsystem lift;
	LiftStates    liftStates;
	LiftLevels liftLevels;

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
		this.liftLevels = liftLevels;
	}

	@Override
	public void execute() {
		lift.getPosition();
		lift.setSetPoint(liftLevels.getLevelPos());
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
					lift.set(lift.calculate(0));
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
		FLOOR(0), LOW(2000), MED(2990), HIGH(3100);

		private final int levelPos;

		LiftLevels(int levelPos) {
			this.levelPos = levelPos;
		}

		public int getLevelPos() {
			return levelPos;
		}
	}
}
