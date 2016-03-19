package me.benjozork.pickaxeachievements.internal;

import me.benjozork.pickaxeachievements.utils.ConfigAccessor;
import org.bukkit.entity.Player;

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

public class AchievementHandler {

    private final ConfigAccessor config;

    public AchievementHandler(ConfigAccessor config) {
        this.config = config;
    }

    //Current minor level
    public int getCurrentLevel(Player p) {
        try {
            return (int) (round(config.getConfig().getInt(p.getUniqueId().toString()), 500, "current")) / 500;
        } catch (Exception e) {
            return 0;
        }
    }

    //Next minor level
    public int getNextLevel(Player p) {
        return (int) (round(config.getConfig().getInt(p.getUniqueId().toString()), 500, "next")) / 500;
    }

    public void addBlock(Player p) {
        if (config.getConfig().get(p.getUniqueId().toString()) != null) {
            config.getConfig().set(p.getUniqueId().toString(), config.getConfig().getInt(p.getUniqueId().toString()) + 1);
        } else {
            config.getConfig().set(p.getUniqueId().toString(), 1);
        }

    }

    public int getRemainingBlocks(Player p) {
        return ((getNextLevel(p) * 500) - config.getConfig().getInt(p.getUniqueId().toString()));
    }

    private double round(double num, int multipleOf, String method) {
        if (method.equals("next")) return num >= 0 ? ((num + multipleOf - 1) / multipleOf) * multipleOf : (num / multipleOf) * multipleOf;
        else if (method.equals("current")) return num >= 0 ? (num / multipleOf) * multipleOf : ((num - multipleOf + 1) / multipleOf) * multipleOf;
        else throw new IllegalArgumentException("invalid level rounding method");
    }
}
