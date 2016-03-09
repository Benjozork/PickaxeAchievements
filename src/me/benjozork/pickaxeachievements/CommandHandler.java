package me.benjozork.pickaxeachievements;

import me.benjozork.pickaxeachievements.internal.AchievementHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * Looks like you decompiled my code :) Don't worry, you have to right to do so.
 * <p>
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Benjozork
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
public class CommandHandler implements CommandExecutor {

    private FileConfiguration config;
    private AchievementHandler aHandler;

    public CommandHandler(FileConfiguration config) {
        this.config = config;
        this.aHandler = new AchievementHandler(config);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!label.equals("mined")) return false;
        //Uitlise message handler !
        if (!(sender instanceof Player)) sender.sendMessage(ChatColor.RED + "Not available in console/command blocks.");
        Player p = (Player) sender;

        int level = aHandler.getLevel(p, "curent");

        //Uitlise message handler !
        sender.sendMessage (
                ChatColor.DARK_GRAY
                + "[" + ChatColor.RED
                + "PickStats"
                + ChatColor.DARK_GRAY
                + "] "
                + ChatColor.GRAY
                + "Level: "
                + level
        );

        level = aHandler.getLevel(p, "next");

        //Uitlise message handler !
        sender.sendMessage (
                ChatColor.DARK_GRAY
                        + "[" + ChatColor.RED
                        + "PickStats"
                        + ChatColor.DARK_GRAY
                        + "] "
                        + ChatColor.GRAY
                        + "Next Level: "
                        + level
        );

        //@TODO barre de progression et niveaux majeurs

        return true;
    }

}
