package com.codeitforyou.votes.util;

import com.codeitforyou.votes.Votes;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public enum Lang {
    PREFIX("&b[&lVotes&b]"),
    MAIN_COMMAND("{0} &7You can vote for us with the following:",
            "&7",
            " &8- &fhttps://vote.link/1",
            " &8- &fhttps://vote.link/2",
            " &8- &fhttps://vote.link/3",
            "&7",
            "{0} &7You have a total of &b{1} &7vote(s)."),
    INFO_COMMAND("{0} &7You're running &f{1} &7version &bv{2} &7by &f{3}&7."),
    CHANNEL_COMMAND_USAGE("{0} &7Usage: &b/channel &7<&f{1}&7>."),
    ERROR_NO_PERMISSION_COMMAND("{0} &7You don't have permission to that command."),
    ERROR_PLAYER_ONLY("{0} &7That's a player only command."),
    ERROR_INVALID_COMMAND("{0} &7That's an invalid command, use &f/votes help&7."),
    ERROR_INVALID_PLAYER("{0} &7That player couldn't be found.");

    private static FileConfiguration c;
    private static File f;
    private String message;

    Lang(final String... def) {
        this.message = String.join("\n", def);
    }

    public static String format(String s, final Object... objects) {
        for (int i = 0; i < objects.length; ++i) {
            s = s.replace("{" + i + "}", String.valueOf(objects[i]));
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean init(Votes votes) {
        Lang.f = new File(votes.getDataFolder() + "/" + "messages.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                votes.getLogger().warning("Failed to create messages.yml.");
                e.printStackTrace();
            }
        }
        Lang.c = YamlConfiguration.loadConfiguration(f);
        for (final Lang value : values()) {
            if (value.getMessage().split("\n").length == 1) {
                Lang.c.addDefault(value.getPath().toLowerCase(), value.getMessage());
            } else {
                Lang.c.addDefault(value.getPath().toLowerCase(), value.getMessage().split("\n"));
            }
        }
        Lang.c.options().copyDefaults(true);
        try {
            Lang.c.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String getMessage() {
        return this.message;
    }

    public String getPath() {
        return this.name().toLowerCase().toLowerCase();
    }

    public void send(final Player player, final Object... args) {
        final String message = this.asString(args);
        Arrays.stream(message.split("\n")).forEach(player::sendMessage);
    }

    public void send(final CommandSender sender, final Object... args) {
        if (sender instanceof Player) {
            this.send((Player) sender, args);
        } else {
            Arrays.stream(this.asString(args).split("\n")).forEach(sender::sendMessage);
        }
    }

    public String asString(final Object... objects) {
        Optional<String> opt = Optional.empty();
        if (Lang.c.contains(this.getPath())) {
            if (Lang.c.isList(getPath())) {
                opt = Optional.of(String.join("\n", Lang.c.getStringList(this.getPath())));
            } else if (Lang.c.isString(this.getPath())) {
                opt = Optional.ofNullable(Lang.c.getString(this.getPath()));
            }
        }
        return this.format(opt.orElse(this.message), objects);
    }
}

