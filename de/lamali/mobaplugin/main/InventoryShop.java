package de.lamali.mobaplugin.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import de.lamali.mobaplugin.main.items.MobaItem;
import de.lamali.mobaplugin.main.items.advanced.*;
import de.lamali.mobaplugin.main.items.basic.*;
import de.lamali.mobaplugin.main.items.finished.*;
import de.lamali.mobaplugin.main.items.starter.DoransBlade;

public class InventoryShop {
	public static List<MobaItem> itemList = new ArrayList<MobaItem>();
	public static MobaItem DoransBlade;
	public static MobaItem AmplifyingTome, AncientCoin, BFSword, BlastingWand, BootsofSpeed, BrawlersGloves,
			CloakOfAgility, ClothArmor, Dagger, DarkSeal, FaerieCharm, HuntersMachete, HuntersTalisman,
			LongSword, NeedlesslyLargeRod, NullMagicMantle, Pickaxe, RejuvenationBead, RelicShield, RubyCrystal,
			SapphireCrystal, SpellthiefsEdge, Stopwatch;
	public static MobaItem AegisOfTheLegion, AetherWisp, BamisCinder,BilgewaterCutlass, BrambleVest, CatalystOfAeons, CaulfieldsWarhammer,
			ChainVest, ChaliceOfHarmony, CrystallineBracer, FiendishCodex, GiantsBelt, InfinityEdge, JaurimsFist, Kindlegem,
			KircheisShard, LostChapter, NegatronCloak, Phage, RecurveBow, SerratedDirk, Sheen, SpectresCowl, Stinger, TearOfTheGoddess,
			Tiamat, WardensMail, VampiricScepter, Zeal;
	public static MobaItem AbyssalMask, AdaptiveHelm, ArchangelsStaff, BlackCleaver, DeadMansPlate,
			DuskbladeOfDraktharr, EssenceReaver, FrozenMallet, GuinsoosRageblade, RabadonsDeathcap, RanduinsOmen, SteraksGage, Thornmail,
			TitanicHydra, TrinityForce, WarmogsArmor, YoumuusGhostblade;

	public static int page = 0;
	
	public static Inventory getShop(String... tags) {
		List<MobaItem> itemListTagged = new ArrayList<MobaItem>();
		if (tags != null) {
			for (MobaItem item : itemList) {
				boolean equalTags = true;
				for (int i = 0; i < tags.length; i++) {
					if (!item.tags.contains(tags[i])) {
						equalTags = false;
					}
				}
				if (equalTags) {
					itemListTagged.add(item);
				}
			}
		} else {
			itemListTagged = itemList;
		}
		
		int maxItemsPerPage = 45;
		int maxPages = (int) Math.floor((float)itemListTagged.size()/(float)maxItemsPerPage);
		if(page>maxPages){
			page=0;
		}
		Inventory shop = Bukkit.createInventory(null, 54, "Shop");
		for(int i = 0;i<maxItemsPerPage;i++){
			if(page*maxItemsPerPage+i< itemListTagged.size()){
				MobaItem item = itemListTagged.get(page*maxItemsPerPage+i);
				shop.addItem(item.getItem());
			}
		}
		return shop;
	}
	
	public static Inventory changePage(){
		page++;
		return getShop();
	}

	public static void initShop() {
		itemList = new ArrayList<MobaItem>();

		// starter
		DoransBlade = new DoransBlade();
		
		// basic
		AmplifyingTome = new AmplifyingTome();
		AncientCoin = new AncientCoin();
		BFSword = new BFSword();
		BlastingWand = new BlastingWand();
		BootsofSpeed = new BootsofSpeed();
		BrawlersGloves = new BrawlersGloves();
		CloakOfAgility = new CloakOfAgility();
		ClothArmor = new ClothArmor();
		Dagger = new Dagger();
		DarkSeal = new DarkSeal();
		FaerieCharm = new FaerieCharm();
		HuntersMachete = new HuntersMachete();
		HuntersTalisman = new HuntersTalisman();
		LongSword = new LongSword();
		NeedlesslyLargeRod = new NeedlesslyLargeRod();
		NullMagicMantle = new NullMagicMantle();
		Pickaxe = new Pickaxe();
		RejuvenationBead = new RejuvenationBead();
		RelicShield = new RelicShield();
		RubyCrystal = new RubyCrystal();
		SapphireCrystal = new SapphireCrystal();
		SpellthiefsEdge = new SpellthiefsEdge();
		Stopwatch = new Stopwatch();

		// advanced
		AegisOfTheLegion = new AegisOfTheLegion();
		AetherWisp = new AetherWisp();
		BamisCinder = new BamisCinder();
		VampiricScepter = new VampiricScepter();
		BilgewaterCutlass = new BilgewaterCutlass();
		BrambleVest = new BrambleVest();
		CatalystOfAeons = new CatalystOfAeons();
		CaulfieldsWarhammer = new CaulfieldsWarhammer();
		ChainVest = new ChainVest();
		ChaliceOfHarmony = new ChaliceOfHarmony();
		CrystallineBracer = new CrystallineBracer();
		FiendishCodex = new FiendishCodex();
		GiantsBelt = new GiantsBelt();
		InfinityEdge = new InfinityEdge();
		JaurimsFist = new JaurimsFist();
		Kindlegem = new Kindlegem();
		KircheisShard = new KircheisShard();
		LostChapter = new LostChapter();
		NegatronCloak = new NegatronCloak();
		Phage = new Phage();
		RecurveBow = new RecurveBow();
		SerratedDirk = new SerratedDirk();
		Sheen = new Sheen();
		SpectresCowl = new SpectresCowl();
		Stinger = new Stinger();
		TearOfTheGoddess = new TearOfTheGoddess();
		Tiamat = new Tiamat();
		WardensMail = new WardensMail();
		Zeal = new Zeal();

		// Tier 3
		AbyssalMask = new AbyssalMask();
		AdaptiveHelm = new AdaptiveHelm();
		ArchangelsStaff = new ArchangelsStaff();
		BlackCleaver = new BlackCleaver();
		DeadMansPlate = new DeadMansPlate();
		DuskbladeOfDraktharr = new DuskbladeOfDraktharr();
		EssenceReaver = new EssenceReaver();
		FrozenMallet = new FrozenMallet();
		GuinsoosRageblade = new GuinsoosRageblade();
		RabadonsDeathcap = new RabadonsDeathcap();
		RanduinsOmen = new RanduinsOmen();
		SteraksGage = new SteraksGage();
		Thornmail = new Thornmail();
		TitanicHydra = new TitanicHydra();
		TrinityForce = new TrinityForce();
		WarmogsArmor = new WarmogsArmor();
		YoumuusGhostblade = new YoumuusGhostblade();

	}
}
