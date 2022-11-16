package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;


public class ClawSubsystem extends SubsystemBase {
	private ServoEx clawLeft, clawRight;
	private boolean active;
	
	public ClawSubsystem(ServoEx clawLeft, ServoEx clawRight) {
		this.clawLeft  = clawLeft;
		this.clawRight = clawRight;
		active         = true;
	}
	
	// Switch the toggle
	public void toggle() {
		active = !active;
	}
	
	// Return the active state
	public boolean active() {
		return active;
	}
	
	public void open() {
		clawLeft.turnToAngle(-40);
		clawRight.turnToAngle(-40);
	}
	
	public void close() {
		clawLeft.turnToAngle(20);
		clawRight.turnToAngle(20);
	}
}
