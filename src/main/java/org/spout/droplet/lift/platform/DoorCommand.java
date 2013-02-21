package org.spout.droplet.lift.platform;

import org.spout.droplet.lift.lift.Floor;

public class DoorCommand extends PlatformCommand {
	Floor floor;
	boolean open;
	
	public DoorCommand(Floor floor, boolean open) {
		this.floor = floor;
		this.open = open;
	}
	
	@Override
	public void run() {
		switch (floor.getDirection()) {
		case NORTH:
		case SOUTH:
			
			break;
		case EAST:
		case WEST:
			
			break;
		default:
			break;
		}
	}

}
