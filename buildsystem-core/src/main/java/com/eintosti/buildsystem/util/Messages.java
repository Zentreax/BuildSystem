/*
 * Copyright (c) 2022, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.eintosti.buildsystem.util;

import com.eintosti.buildsystem.BuildSystem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author einTosti
 */
public class Messages {

    private final BuildSystem plugin;
    private final Map<String, String> messageData;

    public Messages(BuildSystem plugin) {
        this.plugin = plugin;
        this.messageData = new HashMap<>();
    }

    public void createMessageFile() {
        File file = new File(plugin.getDataFolder() + File.separator + "messages.yml");
        try {
            if (file.createNewFile()) {
                plugin.getLogger().info("Created messages.yml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        StringBuilder sb = new StringBuilder();

        addLine(sb, "# ██████╗ ██╗   ██╗██╗██╗     ██████╗ ███████╗██╗   ██╗███████╗████████╗███████╗███╗   ███╗");
        addLine(sb, "# ██╔══██╗██║   ██║██║██║     ██╔══██╗██╔════╝╚██╗ ██╔╝██╔════╝╚══██╔══╝██╔════╝████╗ ████║");
        addLine(sb, "# ██████╔╝██║   ██║██║██║     ██║  ██║███████╗ ╚████╔╝ ███████╗   ██║   █████╗  ██╔████╔██║");
        addLine(sb, "# ██╔══██╗██║   ██║██║██║     ██║  ██║╚════██║  ╚██╔╝  ╚════██║   ██║   ██╔══╝  ██║╚██╔╝██║");
        addLine(sb, "# ██████╔╝╚██████╔╝██║███████╗██████╔╝███████║   ██║   ███████║   ██║   ███████╗██║ ╚═╝ ██║");
        addLine(sb, "# ╚═════╝  ╚═════╝ ╚═╝╚══════╝╚═════╝ ╚══════╝   ╚═╝   ╚══════╝   ╚═╝   ╚══════╝╚═╝     ╚═╝");
        addLine(sb, "");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# Messages");
        addLine(sb, "# ---------");
        setMessage(sb, config, "prefix", "&8▎ &bBuildSystem &8»");
        setMessage(sb, config, "player_join", "&7[&a+&7] &a%player%");
        setMessage(sb, config, "player_quit", "&7[&c-&7] &c%player%");
        setMessage(sb, config, "loading_world", "&7Loading &b%world%&7...");
        setMessage(sb, config, "world_not_loaded", "&cWorld is not loaded!");
        setMessage(sb, config, "enter_world_name", "&7Enter &bWorld Name");
        setMessage(sb, config, "enter_generator_name", "&7Enter &bGenerator Name");
        setMessage(sb, config, "enter_world_creator", "&7Enter &bWorld Creator");
        setMessage(sb, config, "enter_world_permission", "&7Enter &bPermission");
        setMessage(sb, config, "enter_world_project", "&7Enter &bProject");
        setMessage(sb, config, "enter_player_name", "&7Enter &bPlayer Name");
        setMessage(sb, config, "cancel_subtitle", "&7Type &ccancel &7to cancel");
        setMessage(sb, config, "input_cancelled", "%prefix% &cInput cancelled!");
        setList(sb, config, "update_available", Arrays.asList("%prefix% &7Great! A new update is available &8[&bv%new_version%&8]", " &8➥ &7Your current version: &bv%current_version%"));
        setMessage(sb, config, "command_archive_world", "%prefix% &cYou can't use that command here!");
        setMessage(sb, config, "command_not_builder", "%prefix% &cOnly builders can use that command!");
        addLine(sb, "");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# Scoreboard");
        addLine(sb, "# ---------");
        setMessage(sb, config, "title", "&b&lBuildSystem");
        setList(sb, config, "body", Arrays.asList(
                "&7&m                     &8",
                "&7World:",
                " &b%world%",
                " ",
                "&7Status:",
                " %status%",
                "&7&m                     &7"
        ));
        addLine(sb, "");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# Commands");
        addLine(sb, "# ---------");
        setMessage(sb, config, "sender_not_player", "You have to be a player to use this command!");
        setMessage(sb, config, "no_permissions", "%prefix% &cNot enough permissions.");
        addLine(sb, "");
        addLine(sb, "# /back");
        setMessage(sb, config, "back_usage", "%prefix% &7Usage: &b/back");
        setMessage(sb, config, "back_teleported", "%prefix% &7You were teleported to your &bprevious location&7.");
        setMessage(sb, config, "back_failed", "%prefix% &cNo previous location was found.");
        addLine(sb, "");
        addLine(sb, "# /build");
        setMessage(sb, config, "build_usage", "%prefix% &7Usage: &b/build [player]");
        setMessage(sb, config, "build_player_not_found", "%prefix% &cThat player was not found.");
        setMessage(sb, config, "build_activated_self", "%prefix% &7Build mode was &aactivated&7.");
        setMessage(sb, config, "build_activated_other_sender", "%prefix% &7Build mode &8[&7for %target%&8] &7was &aactivated&7.");
        setMessage(sb, config, "build_activated_other_target", "%prefix% &7Build mode was &aactivated &8[&7by %sender%&8]&7.");
        setMessage(sb, config, "build_deactivated_self", "%prefix% &7Build mode was &cdeactivated&7.");
        setMessage(sb, config, "build_deactivated_other_sender", "%prefix% &7Build mode &8[&7for %target%&8] &7was &cdeactivated&7.");
        setMessage(sb, config, "build_deactivated_other_target", "%prefix% &7Build mode was &cdeactivated &8[&7by %sender%&8]&7.");
        addLine(sb, "");
        addLine(sb, "# /buildSystem");
        setMessage(sb, config, "buildsystem_title", "%prefix% &7&nBuildSystem Help:");
        setMessage(sb, config, "buildsystem_back", "&7Teleport to your previous location.");
        setMessage(sb, config, "buildsystem_blocks", "&7Opens a menu with secret blocks.");
        setMessage(sb, config, "buildsystem_build", "&7Puts you into 'build mode'.");
        setMessage(sb, config, "buildsystem_config", "&7Reload the config.");
        setMessage(sb, config, "buildsystem_day", "&7Set a world's time to daytime.");
        setMessage(sb, config, "buildsystem_explosions", "&7Toggle explosions.");
        setMessage(sb, config, "buildsystem_gamemode", "&7Change your gamemode.");
        setMessage(sb, config, "buildsystem_night", "&7Set a world's time to nighttime.");
        setMessage(sb, config, "buildsystem_noai", "&7Toggle entity AIs.");
        setMessage(sb, config, "buildsystem_physics", "&7Toggle block physics.");
        setMessage(sb, config, "buildsystem_settings", "&7Manage user settings.");
        setMessage(sb, config, "buildsystem_setup", "&7Change the default items when creating worlds.");
        setMessage(sb, config, "buildsystem_skull", "&7Receive a player or custom skull.");
        setMessage(sb, config, "buildsystem_speed", "&7Change your flying/walking speed.");
        setMessage(sb, config, "buildsystem_spawn", "&7Teleport to the spawn.");
        setMessage(sb, config, "buildsystem_top", "&7Teleport to the the highest location above you.");
        setMessage(sb, config, "buildsystem_worlds", "&7An overview of all &o/worlds &7commands.");
        addLine(sb, "");
        addLine(sb, "# /config");
        setMessage(sb, config, "config_usage", "%prefix% &7Usage: &b/config reload");
        setMessage(sb, config, "config_reloaded", "%prefix% &7The config was reloaded.");
        addLine(sb, "");
        addLine(sb, "# /explosions");
        setMessage(sb, config, "explosions_usage", "%prefix% &7Usage: &b/explosions <world>");
        setMessage(sb, config, "explosions_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "explosions_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "explosions_activated", "%prefix% &7Explosions in &b%world% &7were &aactivated&7.");
        setMessage(sb, config, "explosions_deactivated", "%prefix% &7Explosions in &b%world% &7were &cdeactivated&7.");
        setMessage(sb, config, "explosions_deactivated_in_world", "%prefix% &7Explosions in &b%world% &7are currently &cdeactivated&7.");
        addLine(sb, "");
        addLine(sb, "# /gamemode");
        setMessage(sb, config, "gamemode_usage", "%prefix% &7Usage: &b/gamemode <mode> [player]");
        setMessage(sb, config, "gamemode_player_not_found", "%prefix% &cThat player was not found.");
        setMessage(sb, config, "gamemode_survival", "Survival");
        setMessage(sb, config, "gamemode_creative", "Creative");
        setMessage(sb, config, "gamemode_adventure", "Adventure");
        setMessage(sb, config, "gamemode_spectator", "Spectator");
        setMessage(sb, config, "gamemode_set_self", "%prefix% &7Your gamemode was set to &b%gamemode%&7.");
        setMessage(sb, config, "gamemode_set_other", "%prefix% &b%target%&7's gamemode was set to &b%gamemode%&7.");
        addLine(sb, "");
        addLine(sb, "# /physics");
        setMessage(sb, config, "physics_usage", "%prefix% &7Usage: &b/physics <world>");
        setMessage(sb, config, "physics_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "physics_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "physics_activated", "%prefix% &7Physics in &b%world% &7were &aactivated&7.");
        setMessage(sb, config, "physics_activated_all", "%prefix% &7Physics in &ball worlds &7were &aactivated&7.");
        setMessage(sb, config, "physics_deactivated", "%prefix% &7Physics in &b%world% &7were &cdeactivated&7.");
        setMessage(sb, config, "physics_deactivated_in_world", "%prefix% &7Physics in &b%world% &7are currently &cdeactivated&7.");
        addLine(sb, "");
        addLine(sb, "# /noAI");
        setMessage(sb, config, "noai_usage", "%prefix% &7Usage: &b/noai <world>");
        setMessage(sb, config, "noai_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "noai_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "noai_deactivated", "%prefix% &7Entity AIs in &b%world% &7were &aactivated&7.");
        setMessage(sb, config, "noai_activated", "%prefix% &7Entity AIs in &b%world% &7were &cdeactivated&7.");
        setMessage(sb, config, "noai_activated_in_world", "%prefix% &7Entity AIs in &b%world% &7are currently &cdeactivated&7.");
        addLine(sb, "");
        addLine(sb, "# /skull");
        setMessage(sb, config, "skull_usage", "%prefix% &7Usage: &b/skull [name]");
        setMessage(sb, config, "skull_player_received", "%prefix% &7You received the skull of &b%player%&7.");
        setMessage(sb, config, "skull_custom_received", "%prefix% &7You received a &bcustom skull&7.");
        addLine(sb, "");
        addLine(sb, "# /spawn");
        setMessage(sb, config, "spawn_usage", "%prefix% &7Usage: &b/spawn");
        setMessage(sb, config, "spawn_admin", "%prefix% &7Usage: &b/spawn [set/remove]");
        setMessage(sb, config, "spawn_teleported", "%prefix% &7You were teleported to the &bspawn&7.");
        setMessage(sb, config, "spawn_unavailable", "%prefix% &cThere isn't a spawn to teleport to.");
        setMessage(sb, config, "spawn_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "spawn_set", "%prefix% &7Spawn set to &b%x% %y% %z% &7in &b%world%&7.");
        setMessage(sb, config, "spawn_remove", "%prefix% &7The spawn was removed.");
        addLine(sb, "");
        addLine(sb, "# /speed");
        setMessage(sb, config, "speed_usage", "%prefix% &7Usage: &b/speed [1-5]");
        setMessage(sb, config, "speed_set_flying", "%prefix% &7Your flying speed was set to &b%speed%&7.");
        setMessage(sb, config, "speed_set_walking", "%prefix% &7Your walking speed was set to &b%speed%&7.");
        addLine(sb, "");
        addLine(sb, "# /day");
        setMessage(sb, config, "day_usage", "%prefix% &7Usage: &b/day [world]");
        setMessage(sb, config, "day_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "day_set", "%prefix% &7It is now day in &b%world%&7.");
        addLine(sb, "");
        addLine(sb, "# /night");
        setMessage(sb, config, "night_usage", "%prefix% &7Usage: &b/night [world]");
        setMessage(sb, config, "night_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "night_set", "%prefix% &7It is now night in &b%world%&7.");
        addLine(sb, "");
        addLine(sb, "# /top");
        setMessage(sb, config, "top_usage", "%prefix% &7Usage: &b/top");
        setMessage(sb, config, "top_teleported", "%prefix% &7You were teleported to the &btop&7.");
        setMessage(sb, config, "top_failed", "%prefix% &cNo higher location was found.");
        addLine(sb, "");
        addLine(sb, "# /worlds");
        setMessage(sb, config, "worlds_addbuilder_usage", "%prefix% &7Usage: &b/worlds addBuilder <world>");
        setMessage(sb, config, "worlds_addbuilder_error", "%prefix% &cError: Please try again!");
        setMessage(sb, config, "worlds_addbuilder_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_addbuilder_player_not_found", "%prefix% &cThat player was not found.");
        setMessage(sb, config, "worlds_addbuilder_not_creator", "%prefix% &cYou are not the creator of this world.");
        setMessage(sb, config, "worlds_addbuilder_already_creator", "%prefix% &cYou are already the creator.");
        setMessage(sb, config, "worlds_addbuilder_already_added", "%prefix% &cThis player is already a builder.");
        setMessage(sb, config, "worlds_addbuilder_added", "%prefix% &b%builder% &7was &aadded &7as a builder.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_builders_usage", "%prefix% &7Usage: &b/worlds builders <world>");
        setMessage(sb, config, "worlds_builders_unknown_world", "%prefix% &cUnknown world.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_world_name", "World name");
        setMessage(sb, config, "worlds_world_exists", "%prefix% &cThis world already exists.");
        setMessage(sb, config, "worlds_world_creation_invalid_characters", "%prefix% &7&oRemoved invalid characters from world name.");
        setMessage(sb, config, "worlds_world_creation_name_bank", "%prefix% &cThe world name cannot be blank.");
        setMessage(sb, config, "worlds_world_creation_started", "%prefix% &7The creation of &b%world% &8(&7Type: &f%type%&8) &7has started...");
        setMessage(sb, config, "worlds_template_creation_started", "%prefix% &7The creation of &b%world% &8(&7Template: &f%template%&8) &7has started...");
        setMessage(sb, config, "worlds_creation_finished", "%prefix% &7The world was &asuccessfully &7created.");
        setMessage(sb, config, "worlds_template_does_not_exist", "%prefix% &cThis template does not exist.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_unknown_command", "%prefix% &7Unknown command: &b/worlds help");
        setMessage(sb, config, "worlds_navigator_open", "%prefix% &cYou have already opened the navigator!");
        addLine(sb, "");
        setMessage(sb, config, "worlds_delete_usage", "%prefix% &7Usage: &b/worlds delete <world>");
        setMessage(sb, config, "worlds_delete_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_delete_unknown_directory", "%prefix% &cError while deleting world: Directory not found!");
        setMessage(sb, config, "worlds_delete_not_creator", "%prefix% &cYou are not the creator of this world.");
        setMessage(sb, config, "worlds_delete_error", "%prefix% &cError while deleting world: Please try again!");
        setMessage(sb, config, "worlds_delete_canceled", "%prefix% &7The deletion of &b%world% &7was canceled.");
        setMessage(sb, config, "worlds_delete_started", "%prefix% &7The deletion of &b%world% &7has started...");
        setMessage(sb, config, "worlds_delete_finished", "%prefix% &7The world was &asuccessfully &7deleted.");
        setMessage(sb, config, "worlds_delete_players_world", "%prefix% &7&oThe world you were in was deleted.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_edit_usage", "%prefix% &7Usage: &b/worlds edit <world>");
        setMessage(sb, config, "worlds_edit_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_edit_error", "%prefix% &cError: Please try again.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_help_usage", "%prefix% &7Usage: &b/worlds help [page]");
        setMessage(sb, config, "worlds_help_invalid_page", "%prefix% &cInvalid page.");
        setMessage(sb, config, "worlds_help_title_with_page", "%prefix% &7&nWorlds Help:&8 (&7%page%/%max%&8)");
        setMessage(sb, config, "worlds_help_permission", "&7&nPermission&8: &b%permission%");
        setMessage(sb, config, "worlds_help_help", "&7Shows the list of all subcommands.");
        setMessage(sb, config, "worlds_help_info", "&7Shows information about a world.");
        setMessage(sb, config, "worlds_help_item", "&7Receive the 'World Navigator'.");
        setMessage(sb, config, "worlds_help_tp", "&7Teleport to another world.");
        setMessage(sb, config, "worlds_help_edit", "&7Opens the world editor.");
        setMessage(sb, config, "worlds_help_addbuilder", "&7Add a builder to a world.");
        setMessage(sb, config, "worlds_help_removebuilder", "&7Remove a builder from a &7world.");
        setMessage(sb, config, "worlds_help_builders", "&7Opens a world's list of builders.");
        setMessage(sb, config, "worlds_help_rename", "&7Rename an existing world.");
        setMessage(sb, config, "worlds_help_setitem", "&7Set a world's item.");
        setMessage(sb, config, "worlds_help_setcreator", "&7Set a world's creator.");
        setMessage(sb, config, "worlds_help_setproject", "&7Set a world's project.");
        setMessage(sb, config, "worlds_help_setpermission", "&7Set a world's permission.");
        setMessage(sb, config, "worlds_help_setstatus", "&7Set a world's status.");
        setMessage(sb, config, "worlds_help_setspawn", "&7Set a world's spawnpoint.");
        setMessage(sb, config, "worlds_help_removespawn", "&7Removes a world's spawnpoint.");
        setMessage(sb, config, "worlds_help_delete", "&7Delete a world.");
        setMessage(sb, config, "worlds_help_import", "&7Import a world.");
        setMessage(sb, config, "worlds_help_importall", "&7Import all worlds at once.");
        setMessage(sb, config, "worlds_help_unimport", "&7Unimport a world.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_import_usage", "%prefix% &7Usage: &b/worlds import <world> [-g <generator>]");
        setMessage(sb, config, "worlds_import_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_import_world_is_imported", "%prefix% &7This world is already imported.");
        setMessage(sb, config, "worlds_import_unknown_generator", "%prefix% &cUnknown generator.");
        setMessage(sb, config, "worlds_import_started", "%prefix% &7The import of &b%world% &7has started...");
        setMessage(sb, config, "worlds_import_invalid_character", "%prefix% &7Unable to import &c%world%&7.\n" +
                "%prefix% &7&oName contains invalid character: &c%char%");
        setMessage(sb, config, "worlds_import_finished", "%prefix% &7The world was &asuccessfully &7imported.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_importall_usage", "%prefix% &7Usage: &b/worlds importall");
        setMessage(sb, config, "worlds_importall_no_worlds", "%prefix% &cNo worlds were found.");
        setMessage(sb, config, "worlds_importall_started", "%prefix% &7Beginning import of &b%amount% &7worlds...");
        setMessage(sb, config, "worlds_importall_delay", "%prefix% &8➥ &7Delay between each world: &b%delay%s&7.");
        setMessage(sb, config, "worlds_importall_invalid_character", "%prefix% &c✘ &7&o%world% &7contains invalid character &8(&c%char%&8)");
        setMessage(sb, config, "worlds_importall_world_already_imported", "%prefix% &c&l✗ &7World already imported: &b%world%");
        setMessage(sb, config, "worlds_importall_world_imported", "%prefix% &a✔ &7World imported: &b%world%");
        setMessage(sb, config, "worlds_importall_finished", "%prefix% &7All worlds have been &asuccessfully &7imported.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_info_usage", "%prefix% &7Usage: &b/worlds info [world]");
        setMessage(sb, config, "worlds_info_unknown_world", "%prefix% &cUnknown world.");
        setList(sb, config, "world_info", Arrays.asList(
                "&7&m-------------------------------------",
                "%prefix% &7&nWorld info:&b %world%",
                " ",
                " &8- &7Creator: &b%creator%",
                " &8- &7Type: &b%type%",
                " &8- &7Private: &b%private%",
                " &8- &7Builders enabled: &b%builders_enabled%",
                " &8- &7Builders: &b%builders%",
                " &8- &7Item: &b%item%",
                " &8- &7Status: &b%status%",
                " &8- &7Project: &b%project%",
                " &8- &7Permission: &b%permission%",
                " &8- &7Time: &b%time%",
                " &8- &7Creation date: &b%creation%",
                " &8- &7Physics: &b%physics%",
                " &8- &7Explosions: &b%explosions%",
                " &8- &7Block breaking: &b%block_breaking%",
                " &8- &7Block placement: &b%block_placement%",
                " &8- &7MobAI: &b%mobai%",
                " &8- &7Custom spawn: &b%custom_spawn%",
                "&7&m-------------------------------------"));
        addLine(sb, "");
        setMessage(sb, config, "worlds_item_receive", "%prefix% &7You received the &bNavigator&7.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_removebuilder_usage", "%prefix% &7Usage: &b/worlds removeBuilder <world>");
        setMessage(sb, config, "worlds_removebuilder_error", "%prefix% &cError: Please try again!");
        setMessage(sb, config, "worlds_removebuilder_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_removebuilder_player_not_found", "%prefix% &cThat player was not found.");
        setMessage(sb, config, "worlds_removebuilder_not_creator", "%prefix% &cYou are not the creator of this world.");
        setMessage(sb, config, "worlds_removebuilder_not_yourself", "%prefix% &cYou cannot remove yourself as creator.");
        setMessage(sb, config, "worlds_removebuilder_not_builder", "%prefix% &cThis player is not a builder.");
        setMessage(sb, config, "worlds_removebuilder_removed", "%prefix% &b%builder% &7was &cremoved &7as a builder.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_rename_usage", "%prefix% &7Usage: &b/worlds rename <world>");
        setMessage(sb, config, "worlds_rename_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_rename_error", "%prefix% &cPlease try again.");
        setMessage(sb, config, "worlds_rename_same_name", "%prefix% &cThis is the world's current name.");
        setMessage(sb, config, "worlds_rename_set", "%prefix% &b%oldName% &7was successfully renamed to &b%newName%&7.");
        setMessage(sb, config, "worlds_rename_players_world", "%prefix% &7&oThe world you are in is being renamed...");
        addLine(sb, "");
        setMessage(sb, config, "worlds_setitem_usage", "%prefix% &7Usage: &b/worlds setItem <world>");
        setMessage(sb, config, "worlds_setitem_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_setitem_hand_empty", "%prefix% &cYou do not have an item in your hand.");
        setMessage(sb, config, "worlds_setitem_set", "%prefix% &b%world%&7's item was successfully changed.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_setcreator_usage", "%prefix% &7Usage: &b/worlds setCreator <world>");
        setMessage(sb, config, "worlds_setcreator_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_setcreator_error", "%prefix% &cPlease try again.");
        setMessage(sb, config, "worlds_setcreator_set", "%prefix% &b%world%&7's creator was successfully changed.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_setproject_usage", "%prefix% &7Usage: &b/worlds setProject <world>");
        setMessage(sb, config, "worlds_setproject_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_setproject_error", "%prefix% &cPlease try again.");
        setMessage(sb, config, "worlds_setproject_set", "%prefix% &b%world%&7's project was successfully changed.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_setstatus_usage", "%prefix% &7Usage: &b/worlds setStatus <world>");
        setMessage(sb, config, "worlds_setstatus_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_setstatus_error", "%prefix% &cPlease try again.");
        setMessage(sb, config, "worlds_setstatus_set", "%prefix% &b%world%&7's status was was changed to: %status%&7.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_setpermission_usage", "%prefix% &7Usage: &b/worlds setPermission <world>");
        setMessage(sb, config, "worlds_setpermission_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_setpermission_error", "%prefix% &cPlease try again.");
        setMessage(sb, config, "worlds_setpermission_set", "%prefix% &b%world%&7's permission was successfully changed.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_setspawn_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "worlds_setspawn_world_spawn_set", "%prefix% &b%world%&7's spawnpoint was set.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_removespawn_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "worlds_removespawn_world_spawn_removed", "%prefix% &b%world%&7's spawnpoint was removed.");
        addLine(sb, "");
        setMessage(sb, config, "worlds_tp_usage", "%prefix% &7Usage: &b/worlds tp <world>");
        setMessage(sb, config, "worlds_tp_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_tp_world_not_imported", "%prefix% &cWorld must be imported » /worlds import <world>");
        setMessage(sb, config, "worlds_tp_entry_forbidden", "%prefix% &cYou are not allowed to enter this world!");
        addLine(sb, "");
        setMessage(sb, config, "worlds_unimport_usage", "%prefix% &7Usage: &b/worlds import <world> [-g <generator>]");
        setMessage(sb, config, "worlds_unimport_unknown_world", "%prefix% &cUnknown world.");
        setMessage(sb, config, "worlds_unimport_players_world", "%prefix% &7&oThe world you were in was unimported.");
        setMessage(sb, config, "worlds_unimport_finished", "%prefix% &b%world% &7has been &aunimported&7.");
        addLine(sb, "");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# Items");
        addLine(sb, "# ---------");
        setMessage(sb, config, "navigator_item", "&b&lNavigator");
        setMessage(sb, config, "barrier_item", "&c&lClose Inventory");
        setMessage(sb, config, "custom_skull_item", "&bCustom Skull");
        addLine(sb, "");
        addLine(sb, "");
        addLine(sb, "# ---------");
        addLine(sb, "# GUIs");
        addLine(sb, "# ---------");
        addLine(sb, "# Multi-page inventory");
        setMessage(sb, config, "gui_previous_page", "&b« &7Previous Page");
        setMessage(sb, config, "gui_next_page", "&7Next Page &b»");
        addLine(sb, "");
        addLine(sb, "# Old Navigator");
        setMessage(sb, config, "old_navigator_title", "&3» &8Navigator");
        setMessage(sb, config, "old_navigator_world_navigator", "&aWorld Navigator");
        setMessage(sb, config, "old_navigator_world_archive", "&6World Archive");
        setMessage(sb, config, "old_navigator_private_worlds", "&bPrivate Worlds");
        setMessage(sb, config, "old_navigator_settings", "&cSettings");
        addLine(sb, "");
        addLine(sb, "# New Navigator");
        setMessage(sb, config, "new_navigator_world_navigator", "&a&lWORLD NAVIGATOR");
        setMessage(sb, config, "new_navigator_world_archive", "&6&lWORLD ARCHIVE");
        setMessage(sb, config, "new_navigator_private_worlds", "&b&lPRIVATE WORLDS");
        addLine(sb, "");
        addLine(sb, "# World Navigator");
        setMessage(sb, config, "world_navigator_title", "&3» &8World Navigator");
        setMessage(sb, config, "world_navigator_no_worlds", "&c&nNo worlds available");
        setMessage(sb, config, "world_navigator_create_world", "&bCreate World");
        setMessage(sb, config, "world_item_title", "&3&l%world%");
        setList(sb, config, "world_item_lore_normal", Arrays.asList(
                "&7Status&8: %status%",
                "",
                "&7Creator&8: &b%creator%",
                "&7Project&8: &b%project%",
                "&7Permission&8: &b%permission%",
                "",
                "&7Builders&8:",
                "%builders%"
        ));
        setList(sb, config, "world_item_lore_edit", Arrays.asList(
                "&7Status&8: %status%",
                "",
                "&7Creator&8: &b%creator%",
                "&7Project&8: &b%project%",
                "&7Permission&8: &b%permission%",
                "",
                "&7Builders&8:",
                "%builders%",
                "",
                "&8- &7&oLeft click&8: &7Teleport",
                "&8- &7&oRight click&8: &7Edit"
        ));
        setMessage(sb, config, "world_item_builders_builder_template", "&b%builder%&7, ");
        addLine(sb, "");
        addLine(sb, "# World Archive");
        setMessage(sb, config, "archive_title", "&3» &8World Archive");
        setMessage(sb, config, "archive_no_worlds", "&c&nNo worlds available");
        addLine(sb, "");
        addLine(sb, "# Private Worlds");
        setMessage(sb, config, "private_title", "&3» &8Private Worlds");
        setMessage(sb, config, "private_no_worlds", "&c&nNo worlds available");
        setMessage(sb, config, "private_create_world", "&bCreate a private world");
        addLine(sb, "");
        setMessage(sb, config, "folder_title", "&3» &8hurensohn");
        setMessage(sb, config, "enter_folder_name", "&c&nhurensohn");
        setMessage(sb, config, "folder_creation_invalid_characters", "&bhurensohn");
        setMessage(sb, config, "folder_creation_name_blank", "&bhurensohn");
        setMessage(sb, config, "world_navigator_create_folder", "&bhurensohn");
        addLine(sb, "");
        addLine(sb, "# Setup");
        setMessage(sb, config, "setup_title", "&3» &8Setup");
        setMessage(sb, config, "setup_create_item_name", "&bCreate World Item");
        setList(sb, config, "setup_create_item_lore", Arrays.asList("&7The item which is shown", "&7when you create a world.", "", "&7&nTo change&7:", "&8» &7&oDrag new item onto old one"));
        setMessage(sb, config, "setup_default_item_name", "&bDefault Item");
        setList(sb, config, "setup_default_item_lore", Arrays.asList("&7The item which a world", "&7has by default when created.", "", "&7&nTo change&7:", "&8» &7&oDrag new item onto old one"));
        setMessage(sb, config, "setup_status_item_name", "&bStatus Item");
        setList(sb, config, "setup_status_item_name_lore", Arrays.asList("&7The item which is shown when", "&7you change a world's status.", "", "&7&nTo change&7:", "&8» &7&oDrag new item onto old one"));
        setMessage(sb, config, "setup_normal_world", "&bNormal World");
        setMessage(sb, config, "setup_flat_world", "&aFlat World");
        setMessage(sb, config, "setup_nether_world", "&cNether World");
        setMessage(sb, config, "setup_end_world", "&eEnd World");
        setMessage(sb, config, "setup_void_world", "&fEmpty World");
        setMessage(sb, config, "setup_imported_world", "&7Imported World");
        addLine(sb, "");
        addLine(sb, "# Create World");
        setMessage(sb, config, "create_title", "&3» &8Create World");
        setMessage(sb, config, "create_predefined_worlds", "&6Predefined Worlds");
        setMessage(sb, config, "create_templates", "&6Templates");
        setMessage(sb, config, "create_generators", "&6Generators");
        setMessage(sb, config, "create_no_templates", "&c&nNo templates available");
        setMessage(sb, config, "create_normal_world", "&bNormal World");
        setMessage(sb, config, "create_flat_world", "&aFlat World");
        setMessage(sb, config, "create_nether_world", "&cNether World");
        setMessage(sb, config, "create_end_world", "&eEnd World");
        setMessage(sb, config, "create_void_world", "&fEmpty World");
        setMessage(sb, config, "create_template", "&e%template%");
        setMessage(sb, config, "create_generators_create_world", "&eCreate world with generator");
        addLine(sb, "");
        addLine(sb, "# World Type");
        setMessage(sb, config, "type_normal", "Normal");
        setMessage(sb, config, "type_flat", "Flat");
        setMessage(sb, config, "type_nether", "Nether");
        setMessage(sb, config, "type_end", "End");
        setMessage(sb, config, "type_void", "Void");
        setMessage(sb, config, "type_custom", "Custom");
        setMessage(sb, config, "type_template", "Template");
        setMessage(sb, config, "type_private", "Private");
        addLine(sb, "");
        addLine(sb, "# World Status");
        setMessage(sb, config, "status_title", "&8Status &7» &3%world%");
        setMessage(sb, config, "status_not_started", "&cNot Started");
        setMessage(sb, config, "status_in_progress", "&6In Progress");
        setMessage(sb, config, "status_almost_finished", "&aAlmost Finished");
        setMessage(sb, config, "status_finished", "&2Finished");
        setMessage(sb, config, "status_archive", "&9Archive");
        setMessage(sb, config, "status_hidden", "&fHidden");
        addLine(sb, "");
        addLine(sb, "# World Difficulty");
        setMessage(sb, config, "difficulty_peaceful", "&fPeaceful");
        setMessage(sb, config, "difficulty_easy", "&aEasy");
        setMessage(sb, config, "difficulty_normal", "&6Normal");
        setMessage(sb, config, "difficulty_hard", "&cHard");
        addLine(sb, "");
        addLine(sb, "# Delete World");
        setMessage(sb, config, "delete_title", "&3» &8Delete World");
        setMessage(sb, config, "delete_world_name", "&e%world%");
        setList(sb, config, "delete_world_name_lore", Arrays.asList("", "&c&nWarning&c: &7&oOnce a world is", "&7&odeleted it is lost forever", "&7&oand cannot be recovered!"));
        setMessage(sb, config, "delete_world_cancel", "&cCancel");
        setMessage(sb, config, "delete_world_confirm", "&aConfirm");
        addLine(sb, "");
        addLine(sb, "# World Editor");
        setMessage(sb, config, "worldeditor_title", "&3» &8World Editor");
        setMessage(sb, config, "worldeditor_world_item", "&3&l%world%");
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_blockbreaking_item", "&bBlock Breaking");
        setList(sb, config, "worldeditor_blockbreaking_lore", Arrays.asList("&7&oToggle whether or not blocks", "&7&oare able to be broken."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_blockplacement_item", "&bBlock Placement");
        setList(sb, config, "worldeditor_blockplacement_lore", Arrays.asList("&7&oToggle whether or not blocks", "&7&oare able to be placed."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_physics_item", "&bBlock Physics");
        setList(sb, config, "worldeditor_physics_lore", Arrays.asList("&7&oToggle whether or not block", "&7&ophysics are activated."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_time_item", "&bTime");
        setList(sb, config, "worldeditor_time_lore", Arrays.asList("&7&oAlter the time of day", "&7&oin the world.", "", "&7&nCurrently&7: %time%"));
        setMessage(sb, config, "worldeditor_time_lore_sunrise", "&6Sunrise");
        setMessage(sb, config, "worldeditor_time_lore_noon", "&eNoon");
        setMessage(sb, config, "worldeditor_time_lore_night", "&9Night");
        setMessage(sb, config, "worldeditor_time_lore_unknown", "&fUnknown");
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_explosions_item", "&bExplosions");
        setList(sb, config, "worldeditor_explosions_lore", Arrays.asList("&7&oToggle whether or not", "&7&oexplosions are activated."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_butcher_item", "&bButcher");
        setList(sb, config, "worldeditor_butcher_lore", Collections.singletonList("&7&oKill all the mobs in the world."));
        setMessage(sb, config, "worldeditor_butcher_removed", "%prefix% &b%amount% &7mobs were removed.");
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_builders_item", "&bBuilders");
        setList(sb, config, "worldeditor_builders_lore", Arrays.asList("&7&oManage which players can", "&7&obuild in the world.", "", "&8- &7&oLeft click&8: &7Toggle feature", "&8- &7&oRight click&8: &7Manage builders"));
        setMessage(sb, config, "worldeditor_builders_not_creator_item", "&c&mBuilders");
        setList(sb, config, "worldeditor_builders_not_creator_lore", Arrays.asList("&7&oYou are not the creator", "&7&oof this world."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_builders_title", "&3» &8Builders");
        setMessage(sb, config, "worldeditor_builders_creator_item", "&7&nCreator&7:");
        setMessage(sb, config, "worldeditor_builders_creator_lore", "&8» &b%creator%");
        setMessage(sb, config, "worldeditor_builders_no_creator_item", "&cWorld has no creator!");
        setMessage(sb, config, "worldeditor_builders_builder_item", "&b%builder%");
        setList(sb, config, "worldeditor_builders_builder_lore", Collections.singletonList("&8- &7&oShift click&8: &7Remove"));
        setMessage(sb, config, "worldeditor_builders_add_builder_item", "&bAdd builder");
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_gamerules_item", "&bGamerules");
        setList(sb, config, "worldeditor_gamerules_lore", Collections.singletonList("&7&oManage the world's gamerules"));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_gamerules_title", "&3» &8Gamerules");
        setList(sb, config, "worldeditor_gamerules_boolean_enabled", Collections.singletonList("&7&nCurrently&7: &atrue"));
        setList(sb, config, "worldeditor_gamerules_boolean_disabled", Collections.singletonList("&7&nCurrently&7: &cfalse"));
        setList(sb, config, "worldeditor_gamerules_unknown", Collections.singletonList("&7&nCurrently&7: &e%value%"));
        setList(sb, config, "worldeditor_gamerules_integer", Arrays.asList("&7&nCurrently&7: &e%value%", "",
                "&8- &7&oLeft Click&8: &7decrease by 1", "&8- &7&oShift &7+ &7&oLeft Click&8: &7decrease by 10",
                "&8- &7&oRight Click&8: &7increase by 1", "&8- &7&oShift &7+ &7&oRight Click&8: &7increase by 10"));
        setList(sb, config, "worldsettings_gamerule_", Arrays.asList("", "&c&nWarning&c: &7&oOnce a world is", "&7&odeleted it is lost forever", "&7&oand cannot be recovered!"));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_visibility_item", "&bVisibility");
        setList(sb, config, "worldeditor_visibility_lore_public", Arrays.asList("&7&oChange the world's visibility", "", "&7&nCurrently&7: &bPublic"));
        setList(sb, config, "worldeditor_visibility_lore_private", Arrays.asList("&7&oChange the world's visibility", "", "&7&nCurrently&7: &bPrivate"));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_mobai_item", "&bMob AI");
        setList(sb, config, "worldeditor_mobai_lore", Arrays.asList("&7&oToggle whether mobs have an AI."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_blockinteractions_item", "&bBlock Interactions");
        setList(sb, config, "worldeditor_blockinteractions_lore", Arrays.asList("&7&oToggle whether interactions", "&7&owith blocks are cancelled."));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_difficulty_item", "&bDifficulty");
        setList(sb, config, "worldeditor_difficulty_lore", Arrays.asList("&7&oChange the world's difficulty.", "", "&7&nCurrently&7: %difficulty%"));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_status_item", "&bStatus");
        setList(sb, config, "worldeditor_status_lore", Arrays.asList("&7&oChange the world's status.", "", "&7&nCurrently&7: %status%"));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_project_item", "&bProject");
        setList(sb, config, "worldeditor_project_lore", Arrays.asList("&7&oChange the world's project.", "", "&7&nCurrently&7: &b%project%"));
        addLine(sb, "");
        setMessage(sb, config, "worldeditor_permission_item", "&bPermission");
        setList(sb, config, "worldeditor_permission_lore", Arrays.asList("&7&oChange the world's permission.", "", "&7&nCurrently&7: &b%permission%"));
        addLine(sb, "");
        addLine(sb, "# Settings");
        setMessage(sb, config, "settings_title", "&3» &8Settings");
        addLine(sb, "");
        setMessage(sb, config, "settings_change_design_item", "&bChange Design");
        setList(sb, config, "settings_change_design_lore", Arrays.asList("&7&oSelect which colour the", "&7&oglass panes should have."));
        addLine(sb, "");
        setMessage(sb, config, "settings_clear_inventory_item", "&bClear Inventory");
        setList(sb, config, "settings_clear_inventory_lore", Arrays.asList("&7&oWhen enabled a player's", "&7&oinventory is cleared on join."));
        addLine(sb, "");
        setMessage(sb, config, "settings_disableinteract_item", "&bDisable Block Interactions");
        setList(sb, config, "settings_disableinteract_lore", Arrays.asList("&7&oWhen enabled interactions with", "&7&ocertain blocks are disabled."));
        addLine(sb, "");
        setMessage(sb, config, "settings_hideplayers_item", "&bHide Players");
        setList(sb, config, "settings_hideplayers_lore", Arrays.asList("&7&oWhen enabled all players", "&7&oonline will be hidden."));
        addLine(sb, "");
        setMessage(sb, config, "settings_instantplacesigns_item", "&bInstant Place Signs");
        setList(sb, config, "settings_instantplacesigns_lore", Arrays.asList("&7&oWhen enabled signs are placed", "&7&owithout opening the text input."));
        addLine(sb, "");
        setMessage(sb, config, "settings_keep_navigator_item", "&bKeep Navigator");
        setList(sb, config, "settings_keep_navigator_lore", Arrays.asList("&7&oWhen enabled the navigator", "&7&owill remain in your inventory", "&7&oeven when you clear it."));
        addLine(sb, "");
        setMessage(sb, config, "settings_new_navigator_item", "&bNew Navigator");
        setList(sb, config, "settings_new_navigator_lore", Arrays.asList("&7&oA new and improved navigator", "&7&owhich is no longer a GUI."));
        addLine(sb, "");
        setMessage(sb, config, "settings_nightvision_item", "&bNightvision");
        setList(sb, config, "settings_nightvision_lore", Arrays.asList("&7&oWhen enabled you will", "&7&oreceive permanent night vision."));
        addLine(sb, "");
        setMessage(sb, config, "settings_no_clip_item", "&bNoClip");
        setList(sb, config, "settings_no_clip_lore", Arrays.asList("&7&oWhen flying against a wall you", "&7&owill be put into spectator mode."));
        addLine(sb, "");
        setMessage(sb, config, "settings_open_trapdoors_item", "&bOpen Trapdoors");
        setList(sb, config, "settings_open_trapdoors_lore", Arrays.asList("&7&oWhen right clicking iron (trap-)doors", "&7&othey will be opened/closed."));
        addLine(sb, "");
        setMessage(sb, config, "settings_placeplants_item", "&bPlace Plants");
        setList(sb, config, "settings_placeplants_lore", Arrays.asList("&7&oWhen enabled you can place", "&7&oplants on every kind of block."));
        addLine(sb, "");
        setMessage(sb, config, "settings_scoreboard_item", "&bScoreboard");
        setList(sb, config, "settings_scoreboard_lore", Arrays.asList("&7&oA scoreboard which provides", "&7&oyou with useful information."));
        setMessage(sb, config, "settings_scoreboard_disabled_item", "&c&mScoreboard");
        setList(sb, config, "settings_scoreboard_disabled_lore", Arrays.asList("&7&oThe scoreboard has been", "&7&odisabled in the config."));
        addLine(sb, "");
        setMessage(sb, config, "settings_slab_breaking_item", "&bSlab breaking");
        setList(sb, config, "settings_slab_breaking_lore", Arrays.asList("&7&oWhen breaking double slabs", "&7&oonly one half will be broken."));
        addLine(sb, "");
        setMessage(sb, config, "settings_spawnteleport_item", "&bSpawn Teleport");
        setList(sb, config, "settings_spawnteleport_lore", Arrays.asList("&7&oWhen enabled you will be", "&7&oteleported to the spawn", "&7&oif it has been set."));
        addLine(sb, "");
        setMessage(sb, config, "settings_worldsort_item", "&bWorld Sort");
        setList(sb, config, "settings_worldsort_lore_alphabetically_name_az", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oName (A-Z)"));
        setList(sb, config, "settings_worldsort_lore_alphabetically_name_za", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oName (Z-A)"));
        setList(sb, config, "settings_worldsort_lore_alphabetically_project_az", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oProject (A-Z)"));
        setList(sb, config, "settings_worldsort_lore_alphabetically_project_za", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oProject (Z-A)"));
        setList(sb, config, "settings_worldsort_lore_status_not_started", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oStatus (Not Started ➡ Finished)"));
        setList(sb, config, "settings_worldsort_lore_status_finished", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oStatus (Finished ➡ Not Started)"));
        setList(sb, config, "settings_worldsort_lore_date_newest", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oCreation date (Newest)"));
        setList(sb, config, "settings_worldsort_lore_date_oldest", Arrays.asList("&7&oChange the way worlds are", "&7&osorted in the navigator.", "", "&7&nCurrently&7: &b&oCreation date (Oldest)"));
        addLine(sb, "");
        addLine(sb, "# Change Design");
        setMessage(sb, config, "design_title", "&3» &8Change Design");
        setMessage(sb, config, "design_red", "Red");
        setMessage(sb, config, "design_orange", "Orange");
        setMessage(sb, config, "design_yellow", "Yellow");
        setMessage(sb, config, "design_pink", "Pink");
        setMessage(sb, config, "design_magenta", "Magenta");
        setMessage(sb, config, "design_purple", "Purple");
        setMessage(sb, config, "design_brown", "Brown");
        setMessage(sb, config, "design_lime", "Lime");
        setMessage(sb, config, "design_green", "Green");
        setMessage(sb, config, "design_blue", "Blue");
        setMessage(sb, config, "design_aqua", "Aqua");
        setMessage(sb, config, "design_light_blue", "Light Blue");
        setMessage(sb, config, "design_white", "White");
        setMessage(sb, config, "design_grey", "Grey");
        setMessage(sb, config, "design_dark_grey", "Dark Grey");
        setMessage(sb, config, "design_black", "Black");
        addLine(sb, "");
        addLine(sb, "# Speed");
        setMessage(sb, config, "speed_title", "&3» &8Speed");
        setMessage(sb, config, "speed_1", "&b1");
        setMessage(sb, config, "speed_2", "&b2");
        setMessage(sb, config, "speed_3", "&b3");
        setMessage(sb, config, "speed_4", "&b4");
        setMessage(sb, config, "speed_5", "&b5");
        addLine(sb, "");
        addLine(sb, "# Secret Blocks");
        setMessage(sb, config, "blocks_title", "&3» &8Secret Blocks");
        setMessage(sb, config, "blocks_full_oak_barch", "&bFull Oak Bark");
        setMessage(sb, config, "blocks_full_spruce_barch", "&bFull Spruce Bark");
        setMessage(sb, config, "blocks_full_birch_barch", "&bFull Birch Bark");
        setMessage(sb, config, "blocks_full_jungle_barch", "&bFull Jungle Bark");
        setMessage(sb, config, "blocks_full_acacia_barch", "&bFull Acacia Bark");
        setMessage(sb, config, "blocks_full_dark_oak_barch", "&bFull Dark Oak Bark");
        setMessage(sb, config, "blocks_red_mushroom", "&bRed Mushroom");
        setMessage(sb, config, "blocks_brown_mushroom", "&bBrown Mushroom");
        setMessage(sb, config, "blocks_full_mushroom_stem", "&bFull Mushroom Stem");
        setMessage(sb, config, "blocks_mushroom_stem", "&bMushroom Stem");
        setMessage(sb, config, "blocks_mushroom_block", "&bMushroom Block");
        setMessage(sb, config, "blocks_smooth_stone", "&bSmooth Stone");
        setMessage(sb, config, "blocks_double_stone_slab", "&bDouble Stone Slab");
        setMessage(sb, config, "blocks_smooth_sandstone", "&bSmooth Sandstone");
        setMessage(sb, config, "blocks_smooth_red_sandstone", "&bSmooth Red Sandstone");
        setMessage(sb, config, "blocks_powered_redstone_lamp", "&bPowered Redstone Lamp");
        setMessage(sb, config, "blocks_burning_furnace", "&bBurning Furnace");
        setMessage(sb, config, "blocks_piston_head", "&bPiston Head");
        setMessage(sb, config, "blocks_command_block", "&bCommand Block");
        setMessage(sb, config, "blocks_barrier", "&bBarrier");
        setMessage(sb, config, "blocks_mob_spawner", "&bMob Spawner");
        setMessage(sb, config, "blocks_nether_portal", "&bNether Portal");
        setMessage(sb, config, "blocks_end_portal", "&bEnd Portal");
        setMessage(sb, config, "blocks_dragon_egg", "&bDragon Egg");

        try {
            FileOutputStream fileStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        config.getConfigurationSection("")
                .getKeys(false)
                .forEach(message -> this.messageData.put(message, config.getString(message)));
    }

    private void addLine(StringBuilder stringBuilder, String value) {
        stringBuilder.append(value).append("\n");
    }

    private void setMessage(StringBuilder stringBuilder, FileConfiguration config, String key, String defaultValue) {
        String value = config.getString(key, defaultValue);
        stringBuilder.append(key).append(": \"").append(value).append("\"").append("\n");
    }

    private void setList(StringBuilder stringBuilder, FileConfiguration config, String key, List<String> defaultValues) {
        List<String> values = config.getStringList(key);
        if (values.isEmpty()) {
            values = defaultValues;
        }

        stringBuilder.append(key).append(":\n");
        for (String value : values) {
            stringBuilder.append(" - \"").append(value).append("\"").append("\n");
        }
    }

    public Map<String, String> getMessageData() {
        return messageData;
    }
}