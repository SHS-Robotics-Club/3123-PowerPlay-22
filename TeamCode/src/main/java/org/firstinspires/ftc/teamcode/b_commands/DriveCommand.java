package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private final DriveSubsystem mecDrive;
    private final DoubleSupplier strafe, forward, turn;
    private double multiplier;

    public DriveCommand(DriveSubsystem subsystem, DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier turn, double mult) {
        mecDrive = subsystem;
        this.strafe = strafe;
        this.forward = forward;
        this.turn = turn;
        multiplier = mult;

        addRequirements(subsystem);
    }

    public DriveCommand(DriveSubsystem subsystem, DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier turn) {
        mecDrive = subsystem;
        this.strafe = strafe;
        this.forward = forward;
        this.turn = turn;
        multiplier = 1.0;

        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        mecDrive.drive(strafe.getAsDouble() * 0.9 * multiplier,
                forward.getAsDouble() * 0.9 * multiplier,
                turn.getAsDouble() * 0.88 * multiplier);
    }
}