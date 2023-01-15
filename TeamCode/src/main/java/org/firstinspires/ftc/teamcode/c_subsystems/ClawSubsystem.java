package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;

public class ClawSubsystem extends SubsystemBase {
    private final ServoEx clawLeft;
    private final ServoEx clawRight;
    private boolean isOpen;

    public ClawSubsystem(ServoEx clawLeft, ServoEx clawRight) {
        this.clawLeft = clawLeft;
        this.clawRight = clawRight;
        isOpen = true;
    }

    public void toggle() {
        isOpen = !isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        clawLeft.turnToAngle(20);
        clawRight.turnToAngle(20);
        isOpen = true;
    }

    public void close() {
        clawLeft.turnToAngle(-15);
        clawRight.turnToAngle(-15);
        isOpen = false;
    }
}
