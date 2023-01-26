package org.firstinspires.ftc.teamcode.c_subsystems.auto;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;

public class PathSubsystem extends SubsystemBase {

    public enum StartingArea {
        RED_LEFT, RED_RIGHT, BLUE_LEFT, BLUE_RIGHT;
    }

    private static final int startX = 36, startY = 65, startH = 90;
    Pose2d redLeft = new Pose2d(startX, -startY, Math.toRadians(startH));
    Pose2d redRight = new Pose2d(-startX, -startY, Math.toRadians(startH));
    Pose2d blueLeft = new Pose2d(startX, startY, Math.toRadians(-startH));
    Pose2d blueRight = new Pose2d(-startX, startY, Math.toRadians(-startH));


    public Pose2d getStartPose(StartingArea startingArea) {
        switch (startingArea) {
            case RED_LEFT:
                return redLeft;
            case RED_RIGHT:
                return redRight;
            case BLUE_LEFT:
                return blueLeft;
            case BLUE_RIGHT:
                return blueRight;
            default:
                return new Pose2d();
        }
    }

    public TrajectorySequence getParkTrajectory(MecanumSubsystem drive, Pose2d startPose, AprilTagSubsystem.Placement zone) {
        switch (zone) {
            case LEFT:
                TrajectorySequence leftZone = drive.trajectorySequenceBuilder(startPose)
                        .forward(24)
                        .strafeLeft(24)
                        .build();
                return leftZone;
            case RIGHT:
                TrajectorySequence rightZone = drive.trajectorySequenceBuilder(startPose)
                        .forward(24)
                        .strafeRight(24)
                        .build();
                return rightZone;
            case CENTER:
                TrajectorySequence centerZone = drive.trajectorySequenceBuilder(startPose)
                        .forward(24)
                        .build();
                return centerZone;
            default:
                TrajectorySequence defaultTraj = drive.trajectorySequenceBuilder(startPose)
                        .build();
                return defaultTraj;
        }
    }
}