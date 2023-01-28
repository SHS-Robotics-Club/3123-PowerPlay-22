package org.firstinspires.ftc.teamcode.a_opmodes.auto;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.a_opmodes.Devices;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.b_commands.auto.ParkCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.AprilTagSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.d_roadrunner.drive.MecanumDrive;

public class AutoConfig {

	public MecanumSubsystem  drive;
	public ClawSubsystem     claw;
	public AprilTagSubsystem aprilTag;

	public ParkCommand      PARK;
	public WaitUntilCommand DETECTOR_WAIT;
	public InstantCommand   CLAW_OPEN, CLAW_CLOSE;
	public RunCommand LIFT_FLOOR, LIFT_LOW, LIFT_MED, LIFT_HIGH;

	public SequentialCommandGroup PARK_GROUP;


	public AutoConfig(HardwareMap hardwareMap, ParkCommand.StartingZone startingZone) {

		Devices devices = new Devices(hardwareMap);

		drive    = new MecanumSubsystem(new MecanumDrive(hardwareMap), true);
		claw     = new ClawSubsystem(devices.clawLeft, devices.clawRight);
		aprilTag = new AprilTagSubsystem(hardwareMap, "Webcam 1", 1280, 720, 0.4, 1552.74274588, 1552.74274588, 793.573231003, 202.006088244);

		aprilTag.init();

		PARK = new ParkCommand(drive, aprilTag, startingZone);

		DETECTOR_WAIT = new WaitUntilCommand(aprilTag::foundZone);

		CLAW_OPEN  = new InstantCommand(claw::close, claw);
		CLAW_CLOSE = new InstantCommand(claw::open, claw);

		PARK_GROUP = new SequentialCommandGroup(
				CLAW_CLOSE,
				DETECTOR_WAIT.withTimeout(20000),
				PARK
		);
	}
}