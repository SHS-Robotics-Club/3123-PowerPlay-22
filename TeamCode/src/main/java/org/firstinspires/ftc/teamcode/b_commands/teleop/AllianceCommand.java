package org.firstinspires.ftc.teamcode.b_commands.teleop;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.teamcode.c_subsystems.teleop.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.teleop.LynxSubsystem;

import java.util.function.DoubleSupplier;

public class AllianceCommand extends CommandBase {
    private final LynxSubsystem mecDrive;
    private int ledcolor;
    private LynxModule.BulkCachingMode cachingMode;

    public AllianceCommand(LynxSubsystem subsystem, LynxModule.BulkCachingMode mode, int color){
        mecDrive = subsystem;
        ledcolor = color;
        cachingMode = mode;

        addRequirements(subsystem);
    }

    @Override
    public void execute(){
        mecDrive.revHub(cachingMode, ledcolor);
    }
}
