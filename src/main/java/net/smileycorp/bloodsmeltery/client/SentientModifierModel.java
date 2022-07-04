package net.smileycorp.bloodsmeltery.client;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.smileycorp.bloodsmeltery.common.tcon.SentientModifier;
import slimeknights.mantle.client.model.util.MantleItemLayerModel;
import slimeknights.mantle.util.ItemLayerPixels;
import slimeknights.tconstruct.library.client.modifiers.IUnbakedModifierModel;
import slimeknights.tconstruct.library.client.modifiers.NormalModifierModel;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;

public class SentientModifierModel extends NormalModifierModel {

	public static final IUnbakedModifierModel UNBAKED_INSTANCE = (smallGetter, largeGetter) -> {
		Map<EnumRenderType, Map<EnumDemonWillType, RenderMaterial>> maps = Maps.newHashMap();
		for (EnumRenderType type : EnumRenderType.values()) {
			Map<EnumDemonWillType, RenderMaterial> map = Maps.newHashMap();
			for (EnumDemonWillType will : EnumDemonWillType.values()) {
				map.put(will, (type.isLarge() ? largeGetter: smallGetter).apply("/" + type.append(will)));
			}
			maps.put(type, map);
		}
		return new SentientModifierModel(maps);
	};

	public final Map<EnumRenderType, Map<EnumDemonWillType, RenderMaterial>> MATERIAL_MAPS;

	public SentientModifierModel(Map<EnumRenderType, Map<EnumDemonWillType, RenderMaterial>> maps) {
		super(maps.get(EnumRenderType.SMALL).get(EnumDemonWillType.DEFAULT), maps.get(EnumRenderType.LARGE).get(EnumDemonWillType.DEFAULT));
		MATERIAL_MAPS = maps;
	}

	@Nullable
	@Override
	public Object getCacheKey(IModifierToolStack tool, ModifierEntry entry) {
		if (entry.getModifier() instanceof SentientModifier) {
			EnumDemonWillType will = SentientModifier.getWillType(tool);
			boolean isActive = SentientModifier.getTier(tool) >= 0;
			return new SentientModifierCacheKey(entry.getModifier(), will, isActive);
		}
		return entry.getModifier();
	}

	@Override
	public ImmutableList<BakedQuad> getQuads(IModifierToolStack tool, ModifierEntry entry, Function<RenderMaterial,TextureAtlasSprite> spriteGetter, TransformationMatrix transforms, boolean isLarge, int startTintIndex, @Nullable ItemLayerPixels pixels) {
		if (entry.getModifier() instanceof SentientModifier) {
			EnumDemonWillType will = SentientModifier.getWillType(tool);
			boolean isActive = SentientModifier.getTier(tool) >= 0;
			RenderMaterial material = MATERIAL_MAPS.get(EnumRenderType.getType(isActive, isLarge)).get(will);
			if (material != null) return MantleItemLayerModel.getQuadsForSprite(0xFFFFFFFF, -1, spriteGetter.apply(material), transforms, 10, pixels);
		}
		return ImmutableList.of();
	}

	public static class SentientModifierCacheKey {

		private final Modifier modifier;
		private final EnumDemonWillType willType;
		private final boolean isActive;

		public SentientModifierCacheKey(Modifier modifier, EnumDemonWillType willType, boolean isActive) {
			this.modifier = modifier;
			this.willType = willType;
			this.isActive = isActive;
		}

		public Modifier getModifier() {
			return modifier;
		}

		public EnumDemonWillType getWillType() {
			return willType;
		}

		public boolean isActive() {
			return isActive;
		}


		@Override
		public boolean equals(Object object) {
			if (object == this) return true;
			if (!(object instanceof SentientModifierCacheKey)) return false;
			SentientModifierCacheKey other = (SentientModifierCacheKey) object;
			if (this.getClass() != other.getClass()) return false;
			return (modifier == other.modifier && willType == other.willType && isActive == other.isActive);
		}

	}

	public static enum EnumRenderType {

		SMALL(false, false), SMALL_ACTIVE(true, false), LARGE(false, true), LARGE_ACTIVE(true, true);

		private final boolean isActive, isLarge;

		private EnumRenderType(boolean isActive, boolean isLarge) {
			this.isActive = isActive;
			this.isLarge = isLarge;
		}

		public boolean isLarge() {
			return isLarge;
		}

		public String append(EnumDemonWillType type) {
			return isActive ? type.toString() + "_activated" : type.toString();
		}

		public static EnumRenderType getType(boolean isActive, boolean isLarge) {
			for (EnumRenderType type : values()) {
				if (type.isActive == isActive && type.isLarge == isLarge) return type;
			}
			return null;
		}
	}

}
