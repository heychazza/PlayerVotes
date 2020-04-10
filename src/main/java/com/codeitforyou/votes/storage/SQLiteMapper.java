package com.codeitforyou.votes.storage;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.storage.util.ObjectMapper;
import com.codeitforyou.votes.storage.util.StorageType;
import com.codeitforyou.votes.storage.util.VoteUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class SQLiteMapper implements StorageType {

    private Votes plugin = Votes.getPlugin();
    private Connection connectionSource;

    private String userTable;

    public SQLiteMapper(String prefix, String host, int port, String database, String username, String password) {
        this.userTable = prefix + "users";
        try {
            File file = new File(plugin.getDataFolder(), "storage.db");
            connectionSource = DriverManager.getConnection("jdbc:sqlite://" + file.getAbsolutePath());
            Statement statement = connectionSource.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + userTable + " ( `id` INTEGER NOT NULL , `uuid` VARCHAR(255) NULL DEFAULT NULL , `votes` INT NULL DEFAULT '0' , PRIMARY KEY (`id`), UNIQUE (`uuid`));");
        } catch (SQLException e) {
            plugin.getLogger().warning("==================================================");
            plugin.getLogger().warning("Failed to connect to SQLite database!");
            plugin.getLogger().warning("If you believe this is an error, send the below stacktrace to the developer..");
            e.printStackTrace();
            plugin.getLogger().warning("==================================================");
        }
    }

    @Override
    public void pullUser(final UUID target) {
        try {
            PreparedStatement stmt = connectionSource.prepareStatement("SELECT * FROM " + userTable + " WHERE uuid = ?");
            stmt.setString(1, target.toString());
            ResultSet rs = stmt.executeQuery();

            ObjectMapper<VoteUser> objectMapper = new ObjectMapper<>(VoteUser.class);
            VoteUser voteUser = objectMapper.map(rs);

            if(voteUser == null) {
                voteUser = new VoteUser(target, 0);
            }
            plugin.getUserManager().addUser(voteUser);

            Player player = Bukkit.getPlayer(voteUser.getVoter());
            if (player != null) plugin.getLogger().info("Pulled user " + player.getName() + " with " + voteUser.getVotes() + " vote(s)!");
        } catch (SQLException e) {
            plugin.getLogger().warning("==================================================");
            plugin.getLogger().warning("Failed to pull user data of " + target.toString() + "!");
            plugin.getLogger().warning("If you believe this is an error, send the below stacktrace to the developer..");
            e.printStackTrace();
            plugin.getLogger().warning("==================================================");
        }
    }

    @Override
    public void pushUser(final UUID target) {
        VoteUser voteUser = plugin.getUserManager().getUser(target);

        try {
            PreparedStatement stmt = connectionSource.prepareStatement("INSERT OR REPLACE INTO " + userTable + " (uuid, votes) VALUES (?, ?)");
            stmt.setString(1, voteUser.getVoter().toString());
            stmt.setInt(2, voteUser.getVotes());

            stmt.executeUpdate();

            plugin.getLogger().info("Pushed user " + Bukkit.getPlayer(voteUser.getVoter()).getName() + " with " + voteUser.getVotes() + " vote(s)!");
            plugin.getUserManager().removeUser(target);
        } catch (SQLException e) {
            plugin.getLogger().warning("==================================================");
            plugin.getLogger().warning("Failed to push user data of " + target.toString() + "!");
            plugin.getLogger().warning("If you believe this is an error, send the below stacktrace to the developer..");
            e.printStackTrace();
            plugin.getLogger().warning("==================================================");
        }
    }
}
