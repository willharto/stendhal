package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.rule.defaultruleset.DefaultSpell;
import games.stendhal.server.entity.spell.Spell;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;

public class AchievementXMLLoaderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockStendlRPWorld.get();
		new DatabaseFactory().initializeDatabase();
	}
	
	
	@Test
	public void testAchievement() throws URISyntaxException, SAXException, IOException {
		AchievementXMLLoaderTest achievement = new AchievementXMLLoaderTest(new URI("achievementstest.xml"));
		
		List<Achievement> list = loader.load();
		
		
		
	

	}

}
