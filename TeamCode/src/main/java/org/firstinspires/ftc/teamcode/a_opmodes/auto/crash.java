package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.a_opmodes.Robot;
import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;


@Autonomous(name = "T:", group = "Parking", preselectTeleOp = "MainTeleOp")
public class crash extends CommandOpMode {
	@Override
	public void initialize() {
		// Get Devices
		final Robot bot = new Robot(hardwareMap, true);

		// Setup Telemetry
		FtcDashboard dashboard          = FtcDashboard.getInstance(); //FTC Dashboard Instance
		Telemetry    dashboardTelemetry = dashboard.getTelemetry();
		telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

		bot.drive.setPoseEstimate(new Pose2d(-40.00, -64.00, Math.toRadians(90.00)));

		TrajectorySequence auto1 = bot.drive.trajectorySequenceBuilder(new Pose2d(-40.00, -64.00, Math.toRadians(90.00)))
		                                    .splineTo(new Vector2d(-34.00, -42.00), Math.toRadians(90.00))
		                                    .splineTo(new Vector2d(-27.00, -9.50), Math.toRadians(55.00))
		                                    .build();

		waitForStart();

		register(bot.lift);
		schedule(new RunCommand(() -> {
			         if (isStopRequested()) return;
			         dashboard.startCameraStream(bot.aprilTag.getCamera(), 0);
			         telemetry.update();
		         }).alongWith(bot.LIFT_HIGH)
		           .alongWith(new TrajectoryFollowerCommand(bot.drive, auto1))
		           .andThen(bot.LIFT_FLOOR)
		        );

	}
}