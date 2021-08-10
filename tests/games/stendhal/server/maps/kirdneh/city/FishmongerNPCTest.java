/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.kirdneh.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.EquipActionConsts;
import games.stendhal.server.actions.equip.EquipAction;
import games.stendhal.server.actions.equip.EquipmentAction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import marauroa.common.game.RPAction;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

/**
 * Test selling lionfish
 * @author Martin Fuchs
 */
public class FishmongerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "int_ados_felinas_house";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public FishmongerNPCTest() {
		setNpcNames("Fishmonger");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new KirdnehFishyMarketNPC(), ZONE_NAME);
	}


	/**
	 * Tests for sell 
	 */
	@Test
	public void testSellLionFish() {
		
		StendhalRPZone localzone = new StendhalRPZone("testzone", 20, 20);
		StackableItem item = (StackableItem) SingletonRepository.getEntityManager().getItem("red lionfish");
		System.out.println(item);
		item.setQuantity(1);
		localzone.add(item);
		localzone.add(player);
		RPAction equip = new RPAction();
		equip.put("type", "equip");
		equip.put(EquipActionConsts.BASE_ITEM, item.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_OBJECT, player.getID().getObjectID());
		equip.put(EquipActionConsts.TARGET_SLOT, "bag");
		equip.put(EquipActionConsts.QUANTITY, "1");
		final EquipmentAction action = new EquipAction();
		action.onAction(player, equip);
		
		final SpeakerNPC npc = getNPC("Fishmonger");
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "hi"));
		assertEquals("Ahoy, me hearty! Back from yer swashbucklin, ah see.", getReply(npc));
		
		assertTrue(player.isEquipped("red lionfish", 1));
		
		assertTrue(en.step(player, "sell red lionfish"));
		assertEquals("A red lionfish is worth 120. Do you want to sell it?", getReply(npc));
		assertTrue(en.step(player, "yes"));
		assertEquals("Thanks! Here is your money.", getReply(npc));	
		
		assertFalse(player.isEquipped("red lionfish"));
		assertTrue(player.isEquipped("money", 120));

			
		
		
		
	}

}
