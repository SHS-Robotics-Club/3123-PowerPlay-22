package org.firstinspires.ftc.teamcode.c_subsystems.teleop;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;

import java.util.List;

public class LynxSubsystem extends SubsystemBase {
    List<LynxModule> allHubs;

    public LynxSubsystem(List lynx){
        allHubs = lynx;

    }

    // Method which runs the subsystem
    public void revHub( int color) {
        for (LynxModule hub : allHubs) {
            hub.setConstant(color);
        }
    }
}
