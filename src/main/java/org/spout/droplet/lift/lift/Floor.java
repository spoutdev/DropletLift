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

import java.io.Serializable;

import org.spout.api.material.block.BlockFace;

public class Floor implements Serializable, Comparable<Floor> {
	private static final long serialVersionUID = 1127361782631237L;

	private int floorNumber = 0;
	private int floorOffset = 0;
	private String permission = null;
	private Lift lift;
	private BlockFace direction = BlockFace.NORTH;

	public Floor(int floorNumber, int floorOffset, String permission, BlockFace direction) {
		super();
		this.floorNumber = floorNumber;
		this.floorOffset = floorOffset;
		this.permission = permission;
		this.direction = direction;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public void setFloorOffset(int floorOffset) {
		this.floorOffset = floorOffset;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setLift(Lift lift) {
		this.lift = lift;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public int getFloorOffset() {
		return floorOffset;
	}

	public String getPermission() {
		return permission;
	}

	public Lift getLift() {
		return lift;
	}

	public BlockFace getDirection() {
		return direction;
	}

	public int compareTo(Floor arg0) {
		return floorOffset - arg0.floorOffset;
	}

	@Override
	public String toString() {
		return "Floor [floorNumber=" + floorNumber + ", floorOffset=" + floorOffset + ", permission=" + permission + "]";
	}
}
