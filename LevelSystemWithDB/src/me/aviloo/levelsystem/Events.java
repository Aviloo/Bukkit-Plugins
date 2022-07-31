package me.aviloo.levelsystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerQuitEvent;


public class Events implements Listener {

    Main plugin = Main.getPlugin(Main.class);



    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if(!player.hasPlayedBefore()){
            createPlayer(uuid,player);
        }
        if(player.hasPlayedBefore()){
            me.aviloo.levelsystem.LevelSystem.updateValue(uuid,getLevel(uuid));
        }
         // TODO заменить этот метод на получение данных из БД и запись их в хэш-мап
    }
    @EventHandler
    public void onBreaking(BlockBreakEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Integer value = 1;
        me.aviloo.levelsystem.LevelSystem.addValue(uuid,value);
    }
    @EventHandler
    public void onDisconnect(PlayerQuitEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        createPlayer(uuid,player);
    }

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                plugin.getServer().broadcastMessage(ChatColor.GOLD + "Player Found");
                return true;
            }
            plugin.getServer().broadcastMessage(ChatColor.RED + "Player NOT Found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (!(playerExists(uuid))) {
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.table + " (UUID,NAME,LEVEL) VALUES (?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3,me.aviloo.levelsystem.LevelSystem.getValue(uuid));
                insert.executeUpdate();

                plugin.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Integer getLevel(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            System.out.print(results.getInt("LEVEL"));
            return results.getInt("LEVEL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
