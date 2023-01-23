package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftState;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

//@Disabled
@Config
@TeleOp(name = "MainTeleOp", group = ".")
public class MainTeleOp extends CommandOpMode {
    @Override
    public void initialize() {
        if (isStopRequested()) return;

        // Get Devices
        final Devices devices = new Devices(hardwareMap);

        // Gamepad
        GamepadEx gPad1 = new GamepadEx(gamepad1);

        // Define Systems ----------------------------------------------------------------------------------------------------
        DriveSubsystem driveSubsystem = new DriveSubsystem(devices.frontLeft, devices.frontRight, devices.backLeft, devices.backRight);
        ClawSubsystem claw = new ClawSubsystem(devices.clawLeft, devices.clawRight);
        LiftSubsystem liftSubsystem = new LiftSubsystem(devices.lift);

        DriveCommand driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX, 1);
        LiftCommand liftCommand = new LiftCommand(liftSubsystem);

        // CONTROLS ----------------------------------------------------------------------------------------------------
        // X Button = Claw Open/Close
        gPad1.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new ConditionalCommand(
                        new InstantCommand(claw::open, claw),
                        new InstantCommand(claw::close, claw),
                        () -> {
                            claw.toggle();
                            return claw.isOpen();
                        }
                ));

        register(liftSubsystem);
        liftSubsystem.setDefaultCommand(liftCommand);
/*        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new LiftCommand(liftSubsystem, 0).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new LiftCommand(liftSubsystem, 1).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new LiftCommand(liftSubsystem, 2).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new LiftCommand(liftSubsystem, 3).withTimeout(5000));*/

        // Register and Schedule ----------------------------------------------------------------------------------------------------
        register(driveSubsystem);
        driveSubsystem.setDefaultCommand(driveCommand);
        schedule(new RunCommand(() -> {
            // Telemetry
            telemetry.update();
            telemetry.addData("velo", liftSubsystem.getVelocity());
            telemetry.addData("pos", liftSubsystem.getPosition());
            telemetry.addData("pid", liftSubsystem.calculate());
            telemetry.addData("target", liftSubsystem.getTarget());
        }));
    }
}
