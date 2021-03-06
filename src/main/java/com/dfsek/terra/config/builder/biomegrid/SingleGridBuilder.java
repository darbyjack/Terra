package com.dfsek.terra.config.builder.biomegrid;

import com.dfsek.terra.biome.grid.SingleBiomeGrid;
import com.dfsek.terra.config.base.WorldConfig;
import org.bukkit.World;
import org.polydev.gaea.biome.Biome;

public class SingleGridBuilder implements BiomeGridBuilder {
    private final Biome biome;

    public SingleGridBuilder(Biome biome) {
        this.biome = biome;
    }

    @Override
    public SingleBiomeGrid build(World world, WorldConfig config) {
        return new SingleBiomeGrid(world, biome);
    }
}
