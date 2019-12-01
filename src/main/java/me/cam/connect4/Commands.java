package me.cam.connect4;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (!cmd.getName().equalsIgnoreCase("conn4")) return false;

        if (args.length != 1) {
            sender.sendMessage("Please specify a player: /conn4 <player>");
            return false;
        }

        UUID player2;

        try {
            player2 = Bukkit.getPlayer(args[0]).getUniqueId();
        } catch (Exception e) {
            sender.sendMessage("Couldn't find player");
            return false;
        }

        Game.games.add(new Game(new Pair<>(player.getUniqueId(), player2)));
        // Start Game
        // Game.games.add(new Game());

        return true;
    }
}
