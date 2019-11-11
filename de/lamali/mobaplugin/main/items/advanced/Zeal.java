package de.lamali.mobaplugin.main.items.advanced;

import de.lamali.mobaplugin.main.InventoryShop;
import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.mobaobjects.MobaPlayer;

public class Zeal extends MobaItem{
	public Zeal() {
		super("Zeal", 500,InventoryShop.Dagger,InventoryShop.BrawlersGloves);
	}

	@Override
	public void getAktiv(MobaPlayer p) {
		
	}

	@Override
	public void getPassive(MobaPlayer p) {
		
	}

	@Override
	public float getCriticalStrike(MobaPlayer p) {
		return 0.15F;
	}

	@Override
	public float getAttackSpeed(MobaPlayer p) {
		return 0.15F;
	}

	@Override
	public float getMovement(MobaPlayer p) {
		if(p!=null){
		return 0.05F*p.baseMovementSpeed;
		}
		return 1;
	}




}
