package games.stendhal.server.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Collections.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import marauroa.common.Pair;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.core.rp.achievement.factory.FightingAchievementFactory;
import games.stendhal.server.core.rp.achievement.factory.ExperienceAchievementFactory;
import games.stendhal.server.core.rule.defaultruleset.DefaultItem;
import games.stendhal.server.entity.item.behavior.UseBehavior;

public final class AchievementsXMLLoader extends DefaultHandler {
	
	private static final Logger LOGGER = Logger.getLogger(AchievementsXMLLoader.class);
//	private Class< ? > implementation;
//	private Class<?> behaviorClass;
//	private Map<String, String> behaviorMap;
//	private UseBehavior useBehavior;

	private String identifier;
	private String title;
	private Category category;
	private String description;
	private int baseScore;
	private boolean active;
	private String conditionString;
	private boolean conditionBool;


	private List<Pair<String, Pair<LinkedList<String>, LinkedList<String>>>> conditions;
//	private List<String> slots;
//	private Map<String, String> attributes;
//	private List<DefaultItem> list;
//	private boolean attributesTag;
//	private String damageType;
//	private Map<String, Double> susceptibilities = new HashMap<String, Double>();
//	private String activeSlots;
//	private Map<String, Double> resistances = new HashMap<String, Double>();
	private List<Achievement> list;
	private static LinkedList<String> types;
	private static LinkedList<String> arguments;
	private Pair<LinkedList<String>, LinkedList<String>> typesArguments;
	


	public Collection<Achievement> load(final URI uri) throws SAXException {
		list = new LinkedList<Achievement>();
		list.addAll(new FightingAchievementFactory().createAchievements());
		list.addAll(new ExperienceAchievementFactory().createAchievements());
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			final SAXParser saxParser = factory.newSAXParser();
			final InputStream is = AchievementsXMLLoader.class.getResourceAsStream(uri.getPath());
			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			LOGGER.error(t);
		} catch (final IOException e) {
			LOGGER.error(e);
			throw new SAXException(e);
		}
		return list;
	}
	
	public static Object[] getArguments(String[] arguments, String[] types) { return null;}
	
	public static Object[] getArg(String arg, String type) { return null;}
	
	public static Class<?>[] getClasses(Object[] objects) { return null;}

	@Override
	public void startDocument() {
	}

	@Override
	public void endDocument() {
	}

	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		if (qName.equals("category")) {
			category = Category.valueOf(attrs.getValue("value"));
		} 
		
		else if (qName.equals("description")) {
			description = attrs.getValue("value");
		} 
		
		else if (qName.equals("title")) {
			title = attrs.getValue("value");
		} 
		
		else if (qName.equals("identifier")) {
			identifier = attrs.getValue("value");
		} 
		
		else if (qName.equals("baseScore")) {
			baseScore = Integer.parseInt(attrs.getValue("value"));
		} 
		
		else if (qName.equals("active")) {
			active = Boolean.parseBoolean(attrs.getValue("value"));
		}
		
		else if (qName.equals("conditions")) {
			conditions = new ArrayList<Pair<String, Pair<LinkedList<String>, LinkedList<String>>>>();
		}
		
		else if (qName.equals("condition")) {
			conditionString = attrs.getValue("value");
			arguments = new LinkedList<String>();
			types = new LinkedList<String>();
			conditionBool = true;
		}
		
		else if (conditionBool == true) {
			if (qName.equals("argument")) {
				arguments.add(attrs.getValue("value"));
				types.add(attrs.getValue("type"));
			}
		}
	}

	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		if (qName.equals("achievement")) {
			//List<ChatCondition> andConditions = new ArrayList<ChatCondition>();
			String classString;
			for (Pair<String, Pair<LinkedList<String>, LinkedList<String>>> a : conditions) {
				if (a.first().equals("KilledSharedAllCreaturesCondtion")) {
					classString = "games.stendhal.server.core.rp.achievement.condition." + a.first();
				}
				else {
					classString = "games.stendhal.server.entity.npc.condition." + a.first();
				}
				Pair <LinkedList<String>, LinkedList<String>> typesArguments = a.second();
				String[] typeString = new String[typesArguments.second().size()];
				typesArguments.second().toArray(typeString);
				String[] argumentString = new String[typesArguments.first().size()];
				typesArguments.first().toArray(argumentString);
				//Object[] result;
				Class<?> clazz = null;
				try {
					clazz = Class.forName(classString);
				}
				catch (ClassNotFoundException e) {
					
				}
				//Constructor<?> ctor = null;
				assert clazz != null;
				try {
					if (classString.equals("games.stendhal.server.entity.npc.condition.PlayerHasKilledNumberOfCreaturesCondition")) {}
				}
				catch (/*NoSuchMethodException | */SecurityException e) {
					
				}
			}
		}
	}

	@Override
	public void characters(final char[] buf, final int offset, final int len) {
		description = description + (new String(buf, offset, len)).trim();
	}

}
