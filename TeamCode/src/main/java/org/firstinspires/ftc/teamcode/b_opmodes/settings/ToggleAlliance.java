package org.firstinspires.ftc.teamcode.b_opmodes.settings;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GlobalConfig;

@TeleOp(name = "Toggle Alliance", group = "Conf")
public class ToggleAlliance extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        GlobalConfig.alliance = GlobalConfig.alliance == GlobalConfig.Alliance.RED ? GlobalConfig.Alliance.BLUE
                : GlobalConfig.Alliance.RED;
        telemetry.addLine("Alliance: " + GlobalConfig.alliance);
        telemetry.update();
    }
}

/*
public class ToggleAlliance {
    public static Alliance alliance = Alliance.RED; // Current Alliance

    // Alliance switch
    public enum Alliance {
        RED,
        BLUE;

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private boolean toggle;
    private void internalRegisterOpMode(OpModeManager manager) {
        manager.register(
                new OpModeMeta.Builder()
                        .setName("Toggle Alliance")
                        .setFlavor(OpModeMeta.Flavor.TELEOP)
                        .setGroup("dash")
                        .build(),
                new LinearOpMode() {
                    @Override
                    public void runOpMode() throws InterruptedException {
                        telemetry.log().add(Misc.formatInvariant("Alliance is currently" + alliance));
                        telemetry.update();

                        waitForStart();

                        if (isStopRequested()) {
                            return;
                        }

                        if (toggle) {
                            alliance = Alliance.RED;
                        } else {
                            alliance = Alliance.BLUE;
                        }
                    }
                });
    }
}*/
