package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.b_commands.auto.ParkCommand;


@Autonomous(name = "P: RED_RIGHT", group = "Parking", preselectTeleOp = "CommandTeleOp")
public class ParkRR extends CommandOpMode {
	@Override
	public void initialize() {

		FtcDashboard dashboard          = FtcDashboard.getInstance(); //FTC Dashboard Instance
		Telemetry    dashboardTelemetry = dashboard.getTelemetry();
		telemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);

		AutoConfig auto = new AutoConfig(hardwareMap, ParkCommand.StartingZone.RED_RIGHT);

		if (isStopRequested()) return;

		schedule(new RunCommand(() -> {
			         dashboard.startCameraStream(auto.aprilTag.getCamera(), 0);
			         telemetry.addData("Zone", auto.aprilTag.getParkingZone());
			         telemetry.addData("Zone Status", auto.aprilTag.getStatus());
			         telemetry.update();
		         }).alongWith(auto.PARK_GROUP)
		);
	}
}