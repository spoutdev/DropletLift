package org.spout.droplet.lift.platform;

public class WaitCommand extends PlatformCommand {
	int duration;
	
	public WaitCommand(int duration) {
		this.duration = duration;
	}
	@Override
	public void run() {
		duration --;
		// DO A GOOD LOAD OF NOTHING
		setFinished(duration == 0);
	}
}
