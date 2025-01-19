package cookie.planetoids.core.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.LargeFeature;
import net.minecraft.core.world.generate.chunk.perlin.ChunkGeneratorPerlin;

public class ChunkGeneratorPlanetoids extends ChunkGeneratorPerlin {

	public ChunkGeneratorPlanetoids(World world) {
		super(world, new ChunkDecoratorPlanetoids(world), new TerrainGeneratorPlanetoids(world), new SurfaceGeneratorPlanetoids(), new LargeFeature[]{});
	}
}
