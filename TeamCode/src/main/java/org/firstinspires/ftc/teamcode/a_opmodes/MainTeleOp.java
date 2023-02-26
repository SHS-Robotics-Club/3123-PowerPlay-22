package org.firstinspires.ftc.teamcode.a_opmodes;

import static org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem.liftLevels;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.GamepadTrigger;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.MecanumDrive;

//@Disabled
@Config
@TeleOp(name = "MainTeleOp", group = ".")
public class MainTeleOp extends CommandOpMode {
	@Override
	public void initialize() {
		if (isStopRequested()) return;

		// Get Devices
		final Devices devices = new Devices(hardwareMap);

		// Gamepad
		GamepadEx gPad1 = new GamepadEx(gamepad1);

		// Define Systems ----------------------------------------------------------------------------------------------------
		MecanumSubsystem drive = new MecanumSubsystem(new MecanumDrive(hardwareMap), true);
		ClawSubsystem    claw  = new ClawSubsystem(devices.clawLeft, devices.clawRight);
		LiftSubsystem    lift  = new LiftSubsystem(devices.lift, devices.spool);

		MecanumCommand driveCommand = new MecanumCommand(drive, gPad1::getLeftY, gPad1::getLeftX, gPad1::getRightX, liftLevels.getDriveMult());

		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

		// CONTROLS ----------------------------------------------------------------------------------------------------
		// X Button = Claw Open/Close
		gPad1.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ConditionalCommand(new InstantCommand(claw::open, claw), new InstantCommand(claw::close, claw), () -> {
			claw.toggle();
			return claw.isOpen();
		}));

		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(lift::floor, lift));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(lift::low, lift));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(lift::med, lift));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(lift::high, lift));

		gPad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new InstantCommand(() -> lift.lower(true))).whenReleased(new InstantCommand(() -> lift.lower(false)));

		new GamepadTrigger(gPad1, GamepadKeys.Trigger.LEFT_TRIGGER).whileHeld(new RunCommand(() -> lift.LiftDown(50)));
		new GamepadTrigger(gPad1, GamepadKeys.Trigger.RIGHT_TRIGGER).whileHeld(new RunCommand(() -> lift.LiftUp(50)));


		// Register and Schedule ----------------------------------------------------------------------------------------------------
		gPad1.readButtons();
		register(drive, lift);
		schedule(driveCommand.alongWith(new RunCommand(() -> {
			// Telemetry
			telemetry.update();
			telemetry.addData("LiftPos", lift.getPosition());
			telemetry.addData("LiftVel", lift.getVelocity());
			telemetry.addData("LiftError", lift.getPositionError());
			telemetry.addData("LiftMod", lift.getMod());
		})));
	}
}

