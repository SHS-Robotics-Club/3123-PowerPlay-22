package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.archive.LiftSubsystemOtherOther;

public class LiftCommand extends CommandBase {
	private              LiftSubsystemOtherOther liftSubsystem;
	private static final int[]         liftPositions = {0, 500, 1500, 3000};
	private              GamepadEx     gamepadEx;

	public LiftCommand(LiftSubsystemOtherOther liftSubsystem, GamepadEx gamepadEx) {
		this.liftSubsystem = liftSubsystem;
		this.gamepadEx     = gamepadEx;

	}

	@Override
	public void execute() {
		if (gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
			liftSubsystem.set(liftPositions[2]);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
			liftSubsystem.set(liftPositions[1]);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_DOWN)) {
			liftSubsystem.set(liftPositions[0]);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT)) {
			liftSubsystem.set(liftPositions[3]);
		}
	}
}