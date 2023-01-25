package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

//@Disabled
@Config
@TeleOp(name = "LiftTuner", group = ".")
public class LiftTuner extends CommandOpMode {
	@Override
	public void initialize() {
		if (isStopRequested()) return;

		// Get Devices
		final Devices devices = new Devices(hardwareMap);
		GamepadEx     gPad1   = new GamepadEx(gamepad1);

		// Define Systems ----------------------------------------------------------------------------------------------------
		LiftSubsystem liftSubsystem = new LiftSubsystem(devices.liftLeft, devices.liftRight, getBatteryVoltage());

		LiftCommand liftCommand = new LiftCommand(liftSubsystem, gPad1);

		register(liftSubsystem);
/*       gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed();
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed();
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed();
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed();*/

		schedule(liftCommand.alongWith(new RunCommand(() -> {
			// Telemetry
			telemetry.update();
			telemetry.addData("LiftPos", liftSubsystem.getPosition());
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
