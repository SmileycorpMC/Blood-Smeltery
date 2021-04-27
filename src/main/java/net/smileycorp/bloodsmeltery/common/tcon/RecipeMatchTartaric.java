package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import slimeknights.mantle.util.RecipeMatch;
import WayofTime.bloodmagic.iface.IMultiWillTool;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWillGem;

public class RecipeMatchTartaric extends RecipeMatch {
	
	protected final EnumDemonWillType type;
	protected final float amount;
	protected final int tier;

	protected RecipeMatchTartaric(EnumDemonWillType type, float amount, int tier) {
		super(1, 1);
		this.type=type;
		this.amount=amount;
		this.tier=tier;
	}

	@Override
	public List<ItemStack> getInputs() {
		return new ArrayList<ItemStack>();
	}

	@Override
	public Optional<Match> matches(NonNullList<ItemStack> stacks) {
		int matched = 0;
		List<ItemStack> matchedStacks = new ArrayList<ItemStack>();	
		for (ItemStack stack : stacks) {
			//NBTTagCompound nbt = stack.getTagCompound();
			net.minecraft.item.Item item = stack.getItem();
			if (stack.getMetadata() != tier) return Optional.empty();
			if (item instanceof IDemonWillGem && ((IMultiWillTool)item).getCurrentType(stack) == type) {
				float max = ((IDemonWillGem) item).getMaxWill(type, stack);
				if (((IDemonWillGem)item).getWill(type, stack) + amount > max) {
					return Optional.empty();
				}
				matchedStacks.add(stack);
				matched++;
			} else {
				return Optional.empty();
			}
		}
		return Optional.of(new Match(matchedStacks, matched));
	}
	
	public static RecipeMatch of(EnumDemonWillType type, float amount, int tier) {
		return new RecipeMatchTartaric(type, amount, tier);
	}
	
}
