package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.ArmCommand;
import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.b_commands.LynxCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

import java.util.List;

// @Disabled
@TeleOp(name = "CommandTeleOp", group = ".")
@Config
public class MainTeleOp extends CommandOpMode {
	@Override
	public void initialize() {
		// DEFINITIONS ----------------------------------------------------------------------------------------------------
		// Get Devices
		final Devices devices = new Devices(hardwareMap);
		
		// Gamepad
		GamepadEx gPad1 = new GamepadEx(gamepad1);
		
		// Subsystems
		DriveSubsystem driveSubsystem = new DriveSubsystem(devices.frontLeft, devices.frontRight, devices.backLeft, devices.backRight, devices.revIMU);
		ClawSubsystem  claw           = new ClawSubsystem(devices.clawLeft, devices.clawRight);
		//ArmSubsystem   armSubsystem   = new ArmSubsystem(devices.arm);
		LiftSubsystem  liftSubsystem  = new LiftSubsystem(devices.lift);
		
		// Commands
		DriveCommand driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX);
		//ArmCommand   armCommand   = new ArmCommand(armSubsystem, gPad1);
		
		// CONTROLS ----------------------------------------------------------------------------------------------------
		// X Button = Claw Open/Close
		gPad1.getGamepadButton(GamepadKeys.Button.X)
				.whenPressed(new ConditionalCommand(
						new InstantCommand(claw::open, claw),
						new InstantCommand(claw::close, claw),
						() -> {
							claw.toggle();
							return claw.active();
						}
				));

		double value = 2000;

		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
				.whenPressed(new InstantCommand(() -> liftSubsystem.liftPos(0)));
		
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
				.whenPressed(new InstantCommand(() -> liftSubsystem.liftPos((int) (value*(1/3)))));
		
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
				.whenPressed(new InstantCommand(() -> liftSubsystem.liftPos((int) (value*2/3))));
		
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
				.whenPressed(new InstantCommand(() -> liftSubsystem.liftPos((int) value)));
		
		// REGISTER/SCHEDULE ----------------------------------------------------------------------------------------------------
		// Rev Hubs Lynx
		LynxCommand lynxCommand = new LynxCommand(devices.revHubs);
		schedule(lynxCommand);
		
		// Default Drivetrain Command
		register(driveSubsystem);
		driveSubsystem.setDefaultCommand(driveCommand);

		//register(armSubsystem);
		//armSubsystem.setDefaultCommand(armCommand);

		schedule(new RunCommand(() -> {
			// Telemetry
			telemetry.update();
			idle();
		}));

		if (isStopRequested()) {
			return;
		}
	}
	
}