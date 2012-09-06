package org.spout.droplet.lift.generator;

import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;
import org.spout.droplet.lift.lift.Floor;
import org.spout.droplet.lift.lift.Lift;
import org.spout.vanilla.world.generator.structure.Structure;

public class LiftStructure extends Structure {
	Lift lift;
	LiftShaftStructureComponent shaft = new LiftShaftStructureComponent(this);
	FloorStructureComponent floorComponent = new FloorStructureComponent(this);
	
	public void setLift(Lift lift) {
		this.lift = lift;
	}
	
	public Lift getLift() {
		return lift;
	}
	
	@Override
	public boolean canPlaceObject(World w, int x, int y, int z) {
		return true;
	}

	@Override
	public void placeObject(World w, int x, int y, int z) {
		shaft.setLift(getLift());
		shaft.setPosition(new Point(w, x, y, z));
		shaft.place();
		
		for (Floor floor:lift.getFloors()) {
			floorComponent.setFloor(floor);
			floorComponent.setPosition(new Point(w, x, y + floor.getFloorOffset(), z));
			float angle = 0;
			switch (floor.getDirection()) {
			case NORTH:
				angle = 90;
				break;
			case EAST:
				angle = 180;
				break;
			case SOUTH:
				angle = 270;
				break;
			case WEST:
				angle = 0;
				break;
			default:
				break;
			}
			floorComponent.setRotation(new Quaternion(angle, Vector3.UNIT_Y));
			floorComponent.setRotationPoint((new Vector3(lift.getStyle().getInnerSize().getX() + 2, 0, lift.getStyle().getInnerSize().getY() + 2)).divide(2));
			floorComponent.place();
		}
	}

}
