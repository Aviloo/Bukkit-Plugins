package commands;
import MainLib.main;
import net.md_5.bungee.api.ChatColor;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class suspect implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("BanList.suspect")) return true;
		if (args.length != 1) return false;
		Player player = (Player) sender;
		String PlayerName = args[0];
		Player argPlayer = Bukkit.getServer().getPlayer(PlayerName);
		if (argPlayer == null) {
			player.sendMessage("Данный игрок оффлайн.");
			return true;
		}
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective objective = scoreboard.registerNewObjective(ChatColor.WHITE + "Внимание", "nn");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = objective.getScore("Вас заподозрили в");
		score.setScore(7);
		Score score4 = objective.getScore("нарушении правил проекта");
		score4.setScore(6);
		Score score5 = objective.getScore(" ");
		score5.setScore(5);
		Score scoresec = objective.getScore(ChatColor.RED + "Не выходите с сервера");
		scoresec.setScore(4);
		Score score6 = objective.getScore(" ");
		score6.setScore(3);
		Score scorethird = objective.getScore("Модерация проверит вас");
		scorethird.setScore(2);
		Score score7 = objective.getScore("в скором времени");
		score7.setScore(1);
		argPlayer.setScoreboard(scoreboard);
		Bukkit.broadcastMessage(argPlayer.getName() + ChatColor.GOLD + " Заподозрен в нарушении правил!");
		argPlayer.playSound(argPlayer.getLocation(), Sound.ENTITY_PLAYER_DEATH, 100, 100);
		argPlayer.sendTitle("ВНИМАНИЕ", "Вас заподозрили в нарушении правил");
		argPlayer.setGameMode(GameMode.ADVENTURE);
		return true;
	}

}
