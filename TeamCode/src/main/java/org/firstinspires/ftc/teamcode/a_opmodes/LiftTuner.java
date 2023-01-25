package org.firstinspires.ftc.teamcode.a_opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.b_commands.LiftCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

//@Disabled
@Config
@TeleOp(name = "LiftTuner", group = ".")
public class LiftTuner extends CommandOpMode {
    @Override
    public void initialize() {
        if (isStopRequested()) return;

        // Get Devices
        final Devices devices = new Devices(hardwareMap);
        GamepadEx gPad1 = new GamepadEx(gamepad1);

        // Define Systems ----------------------------------------------------------------------------------------------------
        LiftSubsystem liftSubsystem = new LiftSubsystem(devices.liftLeft, devices.liftRight);

        LiftCommand liftCommand = new LiftCommand(liftSubsystem, gPad1);

        register(liftSubsystem);
/*        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new LiftCommand(liftSubsystem, 0).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new LiftCommand(liftSubsystem, 1).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new LiftCommand(liftSubsystem, 2).withTimeout(5000));
        gPad1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new LiftCommand(liftSubsystem, 3).withTimeout(5000));*/

        schedule(liftCommand.alongWith(new RunCommand(() -> {
            // Telemetry
            telemetry.update();
            telemetry.addData("LiftPos", liftSubsystem.getPosition());
        })));
    }
}
