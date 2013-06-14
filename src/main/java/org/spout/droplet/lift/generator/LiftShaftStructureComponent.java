package org.spout.droplet.lift.generator;

import java.util.List;

import org.spout.api.material.block.BlockFace;
import org.spout.api.math.IntVector3;

import org.spout.droplet.lift.lift.Lift;

import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.world.generator.structure.ComponentPlanePart;
import org.spout.vanilla.world.generator.structure.SimpleBlockMaterialPicker;
import org.spout.vanilla.world.generator.structure.Structure;
import org.spout.vanilla.world.generator.structure.StructureComponent;

public class LiftShaftStructureComponent extends StructureComponent {
	Lift lift;
	ComponentPlanePart plane = new ComponentPlanePart(this);
	SimpleBlockMaterialPicker picker = new SimpleBlockMaterialPicker();

	public LiftShaftStructureComponent(Structure parent) {
		super(parent);
	}

	@Override
	public boolean canPlace() {
		return true;
	}

	public void setLift(Lift lift) {
		this.lift = lift;
	}

	public Lift getLift() {
		return lift;
	}

	@Override
	public void place() {
		picker.setOuterInnerMaterials(lift.getStyle().getWallMaterial(), VanillaMaterials.AIR);
		IntVector3 min, max;
		min = new IntVector3(0, lift.getLowestFloor().getFloorOffset() - 1, 0);
		max = new IntVector3(lift.getStyle().getInnerSize().getFloorX() + 2, lift.getLowestFloor().getFloorOffset() - 1, lift.getStyle().getInnerSize().getFloorY() + 2);
		IntVector3 ONE_UP = new IntVector3(BlockFace.TOP);
		int from = (lift.getLowestFloor().getFloorOffset() - 1);
		int to = (lift.getHighestFloor().getFloorOffset() + lift.getStyle().getFloorHeight());
		for (int i = from; i <= to; i++) {
			plane.setMinMax(min, max);
			plane.setPicker(picker);
			plane.fill(false);
			min.add(ONE_UP);
			max.add(ONE_UP);
		}
	}

	@Override
	public void randomize() {}

	@Override
	public List<StructureComponent> getNextComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoundingBox getBoundingBox() {
		// TODO Auto-generated method stub
		return null;
	}
}
