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
    public static final double kS = 0, kG = 0, kV = 0, kA = 0;
    double output = 0;

    public LiftSubsystem(MotorGroup lift) {
        this.lift = lift;
        liftPID = new PIDController(kP, kI, kD);
        liftFF = new ElevatorFeedforward(kS, kG, kV, kA);
    }

    public void liftSet(double targetPosition, double targetVelocity, double targetAcceleration) {
        lift.set(output);
        double pidOutput = liftPID.calculate(lift.getCurrentPosition(), targetPosition);
        double ffOutput = liftFF.calculate(targetVelocity, targetAcceleration);

        output = pidOutput + ffOutput;
    }

}
