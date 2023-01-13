package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.List;

/*
 * TYPE			NAME			ID		    DESCRIPTION
 * ------------------------------------------------------------
 * MOTOR		frontLeft		fL		    Front Left Mecanum
 * MOTOR		frontRight		fR          Front Right Mecanum
 * MOTOR		backLeft		bL		    Back Left Mecanum
 * MOTOR		backRight		bR		    Back Right Mecanum
 * MOTOR		liftA			lA			Lift Motor A
 * MOTOR		liftB			lB			Lift Motor B
 * MOTOR		arm				arm			Arm Motor
 *
 * SERVO        clawLeft        clawLeft    Claw Left (Open/Close)
 * SERVO        clawRight       clawRight   Claw Right (Open/Close)
 *
 * CRSERVO		spool			spool		Funnie spool that spools
 *
 * CAMERA		logiCam			cam		    Logitech C270 Webcam
 * IMU          revIMU          imu         REV Hub Built in IMU
 */

@Config
public class Devices {
	// DEFINE DEVICES
	public MotorEx frontLeft, frontRight, backLeft, backRight;// Motors
	public MotorGroup lift;// Motor Group
	public ServoEx clawLeft, clawRight; // Servos
	public CRServo spool; // CR Servo
	public OpenCvWebcam logiCam; // USB Camera
	public RevIMU revIMU; // REV Hub IMU
	
	// MISC DEFINITIONS
	public FtcDashboard dashboard = FtcDashboard.getInstance(); //FTC Dashboard Instance
	public ElapsedTime time = new ElapsedTime(); // Time
	public List<LynxModule> revHubs; //Lynx Module for REV Hubs
	
	// BOT VARIABLES
	public double volt = Double.POSITIVE_INFINITY; // Bot Voltage
	public static final double kp = 0, ki = 0, kd = 0; // Drive PID
	
	public Devices(HardwareMap hardwareMap) {
		// MISC ----------------------------------------------------------------------------------------------------
		// Rev Lynx
		revHubs = hardwareMap.getAll(LynxModule.class);
		
		// Rev imu
		revIMU = new RevIMU(hardwareMap);
		revIMU.init();
		
		// MOTORS ----------------------------------------------------------------------------------------------------
		// Map
		frontLeft  = new MotorEx(hardwareMap, "fL", MotorEx.GoBILDA.RPM_312);
		frontRight = new MotorEx(hardwareMap, "fR", MotorEx.GoBILDA.RPM_312);
		backLeft   = new MotorEx(hardwareMap, "bL", MotorEx.GoBILDA.RPM_312);
		backRight  = new MotorEx(hardwareMap, "bR", MotorEx.GoBILDA.RPM_312);

		spool  = new CRServo(hardwareMap, "spool");
		
		lift = new MotorGroup(
				new MotorEx(hardwareMap, "liftA", MotorEx.GoBILDA.RPM_312),
				new MotorEx(hardwareMap, "liftB", MotorEx.GoBILDA.RPM_312)
		);
		
		// Reset encoders
		frontLeft.resetEncoder();
		frontRight.resetEncoder();
		backLeft.resetEncoder();
		backRight.resetEncoder();

		spool.resetEncoder();
		lift.resetEncoder();
		
		// Set RunMode for motors (RawPower, VelocityControl, PositionControl)
		frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
		backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		backRight.setRunMode(MotorEx.RunMode.VelocityControl);

		spool.setRunMode(CRServo.RunMode.PositionControl);
		
		// Brake when zero power
		frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

		spool.setZeroPowerBehavior(CRServo.ZeroPowerBehavior.BRAKE);
		lift.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
		
		// PID :( 🤷‍
		frontLeft.setVeloCoefficients(kp, ki, kd);
		frontRight.setVeloCoefficients(kp, ki, kd);
		backLeft.setVeloCoefficients(kp, ki, kd);
		backRight.setVeloCoefficients(kp, ki, kd);
		
		// SERVOS ----------------------------------------------------------------------------------------------------
		// Map
		clawLeft  = new SimpleServo(hardwareMap, "clawLeft", -180, 180, AngleUnit.DEGREES);
		clawRight = new SimpleServo(hardwareMap, "clawRight", -180, 180, AngleUnit.DEGREES);
		
		// Invert
		clawRight.setInverted(true);
		
		// Default POS
		//clawLeft.turnToAngle(-10);
		//clawRight.turnToAngle(-10);
		
		// VOLTAGE ----------------------------------------------------------------------------------------------------
		for (VoltageSensor sensor : hardwareMap.voltageSensor) {
			double voltage = sensor.getVoltage();
			if (voltage > 0) {
				volt = Math.min(volt, voltage);
			}
		}
	}
}