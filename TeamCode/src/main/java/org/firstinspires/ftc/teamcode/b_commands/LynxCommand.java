package org.firstinspires.ftc.teamcode.b_commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.teamcode.AllianceToggle;
import org.firstinspires.ftc.teamcode.c_subsystems.teleop.DriveSubsystem;

import java.util.List;
import java.util.function.DoubleSupplier;

public class LynxCommand extends CommandBase {
    private final List<LynxModule> revHubs;
    private int color =0;

    public LynxCommand(List<LynxModule> hubs) {
        revHubs = hubs;
    }

    @Override
    public void initialize() {
        if (AllianceToggle.alliance == AllianceToggle.Alliance.RED) {
            color = 16711680;
            telemetry.addData(".Alliance", "RED");
        } else if (AllianceToggle.alliance == AllianceToggle.Alliance.BLUE) {
            color = 255;
            telemetry.addData(".Alliance", "BLUE");
        }

        for (LynxModule hub : revHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
            hub.setConstant(color);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}