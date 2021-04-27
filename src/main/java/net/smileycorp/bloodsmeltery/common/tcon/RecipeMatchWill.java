package net.smileycorp.bloodsmeltery.common.tcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import slimeknights.mantle.util.RecipeMatch;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.iface.IMultiWillTool;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWill;
import WayofTime.bloodmagic.soul.IDemonWillGem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class RecipeMatchWill extends RecipeMatch {
	
	protected final EnumDemonWillType type;
	protected final net.minecraft.item.Item item;
	protected final float amount;
	protected final float input;
	
	protected RecipeMatchWill(EnumDemonWillType type, float amount) {
		this(null, type, amount);
	}
	
	public RecipeMatchWill(net.minecraft.item.Item item, EnumDemonWillType type, float amount) {
		this(item, type, amount, 0);
	}

	protected RecipeMatchWill(net.minecraft.item.Item item, EnumDemonWillType type, float amount, float input) {
		super(1, 1);
		this.type=type;
		this.item=item;
		this.amount=amount;
		this.input=0;
	}

	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> result = new ArrayList<ItemStack>();
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setFloat("souls", this.amount);
		ItemStack stack = new ItemStack(RegistrarBloodMagicItems.MONSTER_SOUL, 1, type.ordinal());
		stack.setTagCompound(nbt);
		result.add(stack);
		for (int i = 0; i<5; i++) {
			stack = new ItemStack(RegistrarBloodMagicItems.SOUL_GEM, 1, i);
			((IDemonWillGem)RegistrarBloodMagicItems.SOUL_GEM).setWill(type, stack, this.amount);
			result.add(stack);
		}
		return result;
	}

	@Override
	public Optional<Match> matches(NonNullList<ItemStack> stacks) {
		int matched = 0;
		List<ItemStack> matchedStacks = new ArrayList<ItemStack>();	
		for (ItemStack stack : stacks) {
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt == null || !nbt.hasKey("souls") || (nbt.getFloat("souls") <=0 && amount!=0)) return Optional.empty();
			net.minecraft.item.Item item = stack.getItem();
			if (item instanceof IDemonWill) {
				if (type!=((IDemonWill)item ).getType(stack) || (this.item!=null && this.item!=item)){
					return Optional.empty();
				}
				matched++;
				matchedStacks.add(stack.copy());
			} else if (item instanceof IMultiWillTool) {
				if (((IMultiWillTool) item).getCurrentType(stack)!=type || (this.item!=null && this.item!=item)) {
					return Optional.empty();
				}
				matched++;
				matchedStacks.add(stack.copy());
			} else {
				return Optional.empty();
			}
		}
		return Optional.of(new Match(matchedStacks, matched));
	}
	
	public static RecipeMatch of(EnumDemonWillType type, float amount) {
		return new RecipeMatchWill(type, amount);
	}
	
	public static RecipeMatch ofStack(ItemStack stack, float amount) {
		net.minecraft.item.Item item = stack.getItem();
		if (item instanceof IDemonWill) {
			return new RecipeMatchWill(item, ((IDemonWill) item).getType(stack), amount);
		} else if (item instanceof IMultiWillTool) {
			 new RecipeMatchWill(item, ((IMultiWillTool) item).getCurrentType(stack), amount);
		}
		return RecipeMatch.of(stack);
	}
	
}
