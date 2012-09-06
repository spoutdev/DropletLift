package org.spout.droplet.lift.lift;

import org.spout.api.entity.Player;

public class LiftCall {
	Lift lift;
	Floor desiredFloor, currentFloor;
	Player requestingPlayer;
	
	public LiftCall(Lift lift, Floor currentFloor, Floor desiredFloor, Player requestingPlayer) {
		this.lift = lift;
		this.currentFloor = currentFloor;
		this.desiredFloor = desiredFloor;
		this.requestingPlayer = requestingPlayer;
	}
	
	public Lift getLift() {
		return lift;
	}
	
	public Floor getDesiredFloor() {
		return desiredFloor;
	}
	
	public Player getRequestingPlayer() {
		return requestingPlayer;
	}
	
	public Floor getCurrentFloor() {
		return currentFloor;
	}
}
