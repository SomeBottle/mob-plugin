/**
 * WolfSpawner.java
 * 
 * Created on 10:39:49
 */
package de.kniffo80.mobplugin.entities.spawners;

import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.generator.biome.Biome;
import de.kniffo80.mobplugin.AutoSpawnTask;
import de.kniffo80.mobplugin.FileLogger;
import de.kniffo80.mobplugin.entities.autospawn.AbstractEntitySpawner;
import de.kniffo80.mobplugin.entities.autospawn.SpawnResult;
import de.kniffo80.mobplugin.entities.monster.walking.Wolf;

/**
 * Each entity get it's own spawner class.
 * 
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class WolfSpawner extends AbstractEntitySpawner {

    /**
     * @param spawnTask
     */
    public WolfSpawner(AutoSpawnTask spawnTask) {
        super(spawnTask);
    }

    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);
        int biomeId = level.getBiomeId((int) pos.x, (int) pos.z);

        if (biomeId != Biome.FOREST && biomeId != Biome.BIRCH_FOREST && biomeId == Biome.TAIGA) {
            result = SpawnResult.WRONG_BLOCK;
        } else if (pos.y > 127 || pos.y < 1 || level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.AIR) { // cannot spawn on AIR block
            result = SpawnResult.POSITION_MISMATCH;
        } else {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 1.9, 0));
        }

        FileLogger.info(String.format("Spawn for %s at %s,%s,%s with lightlevel %d and biomeId %d, result: %s", iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, biomeId, result));

        return result;
    }
    
    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityNetworkId()
     */
    @Override
    public int getEntityNetworkId() {
        return Wolf.NETWORK_ID;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityName()
     */
    @Override
    public String getEntityName() {
        return "Wolf";
    }

}
