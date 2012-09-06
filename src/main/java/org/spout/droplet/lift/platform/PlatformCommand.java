package org.spout.droplet.lift.platform;

public abstract class PlatformCommand {
	boolean finished = false;
	Platform platform;
	
	void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	public Platform getPlatform() {
		return platform;
	}
	
	protected void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public abstract void run();
}
