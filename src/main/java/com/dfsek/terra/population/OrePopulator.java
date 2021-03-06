package com.dfsek.terra.population;

import com.dfsek.terra.TerraProfiler;
import com.dfsek.terra.TerraWorld;
import com.dfsek.terra.biome.UserDefinedBiome;
import com.dfsek.terra.config.templates.BiomeTemplate;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.polydev.gaea.biome.Biome;
import org.polydev.gaea.generation.GenerationPhase;
import org.polydev.gaea.math.MathUtil;
import org.polydev.gaea.population.GaeaBlockPopulator;
import org.polydev.gaea.profiler.ProfileFuture;
import org.polydev.gaea.util.FastRandom;

import java.util.Random;

public class OrePopulator extends GaeaBlockPopulator {
    @SuppressWarnings("try")
    @Override
    public void populate(@NotNull World world, @NotNull Random r, @NotNull Chunk chunk) {
        try(ProfileFuture ignored = TerraProfiler.fromWorld(world).measure("OreTime")) {
            TerraWorld tw = TerraWorld.getWorld(world);
            if(!tw.isSafe()) return;
            for(int cx = -1; cx <= 1; cx++) {
                for(int cz = -1; cz <= 1; cz++) {
                    Random random = new FastRandom(MathUtil.getCarverChunkSeed(chunk.getX() + cx, chunk.getZ() + cz, world.getSeed()));
                    int originX = ((chunk.getX() + cx) << 4);
                    int originZ = ((chunk.getZ() + cz) << 4);
                    Biome b = TerraWorld.getWorld(world).getGrid().getBiome(originX + 8, originZ + 8, GenerationPhase.POPULATE);
                    BiomeTemplate config = ((UserDefinedBiome) b).getConfig();
                    int finalCx = cx;
                    int finalCz = cz;
                    config.getOreHolder().forEach((ore, oreConfig) -> {
                        int amount = oreConfig.getAmount().get(random);
                        for(int i = 0; i < amount; i++) {
                            Vector location = new Vector(random.nextInt(16) + 16 * finalCx, oreConfig.getHeight().get(random), random.nextInt(16) + 16 * finalCz);
                            ore.generate(location, chunk, random);
                        }
                    });
                }
            }
        }
    }
}
