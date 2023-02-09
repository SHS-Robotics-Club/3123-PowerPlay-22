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
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;

public class Devices {
	public MotorEx liftLeft, liftRight;// Motors
	public MotorGroup lift;// Motor Group
	public ServoEx    clawLeft, clawRight; // Servos
	public CRServo spool; // CR Servo

	// MISC DEFINITIONS
	public FtcDashboard     dashboard = FtcDashboard.getInstance(); //FTC Dashboard Instance
	public List<LynxModule> revHubs; //Lynx Module for REV Hubs

	public Devices(HardwareMap hardwareMap) {
		// Bulk Read
		revHubs = hardwareMap.getAll(LynxModule.class);

		for (LynxModule hub : revHubs) {
			hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
		}

		// MOTORS ----------------------------------------------------------------------------------------------------
		// Map
		liftLeft  = new MotorEx(hardwareMap, "liftL", MotorEx.GoBILDA.RPM_312);
		liftRight = new MotorEx(hardwareMap, "encoderRearLift", MotorEx.GoBILDA.RPM_312);

		// Reset encoders and set direction
		liftLeft.setInverted(true);
		liftLeft.resetEncoder();

		// Group lift motors
		lift = new MotorGroup(liftLeft, liftRight);

		// Set RunMode for motors (RawPower, VelocityControl, PositionControl)
		lift.setRunMode(MotorEx.RunMode.VelocityControl);

		// Break
		lift.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		// SERVOS ----------------------------------------------------------------------------------------------------
		// Map
		clawLeft  = new SimpleServo(hardwareMap, "clawLeft", -180, 180, AngleUnit.DEGREES);
		clawRight = new SimpleServo(hardwareMap, "clawRight", -180, 180, AngleUnit.DEGREES);
		spool     = new CRServo(hardwareMap, "spool");

		// Invert
		clawRight.setInverted(true);

		// Default POS
		clawLeft.turnToAngle(30);
		clawRight.turnToAngle(30);
	}
}