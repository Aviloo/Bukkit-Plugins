package MainLib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class BanHandler implements Listener {
	
	main plugin = main.getPlugin(main.class);
	
	
	@EventHandler

	public void ban(PlayerQuitEvent event) {
	   if (event.getPlayer().isBanned()) {
	     Player player = event.getPlayer();
	     createPlayer(player.getUniqueId(), player);
	     
	   }
	}
	
	public boolean playerExists(UUID uuid) {
		try {
			PreparedStatement statement = plugin.getConnection()
					.prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
			statement.setString(1, uuid.toString());

			ResultSet results = statement.executeQuery();
			if (results.next()) {
				plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
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
			if (playerExists(uuid) != true) {
				PreparedStatement insert = plugin.getConnection()
						.prepareStatement("INSERT INTO " + plugin.table + " (UUID,NAME,ADDRESS) VALUES (?,?,?)");
				insert.setString(1, uuid.toString());
				insert.setString(2, player.getName());
				insert.executeUpdate();

				plugin.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
