package org.firstinspires.ftc.teamcode.c_subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;

@Config
public class LiftSubsystem extends SubsystemBase {
    private MotorGroup lift;
    private PIDController liftPID;
    private ElevatorFeedforward liftFF;
    public static final double kP = 0.05, kI = 0, kD = 0;
    public static final double kV = 0, kA = 0;

    public LiftSubsystem(MotorGroup lift) {
        this.lift = lift;
/*        liftPID = new PIDController(kP, kI, kD);
        liftFF = new ElevatorFeedforward(kS, kG, kV, kA);*/
    }

/*    public void liftSet(double targetPosition, double targetVelocity, double targetAcceleration) {
        lift.set(output);
*//*        double pidOutput = liftPID.calculate(lift.getCurrentPosition(), targetPosition);
        double ffOutput = liftFF.calculate(targetVelocity, targetAcceleration);*//*

*//*        output = pidOutput + ffOutput;*//*
    }*/

/*    public void liftSetL(int targetPosition) {
        lift.setPositionCoefficient(kP);
        lift.setVeloCoefficients(kP, kI, kD);
        //lift.setVeloCoefficients(kP, kI, kD);
        //lift.setFeedforwardCoefficients(kV, kA);
        lift.setTargetPosition((int) (targetPosition / gbTicksDeg));
        lift.set(0);
        lift.setPositionTolerance(300);

        while (!lift.atTargetPosition()) {
            lift.set(0.5);
        }
        lift.stopMotor();
    }*/
    public void liftSet(int targetPosition) {
        // set and get the position coefficient
        lift.setPositionCoefficient(kP);   //Default: 0.05

        lift.setVeloCoefficients(kP, kI, kD);

        // set the target position
        lift.setTargetPosition(targetPosition);  // an integer representing desired tick count

        lift.set(0);

        // set the tolerance
        lift.setPositionTolerance(13.6);  // allowed maximum error Default: 13.6

        // perform the control loop
        while (!lift.atTargetPosition()) {
            lift.set(0.5);
        }
        if(lift.atTargetPosition()){

        }

        lift.stopMotor();   // stop the motor
    }

    public void stop(){
        lift.stopMotor();
    }
}
