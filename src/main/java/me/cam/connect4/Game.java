package me.cam.connect4;

import jdk.internal.vm.compiler.collections.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    public static List<Game> games = new ArrayList<Game>();

    private Pair<UUID, UUID> players;
    private Inventory masterInv;

    public Game(Pair<UUID, UUID> players) {
        this.players = players;
        this.masterInv = initializeInventory();

        Bukkit.getPlayer(players.getLeft()).openInventory(masterInv);
        Bukkit.getPlayer(players.getRight()).openInventory(masterInv);
    }

    public Pair<UUID, UUID> getPlayers() {
        return players;
    }

    public Inventory getMasterInv() {
        return masterInv;
    }

    private Inventory initializeInventory() {
        Inventory inv = Bukkit.createInventory(null, 56, "Connect Four");

        // Initialize inventory slots
        for (int i = 0; i < 56; i++) {
            if (i % 9 == 0 || i % 8 == 0) {
                inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            } else {
                inv.setItem(i, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
            }
        }

        return inv;
    }

    public static Game getGame(UUID uuid) {
        for (Game game : games) {
            if (game.getPlayers().getLeft().equals(uuid) ||
                    game.getPlayers().getRight().equals(uuid)) {
                return game;
            }
        }
        return null;
    }
}