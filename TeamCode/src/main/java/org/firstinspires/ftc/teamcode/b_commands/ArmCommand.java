package org.firstinspires.ftc.teamcode.b_commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;
@Config
public class ArmCommand extends CommandBase {
	private final ArmSubsystem subsystem;
	private GamepadEx gPad1;
	public static final double posCoef = 0.05, tolerance = 13.6;

	public ArmCommand(ArmSubsystem subsystem, GamepadEx gPad1) {
		this.subsystem = subsystem;
		this.gPad1	= gPad1;
		
		addRequirements(subsystem);
	}
	
	@Override
	public void execute() {
		double lT = gPad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
		double rT = gPad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);

		double position = (lT - rT) * 60;

		subsystem.armPit(posCoef, (int) position, tolerance);
	}
}
