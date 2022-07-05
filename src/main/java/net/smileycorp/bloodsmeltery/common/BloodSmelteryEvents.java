package net.smileycorp.bloodsmeltery.common;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import wayoftime.bloodmagic.api.compat.IDemonWillGem;
import wayoftime.bloodmagic.api.compat.IMultiWillTool;

@EventBusSubscriber(modid=ModDefinitions.MODID)
public class BloodSmelteryEvents {

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
		ItemStack stack = event.getObject();
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof IMultiWillTool && item instanceof IDemonWillGem) {
				TartaricFluidCapability cap = new TartaricFluidCapability(stack);
				event.addCapability(ModDefinitions.getResource("TartaricFluid"), cap);
			}
		}
	}

	/*@SubscribeEvent
	public void killMob(LivingDeathEvent event) {
		if (event.getSource() instanceof EntityDamageSource) {
			EntityLivingBase entity = event.getEntityLiving();
			Entity attacker = ((EntityDamageSource) event.getSource()).getTrueSource();
			if (attacker instanceof EntityPlayer) {
				ItemStack held = ((EntityPlayer) attacker).getHeldItemMainhand();
				if (held.getItem() instanceof TinkerToolCore) {
					generateSoul(held, (EntityPlayer)attacker, entity);
				}
			} else if (attacker instanceof EntityProjectileBase) {
				try{
					ItemStack stack = (ItemStack) ReflectionHelper.findMethod(EntityProjectileBase.class, "getArrowStack", null).invoke(attacker);
					Entity shooter = ((EntityProjectileBase) attacker).shootingEntity;
					if (shooter instanceof EntityPlayer) {
						generateSoul(stack, (EntityPlayer)shooter, entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void generateSoul(ItemStack held, EntityPlayer player, EntityLivingBase entity) {
		if (ModifierNBT.readTag(TinkerUtil.getModifierTag(held, TinkersContent.ALIVE_MODIFIER.getName())).level>0) {
			float souls = entity.getMaxHealth()/20f;
			for (ItemStack stack : player.inventory.mainInventory) {
				if (stack.getItem() instanceof IDemonWillGem) {
					IDemonWillGem gem = (IDemonWillGem) stack.getItem();
					NBTTagCompound nbt = stack.getTagCompound();
					EnumDemonWillType type = EnumDemonWillType.DEFAULT;
					if (nbt.hasKey("demonWillType")) {
						type = EnumDemonWillType.valueOf(nbt.getString("demonWillType"));
					}
					if (gem.getWill(type, stack) + souls < gem.getMaxWill(type, stack)) {
						gem.fillWill(type, stack, souls, true);
						return;
					}
				}
			}
			ItemStack soul = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL);
		 	NBTTagCompound nbt = new NBTTagCompound();
		 	nbt.setFloat("souls", souls);
		 	soul.setTagCompound(nbt);
		 	EntityItem drop = new EntityItem(null, entity.posX, entity.posY, entity.posZ, soul);
		 	entity.world.spawnEntity(drop);
		}

	}*/
}
