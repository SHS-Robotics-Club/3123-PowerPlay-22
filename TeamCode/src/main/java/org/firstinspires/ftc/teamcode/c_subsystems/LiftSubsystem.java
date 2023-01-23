package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

@Config
public class LiftSubsystem extends SubsystemBase {
    MotorGroup lift;
    private static final int[] liftPositions = {0, 500, 1500, 3000};
    public static double kP = 0.05, kI = 0.0, kD = 0.0;
    public static double kS = 0.0, kG = 0.0, kV = 0, kA = 0.0;
    public static double velo = 10, accel = 20;
    ElevatorFeedforward ff  = new ElevatorFeedforward(kS, kG, kV, kA);
    PIDFController pid = new PIDFController(kP, kI, kD, 0);

    /**
     * @param lift The MotorGroup housing both lift motors.
     */
    public LiftSubsystem(MotorGroup lift) {
        this.lift = lift;
    }

    public double getVelocity() {
        return lift.getVelocities().get(0);
    }

    public double getPosition() {
        return lift.getPositions().get(0);
    }

    public void setTolerances(double positionTolerance, double velocityTolerance) {
        pid.setTolerance(positionTolerance, velocityTolerance);
    }

    public double[] getTolerances(){
        return pid.getTolerance();
    }

    public void setTarget(int target) {
        pid.setSetPoint(target);
    }

    public double getPositionError(){
        return pid.getPositionError();
    }

    public double getTarget(){
        return pid.getSetPoint();
    }

    public void setPower(double power){
        lift.set(power);
    }

    public void stop(){
        lift.set(0);
        lift.stopMotor();
    }

    public double calculate() {
        pid.setF(ff.calculate(velo, accel));
        return pid.calculate(getPosition());
    }

    public boolean atTargetPosition(){
        return pid.atSetPoint();
    }

}