package me.benjozork.pickaxeachievements;

import me.benjozork.pickaxeachievements.internal.AchievementHandler;
import me.benjozork.pickaxeachievements.utils.MessageHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

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

    private final PickaxeAchievements main;
    private AchievementHandler aHandler;
    private MessageHandler mHandler;

    public CommandHandler(AchievementHandler handlerInstance, MessageHandler mHandlerInstance, PickaxeAchievements main) {
        this.aHandler = handlerInstance;
        this.mHandler = mHandlerInstance;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!label.equals("mined")) return false;
        if (args.length == 1 && Objects.equals(args[0], "reload")) {
            main.reloadConfig();
            mHandler.updateConfigs(main.getConfig());
            sender.sendMessage(mHandler.getMessage("reloaded"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Not available in console/command blocks.");
            return false;
        }
        Player p = (Player) sender;

        int level = aHandler.getCurrentLevel(p);
        int remainingBlocks = aHandler.getRemainingBlocks(p);
        int currentBlcoks = 500 - remainingBlocks;
        int progress = (currentBlcoks * 20) / 500;

        sender.sendMessage (
                mHandler.getMessage("info")
                .replace("%level%", level + "")
                .replace("%percent%", currentBlcoks * 100 / 500 + "")
                .replace("%remaining_blocks%", remainingBlocks + "")
                .replace("%current_blocks%", currentBlcoks + "")
        );

        StringBuilder progressBar = new StringBuilder();

        progressBar.append(mHandler.getRawMessage("prefix"));
        progressBar.append(mHandler.getRawMessage("progressbar_start_info")
        .replace("%level%", level + "")
        .replace("%percent%", currentBlcoks * 100 / 500 + "")
        .replace("%remaining_blocks%", remainingBlocks + "")
        .replace("%current_blocks%", currentBlcoks + "")
        + " ");
        progressBar.append(mHandler.getRawMessage("progressbar_start_char"));
        for (int i = 0; i < 20; i++) {
            if (i < progress - 1) progressBar.append(mHandler.getRawMessage("progressbar_fill_char"));
            else if (i < progress) progressBar.append(mHandler.getRawMessage("progressbar_fill_end_char"));
            else progressBar.append(mHandler.getRawMessage("progressbar_empty_char"));
        }
        progressBar.append(mHandler.getRawMessage("progressbar_end_char"));
        progressBar.append(" " + mHandler.getRawMessage("progressbar_end_info")
                .replace("%level%", level + "")
                .replace("%percent%", currentBlcoks * 100 / 500 + "")
                .replace("%remaining_blocks%", remainingBlocks + "")
                .replace("%current_blocks%", currentBlcoks + "")
        );

        sender.sendMessage(progressBar.toString());

        return true;
    }

}
