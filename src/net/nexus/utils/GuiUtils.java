package net.nexus.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class GuiUtils {

	public static List<String> lorecreator(String lore) {
		List<String> l = new ArrayList<String>();
		String[] parts = lore.split("-n");

		for (int i = 0; i < parts.length; i++) {
			l.add(ChatColor.translateAlternateColorCodes('&', parts[i]));
		}

		return l;
	}
	
	public static String loredestroyer(List<String> lore) {
		StringBuilder builder = new StringBuilder();
		lore.forEach(l -> builder.append(l + "-n"));		
		return builder.toString().substring(0, builder.toString().length()-2);
	}

	public static ItemStack getSkull(String url) {
		return getSkull(url, "§7Kopf", null, 1);
	}

	public static ItemStack getSkull(String url, String display, List<String> lore, int amount) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
		if (url.isEmpty())
			return head;

		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		headMeta.setDisplayName(display);
		headMeta.setLore(lore);
		head.setItemMeta(headMeta);
		return head;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createHead(String owner, int amount, String display) {
		ItemStack it = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
		SkullMeta meta = (SkullMeta) it.getItemMeta();
		meta.setOwner(owner);
		meta.setDisplayName(display);
		it.setItemMeta(meta);
		return it;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createHead(String owner, int amount, String display, List<String> lore) {
		ItemStack it = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
		SkullMeta meta = (SkullMeta) it.getItemMeta();
		meta.setOwner(owner);
		meta.setDisplayName(display);
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}

	public static ItemStack createItem(Material mat, int amount, short id, String display) {
		ItemStack it = new ItemStack(mat, amount, id);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(display);
		it.setItemMeta(meta);
		return it;
	}

	public static ItemStack createItem(Material mat, int amount, short id, String display, List<String> lore) {
		ItemStack it = new ItemStack(mat, amount, id);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(display);
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}

	public static ItemStack createColoredLeatherArmor(Material leathermat, int amount, String display, int r, int g,
			int b) {
		ItemStack it = new ItemStack(leathermat, amount, (byte) 0);
		LeatherArmorMeta meta = (LeatherArmorMeta) it.getItemMeta();
		meta.setDisplayName(display);
		meta.setColor(Color.fromRGB(r, g, b));
		it.setItemMeta(meta);
		return it;
	}
	
	public static ItemStack createColoredLeatherArmor(Material leathermat, int amount, String display, int r, int g,
			int b, List<String> lore) {
		ItemStack it = new ItemStack(leathermat, amount, (byte) 0);
		LeatherArmorMeta meta = (LeatherArmorMeta) it.getItemMeta();
		meta.setDisplayName(display);
		meta.setColor(Color.fromRGB(r, g, b));
		meta.setLore(lore);
		it.setItemMeta(meta);
		return it;
	}
}
