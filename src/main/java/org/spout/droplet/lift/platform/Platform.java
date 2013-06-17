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
package org.spout.droplet.lift.platform;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.spout.api.Spout;
import org.spout.api.geo.World;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.math.Vector2;
import org.spout.api.scheduler.TaskPriority;

import org.spout.droplet.lift.DropletLift;
import org.spout.droplet.lift.lift.Lift;

import org.spout.vanilla.material.VanillaMaterials;

public class Platform implements Runnable {
	Lift lift;
	int offset = 0;
	Vector2 size;
	boolean spawned = false;
	int taskId = -1;
	Queue<PlatformCommand> commands = new LinkedBlockingQueue<PlatformCommand>();

	public Platform(Lift lift) {
		super();
		this.lift = lift;
		this.size = lift.getStyle().getInnerSize();
	}

	public void spawn() {
		if (!spawned) {
			spawned = true;
			draw();
		}
	}

	private void draw() {
		Block base = getBaseBlock();
		for (int x = 0; x <= size.getFloorX(); x ++) {
			for (int z = 0; z <= size.getFloorY(); z++) {
				Block b = base.translate(x, 0, z);
				b.setMaterial(lift.getStyle().getPlatformMaterial());
				lift.getStyle().getPlatformMaterial().setTop(b, offset % 2 != 0);
			}
		}
	}

	private void remove() {
		Block base = getBaseBlock();
		for (int x = 0; x <= size.getFloorX(); x ++) {
			for (int z = 0; z <= size.getFloorY(); z++) {
				base.translate(x, 0, z).setMaterial(VanillaMaterials.AIR);
			}
		}
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset, boolean update) {
		if (update) {
			remove();
		}
		this.offset = offset ;
		if (update) {
			draw();
		}
	}

	private Block getBaseBlock() {
		World w = lift.getOffset().getWorld();
		return w.getBlock(lift.getOffset().getBlockX() + 1, lift.getOffset().getBlockY() + offset / 2, lift.getOffset().getBlockZ() + 1);
	}

	public void addCommand(PlatformCommand command) {
		command.setPlatform(this);
		commands.add(command);
		if (!isRunning()) {
			taskId = Spout.getScheduler().scheduleSyncRepeatingTask(DropletLift.getInstance(), this, 100, 100, TaskPriority.MEDIUM).getTaskId();
		}
	}

	public void run() {
		PlatformCommand cmd = commands.peek();

		if (cmd != null) {
			cmd.run();
			if (cmd.isFinished()) {
				commands.poll();
			}
		}

		if (commands.isEmpty()) {
			lift.nextTask(); // Poll the lift for more tasks.
			if (commands.isEmpty()) {
				Spout.getScheduler().cancelTask(taskId);
				taskId = -1;
			}
		}
	}

	public boolean isRunning() {
		return taskId != -1;
	}

	public boolean isSpawned() {
		return spawned;
	}
}
