package org.firstinspires.ftc.teamcode.old.a_hardware;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

/*
 * TYPE			NAME			ID		DESCRIPTION
 * ------------------------------------------------------------
 * MOTOR		frontLeft		fL		Front Left Mecanum
 * MOTOR		frontRight		fR		Front Right Mecanum
 * MOTOR		backLeft		bL		Back Left Mecanum
 * MOTOR		backRight		bR		Back Right Mecanum
 *
 * CAMERA		logiCam			cam		Logitech C270 Webcam
 */

@Config
public class Hardware {
    public double volt = Double.POSITIVE_INFINITY; // Bot Voltage
    public static double kp = 0, ki = 0, kd = 0;

    // DEFINE DEVICES
    public MotorEx frontLeft, frontRight, backLeft, backRight;

    public OpenCvWebcam logiCam;

    public Hardware(HardwareMap hwMap) {
        // SET DEVICES

        // Drive Motors
        frontLeft = new MotorEx(hwMap, "fL");
        frontLeft.resetEncoder();
        frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
        frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setVeloCoefficients(kp, ki, kd);

        frontRight = new MotorEx(hwMap, "fR");
        frontRight.resetEncoder();
        frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
        frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setVeloCoefficients(kp, ki, kd);

        backLeft = new MotorEx(hwMap, "bL");
        backLeft.resetEncoder();
        backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
        backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setVeloCoefficients(kp, ki, kd);

        backRight = new MotorEx(hwMap, "bR");
        backRight.resetEncoder();
        backRight.setRunMode(MotorEx.RunMode.VelocityControl);
        backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setVeloCoefficients(kp, ki, kd);

        // Camera
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                hwMap.appContext.getPackageName());

        logiCam = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "cam"),
                cameraMonitorViewId);

        // logiCam.setPipeline(new pipeline());

        logiCam.setMillisecondsPermissionTimeout(2500);
        logiCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                logiCam.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);
                logiCam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) { // This will be called if the camera could not be opened

            }
        });

        // BOT VOLTAGE
        for (VoltageSensor sensor : hwMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                volt = Math.min(volt, voltage);
            }
        }
    }
}