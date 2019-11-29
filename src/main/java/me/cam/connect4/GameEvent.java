package me.cam.connect4;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GameEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        Game game = Game.getGame(event.getWhoClicked().getUniqueId());
        if (game == null) return;

        int slot = event.getSlot();

        // Get the top slot of the slot's column
        while (slot > 8) {
            slot = slot - 9;
        }

        while (game.getMasterInv().getItem(slot).getData().getItemType() == Material.WHITE_STAINED_GLASS_PANE) {

        }
    }
}