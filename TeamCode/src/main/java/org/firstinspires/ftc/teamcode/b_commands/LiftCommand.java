package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.LiftState;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.archive.LiftSubsystemOtherOther;

public class LiftCommand extends CommandBase {
	private LiftSubsystem liftSubsystem;
	private GamepadEx     gamepadEx;

	public LiftCommand(LiftSubsystem liftSubsystem, GamepadEx gamepadEx) {
		this.liftSubsystem = liftSubsystem;
		this.gamepadEx     = gamepadEx;

	}

	@Override
	public void execute() {
		if (gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
			liftSubsystem.set(LiftState.MID);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
			liftSubsystem.set(LiftState.HIGH);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_DOWN)) {
			liftSubsystem.set(LiftState.FLOOR);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT)) {
			liftSubsystem.set(LiftState.LOW);
		}
	}
}