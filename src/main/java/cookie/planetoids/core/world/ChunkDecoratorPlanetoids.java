package cookie.planetoids.core.world;

import cookie.planetoids.core.world.feature.WorldFeaturePlanetoid;
import net.minecraft.core.block.*;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.generate.chunk.ChunkDecorator;

import java.util.Random;

public class ChunkDecoratorPlanetoids implements ChunkDecorator {
	private final World world;

	public ChunkDecoratorPlanetoids(World world) {
		this.world = world;
	}

	@Override
	public void decorate(Chunk chunk) {
		world.scheduledUpdatesAreImmediate = true;
		int chunkX = chunk.xPosition;
		int chunkZ = chunk.zPosition;
		int minY = world.getWorldType().getMinY();
		int maxY = world.getWorldType().getMaxY();
		int rangeY = maxY + 1 - minY;
		float heightModifier = (float) rangeY / 128.0F;
		BlockLogicSand.fallInstantly = false;
		int x = chunkX * 16;
		int z = chunkZ * 16;
		int y = world.getHeightValue(x + 16, z + 16);
		Biome biome = world.getBlockBiome(x + 16, y, z + 16);
		Random rand = new Random(world.getRandomSeed());
		long l1 = rand.nextLong() / 2L * 2L + 1L;
		long l2 = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ world.getRandomSeed());

		int blockID = Blocks.STONE.id();
		int innerID = Blocks.STONE.id();
		int topID = Blocks.STONE.id();
		int bottomID = Blocks.STONE.id();
		int coreID = Blocks.STONE.id();

		int leavesID;
		int logID;

		switch (rand.nextInt(7)) {
			case 6:
				leavesID = Blocks.LEAVES_PALM.id();
				logID = Blocks.LOG_PALM.id();
				break;
			case 5:
				leavesID = Blocks.LEAVES_PINE.id();
				logID = Blocks.LOG_PINE.id();
				break;
			case 4:
				leavesID = Blocks.LEAVES_OAK.id();
				logID = Blocks.LOG_OAK.id();
				break;
			case 3:
				leavesID = Blocks.LEAVES_EUCALYPTUS.id();
				logID = Blocks.LOG_EUCALYPTUS.id();
				break;
			case 2:
				leavesID = Blocks.LEAVES_CHERRY.id();
				logID = Blocks.LOG_CHERRY.id();
				break;
			case 1:
				leavesID = Blocks.LEAVES_CACAO.id();
				logID = Blocks.LOG_OAK_MOSSY.id();
				break;
			case 0:
			default:
				leavesID = Blocks.LEAVES_BIRCH.id();
				logID = Blocks.LOG_BIRCH.id();
				break;
		}

		int stoneID;
		switch (rand.nextInt(5)) {
			case 4:
				stoneID = Blocks.PERMAFROST.id();
				break;
			case 3:
				stoneID = Blocks.GRANITE.id();
				break;
			case 2:
				stoneID = Blocks.LIMESTONE.id();
				break;
			case 1:
				stoneID = Blocks.BASALT.id();
				break;
			case 0:
			default:
				stoneID = Blocks.STONE.id();
				break;
		}

		int oreID;
		switch (rand.nextInt(7)) {
			case 6:
				oreID = Blocks.GRAVEL.id();
				break;
			case 5:
				oreID = BlockLogicOreRedstone.variantMap.get(stoneID);
				break;
			case 4:
				oreID = BlockLogicOreLapis.variantMap.get(stoneID);
				break;
			case 3:
				oreID = BlockLogicOreIron.variantMap.get(stoneID);
				break;
			case 2:
				oreID = BlockLogicOreGold.variantMap.get(stoneID);
				break;
			case 1:
				oreID = BlockLogicOreDiamond.variantMap.get(stoneID);
				break;
			case 0:
			default:
				oreID = BlockLogicOreCoal.variantMap.get(stoneID);
				break;
		}

		int netherInnerID;
		int netherCoreID;
		if (rand.nextInt(2) == 0) {
			netherInnerID = Blocks.FLUID_LAVA_STILL.id();
			netherCoreID = Blocks.PUMICE_WET.id();
		} else {
			netherInnerID = netherCoreID = Blocks.ORE_NETHERCOAL_NETHERRACK.id();
		}

		switch (rand.nextInt(8)) {
			case 7:
				blockID = topID = bottomID = Blocks.BLOCK_SNOW.id();
				innerID = coreID = Blocks.PERMAICE.id();
				break;
			case 6:
				blockID = topID = Blocks.COBBLE_NETHERRACK.id();
				innerID = netherInnerID;
				bottomID = Blocks.SOULSAND.id();
				coreID = netherCoreID;
				break;
			case 5:
				blockID = innerID = topID = bottomID = coreID = Blocks.GLOWSTONE.id();
				break;
			case 4:
				blockID = topID = bottomID = leavesID;
				innerID = coreID = logID;
				break;
			case 3:
				blockID = innerID = bottomID = coreID = Blocks.DIRT.id();
				topID = Blocks.GRASS.id();
				break;
			case 2:
				blockID = innerID = topID = coreID = Blocks.SAND.id();
				bottomID = Blocks.BLOCK_CLAY.id();
				break;
			case 1:
				blockID = topID = bottomID = Blocks.GLASS.id();
				innerID = Blocks.FLUID_WATER_STILL.id();
				coreID = Blocks.SPONGE_WET.id();
				break;
			case 0:
			default:
				blockID = topID = bottomID = stoneID;
				innerID = coreID = oreID;
				break;
		}

		if (rand.nextInt(8) == 0) {
			int _x = x + rand.nextInt(16);
			int _y = minY + rand.nextInt(rangeY);
			int _z = z + rand.nextInt(16);

			new WorldFeaturePlanetoid(blockID,
				0,
				innerID,
				0,
				topID,
				0,
				bottomID,
				0,
				coreID,
				0).place(world, rand, _x, _y, _z);
		}

		BlockLogicSand.fallInstantly = false;
		this.world.scheduledUpdatesAreImmediate = false;
	}
}
