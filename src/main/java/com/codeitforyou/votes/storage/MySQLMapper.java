package com.codeitforyou.votes.storage;

import com.codeitforyou.votes.Votes;
import com.codeitforyou.votes.storage.util.ObjectMapper;
import com.codeitforyou.votes.storage.util.VoteUser;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.List;

public class MySQLMapper {

    private Connection connectionSource;

    public MySQLMapper(String prefix, String host, int port, String database, String username, String password) {
        try {
            connectionSource = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }

    public void loadUser() {
        try {
            Statement stmt = connectionSource.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");

            ObjectMapper<VoteUser> objectMapper = new ObjectMapper<>(VoteUser.class);
            List<VoteUser> voteUser = objectMapper.map(rs);

            voteUser.forEach(user -> {
                Votes.getPlugin().getLogger().info("Found user " + Bukkit.getPlayer(user.getVoter()).getName() + " with " + user.getVotes() + "!");
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
