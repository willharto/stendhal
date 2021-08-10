package games.stendhal.server.maps.quests;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.*;
import games.stendhal.server.entity.npc.action.*;
import games.stendhal.server.entity.npc.condition.*;
import games.stendhal.server.entity.player.*;
import java.util.*;

public class LonJathamQuest extends AbstractQuest {
    public static final String QUEST_SLOT = "lon_jatham_quest";

    @Override
    public String getSlotName() {
        return QUEST_SLOT;
    }
    
    @Override
    public void addToWorld() {
    	fillQuestInfo(
				"Lon Jatham Quest",
				"Lon Jatham needs help enrolling students in the school of computer science.",
				false);
        prepareQuestStep();
        prepareBringingStep();
    }

    @Override
    public String getName() {
        return "LonJathamQuest";
    }
 
    @Override
    public List<String> getHistory(final Player player) {
        final List<String> res = new ArrayList<String>();
        if (!player.hasQuest(QUEST_SLOT)) {
        	return res;
        }
        res.add("I have talked to Lon Jatham.");
        final String questState = player.getQuest(QUEST_SLOT);
        if ("rejected".equals(questState)) {
        	res.add("I do not want to enroll students.");
        }
        if (player.isQuestInState(QUEST_SLOT, "start", "done")) {
        	res.add("I promised to give the form to the student to sign.");
        }
        
		if (("start".equals(questState) && player.isEquippedWithInfostring("note", "signed note")) || "done".equals(questState)) {
	    	res.add("I got the student to sign up.");
		}
        
        if ("done".equals(questState)) {
        	res.add("I gave the form to Lon Jatham. He gave me 25 pizzas and I got some experience.");
        }
        return res;
    }
    
    public void prepareQuestStep() {
    	prepareLonJatham();
    	prepareStudentOne();
    }
    
    public void prepareLonJatham() {
    	// get a reference to the Lon Jatham npc
        SpeakerNPC npc = npcs.get("Lon Jatham");
        
        // check that the player has not completed the quest, yet.
        npc.add(
        	ConversationStates.ATTENDING,
            ConversationPhrases.QUEST_MESSAGES, 
            new QuestNotCompletedCondition(QUEST_SLOT),
            ConversationStates.QUEST_OFFERED, 
            "Help me recruit students for the school of computer science.",
            null);
        
        // in state QUEST_OFFERED, accept "yes" and go back to ATTENDING
        npc.add(
            ConversationStates.QUEST_OFFERED,
            ConversationPhrases.YES_MESSAGES,
            null,
            ConversationStates.ATTENDING,
            "Get the student to sign this enrolment form.",
            new ChatAction() {
            	@Override
				public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
            		final Item note = SingletonRepository.getEntityManager().getItem("note");
            		   note.setInfoString("unsigned note");
            		   player.equipOrPutOnGround(note);
            	}});

        // in state QUEST_OFFERED, accept "no" and go back to ATTENDING
        npc.add(
            ConversationStates.QUEST_OFFERED,
            ConversationPhrases.NO_MESSAGES,
            null,
            ConversationStates.ATTENDING,
            "I am deeply saddened by your response.",
            null);
        
        // send him away if he has completed the quest already.
        npc.add(
        	ConversationStates.ATTENDING,
            ConversationPhrases.QUEST_MESSAGES,
            new QuestCompletedCondition(QUEST_SLOT),
            ConversationStates.ATTENDING, 
            "You've already recruited the students.",
            null);   
    }
    
    public void prepareStudentOne() {
    	// get a reference to the Student One npc
        SpeakerNPC npc = npcs.get("Student One");
    	
    	// player has the quest active and has a note with him, student will sign it
        npc.add(
            ConversationStates.ATTENDING,
            "sign",
            new PlayerHasInfostringItemWithHimCondition("note", "unsigned note"),
            ConversationStates.ATTENDING, 
            "Sure, I'll sign up to the school of Computer Science, here's my signature.",
            new MultipleActions(
            		new DropInfostringItemAction("note", "unsigned note"),
            		new ChatAction() {
                    	@Override
        				public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
                    		final Item note = SingletonRepository.getEntityManager().getItem("note");
                    		   note.setInfoString("signed note");
                    		   player.equipOrPutOnGround(note);
                    	}}));
    }
    
    private void prepareBringingStep() {
        SpeakerNPC npc = npcs.get("Lon Jatham");

        // if the players says "done" and has a signed note, we set the quest slot to "done".
        List<ChatAction> reward = new LinkedList<ChatAction>();
        reward.add(new DropInfostringItemAction("note", "signed note"));
        reward.add(new EquipItemAction("pizza", 25));
        reward.add(new IncreaseXPAction(50));
        reward.add(new SetQuestAction(QUEST_SLOT, "done"));
        
        npc.add(
             ConversationStates.ATTENDING,
             "done",
             new PlayerHasInfostringItemWithHimCondition("note", "signed note"),
             ConversationStates.ATTENDING,
             "Good job, now I have students to teach.",
             new MultipleActions(reward));
        
     // player has the quest active and has a signed note with him, ask for it
        npc.add(
            ConversationStates.IDLE,
            ConversationPhrases.GREETING_MESSAGES,
            new PlayerHasInfostringItemWithHimCondition("note", "signed note"),
            ConversationStates.QUEST_ITEM_BROUGHT, 
            "Is that the signed enrolment form?",
            null);

        // player has accepted the quest but did not bring a signed note, remind him
        npc.add(
            ConversationStates.IDLE,
            ConversationPhrases.GREETING_MESSAGES,
            new PlayerHasInfostringItemWithHimCondition("note", "unsigned note"),
            ConversationStates.ATTENDING, 
            "You still need to sign up those students.",
            null);
        
        npc.add(
             ConversationStates.QUEST_ITEM_BROUGHT,
             ConversationPhrases.YES_MESSAGES,
             new PlayerHasInfostringItemWithHimCondition("note", "signed note"),
             ConversationStates.ATTENDING,
             "Good job, now I have students to teach.",
             new MultipleActions(reward));

        // the player has a note but keeps it
        npc.add(
            ConversationStates.QUEST_ITEM_BROUGHT,
            ConversationPhrases.NO_MESSAGES,
            new PlayerHasInfostringItemWithHimCondition("note", "signed note"),
            ConversationStates.ATTENDING,
            "I need the signed forms so i can enroll students in the school.",
            null);
    }
}
/**
 * QUEST: Lon Jatham Quest
 * 
 * PARTICIPANTS:
 * <ul>
 * <li>Lon Jatham (Java lecturer in Deniran)</li>
 * <li>Student One (Student in Deniran)</li>
 * </ul>
 * 
 * STEPS:
 * <ul>
 * <li>Lon Jatham asks you to get students to sign the enrolment form .</li>
 * <li>Student One signs the form.</li>
 * <li>Player gives form back to Lon Jatham.</li>
 * </ul>
 * 
 * REWARD:
 * <ul>
 * <li>25 pizzas</li>
 * <li>50 XP</li>
 * </ul>
 * 
 * REPETITIONS:
 * <ul>
 * <li>None</li>
 * </ul>
 */