package me.cam.connect4;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GameLogic implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Game game = Game.getGame(event.getPlayer().getUniqueId());

        Player player = null;
        if (event.getPlayer() instanceof Player) player = (Player) event.getPlayer();
        if (player == null) return; // Just for safety

        if (game != null) {
            //TODO close inventory
            Game.games.remove(game);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Game game = Game.getGame(event.getWhoClicked().getUniqueId());

        Player player = null;
        if (event.getWhoClicked() instanceof Player) player = (Player) event.getWhoClicked();
        if (player == null) return; // Just for safety

        if (game == null) return; // If in game
        event.setCancelled(true); // Stop players moving items

        if (!game.getCurrentTurn().equals(event.getWhoClicked().getUniqueId())) return; // Checking if player's turn

        int slot = event.getSlot();

        // Check if player clicks on borders
        if (slot % 9 == 0 || (slot + 1) % 9 == 0) return;

        /*
        This simply gets the slot's column
        and moves up to the highest slot
        in the column so an iteration over
        all the slots beneath it can be made.
         */
        while (slot > 8) {
            slot = slot - 9;
        }

        // Loop through all the slots below the top
        while (!game.slotIsTaken(slot)) {
            // If the starting item is taken, cancel for good measure. Should not run other than once
            if (game.slotIsTaken(slot)) break;

            // If there's no taken spots in a given column
            if (slot + 9 > 53) {
                // Win detection is called inside this function but written in here
                game.fillSlotAndUpdate(slot);
                break;
            }

            // If the next slot is taken then the current one is where the slot should be filled
            if (game.slotIsTaken(slot + 9)) {
                // Fill the color in with a single line if-statement
                // Win detection is called inside this function but written in here
                game.fillSlotAndUpdate(slot);
                break;
            }
            slot += 9;
        }
    }

    public static boolean hasWon(Game game, Material playerColor) {
        // All 8 possible directions a connect-4 can be made.
        int[][] directions = {{1, 0}, {1, -9}, {0, -9}, {-1, -9}, {-1, 0}, {-1, 9}, {0, 9}, {1, 9}};

        for (int[] direction : directions) {
            // The change in x and y per each direction
            int dx = direction[0];
            int dy = direction[1];
            // For every spot
            iteration_loop:
            for (int i = 0; i < 54; i++) {

                if (i % 9 == 0 || (i + 1) % 9 == 0) continue; // Skip borders of inventory
                if (!game.slotIsTaken(i)) continue; // No need to check a square that's not filled in
                if (game.getMasterInv().getItem(i).getType() != playerColor) continue; // Check if the origin square is the right color

                /*
                These are sets of 3 squares in all directions
                around the current square to be checked if
                they are filled.
                 */
                int[] squaresToCheck = {i + dx + dy, i + 2*dx + 2*dy, i + 3*dx + 3*dy};

                /*
                This checks if any of the squares sit on the border line.
                If they do then we need to skip them because wrapping-
                connect fours don't count.
                 */
                for (int sq : squaresToCheck) {

                    // Cant be having no index array out of bounds ;)
                    if (sq > 53 || sq < 0) continue iteration_loop;

                    // If the line passes through or onto a border
                    if (sq % 9 == 0 || (sq + 1) % 9 == 0) continue iteration_loop;

                    // Checks to see if any spot in the checkable spots are the player's icons
                    if (game.getMasterInv().getItem(sq).getType() != playerColor) continue iteration_loop;
                }
                return true;
            }
        }
        return false;
    }
}