package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;

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