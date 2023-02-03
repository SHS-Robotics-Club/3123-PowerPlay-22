package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

@Config
public class LiftSubsystem extends SubsystemBase {
	public static double kP = 0.01, kI = 0.0, kD = 0.0001, kS = 0.0, kG = 0.0, kV = 0, kA = 0.0;
	// kv = 1.0 / (312 * 2 * Math.PI * 1.7 / 60.0)
	public static double positionTolerance = 5.0, velocityTolerance = 1.0;
	public static double     ffVelocity = 50;
	public static LiftLevels liftLevels = LiftLevels.FLOOR;
	boolean             lower = false;
	MotorGroup          lift;
	CRServo             spool;
	PIDFController      pidf  = new PIDFController(kP, kI, kD, 0);
	ElevatorFeedforward eff   = new ElevatorFeedforward(kS, kG, kV, kA);

	public LiftSubsystem(MotorGroup lift, CRServo spool) {
		this.lift    = lift;
		this.spool   = spool;

		setTolerance(positionTolerance, velocityTolerance);
	}

	@Override
	public void periodic() {
		checkPosition();
	}

	public void lowerLift(boolean lower) {
		this.lower = lower;
	}

	public boolean checkLowered() {
		return lower;
	}

	public void setSpool(double output) {
		spool.set(output);
	}

	public void stopSpool() {
		setSpool(0);
		spool.stopMotor();
	}

	public void set(double output) {
		lift.set(output);
	}

	public void stop() {
		set(0);
		lift.stopMotor();
	}

	/**
	 * @return the velocity of the motor in ticks per second
	 */
	public double getVelocity() {
		return lift.getVelocities().get(0);
	}

	/**
	 * @return the current position of the encoder
	 */
	public Double getPosition() {
		return lift.getPositions().get(0);
	}

	/**
	 * @return the distance traveled by the encoder
	 */
	public double getDistance() {
		return lift.getDistance();
	}


	/**
	 * Resets the encoder without having to stop the motor.
	 */
	public void resetEncoder() {
		lift.resetEncoder();
	}

	/**
	 * Resets pid loop.
	 */
	public void reset() {
		pidf.reset();
	}

	/**
	 * Sets the error which is considered tolerable for use with {@link #atSetPoint()}.
	 *
	 * @param positionTolerance Position error which is tolerable.
	 * @param velocityTolerance Velocity error which is tolerable.
	 */
	public void setTolerance(double positionTolerance, double velocityTolerance) {
		pidf.setTolerance(positionTolerance, velocityTolerance);
	}

	/**
	 * Sets the error which is considered tolerable for use with {@link #atSetPoint()}.
	 *
	 * @param positionTolerance Position error which is tolerable.
	 */
	public void setTolerance(double positionTolerance) {
		pidf.setTolerance(positionTolerance);
	}

	/**
	 * Returns the current setpoint of the PIDFController.
	 *
	 * @return The current setpoint.
	 */
	public double getSetPoint() {
		return pidf.getSetPoint();
	}

	/**
	 * Sets the setpoint for the PIDFController
	 *
	 * @param sp The desired setpoint.
	 */
	public void setSetPoint(double sp) {
		pidf.setSetPoint(sp);
	}

	/**
	 * Returns true if the error is within the percentage of the total input range, determined by
	 * {@link #setTolerance}.
	 *
	 * @return Whether the error is within the acceptable bounds.
	 */
	public boolean atSetPoint() {
		return pidf.atSetPoint();
	}


	/**
	 * @return the positional error e(t)
	 */
	public double getPositionError() {
		return pidf.getPositionError();
	}

	/**
	 * @return the velocity error e'(t)
	 */
	public double getVelocityError() {
		return pidf.getVelocityError();
	}

	/**
	 * Calculates the control value, u(t).
	 *
	 * @return the value produced by u(t).
	 */
	public double calculate() {
		pidf.setF(eff.calculate(ffVelocity));
		return pidf.calculate(getPosition());
	}

	/**
	 * Checks if the lift is where it should be.
	 */
	public void checkPosition() {
		setSetPoint(liftLevels.getLevelPos(checkLowered()));
		getPosition();
		if (atSetPoint()) {
			stop();
			stopSpool();
		} else if (!atSetPoint()) {
			set(calculate());
			setSpool(1);
		}
	}

	/**
	 * @return the PIDF coefficients
	 */
	public double[] getCoefficients() {
		return pidf.getCoefficients();
	}

	public enum LiftLevels {
		FLOOR(0, 1.0), LOW(450, 1.0), MED(2990, 0.8), HIGH(2250, 0.7);

		private final int    levelPos;
		private final double driveMult;

		LiftLevels(int levelPos, double driveMult) {
			this.levelPos  = levelPos;
			this.driveMult = driveMult;
		}

		public int getLevelPos(boolean lower) {
			if (lower) {
				return levelPos - 200;
			} else {
				return levelPos;
			}
		}

		public double getDriveMult() {
			return driveMult;
		}
	}
}