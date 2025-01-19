package cookie.planetoids.core.world.feature;

import cookie.planetoids.PlanetoidsConfig;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldFeaturePlanetoid extends WorldFeature {
	private final int blockID;
	private final int blockMeta;
	private final int innerBlockID;
	private final int innerBlockMeta;
	private final int topBlockID;
	private final int topBlockMeta;
	private final int bottomBlockID;
	private final int bottomBlockMeta;
	private final int coreBlockID;
	private final int coreBlockMeta;

	public WorldFeaturePlanetoid(int blockID, int blockMeta, int innerBlockID, int innerBlockMeta, int topBlockID, int topBlockMeta, int bottomBlockID, int bottomBlockMeta, int coreBlockID, int coreBlockMeta) {
		this.blockID = blockID;
		this.blockMeta = blockMeta;
		this.innerBlockID = innerBlockID;
		this.innerBlockMeta = innerBlockMeta;
		this.topBlockID = topBlockID;
		this.topBlockMeta = topBlockMeta;
		this.bottomBlockID = bottomBlockID;
		this.bottomBlockMeta = bottomBlockMeta;
		this.coreBlockID = coreBlockID;
		this.coreBlockMeta = coreBlockMeta;
	}

	public WorldFeaturePlanetoid(int blockID, int blockMeta, int innerBlockID, int innerBlockMeta) {
		this.blockID = blockID;
		this.blockMeta = blockMeta;
		this.innerBlockID = innerBlockID;
		this.innerBlockMeta = innerBlockMeta;
		this.topBlockID = blockID;
		this.topBlockMeta = blockMeta;
		this.bottomBlockID = blockID;
		this.bottomBlockMeta = blockMeta;
		this.coreBlockID = innerBlockID;
		this.coreBlockMeta = innerBlockMeta;
	}

	public WorldFeaturePlanetoid(int blockID, int blockMeta) {
		this.blockID = blockID;
		this.blockMeta = blockMeta;
		this.innerBlockID = blockID;
		this.innerBlockMeta = blockMeta;
		this.topBlockID = blockID;
		this.topBlockMeta = blockMeta;
		this.bottomBlockID = blockID;
		this.bottomBlockMeta = blockMeta;
		this.coreBlockID = blockID;
		this.coreBlockMeta = blockMeta;
	}

	private boolean isPointInsideSphere(int x, int y, int z, double radius) {
		return x * x + y * y + z * z < radius * radius;
	}

	@Override
	public boolean place(World world, Random rand, int x, int y, int z) {
		double radius = rand.nextInt(PlanetoidsConfig.CFG.getInt("Planetoids.maxRadius")) + 8;
		int blockRadius = (int) Math.round(radius);
		int innerBlockRadius = (int) Math.round(radius - 3);

		if (y < world.getHeightBlocks() - radius && y > 16 + radius) {

			for (int _x = -blockRadius; _x <= blockRadius; ++_x) {
				for (int _y = -blockRadius; _y <= blockRadius; ++_y) {
					for (int _z = -blockRadius; _z <= blockRadius; ++_z) {
						if (isPointInsideSphere(_x, _y, _z, radius) && world.getBlockId(x + _x, y + _y, z + _z) == 0) {
							if (!isPointInsideSphere(_x, _y + 1, _z, radius)) {
								world.setBlockAndMetadataWithNotify(x + _x, y + _y, z + _z, topBlockID, topBlockMeta);
							} else if (!isPointInsideSphere(_x, _y - 1, _z, radius)) {
								world.setBlockAndMetadataWithNotify(x + _x, y + _y, z + _z, bottomBlockID, bottomBlockMeta);
							} else {
								world.setBlockAndMetadataWithNotify(x + _x, y + _y, z + _z, blockID, blockMeta);
							}
						}
					}
				}
			}

			for (int _x = -innerBlockRadius; _x <= innerBlockRadius; ++_x) {
				for (int _y = -innerBlockRadius; _y <= innerBlockRadius; ++_y) {
					for (int _z = -innerBlockRadius; _z <= innerBlockRadius; ++_z) {
						if (isPointInsideSphere(_x, _y, _z, radius - 3)) {
							world.setBlockAndMetadataWithNotify(x + _x, y + _y, z + _z, innerBlockID, innerBlockMeta);
						}
					}
				}
			}

			for (int _x = -innerBlockRadius; _x <= innerBlockRadius; ++_x) {
				for (int _y = -innerBlockRadius; _y <= innerBlockRadius; ++_y) {
					for (int _z = -innerBlockRadius; _z <= innerBlockRadius; ++_z) {
						if (isPointInsideSphere(_x, _y, _z, radius - (radius / 1.33))) {
							world.setBlockAndMetadataWithNotify(x + _x, y + _y, z + _z, coreBlockID, coreBlockMeta);
						}
					}
				}
			}

			return true;
		}

		return false;
	}
}
