package org.firstinspires.ftc.teamcode.a_opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AllianceToggle;
import org.firstinspires.ftc.teamcode.b_commands.teleop.AllianceCommand;
import org.firstinspires.ftc.teamcode.b_commands.teleop.DriveCommand;
import org.firstinspires.ftc.teamcode.c_subsystems.teleop.DriveSubsystem;
import org.firstinspires.ftc.teamcode.c_subsystems.teleop.LynxSubsystem;

import java.util.List;

//@Disabled
@TeleOp(name = "CommandTeleOp", group = ".")
@Config
public class MainTeleOp extends CommandOpMode {
    // MOTORS

    // DEFINE DEVICES
    public MotorEx frontLeft, frontRight, backLeft, backRight;
    
    // SUBSYSTEMS
    private DriveSubsystem driveSubsystem;
    private LynxSubsystem lynxSubsystem;

    // COMMANDS
    private DriveCommand driveCommand;
    private AllianceCommand allianceCommand;

    // EXTRAS
    private GamepadEx gPad1, gPad2;
    private RevIMU revIMU;

    // CONSTANTS
    public static double kp = 0, ki = 0, kd = 0;
    public int color = 1;

    @Override
    public void initialize() {
//        List<LynxModule> allHubs = hardwareMap.getAll(LynxModule.class);

        // Initialize Motors
        frontLeft = new MotorEx(hardwareMap, "fL");
        frontRight = new MotorEx(hardwareMap, "fR");
        backLeft = new MotorEx(hardwareMap, "bL");
        backRight = new MotorEx(hardwareMap, "bR");

        // Initialize Extras
        gPad1 = new GamepadEx(gamepad1);
        gPad2 = new GamepadEx(gamepad2);

        // Initialize Commands and Subsystems
        driveSubsystem = new DriveSubsystem(frontLeft, frontRight, backLeft, backRight, revIMU);
        driveCommand = new DriveCommand(driveSubsystem, gPad1::getLeftX, gPad1::getLeftY, gPad1::getRightX);

/*        GamepadButton grabButton = new GamepadButton(
                gPad1, GamepadKeys.Button.A
        );*/

//        if (gPad1.getButton(GamepadKeys.Button.A)){
//            n += 1;
//        }
//
//        for (LynxModule hub : allHubs) {
//            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
//            hub.setConstant(n);
//        }

        if (AllianceToggle.alliance == AllianceToggle.Alliance.RED){
            color = 16711680;
        } else if (AllianceToggle.alliance == AllianceToggle.Alliance.BLUE){
            color = 255;
        }

        allianceCommand = new AllianceCommand(hardwareMap);

        // Motor Settings
        frontLeft.resetEncoder();
        frontRight.resetEncoder();
        backLeft.resetEncoder();
        backRight.resetEncoder();

        frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
        frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
        backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
        backRight.setRunMode(MotorEx.RunMode.VelocityControl);

        frontLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(MotorEx.ZeroPowerBehavior.BRAKE);

        frontLeft.setVeloCoefficients(kp, ki, kd);
        frontRight.setVeloCoefficients(kp, ki, kd);
        backLeft.setVeloCoefficients(kp, ki, kd);
        backRight.setVeloCoefficients(kp, ki, kd);

        // Default Drivetrain Command
        register(driveSubsystem);
        driveSubsystem.setDefaultCommand(driveCommand);

        // Telemetry
        schedule(new RunCommand(() -> {
            telemetry.addData("frontLeft encoder position", frontLeft.encoder.getPosition());
            telemetry.addData("frontRight encoder position", frontRight.encoder.getPosition());
            telemetry.addData("backLeft encoder position", backLeft.encoder.getPosition());
            telemetry.addData("backRight encoder position", backRight.encoder.getPosition());

            telemetry.addData("asd", color);

            telemetry.update();
        }));

        idle();
    }

}
