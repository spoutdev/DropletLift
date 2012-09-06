package org.spout.droplet.lift.style;

import org.spout.api.math.Vector2;
import org.spout.vanilla.material.VanillaBlockMaterial;
import org.spout.vanilla.material.block.misc.Slab;

public abstract class LiftStyle {
	public abstract VanillaBlockMaterial getWallMaterial();
	public abstract Slab getPlatformMaterial();
	public abstract VanillaBlockMaterial getDoorMaterial();
	
	public abstract int getFloorHeight();
	public abstract Vector2 getInnerSize();
	
	public abstract String getStyleName();
}
