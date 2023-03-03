package org.firstinspires.ftc.teamcode.a_opmodes;

/*
 * TYPE			NAME			ID		    DESCRIPTION
 * ------------------------------------------------------------
 * MOTOR		liftLeft		liftL		Lift Motor Left
 * MOTOR		liftRight		liftR		Lift Motor Right
 *
 * SERVO        clawLeft        clawLeft    Claw Left (Open/Close)
 * SERVO        clawRight       clawRight   Claw Right (Open/Close)
 *
 * CRSERVO		spool			spool		Tensions MGN Rail
 */

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.AprilTagSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.MecanumDrive;

import java.util.List;

public class Robot {
	public MotorEx liftLeft, liftRight;// Motors
	public MotorGroup liftGroup;// Motor Group
	public ServoEx    clawLeft, clawRight; // Servos
	public CRServo spool; // CR Servo
	boolean isAuto;

	// MISC DEFINITIONS
	public FtcDashboard     dashboard = FtcDashboard.getInstance(); //FTC Dashboard Instance
	public List<LynxModule> revHubs; //Lynx Module for REV Hubs

	public MecanumSubsystem drive;
	public ClawSubsystem    claw;
	public LiftSubsystem    lift;

	public InstantCommand CLAW_OPEN, CLAW_CLOSE, LIFT_FLOOR, LIFT_LOW, LIFT_MED, LIFT_HIGH, LOWER_T, LOWER_F, LIFT_DOWN, LIFT_UP;
	public FunctionalCommand CLAW_OPEN_F, CLAW_CLOSE_F;

	public AprilTagSubsystem aprilTag;
	public WaitUntilCommand DETECTOR_WAIT;

	public Robot(HardwareMap hardwareMap) {
		this(hardwareMap, false);
	}

	public Robot(HardwareMap hardwareMap, boolean isAuto) {
		this.isAuto = isAuto;

		if (isAuto) {
			autoConfig(hardwareMap);
		}

		// Bulk Read
		revHubs = hardwareMap.getAll(LynxModule.class);

		for (LynxModule hub : revHubs) {
			hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
		}

		// MOTORS ----------------------------------------------------------------------------------------------------
		// Map
		liftLeft  = new MotorEx(hardwareMap, "liftL", MotorEx.GoBILDA.RPM_312);
		liftRight = new MotorEx(hardwareMap, "encoderLeftLift", MotorEx.GoBILDA.RPM_312);

		// Reset encoders and set direction
		liftLeft.setInverted(true);
		liftLeft.resetEncoder();

		// Group lift motors
		liftGroup = new MotorGroup(liftLeft, liftRight);

		// Set RunMode for motors (RawPower, VelocityControl, PositionControl)
		liftGroup.setRunMode(MotorEx.RunMode.VelocityControl);

		// Break
		liftGroup.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		// SERVOS ----------------------------------------------------------------------------------------------------
		// Map
		clawLeft  = new SimpleServo(hardwareMap, "clawLeft", -180, 180, AngleUnit.DEGREES);
		clawRight = new SimpleServo(hardwareMap, "clawRight", -180, 180, AngleUnit.DEGREES);
		spool     = new CRServo(hardwareMap, "spool");

		// Invert
		clawRight.setInverted(true);
		spool.setInverted(true);

		// Default POS
/*		clawLeft.turnToAngle(30);
		clawRight.turnToAngle(30);*/

		// COMMANDS & SUBSYSTEMS --------------------------------------------------------------------------------------
		drive = new MecanumSubsystem(new MecanumDrive(hardwareMap), true);
		claw  = new ClawSubsystem(clawLeft, clawRight);
		lift  = new LiftSubsystem(liftGroup, spool);

		CLAW_OPEN  = new InstantCommand(claw::open, claw);
		CLAW_CLOSE = new InstantCommand(claw::close, claw);

/*		CLAW_TOGGLE = new ConditionalCommand(CLAW_OPEN, CLAW_CLOSE, () -> {
			claw.toggle();
			return claw.isOpen();
		});*/

		LIFT_FLOOR = new InstantCommand(lift::floor, lift);
		LIFT_LOW   = new InstantCommand(lift::low, lift);
		LIFT_MED   = new InstantCommand(lift::med, lift);
		LIFT_HIGH  = new InstantCommand(lift::high, lift);

		LOWER_T = new InstantCommand(() -> lift.lower(true));
		LOWER_F = new InstantCommand(() -> lift.lower(false));

		LIFT_DOWN = new InstantCommand(() -> lift.down(25));
		LIFT_UP   = new InstantCommand(() -> lift.up(25));
	}

	private void autoConfig(HardwareMap hardwareMap) {

		aprilTag = new AprilTagSubsystem(hardwareMap, "Webcam 1", 1280, 720, 0.4, 1552.74274588,
		                                 1552.74274588, 793.573231003, 202.006088244);
		aprilTag.init();

		DETECTOR_WAIT = new WaitUntilCommand(aprilTag::foundZone);
	}
}