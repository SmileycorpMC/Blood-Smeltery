package net.smileycorp.bloodsmeltery.client;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.smileycorp.bloodsmeltery.common.modifiers.SentienceModifier;
import net.smileycorp.bloodsmeltery.common.util.DemonWillUtils;
import slimeknights.mantle.client.model.util.MantleItemLayerModel;
import slimeknights.mantle.util.ItemLayerPixels;
import slimeknights.tconstruct.library.client.modifiers.IUnbakedModifierModel;
import slimeknights.tconstruct.library.client.modifiers.NormalModifierModel;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class SentienceModifierModel extends NormalModifierModel {

	public static final IUnbakedModifierModel UNBAKED_INSTANCE = (smallGetter, largeGetter)
			-> new SentienceModifierModel(smallGetter.apply(""), largeGetter.apply(""));

	protected final Material small, large;

	public SentienceModifierModel(Material small, Material large) {
		super(small, large);
		this.small = small;
		this.large = large;
	}

	@Nullable
	@Override
	public Object getCacheKey(IToolStackView tool, ModifierEntry entry) {
		if (!(entry.getModifier() instanceof SentienceModifier)) return super.getCacheKey(tool, entry);
		return SentienceModifier.getTier(tool) < 0 ? null : new CacheKey(entry.getModifier(), SentienceModifier.getWillType(tool));
	}

	@Override
	public void addQuads(IToolStackView tool, ModifierEntry entry, Function<Material,TextureAtlasSprite> spriteGetter, Transformation transforms, boolean isLarge, int startTintIndex, Consumer<Collection<BakedQuad>> quadConsumer, @Nullable ItemLayerPixels pixels) {
		if (!(entry.getModifier() instanceof SentienceModifier)) return;
		if (SentienceModifier.getTier(tool) < 0) return;
		Material material = isLarge ? large : small;
		if (material == null) return;
		quadConsumer.accept(MantleItemLayerModel.getQuadsForSprite(0xFF000000 + DemonWillUtils.getColour(SentienceModifier.getWillType(tool)),
				-1, spriteGetter.apply(material), transforms, 10, pixels));
	}
	
	public record CacheKey(Modifier modifier, EnumDemonWillType willType) {
		
		@Override
		public boolean equals(Object object) {
			if (object == this) return true;
			if (!(object instanceof CacheKey other)) return false;
            return (modifier == other.modifier && willType == other.willType);
		}
		
	}

}
