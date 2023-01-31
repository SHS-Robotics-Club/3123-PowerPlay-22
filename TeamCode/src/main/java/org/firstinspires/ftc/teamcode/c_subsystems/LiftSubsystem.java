package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

@Config
public class LiftSubsystem extends SubsystemBase {
	public static double kP = 0.01, kI = 0.0, kD = 0.0001, kS = 0.0, kG = 0.0, kV = 0.0, kA = 0.0;
	boolean lower = false;
	MotorGroup lift;
	MotorEx    liftLeft, liftRight;
	double              botVoltage;
	int                 targetPosition = 0;
	PIDFController      pidf           = new PIDFController(kP, kI, kD, 0);
	ElevatorFeedforward eff            = new ElevatorFeedforward(kS, kG, kV, kA);

	public LiftSubsystem(MotorEx liftLeft, MotorEx liftRight) {
		this.liftLeft  = liftLeft;
		this.liftRight = liftRight;

		lift = new MotorGroup(liftLeft, liftRight);
		lift.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
	}

	@Override
	public void periodic() {
		getPosition();
		getVelocity();
		calculate();
	}

	public void setLower(boolean lower){
		this.lower = lower;
	}

	public boolean getLower(){
		return lower;
	}

	public void set(double output) {
		lift.set(output);
	}

	public void stop() {
		lift.stopMotor();
	}

	/**
	 * @return the velocity of the motor in ticks per second
	 */
	public double getVelocity() {
		return lift.getVelocities().get(0);
	}

	/**
	 * @param velocity the velocity in ticks per second
	 */
	public void setVelocity(double velocity) {
		liftLeft.setVelocity(velocity);
		liftRight.setVelocity(velocity);
	}

	/**
	 * @return the acceleration of the motor in ticks per second squared
	 */
	public double getAcceleration() {
		return liftLeft.getAcceleration();
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
		return liftLeft.getDistance();
	}

	/**
	 * @return the velocity of the encoder adjusted to account for the distance per pulse
	 */
	public double getRate() {
		return liftLeft.getRate();
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
	 * Corrects for velocity overflow
	 *
	 * @return the corrected velocity
	 */
	public double getCorrectedVelocity() {
		return liftLeft.getCorrectedVelocity();
	}

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
	 * @return the PIDF coefficients
	 */
	public double[] getCoefficients() {
		return pidf.getCoefficients();
	}

	/**
	 * @return the positional error e(t)
	 */
	public double getPositionError() {
		return pidf.getPositionError();
	}

	/**
	 * @return the tolerances of the controller
	 */
	public double[] getTolerance() {
		return pidf.getTolerance();
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
	 * @return the velocity error e'(t)
	 */
	public double getVelocityError() {
		return pidf.getVelocityError();
	}

	/**
	 * Calculates the control value, u(t).
	 *
	 * @param veloSetPoint  VelocitySetPoint
	 * @return the value produced by u(t).
	 */
	public double calculate(double veloSetPoint) {
		pidf.setF(eff.calculate(veloSetPoint));
		return pidf.calculate(getPosition());
	}

	public double calculate() {
		return pidf.calculate();
	}

}