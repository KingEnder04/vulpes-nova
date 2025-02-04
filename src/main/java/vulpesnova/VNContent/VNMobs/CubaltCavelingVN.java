package vulpesnova.VNContent.VNMobs;

import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.friendly.critters.caveling.CavelingMob;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootTable;

import java.awt.*;

public class CubaltCavelingVN extends CavelingMob {
    public CubaltCavelingVN() {
        super(200, 40);
    }

    public void init() {
        super.init();
        this.texture = MobRegistry.Textures.stoneCaveling;
        this.popParticleColor = new Color(139, 58, 222);
        this.singleRockSmallStringID = "cubesurfacerocksmallvn";
        if (this.item == null) {
            this.item = new InventoryItem("cubaltoreitemvn", GameRandom.globalRandom.getIntBetween(3, 15));
        }

    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }
}
