package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import java.util.Map;

public class NewsNPC implements ZoneConfigurator {
	
  /**
  * Configure a zone.
  *
  * @param	zone		The zone to be configured.
  * @param	attributes	Configuration attributes.
  */
  @Override
public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
	buildNPC(zone);
  }

  private void buildNPC(final StendhalRPZone zone){
    final SpeakerNPC npc = new SpeakerNPC("Mrs Swanson") {
    	  
    @Override
	protected void createPath() {
        setPath(null);
    }
    
	@Override
	protected void createDialog(){
		addGreeting("Hello would you like to read todays news?");
		add(ConversationStates.ATTENDING, ConversationPhrases.YES_MESSAGES, null,
			ConversationStates.ATTENDING, "There are some big stories in there!", new EquipItemAction("newspaper"));
        addGoodbye("See you soon for new stories");
    }
  };

  npc.setEntityClass("welcomernpc");
  npc.setDescription("You see Mrs Swanson, she looks like she has news for you");
  npc.setPosition(82, 58);
  npc.initHP(100);
  zone.add(npc); 
  }
}