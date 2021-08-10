/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.quests.EmotionCrystals;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class EmotionCrystalTest {

	private Player player = null;
	private AbstractQuest quest = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		QuestHelper.setUpBeforeClass();
	}

	@Before
	public void setUp() {
		player = PlayerTestHelper.createPlayer("player");
		quest = new EmotionCrystals();
		//quest.addToWorld();
	}

	/**
	 * Tests for getCorrectTravelLogHistory.
	 */
	@Test
	public void testGetCorrectTravelLogHistory() {
		player.setQuest(new EmotionCrystals().getSlotName(),"start");
		final Item redCrystal = ItemTestHelper.createItem("red emotion crystal");
		final Item blueCrystal = ItemTestHelper.createItem("blue emotion crystal");
		final Item yellowCrystal = ItemTestHelper.createItem("yellow emotion crystal");
		
		player.getSlot("bag").add(redCrystal);
		player.getSlot("bag").add(blueCrystal);
		player.getSlot("bag").add(yellowCrystal);

		List<String> expectedLog = quest.getHistory(player);
		
		player.drop(redCrystal);
		player.drop(blueCrystal);
		player.drop(yellowCrystal);

		List<String> actualLog = quest.getHistory(player);
		
		assertEquals(actualLog,expectedLog);
	}
	
}
