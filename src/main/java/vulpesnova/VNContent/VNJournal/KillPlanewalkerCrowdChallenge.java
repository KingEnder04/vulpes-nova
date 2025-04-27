package vulpesnova.VNContent.VNJournal;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;

public class KillPlanewalkerCrowdChallenge extends MobsKilledJournalChallenge {

	public int timeGiven;

	public KillPlanewalkerCrowdChallenge(int targetCount, int time_given) {
		super(targetCount, new String[] { "planewalkermobvn" });
		this.timeGiven = time_given;		
	}

	@Override
	public void onMobKilled(ServerClient serverClient, Mob mob) {
		if(this.isCompleted(serverClient)) return;
		
		Level level = mob.getLevel();
		if (!level.isCave && VNJournalChallengeUtils.isFlatlandsBiome(level.biome)) {
	
			long currentTime = System.currentTimeMillis();
			String firstKillKey = this.getStringID() + "FirstKillTime";
			String killsKey = this.getStringID() + "Kills";

			long firstKillTime = serverClient.newStats.challenges_data.getData().getLong(firstKillKey, 0L);

			// If this is the first valid kill
			if (firstKillTime == 0L) {				
				firstKillTime = currentTime;
				resetKillCount(serverClient, firstKillTime);
			}
			//serverClient.playerMob.spawnDamageText(this.getProgress(serverClient.playerStats()), 24, true);
			// Check if the time window has expired
			if ((currentTime - firstKillTime) <= timeGiven) {			
				super.onMobKilled(serverClient, mob); // Increments kill count
			} else {
				// Time window expired — reset
				resetKillCount(serverClient, 0L);
			}
		}
	}

	protected void resetKillCount(ServerClient serverClient, long newKillTime) {
		String firstKillKey = this.getStringID() + "FirstKillTime";
		String killsKey = this.getStringID() + "Kills";

		// Offset newStats so the difference becomes 0 again
		serverClient.newStats.challenges_data.getData().setInt(killsKey, 0);
		serverClient.newStats.challenges_data.getData().setLong(firstKillKey, newKillTime);	
		serverClient.forceCombineNewStats();
	}
}
