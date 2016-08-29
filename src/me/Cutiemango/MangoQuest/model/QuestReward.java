package me.Cutiemango.MangoQuest.model;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Cutiemango.MangoQuest.Main;
import me.Cutiemango.MangoQuest.QuestStorage;
import me.Cutiemango.MangoQuest.QuestUtil;
import net.md_5.bungee.api.ChatColor;

public class QuestReward {

	private double money;
	private List<ItemStack> items = new ArrayList<>();
	private int experience;
	
	public QuestReward(){
		
	}

	public QuestReward(ItemStack is) {
		items.add(is);
	}

	public QuestReward(double amount) {
		money = amount;
	}
	
	public QuestReward(int exp){
		experience = exp;
	}

	public void addItem(ItemStack is) {
		items.add(is);
	}

	public void addMoney(double d) {
		money += d;
	}
	
	public void addExp(int i){
		experience += i;
	}

	public void removeItem(ItemStack is) {
		if (items.contains(is))
			items.remove(is);
		else
			return;
	}

	public void removeMoney(double d) {
		if (money < d)
			money = 0;
		else
			money -= d;
	}
	
	public void removeExp(int i){
		if (experience < i)
			experience = 0;
		else
			experience -= i;
	}

	public boolean hasItem() {
		return !(items.isEmpty());
	}

	public boolean hasMoney() {
		return !(money == 0.0D);
	}
	
	public boolean hasExp(){
		return !(experience == 0);
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public double getMoney() {
		return money;
	}
	
	public int getExp() {
		return experience;
	}
	
	public boolean isEmpty(){
		return items.isEmpty() && money == 0.0D;
	}

	public void giveRewardTo(Player p) {
		if (this.hasItem()) {
			for (ItemStack is : items) {
				if (p.getInventory().firstEmpty() == -1) {
					p.sendMessage(QuestStorage.prefix + ChatColor.RED + "背包物品過多，你的任務獎勵 "
							+ is.getItemMeta().getDisplayName() + ChatColor.RED + " 掉落地面！");
					p.getWorld().dropItem(p.getLocation(), is);
					return;
				} else {
					p.getInventory().addItem(is);
					if (is.getItemMeta().hasDisplayName())
						QuestUtil.info(p, "&e任務獎勵 - 給予 " + is.getItemMeta().getDisplayName() + " &f" + is.getAmount() + " &e個");
					else
						QuestUtil.info(p, "&e任務獎勵 - 給予 " + QuestUtil.translate(is.getType(), is.getDurability()) + " &f" + is.getAmount() + " &e個");
				}
			}
		}

		if (this.hasMoney()) {
			Main.economy.depositPlayer(p, money);
			QuestUtil.info(p, "&e任務獎勵 - 給予 &f" + money + " &e元");
		}
		
		if (this.hasExp()) {
			p.giveExp(experience);
			QuestUtil.info(p, "&e任務獎勵 - 給予 &f" + experience + " &e點 &a經驗值");
		}
	}
}
