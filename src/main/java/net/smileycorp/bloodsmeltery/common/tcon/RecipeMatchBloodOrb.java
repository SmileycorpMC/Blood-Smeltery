package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import WayofTime.bloodmagic.core.RegistrarBloodMagic;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.orb.BloodOrb;
import WayofTime.bloodmagic.orb.IBloodOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import slimeknights.mantle.util.RecipeMatch;

public class RecipeMatchBloodOrb extends RecipeMatch {
	
	protected final ResourceLocation orb;

	public RecipeMatchBloodOrb(BloodOrb orb) {
		super(1, 1);
		this.orb = RegistrarBloodMagic.BLOOD_ORBS.getKey(orb);
	}

	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> result = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(RegistrarBloodMagicItems.BLOOD_ORB);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("orb", orb.toString());
	 	stack.setTagCompound(nbt);
	 	result.add(stack);
		return result;
	}

	@Override
	public Optional<Match> matches(NonNullList<ItemStack> stacks) {
		int matched = 0;
		List<ItemStack> matchedStacks = new ArrayList<ItemStack>();	
		for (ItemStack stack : stacks) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null || !nbt.hasKey("orb")) return Optional.empty();
			net.minecraft.item.Item item = stack.getItem();
			if (item instanceof IBloodOrb) {
				if (!nbt.getString("orb").equals((orb.toString()))){
					return Optional.empty();
				}
				matched++;
				matchedStacks.add(stack.copy());
			}else {
				return Optional.empty();
			}
		}
		return Optional.of(new Match(matchedStacks, matched));
	}

	public static RecipeMatch of(BloodOrb orb) {
		return new RecipeMatchBloodOrb(orb);
	}

}
