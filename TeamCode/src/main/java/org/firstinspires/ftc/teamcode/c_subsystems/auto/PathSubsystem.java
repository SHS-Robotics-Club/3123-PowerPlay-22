package org.firstinspires.ftc.teamcode.c_subsystems.auto;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;

public class PathSubsystem extends SubsystemBase {

	private static final int startX = 36, startY = 65, startH = 90;

	public enum StartingArea {
		RED_LEFT(startX, -startY, startH),
		RED_RIGHT(startX, -startY, startH),
		BLUE_LEFT(startX, -startY, startH),
		BLUE_RIGHT(startX, -startY, startH);

		private final int X, Y, H;

		StartingArea(int X, int Y, int H) {
			this.X = X;
			this.Y = Y;
			this.H = H;
		}

		public Pose2d getStartPose() {
			return new Pose2d(X, Y, Math.toRadians(H));
		}
	}

	public TrajectorySequence getParkTrajectory(MecanumSubsystem drive, Pose2d startPose, AprilTagDetectionSubsystem.ParkingZone zone) {
		switch (zone) {
			case LEFT:
				return drive.trajectorySequenceBuilder(startPose)
						.forward(24)
						.strafeLeft(24)
						.build();
			case RIGHT:
				return drive.trajectorySequenceBuilder(startPose)
						.forward(24)
						.strafeRight(24)
						.build();
			case CENTER:
				return drive.trajectorySequenceBuilder(startPose)
						.forward(24)
						.build();
			default:
				return drive.trajectorySequenceBuilder(startPose)
						.build();
		}
	}
}