package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.SampleMecanumDrive;

@Autonomous(name = "Drive Forward ~1.5 Tiles?", group = "Parking", preselectTeleOp = "CommandTeleOp")
public class FowardStrafe1Tile extends CommandOpMode {

	// CONFIG
	private static long DELAY = 0; //ms
	private static double DISTANCE = 36; //in

	// Subsystems
	private MecanumSubsystem mecanumSubsystem;

	@Override
	public void initialize() {

		mecanumSubsystem = new MecanumSubsystem(new SampleMecanumDrive(hardwareMap), true);

		Trajectory strafe = mecanumSubsystem.trajectoryBuilder(new Pose2d(0, 0, Math.toRadians(0)))
		                                    .forward(DISTANCE)
		                                    .build();

		TrajectoryFollowerCommand autonomous = new TrajectoryFollowerCommand(mecanumSubsystem, strafe);

		if (isStopRequested()) {
			return;
		}

		schedule(new WaitCommand(DELAY).andThen(autonomous));
	}
}