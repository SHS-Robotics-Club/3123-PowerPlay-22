package org.firstinspires.ftc.teamcode.a_opmodes;

import static org.firstinspires.ftc.teamcode.b_commands.LiftCommand.liftLevels;

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

import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommandNormal;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystemNormal;

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
		DriveSubsystem driveSubsystem = new DriveSubsystem(devices.frontLeft, devices.frontRight, devices.backLeft, devices.backRight);
		ClawSubsystem claw          = new ClawSubsystem(devices.clawLeft, devices.clawRight);
//		LiftSubsystem liftSubsystem = new LiftSubsystem(devices.lift);
		LiftSubsystemNormal liftSubsystem = new LiftSubsystemNormal(devices.lift);

//		LiftCommand liftCommand = new LiftCommand(liftSubsystem, gPad1);
		LiftCommandNormal liftCommand = new LiftCommandNormal(liftSubsystem);
		DriveCommand driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX, liftLevels.getDriveMult());

		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

		// CONTROLS ----------------------------------------------------------------------------------------------------
		// X Button = Claw Open/Close
		gPad1.getGamepadButton(GamepadKeys.Button.X)
		     .whenPressed(new ConditionalCommand(new InstantCommand(claw::open, claw), new InstantCommand(claw::close, claw), () -> {
			     claw.toggle();
			     return claw.isOpen();
		     }));

		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
		     .whenPressed(new InstantCommand(() -> liftCommand.setLiftLevels(LiftCommandNormal.LiftLevels.FLOOR)));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
		     .whenPressed(new InstantCommand(() -> liftCommand.setLiftLevels(LiftCommandNormal.LiftLevels.LOW)));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
		     .whenPressed(new InstantCommand(() -> liftCommand.setLiftLevels(LiftCommandNormal.LiftLevels.MED)));
		gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
		     .whenPressed(new InstantCommand(() -> liftCommand.setLiftLevels(LiftCommandNormal.LiftLevels.HIGH)));


		//TODO: Test lift lowering
		gPad1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
		     .whenPressed(new InstantCommand(() -> liftSubsystem.setLower(true)))
		     .whenReleased(new InstantCommand(() -> liftSubsystem.setLower(false)));


		// Register and Schedule ----------------------------------------------------------------------------------------------------
		register(driveSubsystem, liftSubsystem);
		schedule(driveCommand.alongWith(liftCommand, new RunCommand(() -> {
			// Telemetry
			telemetry.update();
			telemetry.addData("LiftPos", liftSubsystem.getPosition());
			telemetry.addData("LiftVel", liftSubsystem.getVelocity());
			telemetry.addData("LiftError", liftSubsystem.getPositionError());
			telemetry.addData("voltage", "%.1f volts", getBatteryVoltage());
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
