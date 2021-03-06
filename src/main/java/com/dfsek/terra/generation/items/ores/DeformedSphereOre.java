package com.dfsek.terra.generation.items.ores;

import com.dfsek.terra.util.MaterialSet;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;
import org.polydev.gaea.math.FastNoiseLite;
import org.polydev.gaea.math.Range;

import java.util.Random;

public class DeformedSphereOre extends Ore {
    private final double deform;
    private final double deformFrequency;
    private final Range size;

    public DeformedSphereOre(BlockData material, MaterialSet replaceable, boolean applyGravity, double deform, double deformFrequency, Range size) {
        super(material, replaceable, applyGravity);
        this.deform = deform;
        this.deformFrequency = deformFrequency;
        this.size = size;
    }


    @Override
    public void generate(Vector origin, Chunk c, Random r) {
        FastNoiseLite ore = new FastNoiseLite(r.nextInt());
        ore.setNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        ore.setFrequency(deformFrequency);
        int rad = size.get(r);
        for(int x = -rad; x <= rad; x++) {
            for(int y = -rad; y <= rad; y++) {
                for(int z = -rad; z <= rad; z++) {
                    Vector oreLoc = origin.clone().add(new Vector(x, y, z));
                    if(oreLoc.getBlockX() > 15 || oreLoc.getBlockZ() > 15 || oreLoc.getBlockY() > 255 || oreLoc.getBlockX() < 0 || oreLoc.getBlockZ() < 0 || oreLoc.getBlockY() < 0)
                        continue;
                    if(oreLoc.distance(origin) < (rad + 0.5) * ((ore.getNoise(x, y, z) + 1) * deform)) {
                        Block b = c.getBlock(oreLoc.getBlockX(), oreLoc.getBlockY(), oreLoc.getBlockZ());
                        if(getReplaceable().contains(b.getType()) && b.getLocation().getY() >= 0)
                            b.setBlockData(getMaterial(), isApplyGravity());
                    }
                }
            }
        }
    }
}
