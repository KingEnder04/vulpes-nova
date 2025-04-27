package vulpesnova.VNContent.VNMobs;

import java.awt.Color;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.critters.caveling.CavelingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;

public class DeepGemStoneCavelingVN extends CavelingMob {
    public DeepGemStoneCavelingVN() {
        super(500, 50);
    }

    @Override
    public void init() {
        super.init();
        this.texture = Textures.deepStoneCaveling;
        this.popParticleColor = new Color(139, 58, 222);
        this.singleRockSmallStringID = "cubegroundrocksmallvn";
        if (this.item == null) {
            this.item = new InventoryItem("dustygemsackvn", GameRandom.globalRandom.getIntBetween(1, 1));
        }

    }

    @Override
    public LootTable getLootTable() {
        return super.getLootTable();
    }
}
