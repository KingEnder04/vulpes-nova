package vulpesnova.VNContent.VNJournal;

import necesse.engine.journal.MobsKilledJournalChallenge;
import necesse.engine.network.server.ServerClient;
import necesse.entity.mobs.Mob;
import necesse.level.maps.Level;
public class KillTitancubesInFlatlandsChallenge extends MobsKilledJournalChallenge{

	public KillTitancubesInFlatlandsChallenge() {
		super(25, new String[]{"titancubemobvn"});
	}
	public void onMobKilled(ServerClient serverClient, Mob mob) {
		Level level = mob.getLevel();
		if (!level.isCave) {
			if (VNJournalChallengeUtils.isFlatlandsBiome(level.biome)) {
				super.onMobKilled(serverClient, mob);
			}
		}
	}
}



