package net.smileycorp.bloodsmeltery.common.tcon;

import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.book.sectiontransformer.materials.AbstractMaterialSectionTransformer;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class SingleMaterialSectionTransformer extends AbstractMaterialSectionTransformer {

	protected final MaterialId material;

	public SingleMaterialSectionTransformer(ResourceLocation loc) {
		super(loc.getPath(), true);
		material = new MaterialId(loc);
	}

	@Override
	protected boolean isValidMaterial(IMaterial material) {
		return material.getIdentifier().equals(this.material);
	}

}
