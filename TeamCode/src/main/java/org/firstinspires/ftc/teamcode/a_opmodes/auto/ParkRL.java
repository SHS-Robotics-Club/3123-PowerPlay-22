package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.AprilTagSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.PathSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.Status;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "P: RED_LEFT", group = "Parking", preselectTeleOp = "CommandTeleOp")
public class ParkRL extends CommandOpMode {
	@Override
	public void initialize() {

		final PathSubsystem pathSubsystem = new PathSubsystem();
		AprilTagSubsystem tagSubsystem = new AprilTagSubsystem(hardwareMap, "webcam", 1280, 720, 0.4, 1552.74274588, 1552.74274588, 793.573231003, 202.006088244);

		tagSubsystem.init();

		MecanumSubsystem mecanumSubsystem = new MecanumSubsystem(new SampleMecanumDrive(hardwareMap), true);

		TrajectorySequence park = pathSubsystem.getParkTrajectory(mecanumSubsystem, pathSubsystem.getStartPose(PathSubsystem.StartingArea.RED_LEFT), tagSubsystem.getPlacement());

		TrajectoryFollowerCommand autonomous = new TrajectoryFollowerCommand(mecanumSubsystem, park);

		if (isStopRequested()) return;

		if (tagSubsystem.getStatus() == Status.RUNNING) {
			schedule(autonomous.alongWith(new RunCommand(() -> {
				FtcDashboard.getInstance().startCameraStream(tagSubsystem.getCamera(), 0);
					})

			));
		}
	}
}