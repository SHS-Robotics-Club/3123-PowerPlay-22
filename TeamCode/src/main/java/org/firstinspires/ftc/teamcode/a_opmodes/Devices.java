package org.firstinspires.ftc.teamcode.a_opmodes;

/*
 * TYPE			NAME			ID		    DESCRIPTION
 * ------------------------------------------------------------
 * MOTOR		frontLeft		fL		    Front Left Mecanum
 * MOTOR		frontRight		fR          Front Right Mecanum
 * MOTOR		backLeft		bL		    Back Left Mecanum
 * MOTOR		backRight		bR		    Back Right Mecanum
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
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;

public class Devices {
	public MotorEx frontLeft, frontRight, backLeft, backRight, liftLeft, liftRight;// Motors
	public MotorGroup lift;// Motor Group
	public ServoEx    clawLeft, clawRight; // Servos
	public CRServo spool; // CR Servo

	// MISC DEFINITIONS
	public FtcDashboard     dashboard = FtcDashboard.getInstance(); //FTC Dashboard Instance
	public List<LynxModule> revHubs; //Lynx Module for REV Hubs
	public VoltageSensor    sensor;

	public Devices(HardwareMap hardwareMap) {
		// Bulk Read
		revHubs = hardwareMap.getAll(LynxModule.class);

		for (LynxModule hub : revHubs) {
			hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
		}

		// MOTORS ----------------------------------------------------------------------------------------------------
		// Map
		frontLeft  = new MotorEx(hardwareMap, "fL", MotorEx.GoBILDA.RPM_312);
		frontRight = new MotorEx(hardwareMap, "fR", MotorEx.GoBILDA.RPM_312);
		backLeft   = new MotorEx(hardwareMap, "bL", MotorEx.GoBILDA.RPM_312);
		backRight  = new MotorEx(hardwareMap, "bR", MotorEx.GoBILDA.RPM_312);

		liftLeft  = new MotorEx(hardwareMap, "lftL", MotorEx.GoBILDA.RPM_312);
		liftRight = new MotorEx(hardwareMap, "lftR", MotorEx.GoBILDA.RPM_312);

		// Set lift setting before group else it tis broken
		liftLeft.setInverted(false);
		liftRight.setInverted(true);
		liftLeft.resetEncoder();
		liftRight.resetEncoder();

		//lift = new MotorGroup(liftLeft, liftRight);

		// Reset encoders
		frontLeft.resetEncoder();
		frontRight.resetEncoder();
		backLeft.resetEncoder();
		backRight.resetEncoder();

		// Set RunMode for motors (RawPower, VelocityControl, PositionControl)
		//lift.setRunMode(MotorEx.RunMode.PositionControl);

		// Brake when zero power
		frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		//lift.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		// SERVOS ----------------------------------------------------------------------------------------------------
		// Map
		clawLeft  = new SimpleServo(hardwareMap, "clawLeft", -180, 180, AngleUnit.DEGREES);
		clawRight = new SimpleServo(hardwareMap, "clawRight", -180, 180, AngleUnit.DEGREES);
		spool     = new CRServo(hardwareMap, "spool");

		// Invert
		clawRight.setInverted(true);

		// Default POS
		clawLeft.turnToAngle(0);
		clawRight.turnToAngle(0);

		// VOLTAGE ----------------------------------------------------------------------------------------------------
		for (VoltageSensor sensor : hardwareMap.voltageSensor) {
			double voltage = sensor.getVoltage();
		}
	}
}