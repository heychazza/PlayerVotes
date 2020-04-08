package com.codeitforyou.votes.command.sub;

import com.codeitforyou.lib.api.command.Command;
import com.codeitforyou.lib.api.general.JSONMessage;
import com.codeitforyou.lib.api.general.StringUtil;
import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.command.PluginCommandManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HelpCommand {
    @Command(aliases = {"help"}, about = "View a list of commands.", permission = "cifyvotes.help", usage = "help", title = "Help Menu")
    public static void execute(final CommandSender sender, final Votes plugin, final String[] args) {
        final List<Method> commandMethods = PluginCommandManager.getCommandManager().getCommands().values().stream().distinct().collect(Collectors.toList());

        sender.sendMessage(" ");
        sender.sendMessage(StringUtil.translate("&b[&l" + "Votes" + "&b] &7v" + plugin.getDescription().getVersion() + " &8- &bHelp Menu"));
        sender.sendMessage(" ");

        if (sender instanceof ConsoleCommandSender) sender.sendMessage(" ");
        for (final Method commandMethod : commandMethods) {
            final Command command = commandMethod.getAnnotation(Command.class);
            if (!sender.hasPermission(command.permission())) continue;
            getCommandFormat(sender, "vote", command.aliases()[0], command.title(), command.about(), command.usage(), command.permission());
        }

        sender.sendMessage(" ");
    }

    private static void getCommandFormat(final CommandSender commandSender, final String mainCommand, final String command, final String commandTitle, final String commandDesc, String commandUsage, final String commandPerm) {
        final String commandStr = !command.isEmpty() ? " " + command : "";

        if (commandSender instanceof Player) {

            if (commandUsage.equalsIgnoreCase(command)) {
                commandUsage = "";
            } else if (!command.isEmpty()) {
                commandUsage = commandUsage.replace(command + " ", "");
            }

            final List<String> helpTooltip = Arrays.asList("&b&l" + commandTitle,
                    "&f" + commandDesc,
                    "&7",
                    "&7/" + mainCommand + commandStr + " &3" + commandUsage,
                    "&7",
                    "&8Requires &n" + commandPerm + "&8 to use!"
            );

            JSONMessage.create(StringUtil.translate(" &b/" + mainCommand + commandStr))
                    .tooltip(StringUtil.translate(StringUtils.join(helpTooltip, "\n")))
                    .suggestCommand("/" + mainCommand + commandStr).send((Player) commandSender);
        } else {
            commandSender.sendMessage("/" + mainCommand + " " + commandUsage + " - " + commandDesc);
        }
    }
}
