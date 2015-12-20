package me.devcode.CM;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class TuT extends JavaPlugin implements Listener 	{
	
	
	public static int scheduler;
	public static double timer = 0;
	public static boolean gestartet = false;
	
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§aGestartet");
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§cGestopt");
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("countdownmute")) {
			if(args.length < 1) {
				sender.sendMessage("§c/countdownmute <timer>");
				return true;
			}
			
			try{
				timer = Double.parseDouble(args[0]);
			}catch(NumberFormatException  e) {
				sender.sendMessage("§cBitte gib eine zahl ein");
				return true;
			}
			setCountdown();
		}else if(command.getName().equalsIgnoreCase("setpotions")) {
			sender.sendMessage("§cPotions gesetzt.");
			
			String p = PotionEffects.serializeEffects(((Player)sender).getActivePotionEffects());
			getConfig().set("Potions." + sender.getName() + ".Test", p);
		saveConfig();
		}else if(command.getName().equalsIgnoreCase("getpotions")) {
			String potions = getConfig().getString("Potions." + sender.getName() + ".Test");
		for(PotionEffect effect : PotionEffects.getPotionEffects(potions)) {
			((Player)sender).addPotionEffect(effect);
		}
		sender.sendMessage("§cPotion Effecte erhalten!");
		}
		
		return true;
	}

	@SuppressWarnings("deprecation")
	public void setCountdown() {
		if(gestartet == true) {
			Bukkit.getScheduler().cancelTask(scheduler);
			setCountdown();
		}else{
		gestartet = true;
		scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
		
			@Override
			public void run() {
				if(timer <= timer) {
					timer-=0.1D;
					
					
				}
				if(timer <= 0D) {
					gestartet = false;
					Bukkit.getScheduler().cancelTask(scheduler);
				}
				
			}
		}, 0, 2);
		
	}
	}
	
	@EventHandler
	public void onMute(AsyncPlayerChatEvent e) {
		//Ich habe hier noch eine kleine Methode rein gehauen weil ich was falsch gemacht habe und oben statt 0.2D 0.1D - genommen
		if(gestartet == true) {
			DecimalFormat f = new DecimalFormat("#0.00");
			double toFormat = ((double)Math.round(timer*100))/100;
			String format = String.valueOf(toFormat);
			e.getPlayer().sendMessage("§cDer Chat ist noch §a" + format + " §cgemuted.");
			e.setCancelled(true);
		}
	}
	

}
