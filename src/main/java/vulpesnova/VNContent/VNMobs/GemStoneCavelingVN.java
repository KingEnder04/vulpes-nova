package vulpesnova.VNContent.VNMobs;

import java.awt.Color;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.critters.caveling.CavelingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;

public class GemStoneCavelingVN extends CavelingMob {
    public GemStoneCavelingVN() {
        super(200, 40);
    }

    public void init() {
        super.init();
        this.texture = Textures.stoneCaveling;
        this.popParticleColor = new Color(139, 58, 222);
        this.singleRockSmallStringID = "surfacerocksmall";
        if (this.item == null) {
            this.item = new InventoryItem("dustygemsackvn", GameRandom.globalRandom.getIntBetween(1, 1));
        }

    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }
}
