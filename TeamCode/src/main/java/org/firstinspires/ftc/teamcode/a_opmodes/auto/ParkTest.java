package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.a_opmodes.Devices;
import org.firstinspires.ftc.teamcode.b_commands.auto.ParkCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.AprilTagSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.MecanumDrive;


@Disabled
@Autonomous(name = "P: TEST", group = "Parking", preselectTeleOp = "CommandTeleOp")
public class ParkTest extends CommandOpMode {
	@Override
	public void initialize() {

		FtcDashboard dashboard          = FtcDashboard.getInstance(); //FTC Dashboard Instance
		Telemetry    dashboardTelemetry = dashboard.getTelemetry();
		telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

		Devices devices = new Devices(hardwareMap);

		MecanumSubsystem mecanumSubsystem =
				new MecanumSubsystem(new MecanumDrive(hardwareMap), true);
		ClawSubsystem claw = new ClawSubsystem(devices.clawLeft, devices.clawRight);
		AprilTagSubsystem tagSubsystem =
				new AprilTagSubsystem(hardwareMap, "Webcam 1", 1280, 720, 0.4, 1552.74274588,
				                      1552.74274588, 793.573231003, 202.006088244);

		ParkCommand park =
				new ParkCommand(mecanumSubsystem, tagSubsystem, ParkCommand.StartingZone.RED_LEFT);
		tagSubsystem.init();

		InstantCommand close = new InstantCommand(claw::close, claw);
		InstantCommand open  = new InstantCommand(claw::open, claw);

		if (isStopRequested()) return;

		schedule(new RunCommand(() -> {
			         dashboard.startCameraStream(tagSubsystem.getCamera(), 0);
			         telemetry.addData("Zone", tagSubsystem.getParkingZone());
			         telemetry.addData("Zone Status", tagSubsystem.getStatus());
			         telemetry.update();
		         }).alongWith(
				         new WaitUntilCommand(tagSubsystem::foundZone).withTimeout(20000)
				                                                      .andThen(park)
		                     )
		        );
	}
}