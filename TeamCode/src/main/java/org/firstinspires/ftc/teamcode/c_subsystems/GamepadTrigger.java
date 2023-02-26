package org.firstinspires.ftc.teamcode.c_subsystems;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

/*public class LeftTrigger extends Trigger {
	public static boolean get(GamepadEx gPad1) {
		return new TriggerReader(
				gPad1, GamepadKeys.Trigger.LEFT_TRIGGER
		).isDown();
	}
}*/

public class GamepadTrigger extends Button {

	private final GamepadEx m_gamepad;
	private final GamepadKeys.Trigger[] m_triggers;

	/**
	 * Creates a gamepad button for triggering commands.
	 *
	 * @param gamepad the gamepad with the buttons
	 * @param triggers the specified trigger
	 */
	public GamepadTrigger(GamepadEx gamepad, @NonNull GamepadKeys.Trigger... triggers) {
		m_gamepad = gamepad;
		m_triggers = triggers;
	}

	/**
	 * Gets the value of the joystick button.
	 *
	 * @return The value of the joystick button
	 */
	@Override
	public boolean get() {
		boolean res = true;
		for (GamepadKeys.Trigger trigger : m_triggers)
			res = res && m_gamepad.getTrigger(trigger) > 0;
		return res;
	}

}