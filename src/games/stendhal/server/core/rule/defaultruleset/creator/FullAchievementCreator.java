/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.core.rule.defaultruleset.creator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
import games.stendhal.server.core.rp.achievement.Achievement;

/**
 * Create an item class via the full arguments (<em>name, clazz,
 * subclazz, attributes</em>)
 * constructor.
 */
public class FullAchievementCreator extends AbstractAchievementCreator {

	public FullAchievementCreator(DefaultAchievement defaultAchievement, final Constructor< ? > construct) {
		super(defaultAchievement, construct);
	}

	@Override
	protected Achievement createObject() throws IllegalAccessException,
			InstantiationException, InvocationTargetException {
		return (Achievement) construct.newInstance(defaultAchievement.getIdentifier(), defaultAchievement.getTitle(), defaultAchievement.getCategory(), 
				                                   defaultAchievement.getDescription(), defaultAchievement.getBaseScore(), defaultAchievement.getActive(),
				                                   defaultAchievement.getCondition());
	}
}