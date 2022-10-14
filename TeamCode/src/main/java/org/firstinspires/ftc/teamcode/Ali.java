package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;

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
public class Ali {
    private static boolean suppressOpMode = false;

    private static Ali instance;

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
            instance = new Ali();
        }
    }

    /**
     * Returns the active instance instance. This should be called after {@link #start(Context)}.
     * @return active instance instance or null outside of its lifecycle
     */
    public static Ali getInstance() {
        return instance;
    }

    private boolean REDd;

    private Ali() {


        Activity activity = AppUtil.getInstance().getActivity();
    }

    private void RED() {
        if (REDd) return;
        REDd = true;
        GlobalConfig.alliance = GlobalConfig.Alliance.RED;
    }

    private void BLUE() {
        if (!REDd) return;
        REDd = false;
        GlobalConfig.alliance = GlobalConfig.Alliance.BLUE;
    }

    private void internalRegisterOpMode(OpModeManager manager) {
        manager.register(
                new OpModeMeta.Builder()
                    .setName("RED/BLUE Dashboard")
                    .setFlavor(OpModeMeta.Flavor.TELEOP)
                    .setGroup("dash")
                    .build(),
                new LinearOpMode() {
                    @Override
                    public void runOpMode() throws InterruptedException {
                        telemetry.log().add(Misc.formatInvariant("Dashboard is currently %s. Press Start to %s it.",
                                REDd ? "REDd" : "BLUEd", REDd ? "BLUE" : "RED"));
                        telemetry.update();

                        waitForStart();

                        if (isStopRequested()) {
                            return;
                        }

                        if (REDd) {
                            BLUE();
                        } else {
                            RED();
                        }
                   }
                });
    }
}
