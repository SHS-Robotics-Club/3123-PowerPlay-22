package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
	private static final int startX = 36, startY = 65, startH = 90;
	public static void main(String[] args) {
		MeepMeep meepMeep = new MeepMeep(800);



		RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
				// Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
				.setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
				.followTrajectorySequence(drive ->
						                          drive.trajectorySequenceBuilder(new Pose2d(-71.13, 36.76, Math.toRadians(2.64)))
				                                                              .splineTo(new Vector2d(-21.30, 36.61), Math.toRadians(0.00))
				                                                              .build()
				                         );

		meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
		        .setDarkMode(true)
		        .setBackgroundAlpha(0.95f)
		        .addEntity(myBot)
		        .start();
	}
}