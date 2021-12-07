/*
 * Copyright (c) 2021, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.eintosti.buildsystem.manager;

import com.eintosti.buildsystem.BuildSystem;
import com.eintosti.buildsystem.object.navigator.NavigatorType;
import com.eintosti.buildsystem.object.settings.Colour;
import com.eintosti.buildsystem.object.settings.Settings;
import com.eintosti.buildsystem.object.settings.WorldSort;
import com.eintosti.buildsystem.object.world.BuildWorld;
import com.eintosti.buildsystem.util.config.SettingsConfig;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * @author einTosti
 */
public class SettingsManager {
    private final BuildSystem plugin;
    private final SettingsConfig settingsConfig;
    private final WorldManager worldManager;

    private final Map<UUID, Settings> settings;
    private final Map<UUID, FastBoard> boards;

    public SettingsManager(BuildSystem plugin) {
        this.plugin = plugin;
        this.settingsConfig = new SettingsConfig(plugin);
        this.worldManager = plugin.getWorldManager();

        this.settings = new HashMap<>();
        this.boards = new HashMap<>();
    }

    private void createSettings(UUID uuid) {
        if (!settings.containsKey(uuid)) {
            settings.put(uuid, new Settings());
        }
    }

    public void createSettings(Player player) {
        createSettings(player.getUniqueId());
    }

    public Settings getSettings(UUID uuid) {
        if (settings.get(uuid) == null) {
            createSettings(uuid);
        }
        return settings.get(uuid);
    }

    public Settings getSettings(Player player) {
        return getSettings(player.getUniqueId());
    }

    public void startScoreboard(Player player) {
        if (!plugin.isScoreboard()) return;

        Settings settings = getSettings(player);
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);

        if (!settings.isScoreboard()) {
            stopScoreboard(player, settings);
            return;
        }

        board.updateTitle(plugin.getScoreboardTitle());
        BukkitTask scoreboardTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> updateScoreboard(player, board), 0L, 20L);
        settings.setScoreboardTask(scoreboardTask);
    }

    public void startScoreboard() {
        if (!plugin.isScoreboard()) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(this::startScoreboard);
    }

    public void updateScoreboard(Player player) {
        FastBoard board = this.boards.get(player.getUniqueId());
        if (board != null) {
            updateScoreboard(player, board);
        }
    }

    private void updateScoreboard(Player player, FastBoard board) {
        ArrayList<String> body = new ArrayList<>();

        for (String line : plugin.getScoreboardBody()) {
            body.add(injectPlaceholders(line, player));
        }

        board.updateLines(body);
    }

    private String injectPlaceholders(String originalString, Player player) {
        String worldName = player.getWorld().getName();
        BuildWorld buildWorld = worldManager.getBuildWorld(worldName);

        return originalString
                .replace("%world%", worldName)
                .replace("%status%", plugin.getStatus(buildWorld))
                .replace("%permission%", plugin.getPermission(buildWorld))
                .replace("%project%", plugin.getProject(buildWorld))
                .replace("%creator%", plugin.getCreator(buildWorld))
                .replace("%creation%", plugin.getCreationDate(buildWorld));
    }

    private void stopScoreboard(Player player, Settings settings) {
        BukkitTask scoreboardTask = settings.getScoreboardTask();
        if (scoreboardTask != null) {
            scoreboardTask.cancel();
            settings.setScoreboardTask(null);
        }

        FastBoard board = this.boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public void stopScoreboard(Player player) {
        stopScoreboard(player, getSettings(player));
    }

    public void stopScoreboard() {
        Bukkit.getOnlinePlayers().forEach(this::stopScoreboard);
    }

    public void save() {
        settings.forEach(settingsConfig::saveSettings);
    }

    public void load() {
        FileConfiguration configuration = settingsConfig.getFile();
        ConfigurationSection configurationSection = configuration.getConfigurationSection("settings");
        if (configurationSection == null) return;

        Set<String> uuids = configurationSection.getKeys(false);
        uuids.forEach(uuid -> {
            NavigatorType navigatorType = NavigatorType.valueOf(configuration.getString("settings." + uuid + ".type"));
            Colour glassColor = configuration.getString("settings." + uuid + ".glass") != null ? Colour.valueOf(configuration.getString("settings." + uuid + ".glass")) : Colour.BLACK;
            WorldSort worldSort = WorldSort.matchWorldSort(configuration.getString("settings." + uuid + ".world-sort"));
            boolean clearInventory = configuration.isBoolean("settings." + uuid + ".clear-inventory") && configuration.getBoolean("settings." + uuid + ".clear-inventory");
            boolean disableInteract = configuration.isBoolean("settings." + uuid + ".disable-interact") && configuration.getBoolean("settings." + uuid + ".disable-interact");
            boolean hidePlayers = configuration.isBoolean("settings." + uuid + ".hide-players") && configuration.getBoolean("settings." + uuid + ".hide-players");
            boolean instantPlaceSigns = configuration.isBoolean("settings." + uuid + ".instant-place-signs") && configuration.getBoolean("settings." + uuid + ".instant-place-signs");
            boolean keepNavigator = configuration.isBoolean("settings." + uuid + ".keep-navigator") && configuration.getBoolean("settings." + uuid + ".keep-navigator");
            boolean nightVision = configuration.getBoolean("settings." + uuid + ".nightvision");
            boolean noClip = configuration.isBoolean("settings." + uuid + ".no-clip") && configuration.getBoolean("settings." + uuid + ".no-clip");
            boolean placePlants = configuration.isBoolean("settings." + uuid + ".place-plants") && configuration.getBoolean("settings." + uuid + ".place-plants");
            boolean scoreboard = !configuration.isBoolean("settings." + uuid + ".scoreboard") || configuration.getBoolean("settings." + uuid + ".scoreboard");
            boolean slabBreaking = configuration.isBoolean("settings." + uuid + ".slab-breaking") && configuration.getBoolean("settings." + uuid + ".slab-breaking");
            boolean spawnTeleport = !configuration.isBoolean("settings." + uuid + ".spawn-teleport") || configuration.getBoolean("settings." + uuid + ".spawn-teleport");
            boolean trapDoor = configuration.getBoolean("settings." + uuid + ".trapdoor");

            this.settings.put(UUID.fromString(uuid), new Settings(
                    navigatorType,
                    glassColor,
                    worldSort,
                    clearInventory,
                    disableInteract,
                    hidePlayers,
                    instantPlaceSigns,
                    keepNavigator,
                    nightVision,
                    noClip,
                    placePlants,
                    scoreboard,
                    slabBreaking,
                    spawnTeleport,
                    trapDoor
            ));
        });
    }
}