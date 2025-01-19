package cookie.planetoids;

import cookie.planetoids.client.world.WorldTypePlanetoidsFX;
import cookie.planetoids.core.world.WorldTypePlanetoids;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.worldtype.WorldTypeFXDispatcher;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class Planetoids implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "planetoids";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static WorldType worldType;

    @Override
    public void onInitialize() {
		new PlanetoidsConfig();
        LOGGER.info("BTA! Planetoids has been initialized.");
    }

	@Override
	public void beforeGameStart() {
		worldType = WorldTypes.register("planetoids.world.name", new WorldTypePlanetoids());
		WorldTypeGroups.GROUPS.add(new WorldTypeGroups.Group(worldType));
	}

	@Override
	public void afterGameStart() {
		WorldTypeFXDispatcher.getInstance().addDispatch(new WorldTypePlanetoidsFX());
	}
}
