package me.Cutiemango.MangoQuest.manager;

import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import me.Cutiemango.MangoQuest.DebugHandler;
import me.Cutiemango.MangoQuest.I18n;
import me.Cutiemango.MangoQuest.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.util.logging.Level;

public class PluginHooker
{

	public PluginHooker(Main main) {
		plugin = main;
	}

	private final Main plugin;

	private Economy economy;
	private boolean citizens;
	private boolean shopkeepers;
	private boolean mythicMobs;
	private boolean skillAPI;
	public void hookPlugins() {
		try {
			if (plugin.getServer().getPluginManager().isPluginEnabled("Citizens")) {
				citizens = true;
				QuestChatManager.logCmd(Level.INFO, I18n.locMsg("PluginHooker.CitizensHooked"));
			} else {
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.PluginNotHooked"));
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.CitizensNotHooked1"));
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.CitizensNotHooked2"));
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.PleaseInstall"));
			}

			if (plugin.getServer().getPluginManager().isPluginEnabled("Vault")) {
				QuestChatManager.logCmd(Level.INFO, I18n.locMsg("PluginHooker.VaultHooked"));
			} else {
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.PluginNotHooked"));
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.VaultNotHooked"));
				QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.PleaseInstall"));
			}

			if (plugin.getServer().getPluginManager().isPluginEnabled("MythicMobs")) {
				mythicMobs = true;
				QuestChatManager.logCmd(Level.INFO, I18n.locMsg("PluginHooker.MythicMobsHooked"));
			} else {
				DebugHandler.log(1, "MythicMobs not hooked.");
			}

			if (plugin.getServer().getPluginManager().isPluginEnabled("Shopkeepers")) {
				shopkeepers = true;
				QuestChatManager.logCmd(Level.INFO, I18n.locMsg("PluginHooker.ShopkeepersHooked"));
			} else {
				DebugHandler.log(1, "Shopkeepers not hooked.");
			}

			if (plugin.getServer().getPluginManager().isPluginEnabled("SkillAPI")) {
				skillAPI = true;
				QuestChatManager.logCmd(Level.INFO, I18n.locMsg("PluginHooker.SkillAPIHooked"));
			} else {
				DebugHandler.log(1, "SkillAPI not hooked.");
			}
		}
		catch (Exception ignored) {
		}

		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
			QuestChatManager.logCmd(Level.INFO, I18n.locMsg("PluginHooker.EconomyHooked"));
		} else {
			QuestChatManager.logCmd(Level.SEVERE, I18n.locMsg("PluginHooker.EconomyNotHooked"));
		}
	}

	public boolean hasEconomyEnabled() {
		return economy != null;
	}

	public boolean hasMythicMobEnabled() {
		return mythicMobs;
	}

	public boolean hasCitizensEnabled() {
		return citizens;
	}

	public boolean hasShopkeepersEnabled() {
		return shopkeepers;
	}

	public boolean hasSkillAPIEnabled() {
		return skillAPI;
	}


	public Economy getEconomy() {
		return economy;
	}


	public BukkitAPIHelper getMythicMobsAPI() {
		return (BukkitAPIHelper) MythicProvider.get();
	}

	public MythicMob getMythicMob(String id) {
		if (!hasMythicMobEnabled())
			return null;
		return getMythicMobsAPI().getMythicMob(id);
	}

	public NPC getNPC(String id) {
		if (!hasCitizensEnabled() || !QuestValidater.validateInteger(id))
			return null;
		return CitizensAPI.getNPCRegistry().getById(Integer.parseInt(id));
	}

	public NPC getNPC(int id) {
		if (!hasCitizensEnabled())
			return null;
		return CitizensAPI.getNPCRegistry().getById(id);
	}
}
