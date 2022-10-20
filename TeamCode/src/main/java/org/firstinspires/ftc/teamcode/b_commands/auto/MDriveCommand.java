package org.firstinspires.ftc.teamcode.b_commands.auto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.auto.MDriveSubsystem;

import java.util.function.DoubleSupplier;

public class MDriveCommand extends CommandBase {

    private final MDriveSubsystem drive;
    private final DoubleSupplier leftY, leftX, rightX;

    public MDriveCommand(MDriveSubsystem drive, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier rightX) {
        this.drive = drive;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.drive(leftY.getAsDouble(), leftX.getAsDouble(), rightX.getAsDouble());
    }

}
