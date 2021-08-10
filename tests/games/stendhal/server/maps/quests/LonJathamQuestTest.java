package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.deniran.LonJatham;
import games.stendhal.server.maps.deniran.StudentOne;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class LonJathamQuestTest {

	// better: use the one from quest and make it visible
	//private static final String QUEST_SLOT = "lon_jatham_quest";

	private Player player = null;
	private SpeakerNPC npcOne = null;
	private SpeakerNPC npcTwo = null;
	private Engine en = null;
	AbstractQuest quest = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}

	@Before
	public void setUp() {
		PlayerTestHelper.removeAllPlayers();
		final StendhalRPZone zone = new StendhalRPZone("admin_test");
		new LonJatham().configureZone(zone, null);
		new StudentOne().configureZone(zone, null);
		
		player = PlayerTestHelper.createPlayer("bob");
		zone.add(player);

	    quest = new LonJathamQuest();
		quest.addToWorld();

		//player = PlayerTestHelper.createPlayer("bob");
	}
	
	@After
	public void tearDown() {
		PlayerTestHelper.removeNPC("Lon Jatham");
		PlayerTestHelper.removeNPC("Student One");
	}
	
	@Test
	public void testQuest() {
		npcOne = SingletonRepository.getNPCList().get("Lon Jatham");
		npcTwo = SingletonRepository.getNPCList().get("Student One");
		en = npcOne.getEngine();

		en.step(player, "hi");
		assertEquals("Hello!", getReply(npcOne));
		en.step(player, "quest");
		assertEquals("Help me recruit students for the school of computer science.", getReply(npcOne));
		en.step(player, "no");
		assertEquals("I am deeply saddened by your response.", getReply(npcOne));
		en.step(player, "quest");
		assertEquals("Help me recruit students for the school of computer science.", getReply(npcOne));
		en.step(player, "yes");
		assertEquals("Get the student to sign this enrolment form.", getReply(npcOne));
		en.step(player, "bye");
		assertEquals("Goodbye!", getReply(npcOne));
		
	}
}
