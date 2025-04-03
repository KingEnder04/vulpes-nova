package vulpesnova.VNContent.VNJournal;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;
public class KillPlanewalkerCrowdChallenge extends MobsKilledJournalChallenge{

	public int timeGiven;
	public long first_kill_time = 0L;
	public KillPlanewalkerCrowdChallenge(int targetCount, int time_given) {
		super(targetCount, new String[]{"planewalkermobvn"});
		this.timeGiven = time_given;
	}
	
	public void onMobKilled(ServerClient serverClient, Mob mob) {
		Level level = mob.getLevel();
		if (!level.isCave) {
			
			if(first_kill_time == 0L){
				first_kill_time = mob.getTime();
			}
			if((mob.getTime() - first_kill_time) <= timeGiven) {
				if (VNJournalChallengeUtils.isFlatlandsBiome(level.biome)) {
					mob.spawnDamageText(this.getProgress(serverClient.playerStats()), 16, false);
					super.onMobKilled(serverClient, mob);
				}
			}
			else {				
				resetKillCount(serverClient);
			}
		}
	}
	
	protected void resetKillCount(ServerClient serverClient) {
		first_kill_time = 0L;
		serverClient.newStats.challenges_data.getData().setInt(this.getStringID() + "Kills", 0);
		serverClient.forceCombineNewStats();
	}
}



