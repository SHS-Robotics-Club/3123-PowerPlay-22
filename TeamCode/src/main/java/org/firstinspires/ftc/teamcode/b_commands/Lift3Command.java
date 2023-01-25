package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.Lift3Subsystem;

enum LiftStates3 {
    READY, MOVING
}

public class Lift3Command extends CommandBase {
    public static double kP = 0.05;
    public static double posTol = 5;
    GamepadEx gamepadEx;
    int targetPosition = 0;
    Lift3Subsystem lift;
    LiftStates3 liftStates;

    public Lift3Command(Lift3Subsystem liftSubsystem, GamepadEx gamepadEx) {
        lift = liftSubsystem;
        this.gamepadEx = gamepadEx;
        liftStates = LiftStates3.READY;
        addRequirements(liftSubsystem);
    }
    @Override
    public void initialize() {
        lift.setkP(kP);
        lift.setTolerance(posTol);
        lift.setTargetPosition(0);
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
        lift.setTargetPosition(targetPosition);
        switch (liftStates) {
            case READY:
                if (lift.atTargetPosition()) {
                    lift.stop();
                }
                if (!lift.atTargetPosition()){
                    liftStates = LiftStates3.MOVING;
                }
            case MOVING:
                if (!lift.atTargetPosition()) {
                    lift.setPower(0.5);
                }
                if (lift.atTargetPosition()){
                    liftStates = LiftStates3.READY;
                }
        }
    }

}
