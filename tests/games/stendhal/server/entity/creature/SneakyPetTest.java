package games.stendhal.server.entity.creature;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPObject;
import utilities.PlayerTestHelper;
import utilities.RPClass.SheepTestHelper;

public class SneakyPetTest 
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		assertTrue(MockStendlRPWorld.get() instanceof MockStendlRPWorld);
		SheepTestHelper.generateRPClasses();
	}
	//@Test
	/*public void TestIfMonkeySteals() 
	{
		//create the world
		final StendhalRPZone zone = new StendhalRPZone("zone");
		//create the players
		final Player andros = PlayerTestHelper.createPlayer("andros");//the owner of the pet
		final Player mario = PlayerTestHelper.createPlayer("mario");//the one the pet will steal from
		//create the pet
		final SneakyPet alva = new SneakyPet(andros);
		
		
		//add the players and the pet
		zone.add(andros);
		zone.add(mario);
		zone.add(alva);
        //final StendhalRPZone zone = new StendhalRPZone("testzone", 10, 10);
		
		//create and equip a dice to andros
		final Dice dice = (Dice) SingletonRepository.getEntityManager().getItem("dice");
		andros.equip("bag", dice);
		
		//place the monkey next to the person
		andros.setPosition(6, 7);
		alva.setPosition(6, 8);
		
		//monkey 
		alva.steal(mario);
		
		assertFalse(andros.isEquippedItemInSlot("bag", "dice"));
		assertTrue(alva.isEquippedItemInSlot("bag", dice));
	}*/
	List<String> foods = Arrays.asList("chicken", "trout", "cod", "mackerel", "char",
			"perch", "roach", "surgeonfish", "clownfish", "milk");
	@Test
	public void testSneakyPetlayer() 
	{
		//create the world
		final StendhalRPZone zone = new StendhalRPZone("zone");
		//create the player
		final Player andros = PlayerTestHelper.createPlayer("andros");
		//add the player
		zone.add(andros);
		//create the pet
		final SneakyPet alva = new SneakyPet(andros);
		//give food
		assertThat(alva.getFoodNames(), is(foods));
	}

	/**
	 * Tests for snekayPetRPObjectPlayer.
	 */
	@Test
	public void testSneakyPetRPObjectPlayer() 
	{
		RPObject template = new RPObject();
		template.put("hp", 30);
		final SneakyPet alva = new SneakyPet(template, PlayerTestHelper.createPlayer("andros"));
		assertThat(alva.getFoodNames(), is(foods));
	}

}
