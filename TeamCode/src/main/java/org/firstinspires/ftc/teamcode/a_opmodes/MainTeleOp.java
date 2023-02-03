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
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.b_commands.MecanumCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
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
		MecanumSubsystem drive = new MecanumSubsystem(new MecanumDrive(hardwareMap), false);
		ClawSubsystem    claw  = new ClawSubsystem(devices.clawLeft, devices.clawRight);
		LiftSubsystem    lift  = new LiftSubsystem(devices.lift, devices.spool);

		MecanumCommand driveCommand = new MecanumCommand(drive, gPad1::getLeftY, gPad1::getLeftX, gPad1::getRightX, liftLevels.getDriveMult());

		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

		// CONTROLS ----------------------------------------------------------------------------------------------------
		// X Button = Claw Open/Close
		gPad1.getGamepadButton(GamepadKeys.Button.X)
		     .whenPressed(new ConditionalCommand(new InstantCommand(claw::open, claw), new InstantCommand(claw::close, claw), () -> {
			     claw.toggle();
			     return claw.isOpen();
		     }));

		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
		     .whenPressed(new InstantCommand(() -> liftLevels = LiftSubsystem.LiftLevels.FLOOR));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
		     .whenPressed(new InstantCommand(() -> liftLevels = LiftSubsystem.LiftLevels.LOW));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
		     .whenPressed(new InstantCommand(() -> liftLevels = LiftSubsystem.LiftLevels.MED));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
		     .whenPressed(new InstantCommand(() -> liftLevels = LiftSubsystem.LiftLevels.HIGH));

		gPad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
		     .whenPressed(new InstantCommand(() -> lift.lowerLift(true)))
		     .whenReleased(new InstantCommand(() -> lift.lowerLift(false)));


		// Register and Schedule ----------------------------------------------------------------------------------------------------
		register(drive, lift);
		schedule(driveCommand.alongWith(new RunCommand(() -> {
			double forward = (-gPad1.getLeftY() * 0.9) * liftLevels.getDriveMult();
			double turn    = (gPad1.getLeftX() * 0.9) * liftLevels.getDriveMult();
			double strafe  = (gPad1.getRightX() * 0.8) * liftLevels.getDriveMult();

			double forwardValue = driveCommand.applyDeadzoneExponentiation(-gPad1.getLeftY(), 0.1, 1.5);
			double turnValue    = driveCommand.applyDeadzoneExponentiation(gPad1.getRightX(), 0.1, 1.5);
			double strafeValue  = driveCommand.applyDeadzoneExponentiation(gPad1.getLeftX(), 0.15, 1.5);

			// Telemetry
			telemetry.update();
			telemetry.addData("LiftPos", lift.getPosition());
			telemetry.addData("LiftVel", lift.getVelocity());
			telemetry.addData("LiftError", lift.getPositionError());
			telemetry.addLine("Controlls");
			telemetry.addData("LeftY", -gPad1.getLeftY());
			telemetry.addData("LeftYMod", driveCommand.returnForward());
/*			telemetry.addData("LeftX", strafe);
			telemetry.addData("LeftXMod", strafeValue);
			telemetry.addData("RightX", turn);
			telemetry.addData("RightXMod", turnValue);*/
//			telemetry.addData("voltage", "%.1f volts", getBatteryVoltage());
		})));

	}

	// Computes the current battery voltage
	double getBatteryVoltage() {
		double result = Double.POSITIVE_INFINITY;
		for (VoltageSensor sensor : hardwareMap.voltageSensor) {
			double voltage = sensor.getVoltage();
			if (voltage > 0) {
				result = Math.min(result, voltage);
			}
		}
		return result;
	}

}
