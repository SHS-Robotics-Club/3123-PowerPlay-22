package org.firstinspires.ftc.teamcode.old;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Config
public class GlobalConfig {

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
}