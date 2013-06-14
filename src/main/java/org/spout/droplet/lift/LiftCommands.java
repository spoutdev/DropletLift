package org.spout.droplet.lift;

import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.command.annotated.CommandPermissions;
import org.spout.api.entity.Player;
import org.spout.api.geo.discrete.Point;
import org.spout.api.material.block.BlockFace;

import org.spout.droplet.lift.lift.Floor;
import org.spout.droplet.lift.lift.Lift;
import org.spout.droplet.lift.lift.LiftCall;
import org.spout.droplet.lift.style.DefaultLiftStyle;
import org.spout.droplet.lift.style.LiftStyle;

public class LiftCommands {
	DropletLift plugin;

	public LiftCommands(DropletLift plugin) {
		this.plugin = plugin;
	}

	@Command(aliases = {"addlift"}, usage = "<style>", desc="adds a lift at the position you are standing on\nGives you the lift id")
	@CommandPermissions("dropletlift.command.addlift")
	public void addLift(CommandContext args, CommandSource source) {
		Player p;
		if (source instanceof Player) {
			p = (Player) source;
		} else {
			source.sendMessage("You have to be player");
			return;
		}
		if (args.length() >= 0) {
			LiftStyle style = new DefaultLiftStyle();
			Lift lift = new Lift(style, new Point(p.getWorld(), p.getScene().getPosition().getX(), 0, p.getScene().getPosition().getZ()));
			plugin.addLift(lift);
			p.sendMessage(ChatStyle.DARK_GREEN, "Your lift has been added. Id: ", ChatStyle.YELLOW, lift.getId());
		}
	}

	@Command(aliases = {"addfloor"}, usage = "<liftid> <floornumber> [permission]", desc="adds a new floor level to the lift")
	@CommandPermissions("dropletlift.command.addfloor")
	public void addFloor(CommandContext args, CommandSource source) {
		Player p;
		if (source instanceof Player) {
			p = (Player) source;
		} else {
			source.sendMessage("You have to be player");
			return;
		}
		if (args.length() >= 2) {
			if (args.isInteger(0) && args.isInteger(1)) {
				Lift lift = plugin.getLift(args.getInteger(0));
				if (lift != null) {
					Point offset = lift.getOffset();
					Point playerPos = p.getScene().getPosition();
					int floorOffset = playerPos.getBlockY() - offset.getBlockY();
					int floorNumber = args.getInteger(1);
					String permission = args.getString(2, null);
					BlockFace direction = BlockFace.fromYaw(p.getScene().getRotation().getYaw());
					Floor floor = new Floor(floorNumber, floorOffset, permission, direction);
					lift.addFloor(floor);
					lift.regenerate();

					p.sendMessage(ChatStyle.DARK_GREEN, "Floor ", ChatStyle.YELLOW, floorNumber, ChatStyle.DARK_GREEN, " has been added.");
				}
			}
		}
	}

	@Command(aliases = {"removefloor"}, usage = "<liftid> <floornumber>", desc="removes the given floor from the given lift")
	@CommandPermissions("dropletlift.command.removefloor")
	public void removeFloor(CommandContext args, CommandSource source) {
		if (args.length() == 2 && args.isInteger(0) && args.isInteger(1)) {
			Lift lift = plugin.getLift(args.getInteger(0));
			if (lift != null) {
				Floor floor = lift.getFloor(args.getInteger(1));
				if (floor != null) {
					lift.removeFloor(floor);
					lift.regenerate();
				}
			}
		}
	}

	@Command(aliases = {"call", "calllift"}, usage = "<floorid>", desc="calls the lift you are standing at. <floorid> is the floor you want to go to")
	@CommandPermissions("dropletlift.command.call")
	public void call(CommandContext args, CommandSource source) {
		if (args.length() == 1 && args.isInteger(0)) {
			Player p;
			if (source instanceof Player) {
				p = (Player) source;
			} else {
				source.sendMessage("You have to be player");
				return;
			}
			Lift lift = plugin.getNearestLift(p.getScene().getPosition(), 5);
			if (lift == null) {
				p.sendMessage(ChatStyle.RED, "You're not at a lift");
				return;
			}
			Floor desiredFloor = lift.getFloor(args.getInteger(0));
			if (desiredFloor == null) {
				p.sendMessage(ChatStyle.RED, "The floor ", ChatStyle.YELLOW, args.getInteger(0), ChatStyle.RED, " does not exist.");
				return;
			}
			Floor currentFloor = lift.getFloorByY(p.getScene().getPosition().getBlockY());
			if (currentFloor == null) {
				p.sendMessage(ChatStyle.RED, "Sorry, I can't determine your current floor.");
				return;
			}
			LiftCall call = new LiftCall(lift, currentFloor, desiredFloor, p);
			int order = lift.addCall(call);
			p.sendMessage(ChatStyle.DARK_GREEN, "You have called the lift. You're the ", order + 1, "th in line.");
		} else {
			source.sendMessage(ChatStyle.RED, "You have to give the floor id");
		}
	}

	@Command(aliases = {"regen"}, usage = "<liftid>", desc="regenerates the lift")
	@CommandPermissions("dropletlift.command.regen")
	public void regen(CommandContext args, CommandSource source) {
		if (args.length() == 1 && args.isInteger(0)) {
			Lift lift = plugin.getLift(args.getInteger(0));
			if (lift != null) {
				lift.regenerate();
			}
		}
	}
}
