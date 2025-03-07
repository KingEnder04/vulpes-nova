package vulpesnova.VNContent.VNMisc;

import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptionsList;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.item.ItemCategory;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.recipe.Tech;
import necesse.level.gameObject.CraftingStationObject;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;
import necesse.level.maps.multiTile.MultiTile;
import necesse.level.maps.multiTile.SideMultiTile;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

class TableOfAwakeningVN2 extends CraftingStationObject {
    private GameTexture texture;
    protected int counterID;

    protected TableOfAwakeningVN2() {
        super(new Rectangle(32, 32));
        this.mapColor = new Color(70, 175, 72);
        this.drawDamage = false;
        this.toolType = ToolType.ALL;
        this.isLightTransparent = true;
    }

    @Override
    public HashSet<ItemCategory> getForcedSoloCraftingCategories() {
        HashSet<ItemCategory> depths = super.getForcedSoloCraftingCategories();
        depths.add(ItemCategory.craftingManager.getCategory(new String[]{"equipment", "trinkets"}));
        return depths;
    }

    @Override
    public MultiTile getMultiTile(int rotation) {
        return new SideMultiTile(0, 0, 1, 2, rotation, false, new int[]{this.getID(), this.counterID});
    }

    @Override
    public void loadTextures() {
        super.loadTextures();
        this.texture = GameTexture.fromFile("objects/tableofawakeningvn");
    }

    @Override
    public Rectangle getCollision(Level level, int x, int y, int rotation) {
        if (rotation == 0) {
            return new Rectangle(x * 32 + 5, y * 32 + 16, 22, 16);
        } else if (rotation == 1) {
            return new Rectangle(x * 32, y * 32 + 6, 20, 20);
        } else {
            return rotation == 2 ? new Rectangle(x * 32 + 5, y * 32, 22, 26) : new Rectangle(x * 32 + 12, y * 32 + 6, 20, 20);
        }
    }

    @Override
    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        int rotation = level.getObjectRotation(tileX, tileY);
        final DrawOptionsList options = new DrawOptionsList();
        if (rotation == 0) {
            options.add(this.texture.initDraw().sprite(1, 0, 32).light(light).pos(drawX, drawY + 2));
        } else if (rotation == 1) {
            options.add(this.texture.initDraw().sprite(0, 2, 32).mirrorX().light(light).pos(drawX, drawY - 24));
            options.add(this.texture.initDraw().sprite(0, 3, 32).mirrorX().light(light).pos(drawX, drawY + 8));
        } else if (rotation == 2) {
            options.add(this.texture.initDraw().sprite(0, 1, 32).light(light).pos(drawX, drawY + 2));
        } else {
            options.add(this.texture.initDraw().sprite(0, 2, 32).light(light).pos(drawX, drawY - 24));
            options.add(this.texture.initDraw().sprite(0, 3, 32).light(light).pos(drawX, drawY + 8));
        }

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
    public LootTable getLootTable(Level level, int layerID, int tileX, int tileY) {
        return new LootTable();
    }

    @Override
    public Tech[] getCraftingTechs() {
        return new Tech[]{RecipeTechRegistry.getTech("tableofawakeningvn")};
    }
}
