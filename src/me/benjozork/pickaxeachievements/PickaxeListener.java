package me.benjozork.pickaxeachievements;

import me.benjozork.pickaxeachievements.internal.AchievementHandler;
import me.benjozork.pickaxeachievements.utils.MessageHandler;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 Looks like you decompiled my code :) Don't worry, you have to right to do so.

 The MIT License (MIT)

 Copyright (c) 2016 Benjozork

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/

public class PickaxeListener implements Listener {

    private final FileConfiguration config;
    private AchievementHandler aHandler;
    private MessageHandler mHandler;

    public PickaxeListener(AchievementHandler handlerInstance,
                           MessageHandler mHandlerInstance,
                           FileConfiguration config) {
        this.aHandler = handlerInstance;
        this.mHandler = mHandlerInstance;
        this.config = config;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPickaxeBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        if (Bukkit.getServer().getVersion().contains("1.9")) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE)
                    || e.getPlayer().getInventory().getItemInOffHand().getType().equals(Material.DIAMOND_PICKAXE)) {

                int startLevel = aHandler.getCurrentLevel(e.getPlayer());

                aHandler.addBlock(e.getPlayer());
                System.out.println(aHandler.getRemainingBlocks(e.getPlayer()));

                if (aHandler.getCurrentLevel(e.getPlayer()) > startLevel) {
                    mHandler.sendLevelUpMessage(e.getPlayer(), aHandler.getCurrentLevel(e.getPlayer()));
                }

            }
        } else {
            if (e.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {

                int startLevel = aHandler.getCurrentLevel(e.getPlayer());

                aHandler.addBlock(e.getPlayer());
                System.out.println(aHandler.getRemainingBlocks(e.getPlayer()));

                if (aHandler.getCurrentLevel(e.getPlayer()) > startLevel) {
                    e.getPlayer().sendMessage(mHandler.getMessage("level_up")
                    .replace("%level%", aHandler.getCurrentLevel(e.getPlayer()) + ""));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString("command_minor"));
                }
            }
        }
    }

}
