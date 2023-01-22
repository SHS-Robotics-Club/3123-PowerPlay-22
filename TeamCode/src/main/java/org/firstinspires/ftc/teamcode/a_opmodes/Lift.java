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

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

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
@Config
@TeleOp(name = "Lift", group = ".")
public class Lift extends OpMode {
    public Lift() throws Exception {
        
    }
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Define Whatever These Mean
    public static double kP = 0.05, kI = 0.0, kD = 0.0;
    public static double kS = 0.0, kG = 0.0, kV = 312 * 2 * Math.PI / 60.0, kA = 0.0;
    public static double velo = 52.48291908330528, accel = 52.48291908330528;
    public static double veloTol = 0.0, posTol = 0.0;
    double volt = 0, ffOut, output, achAccel = 0, achVel= 0;
    Devices devices = null;
    GamepadEx gamepadEx = null;
    List<Double> liftPos = null, liftVel = null;
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry telemetry = dashboard.getTelemetry();

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        devices = new Devices(hardwareMap);
        gamepadEx = new GamepadEx(gamepad1);
        liftPos = devices.lift.getPositions();
        liftVel = devices.lift.getPositions();

        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                volt = Math.min(volt, voltage);
            }
        }
        Thread liftThread = new LiftThread();
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


        // Create a new ElevatorFeedforward
        ElevatorFeedforward ff = new ElevatorFeedforward(kS, kG, kV, kA);
        PIDFController pid = new PIDFController(kP, kI, kD, 0);

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
            liftPos = devices.lift.getPositions();
            liftVel = devices.lift.getVelocities();
            ffOut = ff.calculate(velo, accel);
            pid.setF(ffOut);
            pid.setTolerance(posTol, veloTol);
            achAccel = ff.maxAchievableAcceleration(volt, liftVel.get(0));
            output = pid.calculate(
                    liftPos.get(0)  // the measured value
            );
            devices.lift.set(output);
            telemetry.addData("Pos", liftPos.get(0));
            telemetry.addData("Vel", liftVel.get(0));
            telemetry.addData("FF", ffOut);
            telemetry.addData("PID", output);
            telemetry.addData("Voltage", volt);
            telemetry.addData("achAcc", achAccel);
            telemetry.addData("ErrorPos", pid.getPositionError());
            telemetry.addData("ErrorVel", pid.getVelocityError());
            telemetry.update();

            if(pid.getPositionError() <= 0 + posTol && pid.getPositionError() >= 0 - posTol){
                break;
            }
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
    
    private class LiftThread extends Thread{
        public LiftThread(){
            this.setName("LiftThread");
        }
        
        @Override
        public void run(){
            try{
                while (!isInterrupted()){
                    idle();
                }
            } catch (Exception ignored) {}
        }
    }

    private void idle() {
    }

}
