package net.smileycorp.bloodsmeltery.common;

import slimeknights.tconstruct.library.fluid.FluidColored;
import slimeknights.tconstruct.library.smeltery.SmelteryTank;
import slimeknights.tconstruct.smeltery.block.BlockMultiblockController;
import slimeknights.tconstruct.smeltery.events.TinkerCastingEvent;
import slimeknights.tconstruct.smeltery.events.TinkerSmelteryEvent;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import WayofTime.bloodmagic.orb.IBloodOrb;
import WayofTime.bloodmagic.soul.IDemonWillGem;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.smileycorp.bloodsmeltery.common.tcon.TinkersContent;

@EventBusSubscriber(modid=ModDefinitions.modid)
public class BloodSmelteryEvents {
	
	@SubscribeEvent
	public static void tinkersMelt(TinkerSmelteryEvent.OnMelting event) {
		boolean retain = false;
		ItemStack input = event.itemStack;
		FluidStack output = event.result;
		TileSmeltery smeltery = event.smeltery;
		World world = smeltery.getWorld();
		for (FluidColored will : TinkersContent.FLUID_WILLS)
			if (output.getFluid( )== will) {
				NBTTagCompound nbt = input.getTagCompound();
				if (nbt != null) {
					if (nbt.hasKey("souls")) {
						int souls =  Math.round(nbt.getFloat("souls") * BloodSmelteryConfig.willFluidAmount);
						SmelteryTank tank = smeltery.getTank();
						if (smeltery.hasFuel()&&smeltery.isActive()) {
							if (tank.getFluidAmount()+souls<=tank.getCapacity()) {
								output.amount = souls;
							} else {
								output.amount = tank.getCapacity()-tank.getFluidAmount();
								nbt.setFloat("souls", (float)((souls-output.amount)/BloodSmelteryConfig.willFluidAmount));
								retain = true;
							}
						}
					}
				}
				if (input.getItem() instanceof  IDemonWillGem) {
					if (nbt==null) nbt = new NBTTagCompound();
					ItemStack stack = input.copy();
					if (!retain) nbt.setFloat("souls", 0f);
					stack.setTagCompound(nbt);
					BlockPos pos = smeltery.getPos().offset(world.getBlockState(smeltery.getPos()).getValue(BlockMultiblockController.FACING));
					EntityItem drop = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
					world.spawnEntity(drop);
				}
			}
	}
	
	@SubscribeEvent
	public static void tinkersCast(TinkerCastingEvent.OnCasted event) {
		ItemStack input = event.tile.getStackInSlot(0);
		ItemStack output = event.output;
		if (output.getItem() instanceof IBloodOrb) {
			NBTTagCompound nbt = output.getTagCompound();
			if (nbt!=null) {
				if (nbt.hasKey("orb")) {
					String orb = nbt.getString("orb");
					nbt = input.getTagCompound();
					nbt.setString("orb", orb);
					event.output = event.tile.getStackInSlot(0).copy();
					event.output.setTagCompound(nbt);
				}
			}
		} else if (output.getItem() instanceof IDemonWillGem) {
			NBTTagCompound nbt = input.getTagCompound();
			if (nbt==null) nbt = new NBTTagCompound();
			if (!nbt.hasKey("souls")) nbt.setFloat("souls", 0f);
			nbt.setFloat("souls", nbt.getFloat("souls")+1f);
			event.output = input.copy();
			event.output.setTagCompound(nbt);
			event.tile.setInventorySlotContents(0, ItemStack.EMPTY);
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
