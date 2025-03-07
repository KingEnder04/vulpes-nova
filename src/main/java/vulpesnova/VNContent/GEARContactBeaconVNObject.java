package vulpesnova.VNContent;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.network.packet.PacketChatMessage;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Attacker;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.pickup.ItemPickupEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.ObjectDamagedTextureArray;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GEARContactBeaconVNObject extends GameObject {
    public ObjectDamagedTextureArray texture;

    public GEARContactBeaconVNObject() {
        super(new Rectangle(32, 32));
        this.mapColor = new Color(99, 218, 102);
        this.displayMapTooltip = true;
        this.objectHealth = 1;
        this.drawDamage = false;
        this.toolType = ToolType.ALL;
    }
    
	@Override
    public void loadTextures() {
        super.loadTextures();
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/gearcontactbeaconvn");
    }
    
	@Override
	public LootTable getLootTable(Level level, int layerID, int tileX, int tileY) {
        return new LootTable();
    }
    
	@Override
    public void onDestroyed(Level level, int layerID, int x, int y, Attacker attacker, ServerClient client, ArrayList<ItemPickupEntity> itemsDropped) {
        super.onDestroyed(level, layerID, x, y, attacker, client, itemsDropped);
        if (level.isServer()) {
            System.out.println("The GEAR Sphere has been spawned at " + level.getIdentifier() + ".");
            float angle = (float) GameRandom.globalRandom.nextInt(360);
            float nx = (float)Math.cos(Math.toRadians((double)angle));
            float ny = (float)Math.sin(Math.toRadians((double)angle));
            float distance = 960.0F;
            Mob mob = MobRegistry.getMob("gearspherebossmobvn", level);
            level.entityManager.addMob(mob, (float)(x * 32 + 16 + (int)(nx * distance)), (float)(y * 32 + 16 + (int)(ny * distance)));
            level.getServer().network.sendToClientsWithTile(new PacketChatMessage(new LocalMessage("misc", "bossawoke", "name", mob.getLocalization())), level, x, y);
        }

    }
    
	@Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX) - 16;
        int drawY = camera.getTileDrawY(tileY) - 36;
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        final TextureDrawOptions options = texture.initDraw().size(72, 72).light(light).pos(drawX, drawY);
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }
    
	@Override
    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX) - 16;
        int drawY = camera.getTileDrawY(tileY) - 36;
        GameTexture texture = this.texture.getDamagedTexture(0.0F);
        texture.initDraw().size(72, 72).light(light).alpha(alpha).draw(drawX, drawY);
    }
}
