package com.songoda.epicspawners.Hooks;

import com.songoda.epicspawners.EpicSpawners;
import com.songoda.epicspawners.Utils.ConfigWrapper;
import com.songoda.epicspawners.Utils.Debugger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by songoda on 3/17/2017.
 */
public class HookHandler {

    public Hooks FactionsHook = null, RedProtectHook = null, ASkyBlockHook = null, USkyBlockHook = null,
            WorldGuardHook = null, GriefPreventionHook = null, PlotSquaredHook = null, KingdomsHook = null,
            TownyHook = null;

    public ConfigWrapper hooksFile = new ConfigWrapper(EpicSpawners.getInstance(), "", "hooks.yml");

    public HookHandler() {
    }

    public void hook() {
        try {
        hooksFile.createNewFile("Loading Hooks File", EpicSpawners.getInstance().getDescription().getName() + " Hooks File");

        new FactionsHook();
        new RedProtectHook();
        new GriefPreventionHook();
        new ASkyBlockHook();
        new USkyBlockHook();
        new WorldGuardHook();
        new PlotSquaredHook();
        new KingdomsHook();
        new TownyHook();

        hooksFile.getConfig().options().copyDefaults(true);
        hooksFile.saveConfig();
        } catch (Exception e) {
            Debugger.runReport(e);
        }

    }

    public boolean isInFaction(String name, Location l) {
        if (FactionsHook != null) {
            return FactionsHook.isInClaim(name, l);
        }
        return false;
    }

    public String getFactionId(String name) {
        if (FactionsHook != null) {
            return FactionsHook.getClaimId(name);
        }
        return null;
    }

    public boolean isInTown(String name, Location l) {
        if (TownyHook != null) {
            return TownyHook.isInClaim(name, l);
        }
        return false;
    }

    public String getTownId(String name) {
        if (TownyHook != null) {
            return TownyHook.getClaimId(name);
        }
        return null;
    }

    public boolean isInIsland(String name, Location l) {
        if (USkyBlockHook != null)
            return USkyBlockHook.isInClaim(name, l);
        else if (ASkyBlockHook != null)
            return ASkyBlockHook.isInClaim(name, l);
        else
            return false;
    }

    public String getIslandId(String name) {
        try {
            return Bukkit.getOfflinePlayer(name).getUniqueId().toString();
        } catch (Exception e) {
        }
        return null;
    }

    public boolean canBuild(Player p, Location l) {
        boolean result = true;
        if (WorldGuardHook != null && result != false)
            result = WorldGuardHook.canBuild(p, l);
        if (RedProtectHook != null && result != false)
            result = RedProtectHook.canBuild(p, l);
        if (FactionsHook != null && result != false)
            result = FactionsHook.canBuild(p, l);
        if (ASkyBlockHook != null && result != false)
            result = ASkyBlockHook.canBuild(p, l);
        if (USkyBlockHook != null && result != false)
            result = USkyBlockHook.canBuild(p, l);
        if (GriefPreventionHook != null && result != false)
            result = GriefPreventionHook.canBuild(p, l);
        if (PlotSquaredHook != null && result != false)
            result = PlotSquaredHook.canBuild(p, l);
        if (KingdomsHook != null && result != false)
            result = KingdomsHook.canBuild(p, l);
        if (TownyHook != null && result != false)
            result = TownyHook.canBuild(p, l);
        return result;
    }
}
