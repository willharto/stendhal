package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import java.util.Map;

public class LonJatham implements ZoneConfigurator {

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Lon Jatham") {
			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting("Hello!");
				addQuest("I have a quest for you, help me recruit students for the school of computer science.");
				addGoodbye("Goodbye!");
			}
		};

		npc.setEntityClass("yetimalenpc");
		npc.setDescription("You see Lon Jatham, he might need your help.");
		npc.setPosition(9, 5);
		npc.initHP(100);

		zone.add(npc);   
	}
}