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
package org.spout.droplet.lift.lift;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import gnu.trove.map.hash.TIntObjectHashMap;

import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Vector2;

import org.spout.droplet.lift.generator.LiftStructure;
import org.spout.droplet.lift.platform.MovePlatformCommand;
import org.spout.droplet.lift.platform.Platform;
import org.spout.droplet.lift.platform.WaitCommand;
import org.spout.droplet.lift.style.LiftStyle;

public class Lift {
	TIntObjectHashMap<Floor> floors = new TIntObjectHashMap<Floor>();
	LinkedList<Floor> orderedFloors = new LinkedList<Floor>();
	Platform platform;

	int id;
	static int nextId = 0;

	Vector2 innerSize = new Vector2(1, 1);

	Point offset;

	private Queue<LiftCall> calls = new LinkedBlockingQueue<LiftCall>();

	LiftStyle style;

	public Lift(LiftStyle style, Point offset) {
		super();
		this.offset = offset;
		this.style = style;
		id = nextId ++;
		platform = new Platform(this);
	}

	public void addFloor(Floor floor) {
		floors.put(floor.getFloorNumber(), floor);
		orderedFloors.add(floor);
		Collections.sort(orderedFloors);
		floor.setLift(this);

		if (!platform.isSpawned()) {
			platform.setOffset(floor.getFloorOffset() * 2 + 1, false);
			platform.spawn();
		}
		//platform.setTarget(floor.getFloorOffset());
	}

	public Floor getFloor(int number) {
		return floors.get(number);
	}

	public int getId() {
		return id;
	}

	public Point getOffset() {
		return offset;
	}

	public int addCall(LiftCall call) {
		if (call.getLift() != this) {
			return -1;
		}
		calls.add(call);
		if (!platform.isRunning()) {
			nextTask();
		}
		return calls.size() - 1;
	}

	public void regenerate() {
		LiftStructure structure = new LiftStructure();
		structure.setLift(this);
		structure.placeObject(offset.getWorld(), offset.getBlockX(), offset.getBlockY(), offset.getBlockZ());
	}

	public LiftStyle getStyle() {
		return style;
	}

	public Floor getLowestFloor() {
		return orderedFloors.getFirst();
	}

	public Floor getHighestFloor() {
		return orderedFloors.getLast();
	}

	public Collection<Floor> getFloors() {
		return floors.valueCollection();
	}

	public void removeFloor(Floor floor) {
		floors.remove(floor.getFloorNumber());
		orderedFloors.remove(floor);
	}

	public Floor getFloorByY(int blockY) {
		int o = blockY - offset.getBlockY();
		for (Floor floor:orderedFloors) {
			if (o == floor.getFloorOffset()) {
				return floor;
			}
		}
		return null;
	}

	public void nextTask() {
		LiftCall next = calls.poll();
		if (next != null) {
			platform.addCommand(new MovePlatformCommand(next.getCurrentFloor().getFloorOffset() - 1));
			platform.addCommand(new WaitCommand(10));
			platform.addCommand(new MovePlatformCommand(next.getDesiredFloor().getFloorOffset() - 1));
		}
	}
}
