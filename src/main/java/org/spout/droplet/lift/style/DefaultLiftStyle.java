package org.spout.droplet.lift.style;

import org.spout.api.math.Vector2;
import org.spout.vanilla.material.VanillaBlockMaterial;
import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.material.block.misc.Slab;

public class DefaultLiftStyle extends LiftStyle {

	@Override
	public VanillaBlockMaterial getWallMaterial() {
		return VanillaMaterials.STONE_BRICK;
	}

	@Override
	public Slab getPlatformMaterial() {
		return Slab.STONE_BRICK;
	}

	@Override
	public VanillaBlockMaterial getDoorMaterial() {
		return VanillaMaterials.GLASS_PANE;
	}

	@Override
	public int getFloorHeight() {
		return 3;
	}
	
	@Override
	public Vector2 getInnerSize() {
		return Vector2.ONE;
	}
	
	
	@Override
	public String getStyleName() {
		return "default";
	}
}
