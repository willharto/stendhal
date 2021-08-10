/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.core.rule.defaultruleset;

import java.lang.reflect.Constructor;
import org.apache.log4j.Logger;

import games.stendhal.server.core.rule.defaultruleset.creator.AbstractCreator;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.core.rule.defaultruleset.creator.FullAchievementCreator;
import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;

public class DefaultAchievement {
	
	private static final Logger logger = Logger.getLogger(DefaultAchievement.class);
	
	private AbstractCreator<Achievement> creator;
	
	private String identifier;
	
	private String title;
	 
	private Category category;
	
	private String description;
	
	private int baseScore;
	
	private boolean active;
	
	private ChatCondition condition;
	
	public DefaultAchievement(final String identifier, final String title, final Category category, final String description, final int baseScore, final boolean active, final ChatCondition condition) {
		this.identifier = identifier;
		this.title = title;
		this.category = category;
		this.condition = condition;
		this.description = description;
		this.baseScore = baseScore;
		this.active = active;
	}
	
	protected AbstractCreator<Achievement> buildCreator(final Class< ? > implementation) {
		Constructor< ? > construct;
		
		try {
			construct = implementation.getConstructor(new Class[] {
					String.class, String.class, Category.class, String.class, int.class, boolean.class, ChatCondition.class });

			this.creator = new FullAchievementCreator(this, construct);
		} catch (final NoSuchMethodException ex) {
			logger.error("No matching full constructor for Achievement found.", ex);
    }
		return null;
	}
		
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(final Category category) {
		this.category = category;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(final String description) {
		this.description = description;
	}
	public int getBaseScore() {
		return baseScore;
	}
	
	public void setBaseScore(final int baseScore) {
		this.baseScore = baseScore;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public void setActive(final boolean active) {
		this.active = active;
	}
	
	public ChatCondition getCondition() {
		return condition;
	}
	
	public void setCondition(final ChatCondition condition) {
		this.condition = condition;
	}
	
}
	
