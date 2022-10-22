package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.b_commands.LynxCommand;
import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;

import java.util.List;
import java.util.function.DoubleSupplier;

// @Disabled
@TeleOp(name = "CommandTeleOp", group = ".")
@Config
public class MainTeleOp extends CommandOpMode {
	// DEFINE DEVICES
	public MotorEx frontLeft, frontRight, backLeft, backRight;

	// SUBSYSTEMS
	private DriveSubsystem driveSubsystem;

	// COMMANDS
	private DriveCommand driveCommand;
	private LynxCommand lynxCommand;

	// EXTRAS
	private GamepadEx gPad1, gPad2;
	private FtcDashboard dashboard;
	private ElapsedTime time;
	private RevIMU revIMU;

	// CONSTANTS
	public static double kp = 0, ki = 0, kd = 0;
	public int color = 0;

	public double strafe;

	@Override
	public void initialize() {

		// Lynx Module
		List<LynxModule> revHubs = hardwareMap.getAll(LynxModule.class);
		lynxCommand = new LynxCommand(revHubs);
		schedule(lynxCommand);

		// Initialize Motors
		frontLeft  = new MotorEx(hardwareMap, "fL", MotorEx.GoBILDA.RPM_312);
		frontRight = new MotorEx(hardwareMap, "fR", MotorEx.GoBILDA.RPM_312);
		backLeft   = new MotorEx(hardwareMap, "bL", MotorEx.GoBILDA.RPM_312);
		backRight  = new MotorEx(hardwareMap, "bR", MotorEx.GoBILDA.RPM_312);

		// Initialize Extras
		gPad1 = new GamepadEx(gamepad1);
		gPad2 = new GamepadEx(gamepad2);

		revIMU = new RevIMU(hardwareMap);
		revIMU.init();
		dashboard = FtcDashboard.getInstance();
		time = new ElapsedTime();

		// Initialize Commands and Subsystems
		driveSubsystem = new DriveSubsystem(frontLeft, frontRight, backLeft, backRight, revIMU);
		driveCommand   = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX);

		// Motor Settings
		frontLeft.resetEncoder();
		frontRight.resetEncoder();
		backLeft.resetEncoder();
		backRight.resetEncoder();

		frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
		backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		backRight.setRunMode(MotorEx.RunMode.VelocityControl);

		frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		frontLeft.setVeloCoefficients(kp, ki, kd);
		frontRight.setVeloCoefficients(kp, ki, kd);
		backLeft.setVeloCoefficients(kp, ki, kd);
		backRight.setVeloCoefficients(kp, ki, kd);

		// Default Drivetrain Command
		register(driveSubsystem);
		driveSubsystem.setDefaultCommand(driveCommand);

		schedule(new RunCommand(() -> {
			// Telemetry
			telemetry.addData("frontLeft encoder position", frontLeft.encoder.getPosition());
			telemetry.addData("frontRight encoder position", frontRight.encoder.getPosition());
			telemetry.addData("backLeft encoder position", backLeft.encoder.getPosition());
			telemetry.addData("backRight encoder position", backRight.encoder.getPosition());
			telemetry.addData("Left", gPad1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
			telemetry.addData("Right", gPad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
			telemetry.addData("strafe", strafe);
			telemetry.update();
		}));

		idle();
	}

}