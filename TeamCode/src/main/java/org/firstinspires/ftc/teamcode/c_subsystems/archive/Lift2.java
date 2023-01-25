package org.firstinspires.ftc.teamcode.c_subsystems.archive;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.a_opmodes.Devices;

import java.util.List;

@Disabled
@TeleOp(name = "Lift Multi-Thread", group = ".")
//@Disabled
public class Lift2 extends LinearOpMode {
	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();

	//Define Whatever These Mean
	public static double kP = 0.05, kI = 0.0, kD = 0.0;
	public static double kS = 0.0, kG = 0.0, kV = 0, kA = 0.0;
	public static double velo = 52.48291908330528, accel = 52.48291908330528;
	public static double veloTol = 0.0, posTol = 0.0;
	double volt = 0, ffOut, output, achAccel = 0, achVel = 0;
	FtcDashboard dashboard = FtcDashboard.getInstance();
	Telemetry    telemetry = dashboard.getTelemetry();
	Devices devices = null;
	GamepadEx gamepadEx = null;
	List<Double> liftPos = null, liftVel = null;
	double targetPos = 0;

	// Create a new ElevatorFeedforward
	ElevatorFeedforward ff  = new ElevatorFeedforward(kS, kG, kV, kA);
	PIDFController      pid = new PIDFController(kP, kI, kD, 0);

	public Lift2() throws Exception {
	}

	// called when init button is  pressed.
	@Override
	public void runOpMode() throws InterruptedException {
		devices = new Devices(hardwareMap);
		gamepadEx = new GamepadEx(gamepad1);
		liftPos = devices.lift.getPositions();
		liftVel = devices.lift.getPositions();

		telemetry.addData("Status", "Initialized");

		for (VoltageSensor sensor : hardwareMap.voltageSensor) {
			double voltage = sensor.getVoltage();
			if (voltage > 0) {
				volt = Math.min(volt, voltage);
			}
		}
		// create an instance of the DriveThread.
		Thread driveThread = new DriveThread();

		telemetry.addData("Mode", "waiting");
		telemetry.update();

		// wait for start button.


		waitForStart();

		runtime.reset();

		// start the driving thread.

		driveThread.start();

		// continue with main thread.

		try {
			while (opModeIsActive()) {

				if (gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
					targetPos = 1000;
				} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
					targetPos = 500;
				} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_DOWN)) {
					targetPos = 0;
				} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT)) {
					targetPos = 1500;
				}

				telemetry.addData("Status", "Run Time: " + runtime.toString());
				telemetry.addData("Pos", liftPos.get(0));
				telemetry.addData("Vel", liftVel.get(0));
				telemetry.addData("FF", ffOut);
				telemetry.addData("PID", output);
				telemetry.addData("Voltage", volt);
				telemetry.addData("achAcc", achAccel);
				telemetry.addData("ErrorPos", pid.getPositionError());
				telemetry.addData("targetPos", targetPos);
				telemetry.addData("ErrorVel", pid.getVelocityError());
				telemetry.update();

				idle();
			}
		} catch (Exception e) {
		}


		// stop the driving thread.

		driveThread.interrupt();

	}

	private class DriveThread extends Thread {
		public DriveThread() {
			this.setName("DriveThread");

		}

		// called when tread.start is called. thread stays in loop to do what it does until exit is
		// signaled by main code calling thread.interrupt.
		@Override
		public void run() {

			try {
				while (!isInterrupted()) {
					// we record the Y values in the main class to make showing them in telemetry
					// easier.
					while (!pid.atSetPoint()) {
						pid.setSetPoint(targetPos);
						liftPos = devices.lift.getPositions();
						liftVel = devices.lift.getVelocities();
						ffOut   = ff.calculate(velo, accel);
						pid.setF(ffOut);
						pid.setTolerance(posTol, veloTol);
						achAccel = ff.maxAchievableAcceleration(volt, liftVel.get(0));
						output   = pid.calculate(
								liftPos.get(0)  // the measured value
						                        );
						devices.lift.set(output);
						telemetry.update();

						if (pid.getPositionError() <= 0 + posTol && pid.getPositionError() >= 0 - posTol) {
							break;
						}
					}
					devices.lift.stopMotor();// stop the motor
					pid.setSetPoint(targetPos);

					idle();
				}
			}
			// interrupted means time to shutdown. note we can stop by detecting isInterrupted = true
			// or by the interrupted exception thrown from the sleep function.
			// an error occurred in the run loop.
			catch (Exception e) {
			}

		}
	}
}