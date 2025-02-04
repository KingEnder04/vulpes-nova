package vulpesnova.VNContent;

import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.GameMath;
import necesse.engine.util.GameRandom;
import necesse.level.maps.presets.Preset;

import java.awt.*;
import java.awt.geom.Point2D;

public class GEARSphereArenaVNPreset extends Preset {
    public GEARSphereArenaVNPreset(int size, GameRandom random) {
        super(size, size);
        int mid = size / 2;
        int maxDistance = size / 2 * 32;
        int woodfloor = TileRegistry.getTileID("cubewoodfloorvn");
        int sandstone = TileRegistry.getTileID("cubestonefloorvn");
        int sandbrick = TileRegistry.getTileID("cubestonetiledfloorvn");
        int[] breakObjects = new int[]{ObjectRegistry.getObjectID("crate"), ObjectRegistry.getObjectID("vase")};

        int x;
        float anglePerColumn;
        for(x = 0; x < this.width; ++x) {
            for(int y = 0; y < this.height; ++y) {
                anglePerColumn = (float)(new Point(mid * 32 + 16, mid * 32 + 16)).distance((double)(x * 32 + 16), (double)(y * 32 + 16));
                float distancePerc = anglePerColumn / (float)maxDistance;
                if (distancePerc < 0.5F) {
                    this.setTile(x, y, woodfloor);
                    if (random.getChance(0.8F)) {
                        this.setTile(x, y, sandbrick);
                    }

                    this.setObject(x, y, 0);
                } else if (distancePerc <= 1.0F) {
                    float chance = Math.abs((distancePerc - 0.5F) * 2.0F - 1.0F) * 2.0F;
                    if (random.getChance(chance)) {
                        if (random.getChance(0.75F)) {
                            this.setTile(x, y, random.getChance(0.75F) ? sandbrick : woodfloor);
                        } else {
                            this.setTile(x, y, sandstone);
                        }

                        this.setObject(x, y, 0);
                    }
                }

                if (distancePerc <= 1.0F && this.getObject(x, y) != -1 && random.getChance(0.1F)) {
                    this.setObject(x, y, breakObjects[random.nextInt(breakObjects.length)]);
                }
            }
        }

        x = random.getIntBetween(8, 10);
        float columnAngle = (float)random.nextInt(360);
        anglePerColumn = 360.0F / (float)x;
        int columnID = ObjectRegistry.getObjectID("cubestonecolumnvn");

        for(int i = 0; i < x; ++i) {
            columnAngle += random.getFloatOffset(anglePerColumn, anglePerColumn / 10.0F);
            Point2D.Float dir = GameMath.getAngleDir(columnAngle);
            float distance = (float)size / 3.0F;
            int tileX = (int)((float)mid + dir.x * distance);
            int tileY = (int)((float)mid + dir.y * distance);
            this.setObject(tileX, tileY, columnID);
        }

        this.setObject(mid, mid, ObjectRegistry.getObjectID("gearcontactbeaconvn"));
    }
}
