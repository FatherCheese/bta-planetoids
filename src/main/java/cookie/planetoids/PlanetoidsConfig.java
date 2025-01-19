package cookie.planetoids;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static cookie.planetoids.Planetoids.MOD_ID;

public class PlanetoidsConfig {
	private static final Toml TOML = new Toml("BTA! Planetoids TOML Config");
	public static final TomlConfigHandler CFG;

	static {
		TOML.addCategory("Planetoids")
			.addEntry("maxRadius", "default: 16", 16);

		CFG = new TomlConfigHandler(MOD_ID, TOML);
	}
}
