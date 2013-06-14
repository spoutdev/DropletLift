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
package org.spout.droplet.lift;

import gnu.trove.map.hash.TIntObjectHashMap;

import org.spout.api.UnsafeMethod;
import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.geo.discrete.Point;
import org.spout.api.math.Vector2;
import org.spout.api.plugin.CommonPlugin;

import org.spout.droplet.lift.lift.Lift;

public class DropletLift extends CommonPlugin {
	private static DropletLift instance;
	@Override
	@UnsafeMethod
	public void onEnable() {
		instance = this;
		CommandRegistrationsFactory<Class<?>> commandRegFactory = new AnnotatedCommandRegistrationFactory(new SimpleInjector(this), new SimpleAnnotatedCommandExecutorFactory());
		getEngine().getRootCommand().addSubCommands(this, LiftCommands.class, commandRegFactory);
		getLogger().info("DropletLift v" + getDescription().getVersion() + " enabled.");
	}

	@Override
	@UnsafeMethod
	public void onDisable() {
		getLogger().info("DropletLift v" + getDescription().getVersion() + " disabled.");
	}

	private TIntObjectHashMap<Lift> lifts = new TIntObjectHashMap<Lift>();

	public Lift getLift(int id) {
		return lifts.get(id);
	}

	public void addLift(Lift lift) {
		lifts.put(lift.getId(), lift);
	}

	public Lift getNearestLift(Point position, int maxRange) {
		Vector2 column = new Vector2(position.getX(), position.getZ());
		double bestRange = maxRange * maxRange;
		Lift bestMatch = null;
		for (Lift lift:lifts.valueCollection()) {
			Point liftPos = lift.getOffset();
			if (liftPos.getWorld().equals(position.getWorld())) {
				Vector2 liftColumn = new Vector2(liftPos.getX(), liftPos.getZ());
				double distance = liftColumn.distanceSquared(column);
				if (distance <= bestRange) {
					bestRange = distance;
					bestMatch = lift;
				}
			}
		}
		return bestMatch;
	}

	public static DropletLift getInstance() {
		return instance;
	}
}
