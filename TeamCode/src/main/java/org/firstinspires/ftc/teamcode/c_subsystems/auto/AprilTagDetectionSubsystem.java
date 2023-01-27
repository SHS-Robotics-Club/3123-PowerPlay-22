package org.firstinspires.ftc.teamcode.c_subsystems.auto;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class AprilTagDetectionSubsystem extends SubsystemBase {

	AprilTagDetectionPipeline aprilTagDetectionPipeline;

	public int getTagId() {
		ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
		AprilTagDetection            tagOfInterest     = null;
		boolean                      tagFound          = false;

		if (currentDetections.size() != 0) {
			for (AprilTagDetection tag : aprilTagDetectionPipeline.getLatestDetections()) {
				if (tag.id >= 1 && tag.id <= 3) {
					tagOfInterest = tag;
					tagFound = true;
					break;
				}
			}
		}

		if(tagFound){
			return tagOfInterest.id;
		} else {
			return -1;
		}
	}

	public enum ParkingZone {
		LEFT, CENTER, RIGHT
	}

	public ParkingZone getParkZone() {
		switch (getTagId()) {
			case 1:
				return ParkingZone.LEFT;
			case 3:
				return ParkingZone.RIGHT;
			default:
				return ParkingZone.CENTER;
		}
	}


}
