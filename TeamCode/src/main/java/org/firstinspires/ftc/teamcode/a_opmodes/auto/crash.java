package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.b_commands.auto.ParkCommand;
import org.firstinspires.ftc.teamcode.b_commands.auto.TrajectoryFollowerCommand;
import org.firstinspires.ftc.teamcode.d_roadrunner.trajectorysequence.TrajectorySequence;


@Autonomous(name = "T:", group = "Parking", preselectTeleOp = "MainTeleOp")
public class crash extends CommandOpMode {
	@Override
	public void initialize() {

		FtcDashboard dashboard          = FtcDashboard.getInstance(); //FTC Dashboard Instance
		Telemetry    dashboardTelemetry = dashboard.getTelemetry();
		telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

		AutoConfig auto = new AutoConfig(hardwareMap, ParkCommand.StartingZone.BLUE_LEFT);

		auto.drive.setPoseEstimate(new Pose2d(-27.23, -37.45, Math.toRadians(72.37)));

		TrajectorySequence auto1 = auto.drive.trajectorySequenceBuilder(new Pose2d(-27.23, -37.45, Math.toRadians(72.37)))
		                                .splineTo(new Vector2d(-29.76, -34.08), Math.toRadians(95.81))
		                                .splineTo(new Vector2d(-31.65, 22.91), Math.toRadians(73.07))
		                                .splineTo(new Vector2d(12.27, 21.65), Math.toRadians(-9.46))
		                                .splineTo(new Vector2d(18.49, -8.37), Math.toRadians(-88.83))
		                                .splineTo(new Vector2d(16.80, -29.23), Math.toRadians(230.06))
		                                .splineTo(new Vector2d(-25.76, -51.88), Math.toRadians(240.64))
		                                .build();

		if (isStopRequested()) return;

		schedule(new RunCommand(() -> {
			         //dashboard.startCameraStream(auto.aprilTag.getCamera(), 0);
			         //telemetry.addData("Zone", auto.aprilTag.getParkingZone());
			         //telemetry.addData("Zone Status", auto.aprilTag.getStatus());
			         telemetry.update();
		         })
				         .alongWith(auto.DETECTOR_WAIT).withTimeout(1)
				         .andThen(auto.CLAW_CLOSE)
				         .andThen(new TrajectoryFollowerCommand(auto.drive, auto1))
/*				         .andThen(auto.LIFT_HIGH)
				         .andThen(auto.CLAW_OPEN)
				         .andThen(auto.LIFT_FLOOR)*/
		        );

	}
}