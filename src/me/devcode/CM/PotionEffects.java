package me.devcode.CM;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffects {
	
	public static String serializeEffects(Collection<PotionEffect> effects) {
		String serialized = "";
		if(effects == null) {
			
		}else{
			for(PotionEffect e : effects) {
				serialized += e.getType().getId()  + ":" + e.getDuration() + ":" + e.getAmplifier() + ";";
			}
		}
		
		
		return serialized;
	}
	
	public static Collection<PotionEffect> getPotionEffects(String serilizedEffects) {
		ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
		if(serilizedEffects.isEmpty()) 
			return effects;
			String[] effs = serilizedEffects.split(";");
			for(int i = 0; i < effs.length; i++) {
				String[] effect = effs[i].split(":");
				if(effect.length < 3) 
					throw new IllegalArgumentException("Der Effect muss eine größe von 3 haben!");
			int id = Integer.parseInt(effect[0]);
			int duration = Integer.parseInt(effect[1]);
			int amplifier = Integer.parseInt(effect[2]);
			PotionEffectType effectType = PotionEffectType.getById(id);
			if(effectType == null)
				throw new IllegalAccessError("Es gibt keinen PotionEffect mit der ID: " + id);
			PotionEffect e = new PotionEffect(effectType, duration, amplifier);
			effects.add(e);
			}
			return effects;
		}
		
		
	}


