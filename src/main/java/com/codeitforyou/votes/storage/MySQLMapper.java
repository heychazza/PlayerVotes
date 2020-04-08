package com.codeitforyou.votes.storage;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.storage.util.ObjectMapper;
import com.codeitforyou.votes.storage.util.StorageType;
import com.codeitforyou.votes.storage.util.VoteUser;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class MySQLMapper implements StorageType {

    private Votes plugin = Votes.getPlugin();
    private Connection connectionSource;

    public MySQLMapper(String prefix, String host, int port, String database, String username, String password) {
        try {
            connectionSource = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            Statement statement = connectionSource.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + database + ".`users` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` VARCHAR(255) NULL DEFAULT NULL , `votes` INT NULL DEFAULT '0' , PRIMARY KEY (`id`), UNIQUE (`uuid`));");
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    @Override
    public void pullUser(final UUID target) {
        try {
            PreparedStatement stmt = connectionSource.prepareStatement("SELECT * FROM users WHERE uuid = ?");
            stmt.setString(1, target.toString());
            ResultSet rs = stmt.executeQuery();

            ObjectMapper<VoteUser> objectMapper = new ObjectMapper<>(VoteUser.class);
            VoteUser voteUser = objectMapper.map(rs);

            if(voteUser == null) {
                voteUser = new VoteUser(target, 0);
            }
            plugin.getUserManager().addUser(voteUser);

            Votes.getPlugin().getLogger().info("Pulled user " + Bukkit.getPlayer(voteUser.getVoter()).getName() + " with " + voteUser.getVotes() + " vote(s)!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushUser(final UUID target) {
        VoteUser voteUser = Votes.getPlugin().getUserManager().getUser(target);

        try {
            //
            PreparedStatement stmt = connectionSource.prepareStatement("INSERT INTO users (uuid, votes) VALUES (?, ?) ON DUPLICATE KEY UPDATE votes = ?");
            stmt.setString(1, voteUser.getVoter().toString());
            stmt.setInt(2, voteUser.getVotes());
            stmt.setInt(3, voteUser.getVotes());

            int rs = stmt.executeUpdate();

            Votes.getPlugin().getLogger().info("Pushed user " + Bukkit.getPlayer(voteUser.getVoter()).getName() + " with " + voteUser.getVotes() + " vote(s)!");
            plugin.getUserManager().removeUser(target);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
