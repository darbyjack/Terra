package com.dfsek.terra.config.loaders;

import com.dfsek.tectonic.exception.LoadException;
import com.dfsek.tectonic.loading.ConfigLoader;
import com.dfsek.tectonic.loading.TypeLoader;
import com.dfsek.terra.config.files.Loader;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.polydev.gaea.structures.loot.LootTable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class LootTableLoader implements TypeLoader<LootTable> {
    private final Loader loader;

    public LootTableLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public LootTable load(Type type, Object o, ConfigLoader configLoader) throws LoadException {
        try(InputStream stream = loader.get("structures/loot/" + o + ".json")) {
            return new LootTable(IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch(IOException | ParseException | NullPointerException e) {
            throw new LoadException("Unable to load loot", e);
        }
    }
}
