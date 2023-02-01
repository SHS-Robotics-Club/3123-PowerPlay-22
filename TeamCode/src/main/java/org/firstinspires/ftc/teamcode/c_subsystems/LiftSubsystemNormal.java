package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Config
public class LiftSubsystemNormal extends SubsystemBase {
	//public static double kP = 0.01, kI = 0.0, kD = 0.0001, kS = 0.0, kV = 0.0;
	private int targetPosition;
	boolean             lower = false;
	MotorGroup          lift;

	public LiftSubsystemNormal(MotorGroup lift) {
		this.lift = lift;
	}

	@Override
	public void periodic() {
		getPosition();
		getVelocity();
	}

	public boolean getLower() {
		return lower;
	}

	public void setLower(boolean lower) {
		this.lower = lower;
	}

	public void setCoeff(double kP){
		lift.setPositionCoefficient(kP);
	}

	public void set(double output) {
		lift.set(output);
	}

	public void stop() {
		//set(0);
		lift.stopMotor();
	}

	/**
	 * @return the velocity of the motor in ticks per second
	 */
	public double getVelocity() {
		return lift.getVelocities().get(0);
	}

	/**
	 * Corrects for velocity overflow
	 *
	 * @return the corrected velocity
	 */
	public double getCorrectedVelocity() {
		return lift.getCorrectedVelocity();
	}

	/**
	 * @return the velocity of the encoder adjusted to account for the distance per pulse
	 */
	public double getRate() {
		return lift.getRate();
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
	 * Sets the distance per pulse of the encoder.
	 *
	 * @param distancePerPulse the desired distance per pulse (in units per tick)
	 */
	public void setDistancePerPulse(double distancePerPulse) {
		lift.setDistancePerPulse(distancePerPulse);
	}

	/**
	 * Sets the setpoint for the PIDFController
	 *
	 * @param targetPosition The desired setpoint.
	 */
	public void setTargetPosition(int targetPosition) {
		this.targetPosition = targetPosition;
		lift.setTargetPosition(targetPosition);
	}

	/**
	 * Returns the current setpoint of the PIDFController.
	 *
	 * @return The current setpoint.
	 */
	public double getTargetPosition() {
		return targetPosition;
	}

	/**
	 * Sets the error which is considered tolerable for use with {@link #atTargetPosition()}.
	 *
	 * @param positionTolerance Position error which is tolerable.
	 */
	public void setTolerance(double positionTolerance) {
		lift.setPositionTolerance(positionTolerance);
	}

	/**
	 * Returns true if the error is within the percentage of the total input range, determined by
	 * {@link #setTolerance}.
	 *
	 * @return Whether the error is within the acceptable bounds.
	 */
	public boolean atTargetPosition() {
		return lift.atTargetPosition();
	}

	/**
	 * @return the positional error e(t)
	 */
	public double getPositionError() {
		return getPosition() - targetPosition;
	}

}