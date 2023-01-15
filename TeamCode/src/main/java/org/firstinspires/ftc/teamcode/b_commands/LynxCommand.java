package org.firstinspires.ftc.teamcode.b_commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.teamcode.AllianceToggle;

import java.util.List;

public class LynxCommand extends CommandBase {
    private final List<LynxModule> revHubs;
    private int color;

    public LynxCommand(List<LynxModule> hubs) {
        revHubs = hubs;
    }

    @Override
    public void initialize() {
        if (AllianceToggle.alliance == AllianceToggle.Alliance.RED) {
            color = 16711680;
        } else if (AllianceToggle.alliance == AllianceToggle.Alliance.BLUE) {
            color = 255;
        }

        for (LynxModule hub : revHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
            hub.setConstant(color);
        }
    }
}