package me.Cutiemango.MangoQuest.versions;

import me.Cutiemango.MangoQuest.I18n;
import me.Cutiemango.MangoQuest.QuestUtil;
import me.Cutiemango.MangoQuest.manager.QuestChatManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Version_v1_19_R2 implements VersionHandler {

    @Override
    public void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        p.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    @Override
    public void openBook(Player p, TextComponent... texts) {
        ArrayList<BaseComponent[]> list = new ArrayList<>();
        for (TextComponent t : texts)
            list.add(new BaseComponent[]{t});

        ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
        CraftMetaBook meta = (CraftMetaBook) book.getItemMeta();
        meta.spigot().setPages(list.toArray(new BaseComponent[][]{}));
        meta.setAuthor("MangoQuest");
        meta.setTitle("MangoQuest");
        book.setItemMeta(meta);
        p.openBook(book);
    }

    @Override
    public TextComponent textFactoryConvertLocation(String name, Location loc, boolean isFinished) {
        if (loc == null)
            return new TextComponent("");

        ItemStack is = new ItemStack(Material.PAINTING);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);

        String displayMsg = I18n.locMsg("QuestJourney.NPCLocDisplay",
                loc.getWorld().getName(),
                Integer.toString(loc.getBlockX()),
                Integer.toString(loc.getBlockY()),
                Integer.toString(loc.getBlockZ()));

        im.setLore(QuestUtil.createList(displayMsg));

        is.setItemMeta(im);
        TextComponent text = new TextComponent(isFinished ? QuestChatManager.finishedObjectFormat(name) : name);
        ItemTag itemTag = ItemTag.ofNbt(CraftItemStack.asNMSCopy(is).u().toString());
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item(is.getType().getKey().toString(), is.getAmount(), itemTag)));
        return text;
    }

    /**
     * displayText = the real text displayed
     * hoverItem = the hover item
     */
    @Override
    public TextComponent textFactoryConvertItem(final ItemStack item, boolean finished) {
        String displayText = QuestUtil.translate(item);

        if (finished)
            displayText = QuestChatManager.finishedObjectFormat(displayText);
        else
            displayText = ChatColor.BLACK + displayText;

        TextComponent text = new TextComponent(displayText);
        if (item != null) {
            NBTTagCompound tag = CraftItemStack.asNMSCopy(item).u();
            if (tag == null)
                return text;
            ItemTag itemTag = ItemTag.ofNbt(tag.toString());
            text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new Item(item.getType().getKey().toString(), item.getAmount(), itemTag)));
        }
        return text;
    }

    @Override
    public boolean hasTag(Player p, String s) {
        return ((CraftPlayer) p).getHandle().fT().b(s);
    }

    @Override
    public ItemStack addGUITag(ItemStack item) {
        net.minecraft.world.item.ItemStack nmscopy = CraftItemStack.asNMSCopy(item);
        NBTTagCompound stag = (nmscopy.t()) ? nmscopy.u() : new NBTTagCompound();
        stag.a("GUIitem", true);
        nmscopy.c(stag);
        return CraftItemStack.asBukkitCopy(nmscopy);
    }

    @Override
    public boolean hasGUITag(ItemStack item) {
        net.minecraft.world.item.ItemStack nmscopy = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = (nmscopy.t()) ? nmscopy.u() : new NBTTagCompound();
        return tag.e("GUIitem");
    }

    @Override
    public void playNPCEffect(Player p, Location location) {
        p.spawnParticle(Particle.NOTE,location.clone().add(0.0,2.0,0.0),1);
    }
}
