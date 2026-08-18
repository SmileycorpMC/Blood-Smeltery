package net.smileycorp.bloodsmeltery.client;

import com.google.common.collect.Maps;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.smileycorp.bloodsmeltery.common.modifiers.SentienceModifier;
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
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class SentienceModifierModel extends NormalModifierModel {

	public static final IUnbakedModifierModel UNBAKED_INSTANCE = (smallGetter, largeGetter) -> {
		Map<RenderType, Map<EnumDemonWillType, Material>> maps = Maps.newHashMap();
		for (RenderType type : RenderType.values()) {
			Map<EnumDemonWillType, Material> map = Maps.newHashMap();
			for (EnumDemonWillType will : EnumDemonWillType.values()) map.put(will, (type.isLarge() ? largeGetter: smallGetter).apply("/" + type.append(will)));
			maps.put(type, map);
		}
		return new SentienceModifierModel(maps);
	};

	public final Map<RenderType, Map<EnumDemonWillType, Material>> MATERIAL_MAPS;

	public SentienceModifierModel(Map<RenderType, Map<EnumDemonWillType, Material>> maps) {
		super(maps.get(RenderType.SMALL).get(EnumDemonWillType.DEFAULT), maps.get(RenderType.LARGE).get(EnumDemonWillType.DEFAULT));
		MATERIAL_MAPS = maps;
	}

	@Nullable
	@Override
	public Object getCacheKey(IToolStackView tool, ModifierEntry entry) {
		if (!(entry.getModifier() instanceof SentienceModifier)) return entry.getModifier();
		EnumDemonWillType will = SentienceModifier.getWillType(tool);
		boolean isActive = SentienceModifier.getTier(tool) >= 0;
		return new CacheKey(entry.getModifier(), will, isActive);
	}

	@Override
	public void addQuads(IToolStackView tool, ModifierEntry entry, Function<Material,TextureAtlasSprite> spriteGetter, Transformation transforms, boolean isLarge, int startTintIndex, Consumer<Collection<BakedQuad>> quadConsumer, @Nullable ItemLayerPixels pixels) {
		if (!(entry.getModifier() instanceof SentienceModifier)) return;
		Material material = MATERIAL_MAPS.get(RenderType.getType(SentienceModifier.getTier(tool) >= 0, isLarge)).get(SentienceModifier.getWillType(tool));
		if (material == null) return;
		quadConsumer.accept(MantleItemLayerModel.getQuadsForSprite(0xFFFFFFFF, -1, spriteGetter.apply(material), transforms, 10, pixels));
	}
	
	public record CacheKey(Modifier modifier, EnumDemonWillType willType, boolean isActive) {
		
		@Override
		public boolean equals(Object object) {
			if (object == this) return true;
			if (!(object instanceof CacheKey)) return false;
			CacheKey other = (CacheKey) object;
			if (this.getClass() != other.getClass()) return false;
			return (modifier == other.modifier && willType == other.willType && isActive == other.isActive);
		}
		
	}

	public enum RenderType {

		SMALL(false, false), SMALL_ACTIVE(true, false), LARGE(false, true), LARGE_ACTIVE(true, true);

		private final boolean isActive, isLarge;

		RenderType(boolean isActive, boolean isLarge) {
			this.isActive = isActive;
			this.isLarge = isLarge;
		}

		public boolean isLarge() {
			return isLarge;
		}

		public String append(EnumDemonWillType type) {
			return isActive ? type.toString() + "_activated" : type.toString();
		}

		public static RenderType getType(boolean isActive, boolean isLarge) {
			for (RenderType type : values()) if (type.isActive == isActive && type.isLarge == isLarge) return type;
			return null;
		}
		
	}

}
