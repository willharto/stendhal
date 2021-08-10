package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.BabyDragon;
import games.stendhal.server.entity.creature.Cat;
import games.stendhal.server.entity.item.scroll.SummonPetScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.player.PlayerPetManager;
import utilities.PlayerTestHelper;

public class SummonPetScrollTest {

	@Test
	public void testNameIsNotResetWhenPetSummoned() {
		final StendhalRPZone zone = new StendhalRPZone("0_fado_forest");
		SingletonRepository.getRPWorld().addRPZone(zone);
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		bob.setPosition(13, 46);
		final BabyDragon pet = new BabyDragon(bob);
		pet.setName("name1");
		final Item summonPetScroll = SingletonRepository.getEntityManager().getItem(
				"summon pet scroll");
		summonPetScroll.setInfoString("baby_dragon");
		pet.getZone().remove(pet);
		PlayerPetManager beastMaster = new PlayerPetManager(bob);
		beastMaster.storePet(pet);
		bob.removePet(pet);
		((SummonPetScroll) summonPetScroll).testSummonScroll(bob);
		assertEquals("name1", bob.getPet().getName());
	}
	
	@Test
	public void testHPIsNotResetWhenPetSummoned() {
		final StendhalRPZone zone = new StendhalRPZone("0_fado_forest");
		SingletonRepository.getRPWorld().addRPZone(zone);
		final Player bob = PlayerTestHelper.createPlayer("bob");
		zone.add(bob);
		bob.setPosition(13, 46);
		final Cat pet = new Cat(bob);
		pet.setHP(404);
		final Item summonPetScroll = SingletonRepository.getEntityManager().getItem(
				"summon pet scroll");
		summonPetScroll.setInfoString("cat");
		pet.getZone().remove(pet);
		PlayerPetManager beastMaster = new PlayerPetManager(bob);
		beastMaster.storePet(pet);
		bob.removePet(pet);
		((SummonPetScroll) summonPetScroll).testSummonScroll(bob);
		assertEquals(404, bob.getPet().getHP());
	}
}
