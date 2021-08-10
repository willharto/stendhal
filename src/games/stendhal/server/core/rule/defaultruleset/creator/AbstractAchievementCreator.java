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

import games.stendhal.server.core.rule.defaultruleset.DefaultAchievement;
import games.stendhal.server.core.rp.achievement.Achievement;

/**
 * Base item creator (using a constructor).
 */
abstract class AbstractAchievementCreator extends AbstractCreator<Achievement>{

	
	/**
	 *
	 */
	final DefaultAchievement defaultAchievement;

	public AbstractAchievementCreator(DefaultAchievement defaultAchievement, final Constructor< ? > construct) {
		super(construct, "Achievement");
		this.defaultAchievement = defaultAchievement;
	}
}