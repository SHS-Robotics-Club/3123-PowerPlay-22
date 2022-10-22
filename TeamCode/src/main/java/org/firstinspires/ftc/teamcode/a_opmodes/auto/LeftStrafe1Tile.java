package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "Strafe Left ~1 Tile", group = "Parking")
public class LeftStrafe1Tile extends CommandOpMode {

	// Devices


	// CONFIG
	private static long START_DELAY = 0; //ms
	private static double STRAFE_DISTANCE = 24; //in

	private ClawSubsystem claw;

	// Extra Stuff
	private final ElapsedTime runtime = new ElapsedTime();

	// Start Pose
	private Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));


	@Override
	public void initialize() {
		// Initialize Servos
		ServoEx clawPitch, clawLeft, clawRight;
		clawLeft  = new SimpleServo(hardwareMap, "clawLeft", -180, 180, AngleUnit.DEGREES);
		clawRight = new SimpleServo(hardwareMap, "clawRight", -180, 180, AngleUnit.DEGREES);
		clawPitch = new SimpleServo(hardwareMap, "clawPitch", -180, 180, AngleUnit.DEGREES);

		clawRight.setInverted(true);

		clawLeft.turnToAngle(-10);
		clawRight.turnToAngle(-10);

		clawPitch.turnToAngle(-60);

		// Subsystems
		MecanumSubsystem mecanumDriveS = new MecanumSubsystem(new SampleMecanumDrive(hardwareMap), true);

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