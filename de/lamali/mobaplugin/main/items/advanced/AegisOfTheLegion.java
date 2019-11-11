package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class AegisOfTheLegion extends MobaItem{
	public AegisOfTheLegion() {
		super("AegisOfTheLegion", 350, InventoryShop.ClothArmor,InventoryShop.NullMagicMantle);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getMagicRes(MobaPlayer p) {
		return 30;
	}

		@Override
	public float getArmor(MobaPlayer p) {
		return 30;
	}


}
