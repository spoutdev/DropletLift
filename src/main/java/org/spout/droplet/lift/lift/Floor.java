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
		return "Floor [floorNumber=" + floorNumber + ", floorOffset="
				+ floorOffset + ", permission=" + permission + "]";
	}
}
