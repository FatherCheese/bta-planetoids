package cookie.planetoids.core.world;

import cookie.planetoids.Planetoids;
import net.minecraft.core.block.BlockLogicLeavesBase;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderOverworld;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.generate.chunk.ChunkGenerator;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.Weathers;

import java.util.Random;

public class WorldTypePlanetoids extends WorldType {
	public WorldTypePlanetoids() {
		super(defaultProperties("planetoids.world"));
	}

	public static float[] createLightRamp() {
		float[] brightnessRamp = new float[32];
		float f = 0.05F;

		for(int i = 0; i <= 31; ++i) {
			float f1 = 1.0F - (float)i / 15.0F;
			if (i > 15) {
				f1 = 0.0F;
			}

			brightnessRamp[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}

		return brightnessRamp;
	}

	public static Properties defaultProperties(String translationKey) {
		return Properties.of(translationKey)
			.defaultWeather(Weathers.OVERWORLD_CLEAR)
			.brightnessRamp(createLightRamp())
			.seasonConfig(SeasonConfig.builder().withSeasonInCycle(Seasons.OVERWORLD_SPRING, 14)
				.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, 14)
				.withSeasonInCycle(Seasons.OVERWORLD_FALL, 14)
				.withSeasonInCycle(Seasons.OVERWORLD_WINTER, 14)
				.build()).oceanBlock(null)
			.fillerBlock(Blocks.STONE)
			.allowRespawn();
	}

	@Override
	public int getMinY() {
		return 0;
	}

	@Override
	public int getMaxY() {
		return 256;
	}

	@Override
	public BiomeProvider createBiomeProvider(World world) {
		return new BiomeProviderOverworld(world.getRandomSeed(), Planetoids.worldType);
	}

	@Override
	public ChunkGenerator createChunkGenerator(World world) {
		return new ChunkGeneratorPlanetoids(world);
	}

	@Override
	public boolean isValidSpawn(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z) != null) {
			return world.getBlock(x, y, z).getLogic() instanceof BlockLogicLeavesBase;
		} else {
			return false;
		}
	}

	@Override
	public void getInitialSpawnLocation(World world) {
		int x = 0;
		int y = 0;
		int z = 0;
		Random rand = new Random(world.getRandomSeed());
		int attemptsRemaining = 10000;

		labelAttempts:
		while(true) {
			if (attemptsRemaining <= 0) {
				x = 0;
				z = 0;
				y = world.getHeightValue(x, z);
				break;
			}

			x += rand.nextInt(64) - rand.nextInt(64);
			z += rand.nextInt(64) - rand.nextInt(64);

			for(y = getMaxY(); y >= getMinY(); --y) {
				if (world.getBlockId(x, y + 1, z) == 0 && isValidSpawn(world, x, y, z)) {
					break labelAttempts;
				}
			}

			--attemptsRemaining;
		}

		world.getLevelData().setSpawn(x, y, z);
	}

	public float getCelestialAngle(World world, long tick, float partialTick) {
		return this.getTimeOfDay(world, tick, partialTick);
	}

	public int getSkyDarken(World world, long tick, float partialTick) {
		float f1 = this.getCelestialAngle(world, tick, partialTick);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F);
		if (f2 < 0.0F) {
			f2 = 0.0F;
		}

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		float weatherOffset = 0.0F;
		Weather currentWeather = world.getCurrentWeather();
		if (currentWeather != null) {
			weatherOffset = (float)currentWeather.subtractLightLevel * world.weatherManager.getWeatherIntensity() * world.weatherManager.getWeatherPower();
		}

		return (int)(f2 * (11.0F - weatherOffset) + weatherOffset);
	}
}
