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
package games.stendhal.server.maps.deniran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.ZonePlayerAndNPCTestImpl;

public class NewsNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "0_deniran_s";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        setupZone(ZONE_NAME);
	}
	
	public NewsNPCTest() {
		setNpcNames("Mrs Swanson");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new NewsNPC(), ZONE_NAME);
	}
	
	@Test
	public void testHiAndBye () {
		final SpeakerNPC npc = getNPC("Mrs Swanson");
		final Engine en = npc.getEngine();
		assertTrue(en.step(player, "hello"));
		assertEquals("Hello would you like to read todays news?", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("See you soon for new stories", getReply(npc));
	}
	
	@Test
	public void testWhenAskedForNews () {
		final SpeakerNPC npc = getNPC("Mrs Swanson");
		final Engine en = npc.getEngine();
		
		assertTrue(en.step(player, "hi"));
		assertEquals("Hello would you like to read todays news?", getReply(npc));
		
		assertTrue(en.step(player, "yes"));
		assertEquals("There are some big stories in there!", getReply(npc));
		assertTrue(player.isEquipped("newspaper"));
	}
}
