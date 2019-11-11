package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class InfinityEdge extends MobaItem{
	public InfinityEdge() {
		super("InfinityEdge", 425, InventoryShop.BFSword, InventoryShop.Pickaxe, InventoryShop.CloakOfAgility);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAttackDamage(MobaPlayer p) {
		return 80;
	}

	@Override
	public float getCriticalStrike(MobaPlayer p) {
		return 0.25F;
	}

	

}
