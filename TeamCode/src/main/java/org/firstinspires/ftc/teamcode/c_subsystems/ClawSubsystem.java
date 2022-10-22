package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.hardware.ServoEx;


public class ClawSubsystem extends SubsystemBase {
	private ServoEx clawLeft, clawRight;
	private boolean s_active;

	public ClawSubsystem(ServoEx clawLeft, ServoEx clawRight) {
		this.clawLeft  = clawLeft;
		this.clawRight = clawRight;
		s_active = true;
	}

	// switch the toggle
	public void toggle() {
		s_active = !s_active;
	}

	// return the active state
	public boolean active() {
		return s_active;
	}

	public void open() {
		clawLeft.turnToAngle(30);
		clawRight.turnToAngle(30);
	}

	public void close() {
		clawLeft.turnToAngle(-10);
		clawRight.turnToAngle(-10);
	}
}
