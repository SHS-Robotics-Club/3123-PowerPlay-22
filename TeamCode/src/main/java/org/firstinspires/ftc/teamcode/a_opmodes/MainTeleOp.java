package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystemOther;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystemOtherOther;

//@Disabled
@Config
@TeleOp(name = "MainTeleOp", group = ".")
public class MainTeleOp extends CommandOpMode {
	@Override
	public void initialize() {
		if (isStopRequested()) return; // Exit program if stop requested??

		// Get Devices
		final Devices devices = new Devices(hardwareMap);

		// Gamepad
		GamepadEx gPad1 = new GamepadEx(gamepad1);

		// Define Systems ----------------------------------------------------------------------------------------------------
		DriveSubsystem          driveSubsystem = new DriveSubsystem(devices.frontLeft, devices.frontRight, devices.backLeft, devices.backRight);
		ClawSubsystem           claw           = new ClawSubsystem(devices.clawLeft, devices.clawRight);
		LiftSubsystemOtherOther lfoo           = new LiftSubsystemOtherOther(devices.lift);

		DriveCommand driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX, 1);
		LiftCommand  liftCommand  = new LiftCommand(lfoo, gPad1);

		register(driveSubsystem, claw, lfoo);

		// CONTROLS ----------------------------------------------------------------------------------------------------
		// X Button = Claw Open/Close
		gPad1.getGamepadButton(GamepadKeys.Button.X)
		     .whenPressed(new ConditionalCommand(
				     new InstantCommand(claw::open, claw),
				     new InstantCommand(claw::close, claw),
				     () -> {
					     claw.toggle();
					     return claw.isOpen();
				     }
		     ));

		// Register and Schedule ----------------------------------------------------------------------------------------------------
		schedule(driveCommand.alongWith(
				liftCommand,
				new RunCommand(() -> {
					// Telemetry
					telemetry.update();
					telemetry.addData("velo", devices.lift.getVelocities());
					telemetry.addData("pos", devices.lift.getPositions());
					telemetry.addData("calc", lfoo.calculate(500));
					telemetry.addData("dpadup", gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP));
				})));
	}
}
