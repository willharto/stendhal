/***************************************************************************
 *                   (C) Copyright 2016 - Faiumoni e.V.                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.item.scroll;

//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.BabyDragon;
import games.stendhal.server.entity.creature.Cat;
import games.stendhal.server.entity.creature.Pet;
import games.stendhal.server.entity.creature.PurpleDragon;
import games.stendhal.server.entity.creature.SneakyPet;
//import games.stendhal.server.entity.creature.Sheep;
//import games.stendhal.server.core.engine.transformer.PlayerTransformer;
import games.stendhal.server.entity.item.Item;
//import games.stendhal.server.core.rp.StendhalRPAction;
//import games.stendhal.server.core.rule.EntityManager;
//import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.player.PlayerPetManager;

/**
 * Represents a creature summon pet scroll.
 */
public class SummonPetScroll extends Scroll {

	private Pet oldPet;
	
	private static final int MAX_ZONE_NPCS = 50;

	private static final Logger logger = Logger.getLogger(SummonPetScroll.class);

	/**
	 * Creates a new summon pet scroll.
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public SummonPetScroll(final String name, final String clazz, final String subclass,
			final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item
	 *            item to copy
	 */
	public SummonPetScroll(final SummonPetScroll item) {
		super(item);
	}

	/**
	 * Is invoked when a summon pet scroll is used.
	 *
	 * @param player
	 *            The player who used the scroll
	 * @return true iff summoning was successful
	 */
	@Override
	protected boolean useScroll(final Player player) {
		final StendhalRPZone zone = player.getZone();

		if (!zone.isTeleportInAllowed(player.getX(), player.getY())) {
			player.sendPrivateText("The aura of protection in this area prevents the scroll from working!");
			return false;
		}

		if (zone.getNPCList().size() >= MAX_ZONE_NPCS) {
			player.sendPrivateText("Mysteriously, the scroll does not function! Perhaps this area is too crowded...");
			logger.info("Too many npcs to summon another creature");
			return false;
		}

		if (player.hasPet()) {
			player.sendPrivateText("The magic is not strong enough to give you more than one pet.");
			return false;
		}

		String type = getInfoString().replaceAll("_", " ");
		if (type == null) {
			// default to cat, if no other type is specified
			type = "cat";
		}

		// create it
		Pet pet = null;
		PlayerPetManager beastMaster = new PlayerPetManager(player);
		oldPet = beastMaster.retrievePet();
		switch (type) {
		case "cat":
			pet = new Cat(player);
			restorePet(pet);
			break;
		case "baby dragon":
			pet = new BabyDragon(player);
			restorePet(pet);
			break;
		case "purple dragon":
			pet = new PurpleDragon(player);
			restorePet(pet);
			break;
		case "sneaky pet":
			pet = new SneakyPet(player);
			restorePet(pet);
			break;
		default:
			// Didn't match a known pet type
			player.sendPrivateText("This scroll does not seem to work. You should talk to the magician who created it.");
			return false;
		}

		pet.setPosition(player.getX(), player.getY() + 1);
		dropBlank(player);
		return true;
	}
	
	private void restorePet(Pet pet) {
		pet.setAtk(oldPet.getAtk());
		pet.setAtkXP(oldPet.getAtkXP());
		pet.setDef(oldPet.getDef());
		pet.setDefXP(oldPet.getDefXP());
		pet.setBaseHP(oldPet.getBaseHP());
		pet.setHP(oldPet.getHP());
		pet.setWeight(oldPet.getWeight());
		pet.setName(oldPet.getName());
		pet.setXP(oldPet.getXP());
		pet.setLevel(oldPet.getLevel());
		pet.setBaseMana(oldPet.getBaseMana());
		pet.setMana(oldPet.getMana());
	}

	@Override
	public String describe() {
		String text = super.describe();

		final String infostring = getInfoString();

		if (infostring != null) {
			text += " It will summon a " + infostring  + ".";
		}
		return (text);
	}

	public boolean dropBlank (Player player) {
		//revert to blank
		final Item blankPetScroll = SingletonRepository.getEntityManager().getItem(
				"blank pet scroll");

		player.equipOrPutOnGround(blankPetScroll);
		player.sendPrivateText("You summon your pet back onto this plane. Faint smoke lingers from the page.");

		return true;
	}
	
	public boolean testSummonScroll(Player player) {
		return useScroll(player);
	}
}
	
