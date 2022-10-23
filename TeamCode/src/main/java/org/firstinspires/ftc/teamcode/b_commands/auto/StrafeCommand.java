package org.firstinspires.ftc.teamcode.b_commands.auto;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.auto.MecanumSubsystem;

public class StrafeCommand extends CommandBase {

    private final MecanumSubsystem drive;
    private final double strafeX, strafeY;

    public StrafeCommand(MecanumSubsystem drive, double strafeX, double strafeY) {
        this.drive = drive;
        this.strafeX = strafeX;
        this.strafeY = strafeY;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.strafe(strafeX, strafeY);
    }

    @Override
    public void execute() {
        drive.update();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drive.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !drive.isBusy();
    }

}
