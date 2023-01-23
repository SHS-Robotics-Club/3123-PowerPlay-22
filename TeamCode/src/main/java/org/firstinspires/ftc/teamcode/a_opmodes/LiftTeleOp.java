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
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

//@Disabled
@Config
@TeleOp(name = "LiftTeleOp", group = ".")
public class LiftTeleOp extends CommandOpMode {
    @Override
    public void initialize() {
        if (isStopRequested()) return;

        // Get Devices
        final Devices devices = new Devices(hardwareMap);

        // Define Systems ----------------------------------------------------------------------------------------------------
        LiftSubsystem liftSubsystem = new LiftSubsystem(devices.lift);

        LiftCommand liftCommand = new LiftCommand(liftSubsystem);

        register(liftSubsystem);
        liftSubsystem.setDefaultCommand(liftCommand);
/*        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new LiftCommand(liftSubsystem, 0).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new LiftCommand(liftSubsystem, 1).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new LiftCommand(liftSubsystem, 2).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new LiftCommand(liftSubsystem, 3).withTimeout(5000));*/

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
