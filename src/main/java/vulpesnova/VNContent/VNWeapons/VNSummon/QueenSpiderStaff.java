package vulpesnova.VNContent.VNWeapons.VNSummon;

import necesse.engine.localization.Localization;
import necesse.engine.network.PacketReader;
import necesse.engine.network.server.FollowPosition;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.summon.summonFollowingMob.attackingFollowingMob.AttackingFollowingMob;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.summonToolItem.SummonToolItem;
import necesse.level.maps.Level;

public class QueenSpiderStaff extends SummonToolItem {
    public QueenSpiderStaff() {
        super("babyspider", FollowPosition.PYRAMID, 0.33F, 400);
        this.rarity = Rarity.EPIC;
        this.attackDamage.setBaseValue(10.0F).setUpgradedValue(1.0F, 30.0F);
    }

    public GameTooltips getSpaceTakenTooltip(InventoryItem item, PlayerMob perspective) {
        return null;
    }

    public void runSummon(Level level, int x, int y, ServerClient client, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        AttackingFollowingMob mob1 = (AttackingFollowingMob)MobRegistry.getMob("babyspider", level);
        this.summonMob(client, mob1, x, y, attackHeight, item);
        AttackingFollowingMob mob2 = (AttackingFollowingMob)MobRegistry.getMob("babyspider", level);
        this.summonMob(client, mob2, x, y, attackHeight, item);
        AttackingFollowingMob mob3 = (AttackingFollowingMob)MobRegistry.getMob("babyspider", level);
        this.summonMob(client, mob3, x, y, attackHeight, item);
    }

    public ListGameTooltips getPreEnchantmentTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getPreEnchantmentTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "queenspiderstaffvntip"));
        return tooltips;
    }
}
