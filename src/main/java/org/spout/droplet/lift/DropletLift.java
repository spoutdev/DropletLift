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
