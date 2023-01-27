package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.PathSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "P: RED_LEFT", group = "Parking", preselectTeleOp = "CommandTeleOp")
public class ParkRL extends CommandOpMode {
	@Override
	public void initialize() {

		PathSubsystem pathSubsystem = new PathSubsystem();

		MecanumSubsystem mecanumSubsystem = new MecanumSubsystem(new MecanumDrive(hardwareMap), true);

		TrajectorySequence park = pathSubsystem.getParkTrajectory(mecanumSubsystem, PathSubsystem.StartingArea.RED_LEFT.getStartPose(), mecanumSubsystem.getParkZone());

		TrajectoryFollowerCommand autonomous = new TrajectoryFollowerCommand(mecanumSubsystem, park);

		if (isStopRequested()) return;

		schedule(new RunCommand(() -> {
			         telemetry.addData("Zone", mecanumSubsystem.getParkZone());
			         FtcDashboard.getInstance().startCameraStream(mecanumSubsystem.getCamera(), 0);
		         }).alongWith(autonomous)
		);
	}
}