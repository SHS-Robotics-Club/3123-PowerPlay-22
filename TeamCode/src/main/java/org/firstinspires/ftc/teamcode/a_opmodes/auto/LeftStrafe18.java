package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.b_commands.auto.MDriveCommand;
import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MDriveSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "Strafe Left 18in", group = "Parking")
public class LeftStrafe18 extends CommandOpMode {

	// CONFIG
	private static long START_DELAY = 0; //ms
	private static double STRAFE_DISTANCE = 18; //in

	// Subsystems
	private MDriveSubsystem mecanumDriveS;

	// Extra Stuff
	private final ElapsedTime runtime = new ElapsedTime();

	// Start Pose
	private Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));


	@Override
	public void initialize() {
		mecanumDriveS = new MDriveSubsystem(new SampleMecanumDrive(hardwareMap), true);

		Trajectory strafe = mecanumDriveS.trajectoryBuilder(startPose)
		                                 .strafeLeft(STRAFE_DISTANCE)
		                                 .build();

		TrajectoryFollowerCommand autonomous = new TrajectoryFollowerCommand(mecanumDriveS, strafe);
		//MDriveCommand auto = new MDriveCommand(mecanumDriveS, 0, -18, 0)

		if (isStopRequested()) {
			return;
		}

		schedule(new WaitCommand(START_DELAY).andThen(autonomous));
	}
}