package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.DriveCommand;
import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.b_commands.LynxCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

import java.util.List;

// @Disabled
@TeleOp(name = "CommandTeleOp", group = ".")
@Config
public class MainTeleOp extends CommandOpMode {
    @Override
    public void initialize() {
        // DEFINITIONS ----------------------------------------------------------------------------------------------------
        // Get Devices
        final Devices devices = new Devices(hardwareMap);

        // Gamepad
        GamepadEx gPad1 = new GamepadEx(gamepad1);

        // Subsystems
        DriveSubsystem driveSubsystem = new DriveSubsystem(devices.frontLeft, devices.frontRight, devices.backLeft, devices.backRight);
        ClawSubsystem claw = new ClawSubsystem(devices.clawLeft, devices.clawRight);
        LiftSubsystem liftSubsystem = new LiftSubsystem(devices.lift);

        // Commands
        DriveCommand driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX);
        LiftCommand liftCommand = new LiftCommand(liftSubsystem, gPad1);
        LynxCommand lynxCommand = new LynxCommand(devices.revHubs);

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

        double value = 2000;

        register(liftSubsystem);
/*        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenHeld(new RunCommand(() -> liftSubsystem.liftPos(0)))
                .whenReleased(new InstantCommand(liftSubsystem::stop));

        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenHeld(new RunCommand(() -> liftSubsystem.liftPos(537)))
                .whenReleased(new InstantCommand(liftSubsystem::stop));

        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenHeld(new RunCommand(() -> liftSubsystem.liftPos(40)))
                .whenReleased(new InstantCommand(liftSubsystem::stop));

        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenHeld(new RunCommand(() -> liftSubsystem.liftPos(60)))
                .whenReleased(new InstantCommand(liftSubsystem::stop));*/
        
        // REGISTER/SCHEDULE ----------------------------------------------------------------------------------------------------
/*      register(driveSubsystem, liftSubsystem);
        ParallelRaceGroup runGroup = new ParallelRaceGroup(driveCommand, liftCommand);


        schedule(runGroup, lynxCommand);*/

        schedule(lynxCommand, liftCommand, new RunCommand(() -> {
            // Telemetry
            telemetry.update();
            telemetry.addData("liftSetL", devices.lift.getPositions());
            telemetry.addData("LiftVelo", devices.lift.getVelocities());
            //idle();
        }));
        register(driveSubsystem);
        driveSubsystem.setDefaultCommand(driveCommand);

        if (isStopRequested()) {
            return;
        }
    }

}