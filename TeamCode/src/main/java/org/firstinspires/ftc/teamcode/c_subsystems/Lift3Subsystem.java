package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

@Config
public class Lift3Subsystem extends SubsystemBase {
    MotorGroup lift;
    int targetPosition = 0;
    double positionTolerance = 5;

    @Override
    public void periodic() {
        getPosition();
    }

    /**
     * @param lift The MotorGroup housing both lift motors.
     */
    public Lift3Subsystem(MotorGroup lift) {
        this.lift = lift;
    }

    public double getVelocity() {
        return lift.getVelocities().get(0);
    }

    public double getPosition() {
        return lift.getPositions().get(0);
    }

    public void setTolerance(double positionTolerance) {
        this.positionTolerance = positionTolerance;
        lift.setPositionTolerance(positionTolerance);
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
        lift.setTargetPosition(targetPosition);
    }

    public void setkP(double kP){
        lift.setPositionCoefficient(kP);
    }

    public double getError() {
        return -(targetPosition - getPosition());
    }

    public boolean atPositionError() {
        return getError() >= (targetPosition - positionTolerance) && getError() <= (targetPosition + positionTolerance);
    }

    public void setPower(double power){
        lift.set(power);
    }

    public void stop(){
        lift.set(0);
        lift.stopMotor();
    }

    public boolean atTargetPosition(){
        return lift.atTargetPosition();
    }

}