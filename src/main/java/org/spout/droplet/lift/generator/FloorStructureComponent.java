package org.spout.droplet.lift.generator;

import java.util.List;

import org.spout.api.math.Vector2;
import org.spout.api.math.Vector3;
import org.spout.droplet.lift.lift.Floor;
import org.spout.vanilla.world.generator.structure.ComponentCuboidPart;
import org.spout.vanilla.world.generator.structure.SimpleBlockMaterialPicker;
import org.spout.vanilla.world.generator.structure.Structure;
import org.spout.vanilla.world.generator.structure.StructureComponent;

public class FloorStructureComponent extends StructureComponent {
	Floor floor;
	SimpleBlockMaterialPicker picker = new SimpleBlockMaterialPicker();
	ComponentCuboidPart cuboid = new ComponentCuboidPart(this);
	
	public FloorStructureComponent(Structure parent) {
		super(parent);
	}

	@Override
	public boolean canPlace() {
		return true;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	public Floor getFloor() {
		return floor;
	}
	
	@Override
	public void place() {
		picker.setOuterMaterial(floor.getLift().getStyle().getDoorMaterial());
		picker.setInnerMaterial(floor.getLift().getStyle().getDoorMaterial());
		
		cuboid.setMinMax(1, 0, 0, 2, floor.getLift().getStyle().getFloorHeight() - 1, 0);
		cuboid.setPicker(picker);
		cuboid.fill(false);
	}

	@Override
	public void randomize() {} // This is not random

	@Override
	public List<StructureComponent> getNextComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoundingBox getBoundingBox() {
		Vector2 inner = floor.getLift().getStyle().getInnerSize();
		inner = inner.add(2, 2);
		Vector3 bounding = new Vector3(inner.getX(), floor.getLift().getStyle().getFloorHeight(), inner.getY());
		return new BoundingBox(Vector3.ZERO, bounding);
	}
}
