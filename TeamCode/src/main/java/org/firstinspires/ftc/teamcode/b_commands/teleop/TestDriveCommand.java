package org.firstinspires.ftc.teamcode.b_commands.teleop;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.c_subsystems.teleop.TestDriveSubsystem;

import java.util.function.DoubleSupplier;

public class TestDriveCommand extends CommandBase {
    private final TestDriveSubsystem mecDrive;
    private final DoubleSupplier m_strafe, m_forward, m_turn;
    private double multiplier;

    public TestDriveCommand(TestDriveSubsystem subsystem, DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier turn, double mult){
        mecDrive = subsystem;
        m_strafe = strafe;
        m_forward = forward;
        m_turn = turn;
        multiplier = mult;

        addRequirements(subsystem);
    }
    public TestDriveCommand(TestDriveSubsystem subsystem, DoubleSupplier strafe, DoubleSupplier forward, DoubleSupplier turn){
        mecDrive = subsystem;
        m_strafe = strafe;
        m_forward = forward;
        m_turn = turn;
        multiplier = 1.0;

        addRequirements(subsystem);
    }

    @Override
    public void execute(){
//        mecDrive.drive(m_strafe.getAsDouble() * 0.8 * multiplier,
//                m_forward.getAsDouble() * 0.8 * multiplier,
//                m_turn.getAsDouble() * 0.78 * multiplier);
        mecDrive.drive(m_strafe.getAsDouble() * 0.9 * multiplier,
                m_forward.getAsDouble() * 0.9 * multiplier,
                m_turn.getAsDouble() * 0.88 * multiplier);
    }
}
