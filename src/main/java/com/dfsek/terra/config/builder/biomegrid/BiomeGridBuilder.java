package com.dfsek.terra.config.builder.biomegrid;

import com.dfsek.terra.config.base.WorldConfig;
import org.bukkit.World;
import org.polydev.gaea.biome.BiomeGrid;

public interface BiomeGridBuilder {
    BiomeGrid build(World world, WorldConfig config);
}
