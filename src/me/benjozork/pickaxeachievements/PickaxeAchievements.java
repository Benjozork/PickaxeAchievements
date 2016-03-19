package me.benjozork.pickaxeachievements;

import me.benjozork.pickaxeachievements.internal.AchievementHandler;
import me.benjozork.pickaxeachievements.internal.PlaceholderHandler;
import me.benjozork.pickaxeachievements.utils.ConfigAccessor;
import me.benjozork.pickaxeachievements.utils.MessageHandler;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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

public class PickaxeAchievements extends JavaPlugin {

    private ConfigAccessor dataConfig = new ConfigAccessor(this, "data.yml");
    private AchievementHandler ahandler = new AchievementHandler(dataConfig);
    private MessageHandler mHandler = new MessageHandler(this.getConfig());
    private Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHandler(this, ahandler).hook();
            log.info("[PickaxeAchievements] PlaceholderAPI hooked successfully.");
        } else log.info("[PickaxeAchievements] PlaceholderAPI not detected, not hooking.");

        getCommand("mined").setExecutor(new CommandHandler(ahandler, mHandler, this));
        Bukkit.getPluginManager().registerEvents(new PickaxeListener(ahandler, mHandler, this.getConfig()), this);
    }


    @Override
    public void onDisable() {
        dataConfig.saveConfig();
    }

}
