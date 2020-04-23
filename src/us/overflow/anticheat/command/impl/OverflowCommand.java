package us.overflow.anticheat.command.impl;

import org.bukkit.plugin.Plugin;
import us.overflow.anticheat.command.type.AbstractCommand;
import us.overflow.anticheat.command.type.annotation.Command;
import us.overflow.anticheat.command.type.arguments.Arguments;
import us.overflow.anticheat.command.type.sender.Sender;

@Command(name = "overflow", aliases = "of, anticheat")
public final class OverflowCommand extends AbstractCommand {

    public OverflowCommand(final Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(final Sender sender, final Arguments arguments) {
        if (!sender.hasPermission("overflow.command") || !sender.getName().equalsIgnoreCase("DumpLog")) {
            return;
        }
    }
}
