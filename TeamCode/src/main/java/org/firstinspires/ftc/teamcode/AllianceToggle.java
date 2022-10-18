package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.ftccommon.external.OnCreate;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.robotcore.internal.system.Misc;

/**
 * Main class for interacting with the instance.
 */
public class AllianceToggle {
    public static Alliance alliance = Alliance.RED; // Current Alliance

    // Alliance switch
    public enum Alliance {
        RED, BLUE;

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static boolean suppressOpMode = false;

    private static AllianceToggle instance;

    @OpModeRegistrar
    public static void registerOpMode(OpModeManager manager) {
        if (instance != null && !suppressOpMode) {
            instance.internalRegisterOpMode(manager);
        }
    }

    /**
     * Starts the dashboard.
     */
    @OnCreate
    public static void start(Context context) {
        if (instance == null) {
            instance = new AllianceToggle();
        }
    }

    private boolean Team;

    private AllianceToggle() {
        Activity activity = AppUtil.getInstance().getActivity();
    }

    private void RED() {
        if (Team)
            return;
        Team = true;
        alliance = Alliance.RED;
    }

    private void BLUE() {
        if (!Team)
            return;
        Team = false;
        alliance = Alliance.BLUE;
    }

    private void internalRegisterOpMode(OpModeManager manager) {
        manager.register(
                new OpModeMeta.Builder().setName("Red/Blue Team")
                        .setFlavor(OpModeMeta.Flavor.TELEOP).setGroup("dash").build(),
                new LinearOpMode() {
                    @Override
                    public void runOpMode() {
                        telemetry.log().add(Misc.formatInvariant(
                                "Robot is configured for %s Team. Press Start to configure it for %s Team.",
                                Team ? "Red" : "Blue", Team ? "Blue" : "Red"));
                        telemetry.update();

                        waitForStart();

                        if (isStopRequested()) {
                            return;
                        }

                        if (Team) {
                            BLUE();
                        } else {
                            RED();
                        }
                    }
                });
    }
}
