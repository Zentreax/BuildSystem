/*
 * Copyright (c) 2022, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.eintosti.buildsystem.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.eintosti.buildsystem.BuildSystem;
import com.eintosti.buildsystem.manager.InventoryManager;
import com.eintosti.buildsystem.manager.WorldManager;
import com.eintosti.buildsystem.object.world.BuildWorld;
import com.eintosti.buildsystem.object.world.data.WorldStatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * @author einTosti
 */
public class FilteredWorldsInventory extends PaginatedInventory implements Listener {

    private static final int MAX_WORLDS = 36;

    private final BuildSystem plugin;
    private final InventoryManager inventoryManager;
    private final WorldManager worldManager;

    private final String inventoryName;
    private final String noWorldsText;
    private final Visibility visibility;
    private final Set<WorldStatus> validStatus;

    public FilteredWorldsInventory(BuildSystem plugin, String inventoryName, String noWorldsText, Visibility visibility, Set<WorldStatus> validStatus) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        this.worldManager = plugin.getWorldManager();

        this.inventoryName = inventoryName;
        this.noWorldsText = noWorldsText;
        this.visibility = visibility;
        this.validStatus = validStatus;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    protected Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, plugin.getString(inventoryName));

        int numOfPages = (numOfWorlds(player) / MAX_WORLDS) + (numOfWorlds(player) % MAX_WORLDS == 0 ? 0 : 1);
        inventoryManager.fillMultiInvWithGlass(plugin, inventory, player, getInvIndex(player), numOfPages);

        return inventory;
    }

    /**
     * Gets the amount of worlds that are to be displayed in the inventory.
     *
     * @param player The player to show the inventory to
     * @return The amount of worlds
     */
    private int numOfWorlds(Player player) {
        return (int) worldManager.getBuildWorlds().stream()
                .filter(buildWorld -> isValidWorld(player, buildWorld))
                .count();
    }

    /**
     * Gets the visibility of the worlds that will be displayed.
     *
     * @return The visibility of the worlds that will be displayed
     */
    protected Visibility getVisibility() {
        return visibility;
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory(player));
    }

    private Inventory getInventory(Player player) {
        addWorlds(player);
        return inventories[getInvIndex(player)];
    }

    private void addWorlds(Player player) {
        int numWorlds = numOfWorlds(player);
        int numInventories = (numWorlds % MAX_WORLDS == 0 ? numWorlds : numWorlds + 1) != 0 ? (numWorlds % MAX_WORLDS == 0 ? numWorlds : numWorlds + 1) : 1;

        inventories = new Inventory[numInventories];
        Inventory inventory = createInventory(player);

        int index = 0;
        inventories[index] = inventory;
        if (numWorlds == 0) {
            inventoryManager.addUrlSkull(inventory, 22, plugin.getString(noWorldsText), "https://textures.minecraft.net/texture/2e3f50ba62cbda3ecf5479b62fedebd61d76589771cc19286bf2745cd71e47c6");
            return;
        }

        int columnWorld = 9, maxColumnWorld = 44;
        for (BuildWorld buildWorld : inventoryManager.sortWorlds(worldManager, plugin.getSettingsManager().getSettings(player))) {
            if (isValidWorld(player, buildWorld)) {
                inventoryManager.addWorldItem(player, inventory, columnWorld++, buildWorld);
            }

            if (columnWorld > maxColumnWorld) {
                columnWorld = 9;
                inventory = createInventory(player);
                inventories[++index] = inventory;
            }
        }
    }

    /**
     * Gets if the world should be shown to the player in the navigator.
     * <p>
     * The following logic is applied to determine the above:
     * <ul>
     *   <li>Does the player have the admin bypass permission?</li>
     *   <li>Is the player the creator of the world?</li>
     *   <li>Has the player been added as a builder?</li>
     *   <li>Does the player have the permission to see the world?</li>
     * </ul>
     *
     * @param player     The player who the world will be shown to
     * @param buildWorld The world to show
     * @return {@code true} if the world should be shown to the player in the navigator, {@code false} otherwise
     */
    private boolean isValidWorld(Player player, BuildWorld buildWorld) {
        if (!worldManager.isCorrectVisibility(buildWorld, visibility)) {
            return false;
        }

        if (!validStatus.contains(buildWorld.getStatus())) {
            return false;
        }

        if (player.hasPermission(BuildSystem.ADMIN_PERMISSION)) {
            return true;
        }

        if (buildWorld.isCreator(player) || buildWorld.isBuilder(player)) {
            return true;
        }

        String worldPermission = buildWorld.getPermission();
        if (!worldPermission.equalsIgnoreCase("-") && !player.hasPermission(worldPermission)) {
            return false;
        }

        return Bukkit.getWorld(buildWorld.getName()) != null || !buildWorld.isLoaded();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!inventoryManager.checkIfValidClick(event, inventoryName)) {
            return;
        }

        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Material itemType = itemStack.getType();

        if (itemType == XMaterial.PLAYER_HEAD.parseMaterial()) {
            switch (event.getSlot()) {
                case 45:
                    decrementInv(player);
                    XSound.ENTITY_CHICKEN_EGG.play(player);
                    openInventory(player);
                    return;
                case 47:
                    XSound.ENTITY_CHICKEN_EGG.play(player);
                    plugin.getCreateInventory().openInventory(player, CreateInventory.Page.PREDEFINED, visibility);
                    return;
                case 49:
                    if (this.inventoryName.matches("private_title")) {
                        XSound.ENTITY_CHICKEN_EGG.play(player);
                        plugin.getCreateInventory().openInventory(player, CreateInventory.Page.PREDEFINED, visibility);
                        return;
                    }
                    XSound.ENTITY_CHICKEN_EGG.play(player);
                    plugin.getFolderInventory().getFolderName(player);
                    return;
                case 53:
                    incrementInv(player);
                    XSound.ENTITY_CHICKEN_EGG.play(player);
                    openInventory(player);
                    return;
            }
        }

        inventoryManager.manageInventoryClick(event, player, itemStack);
    }

    public enum Visibility {
        PRIVATE,
        PUBLIC,
        IGNORE;

        public static Visibility matchVisibility(boolean isPrivateWorld) {
            return isPrivateWorld ? PRIVATE : PUBLIC;
        }
    }
}