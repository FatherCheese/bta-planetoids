package cookie.planetoids.core.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.chunk.perlin.DensityGenerator;
import net.minecraft.core.world.generate.chunk.perlin.TerrainGeneratorLerp;
import net.minecraft.core.world.generate.chunk.perlin.nether.DensityGeneratorNether;

public class TerrainGeneratorPlanetoids extends TerrainGeneratorLerp {
	public TerrainGeneratorPlanetoids(World world) {
		super(world);
	}

	@Override
	protected int getBlockAt(int i, int j, int k, double d) {
		return 0;
	}

	@Override
	public DensityGenerator getDensityGenerator() {
		return new DensityGeneratorNether(world);
	}
}
