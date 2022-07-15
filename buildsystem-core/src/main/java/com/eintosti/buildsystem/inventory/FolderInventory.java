package com.eintosti.buildsystem.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.eintosti.buildsystem.BuildSystem;
import com.eintosti.buildsystem.manager.InventoryManager;
import com.eintosti.buildsystem.util.external.PlayerChatInput;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.stream.IntStream;

public class FolderInventory implements Listener {

    private final BuildSystem plugin;
    private final InventoryManager inventoryManager;
    private String folderName;

    public FolderInventory(BuildSystem plugin) {
        this.plugin = plugin;
        this.inventoryManager = plugin.getInventoryManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Inventory createInvetory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, plugin.getString("folder_title"));
        fillGuiWithGlass(inventory);

        //inventoryManager.addItemStack(inventory, 30, XMaterial.LIME_DYE, "hurensohn");
        //inventoryManager.addItemStack(inventory, 32, XMaterial.RED_DYE, "hurensohn");
        inventoryManager.addItemStack(inventory, 22, XMaterial.ANVIL, "hurensohn");

        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(createInvetory(player));
    }

    private void fillGuiWithGlass(Inventory inventory) {
        IntStream.rangeClosed(0, 8).forEach(i -> inventoryManager.addItemStack(inventory, i, XMaterial.BLACK_STAINED_GLASS_PANE, "§f"));
        IntStream.rangeClosed(45, 53).forEach(i -> inventoryManager.addItemStack(inventory, i, XMaterial.BLACK_STAINED_GLASS_PANE, "§f"));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!inventoryManager.checkIfValidClick(event, "folder_title")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        switch (event.getSlot()) {
            case 22:
                XSound.BLOCK_DEEPSLATE_PLACE.play(player);
                getFolderName(player);
                return;
            case 30:
                XSound.ENTITY_PLAYER_LEVELUP.play(player);
                player.closeInventory();
                return;
            case 32:
                XSound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR.play(player);
                player.closeInventory();
        }
    }

    public void getFolderName(Player player) {
        new PlayerChatInput(plugin, player, "enter_folder_name", input -> {
            for (String charString : input.split("")) {
                if (charString.matches("[^A-Za-z\\d/_-]")) {
                    player.sendMessage(plugin.getString("folder_creation_invalid_characters"));
                    break;
                }
            }

            String folderName = input.replaceAll("[^A-Za-z\\d/_-]", "").replace(" ", "_").trim();
            if (folderName.isEmpty()) {
                player.sendMessage(plugin.getString("folder_creation_name_blank"));
                return;
            }

            player.closeInventory();
            this.folderName = folderName;
            createFolder(player, folderName);
        });

    }

    private void createFolder(Player player, String folderName) {
        player.sendMessage("#createFolder()");
        player.sendMessage(folderName);
    }

}
