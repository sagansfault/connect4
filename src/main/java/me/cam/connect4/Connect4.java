package me.cam.connect4;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Connect4 extends JavaPlugin {

    private static Connect4 instance;
    public static Connect4 getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("conn4").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new GameLogic(), this);
    }

    @Override
    public void onDisable() {

    }
}