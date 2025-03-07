package vulpesnova.VNContent.VNWeapons.VNRange.VNAmmos;

import necesse.engine.localization.Localization;
import necesse.engine.registries.ProjectileRegistry;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.itemAttacker.ItemAttackerMob;
import necesse.entity.projectile.Projectile;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.item.arrowItem.ArrowItem;

public class NovaArrowItem extends ArrowItem {

    public NovaArrowItem() {
        this.damage = 13;
        this.rarity = Rarity.UNCOMMON;
    }

    @Override
    public Projectile getProjectile(float x, float y, float targetX, float targetY, float velocity, int range,
			GameDamage damage, int knockback, ItemAttackerMob owner) {
        return ProjectileRegistry.getProjectile("novaarrowproj", owner.getLevel(), x, y, targetX, targetY, velocity, range, damage, knockback, owner);
    }
    
    @Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective, GameBlackboard blackboard) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective, blackboard);
        tooltips.add(Localization.translate("itemtooltip", "novaarrowvntip"));
        return tooltips;
    }
}

