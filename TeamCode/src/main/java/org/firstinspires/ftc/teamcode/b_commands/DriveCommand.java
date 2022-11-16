package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
	private final DriveSubsystem mecDrive;
	//private final DoubleSupplier m_strafe, m_forward, m_turn;
	private final DoubleSupplier m_forward, m_turn;
	private final GamepadEx gPad1;
	private double multiplier;
	
/*	public DriveCommand(DriveSubsystem subsystem, DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier turn, double mult) {
		mecDrive   = subsystem;
		m_strafe   = strafe;
		m_forward  = forward;
		m_turn     = turn;
		multiplier = mult;
		
		addRequirements(subsystem);
	}
	
	public DriveCommand(DriveSubsystem subsystem, DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier turn) {
		mecDrive   = subsystem;
		m_strafe   = strafe;
		m_forward  = forward;
		m_turn     = turn;
		multiplier = 1.0;
		
		addRequirements(subsystem);
	}*/
	
	public DriveCommand(DriveSubsystem subsystem, DoubleSupplier forward, DoubleSupplier turn, GamepadEx gPad1) {
		mecDrive   = subsystem;
		m_forward  = forward;
		m_turn     = turn;
		this.gPad1 = gPad1;
		multiplier = 1.0;
		
		addRequirements(subsystem);
	}
	
	@Override
	public void execute() {
		double lT = gPad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
		double rT = gPad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
		
		double strafe = (lT - rT);
		
		mecDrive.drive(/*m_strafe.getAsDouble()*/ strafe * 0.9 * multiplier,
				m_forward.getAsDouble() * 0.9 * multiplier,
				m_turn.getAsDouble() * 0.88 * multiplier);
	}
}
