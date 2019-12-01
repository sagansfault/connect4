package me.cam.connect4;

import javafx.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    public static List<Game> games = new ArrayList<Game>();

    // Left: yellow, Right: red
    private Pair<UUID, UUID> players;
    private Inventory masterInv;
    private UUID currentTurn;

    public Game(Pair<UUID, UUID> players) {
        this.players = players;
        this.masterInv = initializeInventory();
        this.currentTurn = players.getKey();

        Bukkit.getPlayer(players.getKey()).openInventory(masterInv);
        Bukkit.getPlayer(players.getValue()).openInventory(masterInv);
    }

    public Pair<UUID, UUID> getPlayers() {
        return players;
    }

    public Inventory getMasterInv() {
        return masterInv;
    }

    public UUID getCurrentTurn() {
        return currentTurn;
    }

    public void fillSlotAndUpdate(int slot) {
        this.getMasterInv().setItem(slot, new ItemStack(this.getCurrentTurn().equals(this.getPlayers().getKey()) ?
                Material.YELLOW_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE));
        Bukkit.getPlayer(currentTurn).playSound(Bukkit.getPlayer(currentTurn).getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 2);

        Bukkit.getPlayer(players.getKey()).updateInventory();
        Bukkit.getPlayer(players.getValue()).updateInventory();

        if (GameLogic.hasWon(this, currentTurn.equals(players.getKey()) ?
                Material.YELLOW_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE)) {
            Bukkit.getPlayer(players.getKey()).sendMessage(Bukkit.getPlayer(currentTurn).getDisplayName() + " has won");
            Bukkit.getPlayer(players.getValue()).sendMessage(Bukkit.getPlayer(currentTurn).getDisplayName() + " has won");

            //TODO Close inventory

            games.remove(this);
            return;
        }

        currentTurn = (players.getKey().equals(currentTurn) ? players.getValue() : players.getKey());
    }

    private Inventory initializeInventory() {
        Inventory inv = Bukkit.createInventory(null, 54, "Connect Four");

        // Initialize inventory slots
        for (int i = 0; i < 54; i++) {
            // There is 1 remainder in the bottom right
            inv.setItem(53, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));

            if (i % 9 == 0 || (i + 1) % 9 == 0) {
                inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
            } else {
                inv.setItem(i, new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
            }
        }

        return inv;
    }

    public static Game getGame(UUID uuid) {
        for (Game game : games) {
            if (game.getPlayers().getKey().equals(uuid) ||
                    game.getPlayers().getValue().equals(uuid)) {
                return game;
            }
        }
        return null;
    }

    public boolean slotIsTaken(int slot) {
        if (this.getMasterInv().getItem(slot).getType() == Material.WHITE_STAINED_GLASS_PANE) {
            return false;
        }
        return true;
    }
}