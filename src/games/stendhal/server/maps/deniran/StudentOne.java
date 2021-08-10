package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import java.util.Map;

public class StudentOne implements ZoneConfigurator {

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Student One") {
			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting("Hello!");
				addGoodbye("Goodbye!");
			}
		};

		npc.setEntityClass("youngnpc");
		npc.setDescription("You see a student, maybe they will enroll in the school of computer science.");
		npc.setPosition(15, 5);
		npc.initHP(100);

		zone.add(npc);   
	}
}