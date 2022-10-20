package org.firstinspires.ftc.teamcode.linear;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceToggle;

import java.text.DecimalFormat;
import java.util.List;

@Disabled
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "LinearTeleOp", group = ".")
@Config
public class TeleOp extends LinearOpMode {

    // TICK TO DEG

    // CONFIGURATION
    public static double DRIVE_MULT = 1; // Drive speed multiplier
    public static double TURN_MULT = 1; // Turn speed multiplier
    public static double STRAFE_MULT = 1.1; // Strafe speed multiplier

    private static final DecimalFormat decimalTenths = new DecimalFormat("0.00");
    private static final DecimalFormat doubleDigits = new DecimalFormat("00");

    // RUN-TIME
    ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Get Hardware
        final Hardware bot = new Hardware(hardwareMap);

        // Setup Dashboard Telemetry
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        Telemetry dTelemetry = dashboard.getTelemetry();

        // Set Status
        telemetry.addData("!Status", "Initialized");
        telemetry.update();

        // START
        waitForStart();
        time.reset();

        if (isStopRequested())
            return;

        while (opModeIsActive()) {
            // GAME-PADS
            GamepadEx gp1 = new GamepadEx(gamepad1);
            // GamepadEx gp2 = new GamepadEx(gamepad2);

            // DRIVE-----------------------------------------------------------------------------------------
            // Mecanum
            MecanumDrive mdrive =
                    new MecanumDrive(bot.frontLeft, bot.frontRight, bot.backLeft, bot.backRight);

            // Set Drive Components
            double strafe = gp1.getLeftX() * STRAFE_MULT;
            double drive = gp1.getLeftY() * DRIVE_MULT;
            double turn = gp1.getRightX() * TURN_MULT;

            // Robot Centric Drive
            mdrive.driveRobotCentric(strafe, drive, turn);

            // MISC------------------------------------------------------------------------------------------
            // Run-Time
            long seconds = round(time.time());
            long t1 = seconds % 60;
            long t2 = seconds / 60;
            long t3 = t2 % 60;
            t2 = t2 / 60;

            // TELEMETRY--------------------------------------------------------------------------------------
            // Driver Station Telemetry
            telemetry.addData("!Status", "Run Time: " + doubleDigits.format(t2) + ":"
                    + doubleDigits.format(t3) + ":" + doubleDigits.format(t1)); // Run Time HH:MM:SS

            // Dashboard Specific Telemetry
            dTelemetry.addData("Voltage", decimalTenths.format(bot.volt) + " V"); // Voltage

            telemetry.update();
            idle();
        }
    }
}
