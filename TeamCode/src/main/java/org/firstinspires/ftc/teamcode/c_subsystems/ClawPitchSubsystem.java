package org.firstinspires.ftc.teamcode.c_subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;


public class ClawPitchSubsystem extends SubsystemBase {
	private ServoEx clawPitch;
	private boolean pit_active;

	public ClawPitchSubsystem(ServoEx clawPitch) {
		this.clawPitch = clawPitch;
		pit_active = true;
	}

	// switch the toggle
	public void toggle() {
		pit_active = !pit_active;
	}

	// return the active state
	public boolean active() {
		return pit_active;
	}

	public void up() {
		clawPitch.turnToAngle(-45);
	}

	public void down() {
		clawPitch.turnToAngle(-10);
	}
}
