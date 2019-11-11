package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class AetherWisp extends MobaItem{
	public AetherWisp() {
		super("AetherWisp", 415, InventoryShop.ClothArmor);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getAbilityPower(MobaPlayer p) {
		return 30;
	}


	@Override
	public float getMovement(MobaPlayer p) {
		if(p!=null){
		return 0.05F*p.baseMovementSpeed;
		}
		return 1;
	}



}
