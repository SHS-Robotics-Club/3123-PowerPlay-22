package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.c_subsystems.LiftSubsystem;

enum LiftStates {
    READY, MOVING
}

public class LiftCommand extends CommandBase {
    public static double posTol = 1;
    GamepadEx gamepadEx;
    public int targetPosition = 0;
    LiftSubsystem lift;
    LiftStates liftStates;
    LiftLevels liftLevels;

    enum LiftLevels {
        FLOOR(0), LOW(1000), MED(2000), HIGH(2500);

        private int levelPos;

        LiftLevels(int levelPos) {
            this.levelPos = levelPos;
        }

        public int getLevelPos(){
            return levelPos;
        }

    }

    public LiftCommand(LiftSubsystem liftSubsystem, GamepadEx gamepadEx) {
        lift = liftSubsystem;
        this.gamepadEx = gamepadEx;
        liftStates = LiftStates.READY;
        liftLevels = LiftLevels.FLOOR;
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
            liftLevels = LiftLevels.FLOOR;
        } else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT)) {
            liftLevels = LiftLevels.LOW;
        } else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
            liftLevels = LiftLevels.MED;
        } else if (gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
            liftLevels = LiftLevels.HIGH;
        }
        lift.getPosition();
        lift.setSetPoint(liftLevels.getLevelPos());
        switch (liftStates) {
            case READY:
                if (lift.atSetPoint()) {
                    lift.stop();
                }
                if (!lift.atSetPoint()){
                    liftStates = LiftStates.MOVING;
                }
            case MOVING:
                if (!lift.atSetPoint()) {
                    lift.set(lift.calculate(lift.getPosition(), 0, 0));
                }
                if (lift.atSetPoint()){
                    liftStates = LiftStates.READY;
                }
        }
    }

}
