package org.spout.droplet.lift.platform;

public class MovePlatformCommand extends PlatformCommand {
	private int target;
	
	public MovePlatformCommand(int f) {
		this.target = f * 2 + 1;
	}
	
	@Override
	public void run() {
		int offset = getPlatform().getOffset();
		int dir = 1;
		if (offset > target) {
			dir = -1;
		}
		offset += dir;
		getPlatform().setOffset(offset, true);
		if (offset == target) {
			setFinished(true);
		}
	}
}
