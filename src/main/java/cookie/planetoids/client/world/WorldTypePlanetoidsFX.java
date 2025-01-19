package cookie.planetoids.client.world;

import cookie.planetoids.Planetoids;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.worldtype.WorldTypeFX;

@Environment(EnvType.CLIENT)
public class WorldTypePlanetoidsFX extends WorldTypeFX {
	public WorldTypePlanetoidsFX() {
		super(Planetoids.worldType);
	}

	@Override
	public float getCloudHeight() {
		return 256 - 20;
	}
}
