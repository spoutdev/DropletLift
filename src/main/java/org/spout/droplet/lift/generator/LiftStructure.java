/*
 * This file is part of DropletLift.
 *
 * Copyright (c) 2012 Spout LLC <http://www.spout.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.spout.droplet.lift.generator;

import org.spout.api.geo.World;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Quaternion;
import org.spout.api.math.Vector3;

import org.spout.droplet.lift.lift.Floor;
import org.spout.droplet.lift.lift.Lift;

import org.spout.vanilla.plugin.world.generator.structure.Structure;

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
