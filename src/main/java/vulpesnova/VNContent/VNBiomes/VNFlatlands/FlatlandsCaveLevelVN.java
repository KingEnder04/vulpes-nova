package vulpesnova.VNContent.VNBiomes.VNFlatlands;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import necesse.engine.GameEvents;
import necesse.engine.events.worldGeneration.GenerateCaveLayoutEvent;
import necesse.engine.events.worldGeneration.GenerateCaveMiniBiomesEvent;
import necesse.engine.events.worldGeneration.GenerateCaveOresEvent;
import necesse.engine.events.worldGeneration.GenerateCaveStructuresEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveMiniBiomesEvent;
import necesse.engine.events.worldGeneration.GeneratedCaveOresEvent;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.LocalMessage;
import necesse.engine.registries.MobRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameLinkedList;
import necesse.engine.util.GameMath;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.world.WorldEntity;
import necesse.entity.mobs.Mob;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.LootTablePresets;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.generationModules.CaveGeneration;
import necesse.level.maps.generationModules.CellAutomaton;
import necesse.level.maps.generationModules.GenerationTools;
import necesse.level.maps.generationModules.LinesGeneration;
import necesse.level.maps.generationModules.PresetGeneration;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetUtils;
import necesse.level.maps.presets.RandomCaveChestRoom;
import necesse.level.maps.presets.caveRooms.CaveRuins;
import necesse.level.maps.presets.set.ChestRoomSet;
import necesse.level.maps.presets.set.FurnitureSet;
import necesse.level.maps.presets.set.WallSet;
import vulpesnova.VNContent.GEARSphereArenaVNPreset;
import vulpesnova.VNContent.VNMisc.VNChestRoomSet;
import vulpesnova.VNContent.VNMisc.VNWallSet;
import vulpesnova.VNContent.VulpesNovaLootTablePresets;

public class FlatlandsCaveLevelVN extends FlatlandsSurfaceLevelVN {
    public FlatlandsCaveLevelVN(LevelIdentifier identifier, int width, int height, WorldEntity worldEntity) {
        super(identifier, width, height, worldEntity);
    }

    public FlatlandsCaveLevelVN(int islandX, int islandY, int dimension, WorldEntity worldEntity) {
        super(new LevelIdentifier(islandX, islandY, dimension), 300, 300, worldEntity);
        this.isCave = true;
        this.generateLevel();
    }

    public void generateLevel() {
        CaveGeneration cg = new CaveGeneration(this, "cubestonefloorvn", "cuberockvn");
        GameEvents.triggerEvent(new GenerateCaveLayoutEvent(this, cg), (e) -> {
            cg.generateLevel();
        });
        int crate = ObjectRegistry.getObjectID("crate");
        int vase = ObjectRegistry.getObjectID("vase");
        int trackObject = ObjectRegistry.getObjectID("minecarttrack");
        LinkedList<Mob> minecartsGenerated = new LinkedList<Mob>();
        GameEvents.triggerEvent(new GenerateCaveMiniBiomesEvent(this, cg), (e) -> {
            GenerationTools.generateRandomPoints(this, cg.random, 0.01F, 15, (p) -> {
                LinesGeneration lg = new LinesGeneration(p.x, p.y);
                ArrayList<LinesGeneration> tntArms = new ArrayList<LinesGeneration>();
                int armAngle = cg.random.nextInt(4) * 90;
                int arms = cg.random.getIntBetween(3, 10);

                for(int i = 0; i < arms; ++i) {
                    lg = lg.addArm((float)cg.random.getIntOffset(armAngle, 20), (float)cg.random.getIntBetween(5, 25), 3.0F);
                    tntArms.add(lg);
                    int angleChange = (Integer)cg.random.getOneOfWeighted(Integer.class, new Object[]{15, 0, 5, 90, 5, -90});
                    armAngle += angleChange;
                }

                CellAutomaton ca = lg.doCellularAutomaton(cg.random);
                ca.forEachTile(this, (level, tileX, tileY) -> {
                    if (level.isSolidTile(tileX, tileY)) {
                        level.setObject(tileX, tileY, 0);
                    }

                    if (cg.random.getChance(0.05)) {
                        level.setObject(tileX, tileY, crate);
                    }

                });
                ArrayList<Point> trackTiles = new ArrayList<Point>();
                lg.getRoot().recursiveLines((lg2) -> {
                    GameLinkedList<Point> tiles = new GameLinkedList<Point>();
                    LinesGeneration.pathTiles(lg2.getTileLine(), true, (from, nextx) -> {
                        tiles.add(nextx);
                    });
                    Iterator<?> var5 = tiles.elements().iterator();

                    while(var5.hasNext()) {
                        GameLinkedList<Point>.Element el = (GameLinkedList.Element)var5.next();
                        Point current = (Point)el.object;
                        if ((new Rectangle(2, 2, this.width - 4, this.height - 4)).contains(current)) {
                            trackTiles.add(current);
                            GameLinkedList<Point>.Element nextEl = el.next();
                            if (nextEl != null) {
                                Point next = (Point)nextEl.object;
                                if (next.x < current.x) {
                                    this.setObject(current.x, current.y, trackObject, 3);
                                } else if (next.x > current.x) {
                                    this.setObject(current.x, current.y, trackObject, 1);
                                } else if (next.y < current.y) {
                                    this.setObject(current.x, current.y, trackObject, 0);
                                } else if (next.y > current.y) {
                                    this.setObject(current.x, current.y, trackObject, 2);
                                }
                            } else {
                                GameLinkedList<Point>.Element prevEl = el.prev();
                                if (prevEl != null) {
                                    Point prev = (Point)prevEl.object;
                                    if (prev.x < current.x) {
                                        this.setObject(current.x, current.y, trackObject, 1);
                                    } else if (prev.x > current.x) {
                                        this.setObject(current.x, current.y, trackObject, 3);
                                    } else if (prev.y < current.y) {
                                        this.setObject(current.x, current.y, trackObject, 2);
                                    } else if (prev.y > current.y) {
                                        this.setObject(current.x, current.y, trackObject, 0);
                                    }
                                } else {
                                    this.setObject(current.x, current.y, trackObject, 0);
                                }
                            }
                        }
                    }

                    return true;
                });
                int minecartCount = (Integer)cg.random.getOneOfWeighted(Integer.class, new Object[]{100, 0, 200, 1, 50, 2});

                int tntCount;
                int ix;
                for(tntCount = 0; tntCount < minecartCount && !trackTiles.isEmpty(); ++tntCount) {
                    ix = cg.random.nextInt(trackTiles.size());
                    Point nextx = (Point)trackTiles.remove(ix);
                    Mob minecart = MobRegistry.getMob("minecart", this);
                    this.entityManager.addMob(minecart, (float)(nextx.x * 32 + 16), (float)(nextx.y * 32 + 16));
                    minecartsGenerated.add(minecart);
                }

                tntCount = (Integer)cg.random.getOneOfWeighted(Integer.class, new Object[]{100, 0, 200, 1, 50, 2});

                for(ix = 0; ix < tntCount && !tntArms.isEmpty(); ++ix) {
                    int index = cg.random.nextInt(tntArms.size());
                    LinesGeneration next = (LinesGeneration)tntArms.remove(index);
                    int wireLength = cg.random.getIntBetween(10, 14) * (Integer)cg.random.getOneOf(new Integer[]{1, -1});
                    float lineLength = (float)(new Point(next.x1, next.y1)).distance((double)next.x2, (double)next.y2);
                    Point2D.Float dir = GameMath.normalize((float)(next.x1 - next.x2), (float)(next.y1 - next.y2));
                    float linePointLength = cg.random.getFloatBetween(0.0F, lineLength);
                    Point2D.Float linePoint = new Point2D.Float((float)next.x2 + dir.x * linePointLength, (float)next.y2 + dir.y * linePointLength);
                    Point2D.Float leverPoint = GameMath.getPerpendicularPoint(linePoint, 2.0F * Math.signum((float)wireLength), dir);
                    Point2D.Float tntPoint = GameMath.getPerpendicularPoint(linePoint, (float)wireLength, dir);
                    Line2D.Float wireLine = new Line2D.Float(leverPoint, tntPoint);
                    LinkedList<Point> tiles = new LinkedList<Point>();
                    LinesGeneration.pathTiles(wireLine, true, (fromTile, nextTile) -> {
                        tiles.add(nextTile);
                    });
                    Iterator<Point> var26 = tiles.iterator();

                    Point tile;
                    while(var26.hasNext()) {
                        tile = (Point)var26.next();
                        this.wireManager.setWire(tile.x, tile.y, 0, true);
                        if (this.getObject(tile.x, tile.y).isSolid) {
                            this.setObject(tile.x, tile.y, 0);
                        }
                    }

                    Point first = (Point)tiles.getFirst();
                    tile = (Point)tiles.getLast();
                    this.setObject(first.x, first.y, ObjectRegistry.getObjectID("rocklever"));
                    this.setObject(tile.x, tile.y, ObjectRegistry.getObjectID("tnt"));
                }

            });
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.02F, 2, 2.0F, 10.0F, 2.0F, 4.0F, TileRegistry.getTileID("lavatile"), 1.0F, true);
            GenerationTools.generateRandomSmoothTileVeins(this, cg.random, 0.02F, 2, 2.0F, 10.0F, 2.0F, 4.0F, TileRegistry.getTileID("watertile"), 1.0F, true);
            this.liquidManager.calculateShores();
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("cubegroundrockvn"), 0.005F);
            cg.generateRandomSingleRocks(ObjectRegistry.getObjectID("cubegroundrocksmallvn"), 0.01F);
        });
        GameEvents.triggerEvent(new GeneratedCaveMiniBiomesEvent(this, cg));
        GameEvents.triggerEvent(new GenerateCaveOresEvent(this, cg), (e) -> {
            cg.generateOreVeins(0.3F, 3, 6, ObjectRegistry.getObjectID("cubaltorecuberockvn"));
            cg.generateOreVeins(0.3F, 3, 6, ObjectRegistry.getObjectID("copperorecuberock"));
            cg.generateOreVeins(0.25F, 3, 6, ObjectRegistry.getObjectID("ironorecuberock"));
            cg.generateOreVeins(0.15F, 3, 6, ObjectRegistry.getObjectID("goldorecuberock"));
        });
        GameEvents.triggerEvent(new GeneratedCaveOresEvent(this, cg));
        GameObject crystalClusterSmall = ObjectRegistry.getObject("amethystclustersmall");
        GenerationTools.generateRandomSmoothVeinsL(this, cg.random, 0.005F, 4, 4.0F, 7.0F, 4.0F, 6.0F, (lg) -> {
            CellAutomaton ca = lg.doCellularAutomaton(cg.random);
            ca.streamAliveOrdered().forEachOrdered((tile) -> {
                cg.addIllegalCrateTile(tile.x, tile.y);
                this.setTile(tile.x, tile.y, TileRegistry.getTileID("amethystgravel"));
                this.setObject(tile.x, tile.y, 0);
            });
            ca.streamAliveOrdered().forEachOrdered((tile) -> {
                if (this.getObjectID(tile.x, tile.y) == 0 && this.getObjectID(tile.x - 1, tile.y) == 0 && this.getObjectID(tile.x + 1, tile.y) == 0 && this.getObjectID(tile.x, tile.y - 1) == 0 && this.getObjectID(tile.x, tile.y + 1) == 0 && cg.random.getChance(0.08F)) {
                    int rotation = cg.random.nextInt(4);
                    Point[] clearPoints = new Point[]{new Point(-1, -1), new Point(1, -1)};
                    if (this.getRelativeAnd(tile.x, tile.y, PresetUtils.getRotatedPoints(0, 0, rotation, clearPoints), (tileX, tileY) -> {
                        return ca.isAlive(tileX, tileY) && this.getObjectID(tileX, tileY) == 0;
                    })) {
                        ObjectRegistry.getObject(ObjectRegistry.getObjectID("amethystcluster")).placeObject(this, tile.x, tile.y, rotation, false);
                    }
                }

                if (cg.random.getChance(0.3F) && crystalClusterSmall.canPlace(this, tile.x, tile.y, 0, false) == null) {
                    crystalClusterSmall.placeObject(this, tile.x, tile.y, 0, false);
                }

            });
        });
        PresetGeneration presets = new PresetGeneration(this);
        GameEvents.triggerEvent(new GenerateCaveStructuresEvent(this, cg, presets), (e) -> {
            for(int i = 0; i < 4; ++i) {
                Preset arena = new GEARSphereArenaVNPreset(36, cg.random);
                presets.findRandomValidPositionAndApply(cg.random, 5, arena, 10, true, true);
            }

            AtomicInteger chestRoomRotation = new AtomicInteger();
            int chestRoomAmount = cg.random.getIntBetween(13, 18);

            for(int ix = 0; ix < chestRoomAmount; ++ix) {
                Preset chestRoom = new RandomCaveChestRoom(cg.random, VulpesNovaLootTablePresets.flatlandsCaveChest, chestRoomRotation, new ChestRoomSet[]{VNChestRoomSet.factory, VNChestRoomSet.factory});
                chestRoom.replaceTile(TileRegistry.stoneFloorID, (Integer)cg.random.getOneOf(new Integer[]{TileRegistry.stoneFloorID, TileRegistry.stoneBrickFloorID}));
                chestRoom.replaceTile(TileRegistry.sandstoneFloorID, (Integer)cg.random.getOneOf(new Integer[]{TileRegistry.sandstoneFloorID, TileRegistry.sandstoneBrickFloorID}));
                presets.findRandomValidPositionAndApply(cg.random, 5, chestRoom, 10, true, true);
            }

            AtomicInteger caveRuinsRotation = new AtomicInteger();
            int caveRuinsCount = cg.random.getIntBetween(25, 35);

            for(int ixx = 0; ixx < caveRuinsCount; ++ixx) {
                WallSet wallSet = (WallSet)cg.random.getOneOf(new WallSet[]{VNWallSet.gearfactorywall});
                FurnitureSet furnitureSet = (FurnitureSet)cg.random.getOneOf(new FurnitureSet[]{FurnitureSet.palm, FurnitureSet.spruce});
                String floorStringID = (String)cg.random.getOneOf(new String[]{"woodfloor", "woodfloor", "sandstonefloor", "sandstonebrickfloor"});
                Preset room = ((CaveRuins.CaveRuinGetter)cg.random.getOneOf(CaveRuins.caveRuinGetters)).get(cg.random, wallSet, furnitureSet, floorStringID, LootTablePresets.desertCaveRuinsChest, caveRuinsRotation);
                presets.findRandomValidPositionAndApply(cg.random, 5, room, 10, true, true);
            }

            int[] crates = new int[]{crate, vase};
            cg.generateRandomCrates(0.04F, crates);
        });
        GenerationTools.checkValid(this);
        Iterator<Mob> var8 = minecartsGenerated.iterator();

        while(var8.hasNext()) {
            Mob mob = (Mob)var8.next();
            if (this.getObjectID(mob.getTileX(), mob.getTileY()) != trackObject) {
                mob.remove();
            }
        }

    }
    
	@Override
    public LootTable getCrateLootTable() {
        return LootTablePresets.desertCrate;
    }
    
	@Override
    public GameMessage getLocationMessage() {
        return new LocalMessage("biome", "cave", "biome", this.biome.getLocalization());
    }
}
