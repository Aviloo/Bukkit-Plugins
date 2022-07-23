package commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;

public class unsuspect implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("BanList.unsuspect")) return true;
		if (args.length != 1) return false;
		Player player = (Player) sender;
		String PlayerName = args[0];
		Player argPlayer = Bukkit.getServer().getPlayer(PlayerName);
		if (argPlayer == null) {
			player.sendMessage("Данный игрок оффлайн.");
			return true;
		}
		Bukkit.broadcastMessage(argPlayer.getName() + ChatColor.GOLD + " Оправдан!");
		argPlayer.setGameMode(GameMode.SURVIVAL);
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		argPlayer.setScoreboard(scoreboard);
		
		
		
		return true;
	}
}
