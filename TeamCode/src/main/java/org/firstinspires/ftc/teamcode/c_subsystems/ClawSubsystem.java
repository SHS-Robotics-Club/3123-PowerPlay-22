package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;


public class ClawSubsystem extends SubsystemBase {
	private ServoEx clawLeft, clawRight, clawPitch;
	private boolean s_active, pit_active;

	public ClawSubsystem(ServoEx clawLeft, ServoEx clawRight, ServoEx clawPitch) {
		this.clawLeft  = clawLeft;
		this.clawRight = clawRight;
		this.clawPitch = clawPitch;
		s_active       = true;
		pit_active     = true;
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

	// switch the toggle
	public void pit_toggle() {
		pit_active = !pit_active;
	}

	// return the active state
	public boolean pit_active() {
		return pit_active;
	}

	public void up() {
		clawPitch.turnToAngle(-45);
	}

	public void down() {
		clawPitch.turnToAngle(-10);
	}
}
