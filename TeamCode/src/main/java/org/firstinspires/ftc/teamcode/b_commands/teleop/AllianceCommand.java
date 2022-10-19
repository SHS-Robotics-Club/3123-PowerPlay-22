package org.firstinspires.ftc.teamcode.b_commands.teleop;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AllianceToggle;

import java.util.List;

public class AllianceCommand extends CommandBase {
    private final HardwareMap hwMap;
    private int color;

    public AllianceCommand(HardwareMap hwMapi){
        hwMap = hwMapi;
    }

    @Override
    public void execute(){
        List<LynxModule> allHubs = hwMap.getAll(LynxModule.class);
        
        if (AllianceToggle.alliance == AllianceToggle.Alliance.RED){
            color = 16711680;
        } else if (AllianceToggle.alliance == AllianceToggle.Alliance.BLUE){
            color = 255;
        }

        for (LynxModule hub : allHubs) {
            hub.setConstant(color);
        }

    }
}
