package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

public class LiftCommand extends CommandBase {
    private LiftSubsystem lift;
    private GamepadEx gamepadEx;
    private int target=0;
    private boolean finish;

    public LiftCommand(LiftSubsystem liftSubsystem) {
        lift = liftSubsystem;
        addRequirements(liftSubsystem);
        finish = false;
    }

    @Override
    public void execute() {
        lift.setTarget(1500);
        while (!lift.atTargetPosition()) {
            double output = lift.calculate();
            lift.setPower(output);
        }
        finish = true;
    }
    public boolean isFinished() {return finish;}

}
