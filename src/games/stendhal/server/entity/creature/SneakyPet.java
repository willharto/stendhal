/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.creature;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPClass;
import marauroa.common.game.RPObject;
import marauroa.common.game.SyntaxException;

/**
 * A sneaky cat is a domestic animal that can be owned by a player.
 * <p>
 * They move much faster than sheep
 *
 * @author william (based on cat by kymara)
 *
 */
public class SneakyPet extends Pet {

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(SneakyPet.class);

	@Override
	void setUp() {
		HP = 200;
		ATK = 10;
		DEF = 30;
		XP = 100;
		baseSpeed = 0.9;

		setAtk(ATK);
		setDef(DEF);
		setXP(XP);
		setBaseHP(HP);
		setHP(HP);
	}

	public static void generateRPClass() {
		try {
			final RPClass sneakypet = new RPClass("sneaky_pet");
			sneakypet.isA("pet");
		} catch (final SyntaxException e) {
			logger.error("cannot generate RPClass", e);
		}
	}

	/**
	 * Creates a new wild sneaky pet.
	 */
	public SneakyPet() {
		this(null);
	}

	/**
	 * Creates a new sneaky pet that may be owned by a player.
	 * @param owner owning player, or <code>null</code>
	 */
	public SneakyPet(final Player owner) {
		// call set up before parent constructor is called as it needs those
		// values
		super();
		setOwner(owner);
		setUp();
		setRPClass("sneaky_pet");
		put("type", "sneaky_pet");

		if (owner != null) {
			// add pet to zone and create RPObject.ID to be used in setPet()
			owner.getZone().add(this);
			owner.setPet(this);
		}

		update();
	}

	/**
	 * Creates a sneaky pet based on an existing sneaky pet RPObject, and assigns it to a
	 * player.
	 *
	 * @param object object containing the data for the sneaky pet
	 * @param owner
	 *            The player who should own the sneaky pet
	 */
	public SneakyPet(final RPObject object, final Player owner) {
		super(object, owner);
		setRPClass("sneaky_pet");
		put("type", "sneaky_pet");
		update();
	}

	@Override
	protected List<String> getFoodNames() {
		return Arrays.asList("chicken", "trout", "cod", "mackerel", "char",
				"perch", "roach", "surgeonfish", "clownfish", "milk");
	}
	
	/**
	 * Does this domestic animal take part in combat?
	 *
	 * @return true, if it can be attacked by creatures, false otherwise
	 */
	@Override
	protected boolean takesPartInCombat() {
		return false;
	}


}
