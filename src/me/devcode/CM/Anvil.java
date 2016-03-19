package me.devcode.CM;

import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.ContainerAnvil;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R2.PacketPlayOutOpenWindow;

public class Anvil {
	
	private EntityPlayer entityplayer;
	private String title = "";
	private AnvilContainer container; 
	
	public Anvil(Player player) {
		entityplayer = ((CraftPlayer)player).getHandle();
		container = new AnvilContainer(entityplayer);
		//Wollte ich eben machen, habe es aber vergessen :D
	}
	
	public void open() {
		int c = entityplayer.nextContainerCounter();
		entityplayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(c, "minecraft:anvil", ChatSerializer.a("{'text':'" + title + "'}")));
		entityplayer.activeContainer = container;
		entityplayer.activeContainer.windowId = c;
		entityplayer.activeContainer.addSlotListener(entityplayer);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	private class AnvilContainer extends ContainerAnvil {
		public AnvilContainer(EntityHuman entity)  {
			super(entity.inventory, entity.world, new BlockPosition(0, 0, 0),  entity);
		}
		@Override
		public boolean a(EntityHuman entityhuman) {
			return true;
		}
		
	}
	
	

}
