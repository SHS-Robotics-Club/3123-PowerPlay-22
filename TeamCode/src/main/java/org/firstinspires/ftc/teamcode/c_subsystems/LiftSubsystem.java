package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

import java.util.List;

@Config
public class LiftSubsystem extends SubsystemBase {
	MotorGroup lift;
	public static final double kP = 0.05, T = 5;
	private              LiftState liftState     = LiftState.FLOOR;
	private static final int[]     liftPositions = {0, 500, 1500, 3000};

	/**
	 * @param lift The MotorGroup housing both lift motors.
	 */
	public LiftSubsystem(MotorGroup lift) {
		this.lift = lift;
	}

	@Override
	public void periodic() {
		int target = 0;
		switch (liftState) {
			case FLOOR:
				target = 0;
			case LOW:
				target = 1;
			case MID:
				target = 2;
			case HIGH:
				target = 3;
		}

		lift.setPositionCoefficient(kP);
		lift.setPositionTolerance(T);
		lift.set(0);
		while (!lift.atTargetPosition()) {
			lift.setTargetPosition(liftPositions[target]);
			lift.set(0.5);
		}
		lift.stopMotor();
	}

	/**
	 * @param liftState The State the lift should take. FLOOR, LOW, MID, HIGH.
	 */
	public void set(LiftState liftState) {
		this.liftState = liftState;
	}

	public double getVelocity() {
		List<Double> velocitys = lift.getVelocities();
		return velocitys.get(0);
	}

	public double getPosition() {
		List<Double> positions = lift.getPositions();
		return positions.get(0);
	}
}