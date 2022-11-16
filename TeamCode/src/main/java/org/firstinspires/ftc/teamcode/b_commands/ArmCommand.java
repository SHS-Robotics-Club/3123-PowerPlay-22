package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.ArmSubsystem;

public class ArmCommand extends CommandBase {
	private final ArmSubsystem subsystem;
	private double pos = 0;
	private GamepadEx gPad1;
	
	public ArmCommand(ArmSubsystem subsystem, GamepadEx gPad1) {
		this.subsystem = subsystem;
		this.gPad1     = gPad1;
		
		addRequirements(subsystem);
	}
	
	@Override
	public void execute() {
		boolean lB = gPad1.getButton(GamepadKeys.Button.LEFT_BUMPER);
		boolean rB = gPad1.getButton(GamepadKeys.Button.RIGHT_BUMPER);
		
		if (gPad1.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
			pos += 10;
		} else if (gPad1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
			pos -= 10;
		}
		
		subsystem.armPit((int) pos);
	}
}
