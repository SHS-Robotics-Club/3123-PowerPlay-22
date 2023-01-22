/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.a_opmodes;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.List;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "Lift", group = ".")
public class Lift extends OpMode {
	// Declare OpMode members.
	private ElapsedTime runtime = new ElapsedTime();

	//Define Whatever These Mean
	public static double kP = 0.0, kI = 0.0, kD = 0.0;
	public static double tolerance = 10.0, velo = 10.0, accel = 20.0;
	Devices   devices   = new Devices(hardwareMap);
	GamepadEx gamepadEx = new GamepadEx(gamepad1);

	/*
	 * Code to run ONCE when the driver hits INIT
	 */
	@Override
	public void init() {
		telemetry.addData("Status", "Initialized");
	}

	/*
	 * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
	 */
	@Override
	public void init_loop() {
	}

	/*
	 * Code to run ONCE when the driver hits PLAY
	 */
	@Override
	public void start() {
		runtime.reset();
	}

	/*
	 * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
	 */
	@Override
	public void loop() {
		List<Double> liftVelo, liftPos;

		// Create a new ElevatorFeedforward
		PIDFController pid = new PIDController(kP, kI, kD);

		if (gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
			pid.setSetPoint(1000);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
			pid.setSetPoint(500);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_DOWN)) {
			pid.setSetPoint(0);
		} else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT)) {
			pid.setSetPoint(1500);
		}

		while (!pid.atSetPoint()) {
			double output = pid.calculate(
					devices.lift.getCurrentPosition()  // the measured value
			                             );
			devices.lift.set(output);
			telemetry.addData("Pos", devices.lift.getCurrentPosition());
			telemetry.update();
		}
		devices.lift.stopMotor(); // stop the motor

		// Show the elapsed game time and wheel power.
		telemetry.addData("Status", "Run Time: " + runtime.toString());
	}

	/*
	 * Code to run ONCE after the driver hits STOP
	 */
	@Override
	public void stop() {
	}

}
