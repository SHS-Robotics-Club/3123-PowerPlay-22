package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.Lift3Subsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.Lift3SubsystemBB;

enum LiftStates3BB {
    READY, MOVING
}

public class Lift3CommandBB extends CommandBase {
    public static double kP = 0.05;
    public static double posTol = 5;
    GamepadEx gamepadEx;
    int targetPosition = 0;
    Lift3SubsystemBB lift;
    LiftStates3BB liftStates;

    public Lift3CommandBB(Lift3SubsystemBB liftSubsystem, GamepadEx gamepadEx) {
        lift = liftSubsystem;
        this.gamepadEx = gamepadEx;
        liftStates = LiftStates3BB.READY;
        addRequirements(liftSubsystem);
    }
    @Override
    public void initialize() {
        lift.setTolerance(posTol);
        lift.setSetPoint(0);
        lift.stop();
    }

    @Override
    public void execute() {
        if (gamepadEx.getButton(GamepadKeys.Button.DPAD_DOWN)) {
            targetPosition = 0;
        } else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT)) {
            targetPosition = 500;
        } else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
            targetPosition = 1000;
        } else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
            targetPosition = 1500;
        }
        lift.getPosition();
        lift.setSetPoint(targetPosition);
        switch (liftStates) {
            case READY:
                if (lift.atSetPoint()) {
                    lift.stop();
                }
                if (!lift.atSetPoint()){
                    liftStates = LiftStates3BB.MOVING;
                }
            case MOVING:
                if (!lift.atSetPoint()) {
                    lift.set(lift.calculate(lift.getPosition(), 0, 0));
                }
                if (lift.atSetPoint()){
                    liftStates = LiftStates3BB.READY;
                }
        }
    }

}
