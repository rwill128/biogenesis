/* Copyright (C) 2006-2010  Joan Queralt Molina
 * Color Mod (C) 2012-2016  MarcoDBAA
 * Backup function by Tyler Coleman
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

import net.jafama.FastMath;

import java.util.*;
import java.util.prefs.*;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class contains all global program parameters and a few useful methods for
 * reading or writing them from the user preferences.
 */
public final class Utils {
	/**
	 * This value indicates the version of the program.
	 * There are two digits for version, two for subversion and two for revision, so
	 * a value of 400 or 000400 means version 0, subversion 4 and revision 0.
	 * 
	 * All serializable classes use this value as their serialVersionUID.
	 */
	static final int FILE_VERSION = 700;
	static final int VERSION = 800; //two digits for version, subversion and revision

	// Default values for parameters
	final static int DEF_WINDOW_X = 0;
	final static int DEF_WINDOW_Y = 0;
	final static int DEF_WINDOW_WIDTH = 640;
	final static int DEF_WINDOW_HEIGHT = 480;
	final static int DEF_WINDOW_STATE = JFrame.NORMAL;
	/**
	 * This is the default value of organisms that are created when a new world begins.
	 */
	final static int DEF_INITIAL_ORGANISMS = 2500;
	/**
	 * This is the default complexity of random organisms. 
	 */
	final static int DEF_INITIAL_COMPLEXITY = 2;
	/**
	 * This is the default amount of O2 that exists in a newly created world.
	 */
	final static double DEF_INITIAL_O2 = 0;
	/**
	 * This is the default amount of CO2 that exists in a newly created world.
	 */
	final static double DEF_INITIAL_CO2 = 100000;
	/**
	 * This is the initial size of the organisms vector.
	 */
	final static int DEF_ORGANISMS_VECTOR_SIZE = 50;
	/**
	 * This is the default world's width.
	 */
	final static int DEF_WORLD_WIDTH = 3000;
	/**
	 * This is the default world's height.
	 */
	final static int DEF_WORLD_HEIGHT = 3000;
	/**
	 * This is the default maximum age that an organism can achieve, 
	 * without counting the number of segments.
	 */
	final static int DEF_MAX_AGE = 6;
	/**
	 * This is the default age divisor, 
	 * which adds the number of segments to the maximum age, divided by this value.
	 */
	final static int DEF_AGE_DIVISOR = 4;
	/**
	 * This is the default rubbing coefficient that is applied to movements. This value is
	 * multiplied by the speed at every frame.
	 */
	final static double DEF_RUBBING = 0.98d;
	/**
	 * This is the default probability that an specified gene has to be modified when
	 * reproducing organisms. 
	 */
	final static double DEF_MUTATION_RATE = 0.01d;
	/**
	 * This default value is used to calculate the energy cost that an organism must
	 * pay to maintain a segment. It spends the length of the segment divided by this
	 * number units of energy.  
	 */
	final static int DEF_SEGMENT_COST_DIVISOR = 5000;
	/**
	 * This default value is multiplied by the length of red segments to calculate
	 * the amount of energy that is stolen from an organism when it is touched.
	 */
	final static double DEF_ORGANIC_OBTAINED_ENERGY = 7d;
	/**
	 * This default value divides the length of green segments to calculate the
	 * amount of solar energy that the segment can get in a frame.
	 */
	final static int DEF_GREEN_OBTAINED_ENERGY_DIVISOR = 500;
	/**
	 * This is the default value that determines how much energy will be released
	 * to the atmosphere when an organism touches another organism with a consuming segment.
	 * The energy that the target lost is multiplied by this value and the result
	 * is added to the atmospheric CO2. The rest is added to the attacker's energy.
	 */
	final static double DEF_ORGANIC_SUBS_PRODUCED = 0.1d;
	/**
	 * This is the default value that determines how much energy will be released
	 * to the atmosphere when an organism touches another organism with a cream segment.
	 * The energy that the target lost is multiplied by this value and the result
	 * is added to the atmospheric CO2. The rest is added to the attacker's energy.
	 */
	final static double DEF_CREAM_ORGANIC_SUBS_PRODUCED = 0d;
	/**
	 * This is the default healing efficiency.
	 * Healing is more efficient, if this value is lower.
	 */
	final static int DEF_HEALING = 4500;
	/**
	 * This is the default immune system efficiency.
	 * Immune system is more efficient, if this value is lower.
	 */
	final static int DEF_IMMUNE_SYSTEM = 4500;
	/**
	 * This is the default indigo efficiency divisor.
	 * Indigo is more efficient, if this value is lower.
	 */
	final static int DEF_INDIGO_DIVISOR = 9;
	/**
	 * This is the default gold efficiency divisor.
	 * Gold is more efficient, if this value is lower.
	 */
	final static double DEF_GOLD_DIVISOR = 3;
	/**
	 * This is the default energy that is consumed when an organism dodged an attack.
	 */
	final static double DEF_DODGE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a dark jade segment regenerates.
	 */
	final static int DEF_DARKJADE_DELAY = 18;
	/**
	 * This is the default energy that is consumed when a red segment is used.
	 */
	final static double DEF_RED_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a green segment is used.
	 */
	final static double DEF_GREEN_ENERGY_CONSUMPTION = 1.1d;
	/**
	 * This is the default energy that is consumed when a blue segment is used.
	 */
	final static double DEF_BLUE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a cyan segment is used.
	 */
	final static double DEF_CYAN_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a white segment is used.
	 */
	final static double DEF_WHITE_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a white non-plant segment is used.
	 */
	final static double DEF_VIRUS_ENERGY_CONSUMPTION = 0.01d;
	/**
	 * This is the default energy that is consumed when a gray segment is used.
	 */
	final static double DEF_GRAY_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a dark gray segment (for enhanced spike) is used.
	 */
	final static double DEF_DARKGRAY_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a silver segment is used.
	 */
	final static double DEF_SILVER_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a yellow segment is used.
	 */
	final static double DEF_YELLOW_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a magenta segment is used.
	 */
	final static double DEF_MAGENTA_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a pink segment is used.
	 */
	final static double DEF_PINK_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a coral segment is used.
	 */
	final static double DEF_CORAL_ENERGY_CONSUMPTION = 1d;
	/**
	 * This is the default energy that is consumed when a orange segment is used.
	 */
	final static double DEF_ORANGE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a forest segment is used.
	 */
	final static double DEF_FOREST_ENERGY_CONSUMPTION = 1d;
	/**
	 * This is the default energy that is consumed when a spring segment is used.
	 */
	final static double DEF_SPRING_ENERGY_CONSUMPTION = 1d;
	/**
	 * This is the default energy that is consumed when a lime segment is used.
	 */
	final static double DEF_LIME_ENERGY_CONSUMPTION = 1.3d;
	/**
	 * This is the default energy that is consumed when a crowded lime segment is used.
	 */
	final static double DEF_CROWDEDLIME_ENERGY_CONSUMPTION = 0.6d;
	/**
	 * This is the default energy that is consumed when a bark segment is used.
	 */
	final static double DEF_BARK_ENERGY_CONSUMPTION = 0.75d;
	/**
	 * This is the default energy that is consumed when a jade segment is used.
	 */
	final static double DEF_JADE_ENERGY_CONSUMPTION = 1d;
	/**
	 * This is the default energy that is consumed when a grass segment is used.
	 */
	final static double DEF_GRASS_ENERGY_CONSUMPTION = 1d;
	/**
	 * This is the default energy that is consumed when a c4 segment is used.
	 */
	final static double DEF_C4_ENERGY_CONSUMPTION = 0.6d;
	/**
	 * This is the default energy that is consumed when a violet segment is used.
	 */
	final static double DEF_VIOLET_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a teal segment is used.
	 */
	final static double DEF_TEAL_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a maroon segment is used.
	 */
	final static double DEF_MAROON_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a olive segment is used.
	 */
	final static double DEF_OLIVE_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a mint segment is used.
	 */
	final static double DEF_MINT_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a cream segment is used.
	 */
	final static double DEF_CREAM_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a rose segment is used.
	 */
	final static double DEF_ROSE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a dark segment is used.
	 */
	final static double DEF_DARK_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when an ochre segment is used.
	 */
	final static double DEF_OCHRE_ENERGY_CONSUMPTION = 0.2d;
	/**
	 * This is the default energy that is consumed when a sky segment is used.
	 */
	final static double DEF_SKY_ENERGY_CONSUMPTION = 0.1d;
	/**
	 * This is the default energy that is consumed when a lilac segment is used.
	 */
	final static double DEF_LILAC_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a fire segment is used.
	 */
	final static double DEF_FIRE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a consuming silver segment is used.
	 */
	final static double DEF_EXPERIENCE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a blond segment is used.
	 */
	final static double DEF_BLOND_ENERGY_CONSUMPTION = 0.02d;
	/**
	 * This is the default energy that is consumed when a auburn segment is used.
	 */
	final static double DEF_AUBURN_ENERGY_CONSUMPTION = 0.01d;
	/**
	 * This is the default energy that is consumed when an indigo segment is used.
	 */
	final static double DEF_INDIGO_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default energy that is consumed when a plague segment is used.
	 */
	final static double DEF_PLAGUE_ENERGY_CONSUMPTION = 5d;
	/**
	 * This is the default energy that is consumed when a non-plant plague segment is used.
	 */
	final static double DEF_SCOURGE_ENERGY_CONSUMPTION = 0.2d;
	/**
	 * This is the default energy that is consumed when a spike segment is used.
	 */
	final static double DEF_SPIKE_ENERGY_CONSUMPTION = 0d;
	/**
	 * This is the default probability for a new segment to be red.
	 */
	final static int DEF_RED_PROB = 1;
	/**
	 * This is the default probability for a new segment to be green.
	 */
	final static int DEF_GREEN_PROB = 2;
	/**
	 * This is the default probability for a new segment to be blue.
	 */
	final static int DEF_BLUE_PROB = 1;
	/**
	 * This is the default probability for a new segment to be cyan.
	 */
	final static int DEF_CYAN_PROB = 7;
	/**
	 * This is the default probability for a new segment to be white.
	 */
	final static int DEF_WHITE_PROB = 4;
	/**
	 * This is the default probability for a new segment to be gray.
	 */
	final static int DEF_GRAY_PROB = 1;
	/**
	 * This is the default probability for a new segment to be dark gray.
	 */
	final static int DEF_DARKGRAY_PROB = 2;
	/**
	 * This is the default probability for a new segment to be silver.
	 */
	final static int DEF_SILVER_PROB = 1;
	/**
	 * This is the default probability for a new segment to be yellow.
	 */
	final static int DEF_YELLOW_PROB = 2;
	/**
	 * This is the default probability for a new segment to be magenta.
	 */
	final static int DEF_MAGENTA_PROB = 2;
	/**
	 * This is the default probability for a new segment to be pink.
	 */
	final static int DEF_PINK_PROB = 1;
	/**
	 * This is the default probability for a new segment to be coral.
	 */
	final static int DEF_CORAL_PROB = 1;
	/**
	 * This is the default probability for a new segment to be orange.
	 */
	final static int DEF_ORANGE_PROB = 1;
	/**
	 * This is the default probability for a new segment to be forest.
	 */
	final static int DEF_FOREST_PROB = 2;
	/**
	 * This is the default probability for a new segment to be spring.
	 */
	final static int DEF_SPRING_PROB = 2;
	/**
	 * This is the default probability for a new segment to be lime.
	 */
	final static int DEF_LIME_PROB = 2;
	/**
	 * This is the default probability for a new segment to be bark.
	 */
	final static int DEF_BARK_PROB = 2;
	/**
	 * This is the default probability for a new segment to be jade.
	 */
	final static int DEF_JADE_PROB = 2;
	/**
	 * This is the default probability for a new segment to be grass.
	 */
	final static int DEF_GRASS_PROB = 2;
	/**
	 * This is the default probability for a new segment to be c4.
	 */
	final static int DEF_C4_PROB = 2;
	/**
	 * This is the default probability for a new segment to be violet.
	 */
	final static int DEF_VIOLET_PROB = 1;
	/**
	 * This is the default probability for a new segment to be teal.
	 */
	final static int DEF_TEAL_PROB = 7;
	/**
	 * This is the default probability for a new segment to be maroon.
	 */
	final static int DEF_MAROON_PROB = 2;
	/**
	 * This is the default probability for a new segment to be olive.
	 */
	final static int DEF_OLIVE_PROB = 1;
	/**
	 * This is the default probability for a new segment to be mint.
	 */
	final static int DEF_MINT_PROB = 2;
	/**
	 * This is the default probability for a new segment to be cream.
	 */
	final static int DEF_CREAM_PROB = 1;
	/**
	 * This is the default probability for a new segment to be rose.
	 */
	final static int DEF_ROSE_PROB = 2;
	/**
	 * This is the default probability for a new segment to be dark.
	 */
	final static int DEF_DARK_PROB = 1;
	/**
	 * This is the default probability for a new segment to be ochre.
	 */
	final static int DEF_OCHRE_PROB = 1;
	/**
	 * This is the default probability for a new segment to be sky.
	 */
	final static int DEF_SKY_PROB = 1;
	/**
	 * This is the default probability for a new segment to be lilac.
	 */
	final static int DEF_LILAC_PROB = 1;
	/**
	 * This is the default probability for a new segment to be fire.
	 */
	final static int DEF_FIRE_PROB = 1;
	/**
	 * This is the default probability for a new segment to be gold.
	 */
	final static int DEF_GOLD_PROB = 2;
	/**
	 * This is the default probability for a new segment to be blond.
	 */
	final static int DEF_BLOND_PROB = 2;
	/**
	 * This is the default probability for a new segment to be auburn.
	 */
	final static int DEF_AUBURN_PROB = 2;
	/**
	 * This is the default probability for a new segment to be indigo.
	 */
	final static int DEF_INDIGO_PROB = 2;
	/**
	 * This is the default probability for a new segment to be plague.
	 */
	final static int DEF_PLAGUE_PROB = 1;
	/**
	 * This is the default probability for a new segment to be spike.
	 */
	final static int DEF_SPIKE_PROB = 1;
	/**
	 * This default value divides the amount of CO2 in the atmosphere to
	 * establish how many CO2 an organism can drain in a frame.
	 */
	final static int DEF_DRAIN_SUBS_DIVISOR = 5000;
	/**
	 * This is the default value for the initial energy that an organism has
	 * if it isn't a child of another organism.
	 */
	final static int DEF_INITIAL_ENERGY = 40;
	/**
	 * This is the default value for the maximum speed that an organism can
	 * achieve.
	 */
	final static double DEF_MAX_VEL = 5d;
	/**
	 * This is the default value for the maximum rotational speed that an organism
	 * can achieve.
	 */
	final static double DEF_MAX_ROT = FastMath.PI / 16d;
	/**
	 * This is the default elasticity coefficient. This value is used to establish
	 * the energy that a movement keeps after a collision.
	 */
	final static double DEF_ELASTICITY = 0.8d;
	/**
	 * This is the default number of milliseconds that pass between frames.
	 */
	final static int DEF_DELAY = 2;
	/**
	 * This is the default value for having or not having automatic backups.
	 */
	final static boolean DEF_AUTO_BACKUP = false;
	/**
	 * This is the default number of game time units that pass between backups.
	 */
	final static int DEF_BACKUP_DELAY = 100;
	/**
	 * This is the default port where the net server will listen for connections.
	 */
	final static int DEF_LOCAL_PORT = 8888;
	/**
	 * This is the default value for the maximum number of network connections allowed.
	 */
	final static int DEF_MAX_CONNECTIONS = 1;
	/**
	 * This is the default value for accepting or not new connections from other hosts.
	 */
	final static boolean DEF_ACCEPT_CONNECTIONS = false;
	/**
	 * This is the default value for using or not a meta-server to find other instances
	 * of biogenesis running. At the moment it is not used.
	 */
	final static boolean DEF_CONNECT_TO_SERVER = false;
	/**
	 * This is the default IP that the meta-server will have. At the moment it is not used.
	 */
	final static String DEF_SERVER_ADDRESS = ""; //$NON-NLS-1$
	/**
	 * This is the default port where the meta-server will listen. At the moment it is not used.
	 */
	final static int DEF_SERVER_PORT = 0;
	/**
	 * This is the default hardware acceleration applied when drawing
	 */
	final static int DEF_HARDWARE_ACCELERATION = 0; //0 none, 1 try opengl, 2 opengl
	
	final static double DEF_DECAY_ENERGY = 0.1d;
	// Effective parameters values
	static int WINDOW_X = DEF_WINDOW_X;
	static int WINDOW_Y = DEF_WINDOW_Y;
	static int WINDOW_WIDTH = DEF_WINDOW_WIDTH;
	static int WINDOW_HEIGHT = DEF_WINDOW_HEIGHT;
	static int WINDOW_STATE = DEF_WINDOW_STATE;
	/**
	 * This is the effective value of organisms that are created when a new world begins.
	 */
	static int INITIAL_ORGANISMS = DEF_INITIAL_ORGANISMS;
	/**
	 * This is the effective complexity of random organisms.
	 */
	static int INITIAL_COMPLEXITY = DEF_INITIAL_COMPLEXITY;
	/**
	 * This is the effective amount of O2 that exists in a newly created world.
	 */
	static double INITIAL_O2 = DEF_INITIAL_O2;
	/**
	 * This is the effective amount of CO2 that exists in a newly created world.
	 */
	static double INITIAL_CO2 = DEF_INITIAL_CO2;
	/**
	 * This is the effective size of the organisms vector.
	 */
	static int ORGANISMS_VECTOR_SIZE = DEF_ORGANISMS_VECTOR_SIZE;
	/**
	 * This is the effective world's width for new worlds.
	 */
	static int WORLD_WIDTH = DEF_WORLD_WIDTH;
	/**
	 * This is the effective world's height for new worlds.
	 */
	static int WORLD_HEIGHT = DEF_WORLD_HEIGHT;
	/**
	 * This is the maximum age that an organism can achieve, 
	 * without counting the number of segments.
	 */
	static int MAX_AGE = DEF_MAX_AGE;
	/**
	 * This is the age divisor, 
	 * which adds the number of segments to the maximum age, divided by this value.
	 */
	static int AGE_DIVISOR = DEF_AGE_DIVISOR;
	/**
	 * This is the rubbing coefficient that is applied to movements. This value is
	 * multiplied by the speed at every frame.
	 */
	static double RUBBING = DEF_RUBBING;
	/**
	 * This is the probability that an specified gene has to be modified when
	 * reproducing organisms. 
	 */
	static double MUTATION_RATE = DEF_MUTATION_RATE;
	/**
	 * This value is used to calculate the energy cost that an organism must
	 * pay to maintain a segment. It spends the length of the segment divided by this
	 * number units of energy.  
	 */
	static int SEGMENT_COST_DIVISOR = DEF_SEGMENT_COST_DIVISOR;
	/**
	 * This value is multiplied by the length of red segments to calculate
	 * the amount of energy that is stolen from an organism when it is touched.
	 */
	static double ORGANIC_OBTAINED_ENERGY = DEF_ORGANIC_OBTAINED_ENERGY;
	/**
	 * This value divides the length of green segments to calculate the
	 * amount of solar energy that the segment can get in a frame.
	 */
	static int GREEN_OBTAINED_ENERGY_DIVISOR = DEF_GREEN_OBTAINED_ENERGY_DIVISOR;
	/**
	 * This is the effective value that determines how much energy will be released
	 * to the atmosphere when an organism touches another organism with a consuming segment.
	 * The energy that the target lost is multiplied by this value and the result
	 * is added to the atmospheric CO2. The rest is added to the attacker's energy.
	 */
	static double ORGANIC_SUBS_PRODUCED = DEF_ORGANIC_SUBS_PRODUCED;
	/**
	 * This is the effective value that determines how much energy will be released
	 * to the atmosphere when an organism touches another organism with a cream segment.
	 * The energy that the target lost is multiplied by this value and the result
	 * is added to the atmospheric CO2. The rest is added to the attacker's energy.
	 */
	static double CREAM_ORGANIC_SUBS_PRODUCED = DEF_CREAM_ORGANIC_SUBS_PRODUCED;
	/**
	 * This is the effective healing efficiency.
	 * Healing is more efficient, if this value is lower.
	 */
	static int HEALING = DEF_HEALING;
	/**
	 * This is the effective immune system efficiency.
	 * Immune system is more efficient, if this value is lower.
	 */
	static int IMMUNE_SYSTEM = DEF_IMMUNE_SYSTEM;
	/**
	 * This is the effective indigo efficiency divisor.
	 * Indigo is more efficient, if this value is lower.
	 */
	static int INDIGO_DIVISOR = DEF_INDIGO_DIVISOR;
	/**
	 * This is the effective gold efficiency divisor.
	 * Gold is more efficient, if this value is lower.
	 */
	static double GOLD_DIVISOR = DEF_GOLD_DIVISOR;
	/**
	 * This is the energy that is consumed when an organism dodged an attack.
	 */
	static double DODGE_ENERGY_CONSUMPTION = DEF_DODGE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a dark jade segment regenerates.
	 */
	static int DARKJADE_DELAY = DEF_DARKJADE_DELAY;
	/**
	 * This is the energy that is consumed when a red segment is used.
	 */
	static double RED_ENERGY_CONSUMPTION = DEF_RED_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a green segment is used.
	 */
	static double GREEN_ENERGY_CONSUMPTION = DEF_GREEN_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a blue segment is used.
	 */
	static double BLUE_ENERGY_CONSUMPTION = DEF_BLUE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a cyan segment is used.
	 */
	static double CYAN_ENERGY_CONSUMPTION = DEF_CYAN_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a white segment is used.
	 */
	static double WHITE_ENERGY_CONSUMPTION = DEF_WHITE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a non-plant white segment is used.
	 */
	static double VIRUS_ENERGY_CONSUMPTION = DEF_VIRUS_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a gray segment is used.
	 */
	static double GRAY_ENERGY_CONSUMPTION = DEF_GRAY_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a dark gray segment is used.
	 */
	static double DARKGRAY_ENERGY_CONSUMPTION = DEF_DARKGRAY_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a silver segment is used.
	 */
	static double SILVER_ENERGY_CONSUMPTION = DEF_SILVER_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a yellow segment is used.
	 */
	static double YELLOW_ENERGY_CONSUMPTION = DEF_YELLOW_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a magenta segment is used.
	 */
	static double MAGENTA_ENERGY_CONSUMPTION = DEF_MAGENTA_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a pink segment is used.
	 */
	static double PINK_ENERGY_CONSUMPTION = DEF_PINK_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a coral segment is used.
	 */
	static double CORAL_ENERGY_CONSUMPTION = DEF_CORAL_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a orange segment is used.
	 */
	static double ORANGE_ENERGY_CONSUMPTION = DEF_ORANGE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a forest segment is used.
	 */
	static double FOREST_ENERGY_CONSUMPTION = DEF_FOREST_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a spring segment is used.
	 */
	static double SPRING_ENERGY_CONSUMPTION = DEF_SPRING_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a lime segment is used.
	 */
	static double LIME_ENERGY_CONSUMPTION = DEF_LIME_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a crowded lime segment is used.
	 */
	static double CROWDEDLIME_ENERGY_CONSUMPTION = DEF_CROWDEDLIME_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a bark segment is used.
	 */
	static double BARK_ENERGY_CONSUMPTION = DEF_BARK_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a jade segment is used.
	 */
	static double JADE_ENERGY_CONSUMPTION = DEF_JADE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a grass segment is used.
	 */
	static double GRASS_ENERGY_CONSUMPTION = DEF_GRASS_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a c4 segment is used.
	 */
	static double C4_ENERGY_CONSUMPTION = DEF_C4_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a violet segment is used.
	 */
	static double VIOLET_ENERGY_CONSUMPTION = DEF_VIOLET_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a teal segment is used.
	 */
	static double TEAL_ENERGY_CONSUMPTION = DEF_TEAL_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a maroon segment is used.
	 */
	static double MAROON_ENERGY_CONSUMPTION = DEF_MAROON_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a olive segment is used.
	 */
	static double OLIVE_ENERGY_CONSUMPTION = DEF_OLIVE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a mint segment is used.
	 */
	static double MINT_ENERGY_CONSUMPTION = DEF_MINT_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a cream segment is used.
	 */
	static double CREAM_ENERGY_CONSUMPTION = DEF_CREAM_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a rose segment is used.
	 */
	static double ROSE_ENERGY_CONSUMPTION = DEF_ROSE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a dark segment is used.
	 */
	static double DARK_ENERGY_CONSUMPTION = DEF_DARK_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when an ochre segment is used.
	 */
	static double OCHRE_ENERGY_CONSUMPTION = DEF_OCHRE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a sky segment is used.
	 */
	static double SKY_ENERGY_CONSUMPTION = DEF_SKY_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a lilac segment is used.
	 */
	static double LILAC_ENERGY_CONSUMPTION = DEF_LILAC_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a fire segment is used.
	 */
	static double FIRE_ENERGY_CONSUMPTION = DEF_FIRE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a consuming silver segment is used.
	 */
	static double EXPERIENCE_ENERGY_CONSUMPTION = DEF_EXPERIENCE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a blond segment is used.
	 */
	static double BLOND_ENERGY_CONSUMPTION = DEF_BLOND_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a auburn segment is used.
	 */
	static double AUBURN_ENERGY_CONSUMPTION = DEF_AUBURN_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when an indigo segment is used.
	 */
	static double INDIGO_ENERGY_CONSUMPTION = DEF_INDIGO_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a plague segment is used.
	 */
	static double PLAGUE_ENERGY_CONSUMPTION = DEF_PLAGUE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a non-plant plague segment is used.
	 */
	static double SCOURGE_ENERGY_CONSUMPTION = DEF_SCOURGE_ENERGY_CONSUMPTION;
	/**
	 * This is the energy that is consumed when a spike segment is used.
	 */
	static double SPIKE_ENERGY_CONSUMPTION = DEF_SPIKE_ENERGY_CONSUMPTION;
	/**
	 * This is the probability for a new segment to be red.
	 */
	static int RED_PROB = DEF_RED_PROB;
	/**
	 * This is the probability for a new segment to be green.
	 */
	static int GREEN_PROB = DEF_GREEN_PROB;
	/**
	 * This is the probability for a new segment to be blue.
	 */
	static int BLUE_PROB = DEF_BLUE_PROB;
	/**
	 * This is the probability for a new segment to be cyan.
	 */
	static int CYAN_PROB = DEF_CYAN_PROB;
	/**
	 * This is the probability for a new segment to be white.
	 */
	static int WHITE_PROB = DEF_WHITE_PROB;
	/**
	 * This is the probability for a new segment to be gray.
	 */
	static int GRAY_PROB = DEF_GRAY_PROB;
	/**
	 * This is the probability for a new segment to be dark gray.
	 */
	static int DARKGRAY_PROB = DEF_DARKGRAY_PROB;
	/**
	 * This is the probability for a new segment to be silver.
	 */
	static int SILVER_PROB = DEF_SILVER_PROB;
	/**
	 * This is the probability for a new segment to be yellow.
	 */
	static int YELLOW_PROB = DEF_YELLOW_PROB;
	/**
	 * This is the probability for a new segment to be magenta.
	 */
	static int MAGENTA_PROB = DEF_MAGENTA_PROB;
	/**
	 * This is the probability for a new segment to be pink.
	 */
	static int PINK_PROB = DEF_PINK_PROB;
	/**
	 * This is the probability for a new segment to be coral.
	 */
	static int CORAL_PROB = DEF_CORAL_PROB;
	/**
	 * This is the probability for a new segment to be orange.
	 */
	static int ORANGE_PROB = DEF_ORANGE_PROB;
	/**
	 * This is the probability for a new segment to be forest.
	 */
	static int FOREST_PROB = DEF_FOREST_PROB;
	/**
	 * This is the probability for a new segment to be spring.
	 */
	static int SPRING_PROB = DEF_SPRING_PROB;
	/**
	 * This is the probability for a new segment to be lime.
	 */
	static int LIME_PROB = DEF_LIME_PROB;
	/**
	 * This is the probability for a new segment to be bark.
	 */
	static int BARK_PROB = DEF_BARK_PROB;
	/**
	 * This is the probability for a new segment to be jade.
	 */
	static int JADE_PROB = DEF_JADE_PROB;
	/**
	 * This is the probability for a new segment to be grass.
	 */
	static int GRASS_PROB = DEF_GRASS_PROB;
	/**
	 * This is the probability for a new segment to be c4.
	 */
	static int C4_PROB = DEF_C4_PROB;
	/**
	 * This is the probability for a new segment to be violet.
	 */
	static int VIOLET_PROB = DEF_VIOLET_PROB;
	/**
	 * This is the probability for a new segment to be teal.
	 */
	static int TEAL_PROB = DEF_TEAL_PROB;
	/**
	 * This is the probability for a new segment to be maroon.
	 */
	static int MAROON_PROB = DEF_MAROON_PROB;
	/**
	 * This is the probability for a new segment to be olive.
	 */
	static int OLIVE_PROB = DEF_OLIVE_PROB;
	/**
	 * This is the probability for a new segment to be mint.
	 */
	static int MINT_PROB = DEF_MINT_PROB;
	/**
	 * This is the probability for a new segment to be cream.
	 */
	static int CREAM_PROB = DEF_CREAM_PROB;
	/**
	 * This is the probability for a new segment to be rose.
	 */
	static int ROSE_PROB = DEF_ROSE_PROB;
	/**
	 * This is the probability for a new segment to be dark.
	 */
	static int DARK_PROB = DEF_DARK_PROB;
	/**
	 * This is the probability for a new segment to be ochre.
	 */
	static int OCHRE_PROB = DEF_OCHRE_PROB;
	/**
	 * This is the probability for a new segment to be sky.
	 */
	static int SKY_PROB = DEF_SKY_PROB;
	/**
	 * This is the probability for a new segment to be lilac.
	 */
	static int LILAC_PROB = DEF_LILAC_PROB;
	/**
	 * This is the probability for a new segment to be fire.
	 */
	static int FIRE_PROB = DEF_FIRE_PROB;
	/**
	 * This is the probability for a new segment to be gold.
	 */
	static int GOLD_PROB = DEF_GOLD_PROB;
	/**
	 * This is the probability for a new segment to be blond.
	 */
	static int BLOND_PROB = DEF_BLOND_PROB;
	/**
	 * This is the probability for a new segment to be auburn.
	 */
	static int AUBURN_PROB = DEF_AUBURN_PROB;
	/**
	 * This is the probability for a new segment to be indigo.
	 */
	static int INDIGO_PROB = DEF_INDIGO_PROB;
	/**
	 * This is the probability for a new segment to be plague.
	 */
	static int PLAGUE_PROB = DEF_PLAGUE_PROB;
	/**
	 * This is the probability for a new segment to be spike.
	 */
	static int SPIKE_PROB = DEF_SPIKE_PROB;
	/**
	 * This value divides the amount of CO2 in the atmosphere to
	 * establish how many CO2 an organism can drain in a frame.
	 */
	static int DRAIN_SUBS_DIVISOR = DEF_DRAIN_SUBS_DIVISOR;
	/**
	 * This is the value for the initial energy that an organism has
	 * if it isn't a child of another organism.
	 */
	static int INITIAL_ENERGY = DEF_INITIAL_ENERGY;
	/**
	 * This is the value for the maximum speed that an organism can
	 * achieve.
	 */
	static double MAX_VEL = DEF_MAX_VEL;
	/**
	 * This is the value for the maximum rotational speed that an organism
	 * can achieve.
	 */
	static double MAX_ROT = DEF_MAX_ROT;
	/**
	 * This is the elasticity coefficient. This value is used to establish
	 * the energy that a movement keeps after a collision.
	 */
	static double ELASTICITY = DEF_ELASTICITY;
	/**
	 * This is the number of milliseconds that pass between frames.
	 */
	static int DELAY = DEF_DELAY;
	/**
	 * This is the value for having or not having automatic backups.
	 */
	static boolean AUTO_BACKUP = DEF_AUTO_BACKUP;
	/**
	 * This is the number of game time units that pass between backups.
	 */
	static int BACKUP_DELAY = DEF_BACKUP_DELAY;
	/**
	 * This is the port where the net server will listen for connections.
	 */
	static int LOCAL_PORT = DEF_LOCAL_PORT;
	/**
	 * This is the value for the maximum number of network connections allowed.
	 */
	static int MAX_CONNECTIONS = DEF_MAX_CONNECTIONS;
	/**
	 * This is the value for accepting or not new connections from other hosts.
	 */
	static boolean ACCEPT_CONNECTIONS = DEF_ACCEPT_CONNECTIONS;
	/**
	 * This is the value for using or not a meta-server to find other instances
	 * of biogenesis running. At the moment it is not used.
	 */
	static boolean CONNECT_TO_SERVER = DEF_CONNECT_TO_SERVER;
	/**
	 * This is the IP that the meta-server will have. At the moment it is not used.
	 */
	static String SERVER_ADDRESS = DEF_SERVER_ADDRESS;
	/**
	 * This is the hardware acceleration applied when drawing.
	 * 0 none, 1 try opengl next time, 2 trying opengl, 3 opengl
	 * 4 try opengl without fbobject next time, 5 trying opengl without fbobject,
	 * 6 opengl without fbobject
	 */
	static int HARDWARE_ACCELERATION = DEF_HARDWARE_ACCELERATION;
	/**
	 * This is the port where the meta-server will listen. At the moment it is not used.
	 */
	static int SERVER_PORT = DEF_SERVER_PORT;
	
	static double DECAY_ENERGY = DEF_DECAY_ENERGY;
	/**
	 * Tolerance. Smaller numbers are considered equal to 0.
	 */
	static final double tol = 0.0000001;
	/**
	 * Indicates the eight possible directions. The row is the direction we want, from 0 to 7, first
	 * column is the x coordinate and second column the y coordinate.  
	 */
	static final int side[][] = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
	/**
	 * These are the scale factor applied to segments depending on the growth rate of the
	 * organism. Segment length is divided by scale[i], where i is the growth rate.
	 * scale[0] indicates that the organism is fully developed and scale[15] that it has just
	 * been born. 
	 */
	static final double scale[] = {1.00, 1.12, 1.25, 1.40, 1.57, 1.76, 1.97, 2.21,
        2.47, 2.77, 3.11, 3.48, 3.90, 4.36, 4.89, 5.47};
	
	/**
	 * Precalculated dark green color
	 */
	static final Color ColorDARK_GREEN = Color.GREEN.darker();
	/**
	 * Precalculated dark red color
	 */
	static final Color ColorDARK_RED = Color.RED.darker();
	/**
	 * Precalculated dark cyan color
	 */
	static final Color ColorDARK_CYAN = Color.CYAN.darker();
	/**
	 * Precalculated dark blue color
	 */
	static final Color ColorDARK_BLUE = Color.BLUE.darker();
	/**
	 * Precalculated dark magenta color
	 */
	static final Color ColorDARK_MAGENTA = Color.MAGENTA.darker();
	/**
	 * Precalculated dark pink color
	 */
	static final Color ColorDARK_PINK = Color.PINK.darker();
	/**
	 * Precalculated dark orange color
	 */
	static final Color ColorDARK_ORANGE = Color.ORANGE.darker();
	/**
	 * Precalculated dark white color
	 */
	static final Color ColorDARK_WHITE = Color.WHITE.darker();
	/**
	 * Precalculated dark gray color
	 */
	static final Color ColorDARK_GRAY = Color.GRAY.darker();
	/**
	 * Precalculated dark yellow color
	 */
	static final Color ColorDARK_YELLOW = Color.YELLOW.darker();
	/**
	 * Precalculated brown color
	 */
	static final Color ColorBROWN = new Color(150,75,0);
	/**
	 * Precalculated light brown color
	 */
	static final Color ColorLIGHTBROWN = new Color(128,112,64);
	/**
	 * Precalculated green brown color
	 */
	static final Color ColorGREENBROWN = new Color(128,132,64);
	/**
	 * Precalculated poisoned jade color
	 */
	static final Color ColorPOISONEDJADE = new Color(54,84,54);
	/**
	 * Precalculated light blue color
	 */
	static final Color ColorLIGHT_BLUE = new Color(0,0,100);
	/**
	 * Precalculated violet color
	 */
	static final Color ColorVIOLET = new Color(128,0,128);
	/**
	 * Precalculated Forest color
	 */
	static final Color ColorFOREST = new Color(0,128,0);
	/**
	 * Precalculated bark color
	 */
	static final Color ColorBARK = new Color(96,128,64);
	/**
	 * Precalculated old bark color
	 */
	static final Color ColorOLDBARK = new Color(80,40,0);
	/**
	 * Precalculated old bark color
	 */
	static final Color ColorDEADBARK = new Color(128,96,64);
	/**
	 * Precalculated jade color
	 */
	static final Color ColorJADE = new Color(0,168,107);
	/**
	 * Precalculated spring color
	 */
	static final Color ColorSPRING = new Color(0,255,128);
	/**
	 * Precalculated lime color
	 */
	static final Color ColorLIME = new Color(176,255,0);
	/**
	 * Precalculated teal color
	 */
	static final Color ColorTEAL = new Color(0,128,128);
	/**
	 * Precalculated mint color
	 */
	static final Color ColorMINT = new Color(160,224,160);
	/**
	 * Precalculated maroon color
	 */
	static final Color ColorMAROON = new Color(128,0,0);
	/**
	 * Precalculated olive color
	 */
	static final Color ColorOLIVE = new Color(176,176,0);
	/**
	 * Precalculated dark olive color
	 */
	static final Color ColorDARKOLIVE = new Color(88,88,0);
	/**
	 * Precalculated cream color
	 */
	static final Color ColorCREAM = new Color(208,192,140);
	/**
	 * Precalculated rose color
	 */
	static final Color ColorROSE = new Color(255,0,128);
	/**
	 * Precalculated coral color
	 */
	static final Color ColorCORAL = new Color(255,100,138);
	/**
	 * Precalculated ochre color
	 */
	static final Color ColorOCHRE = new Color(204,119,34);
	/**
	 * Precalculated sky color
	 */
	static final Color ColorSKY = new Color(128,192,255);
	/**
	 * Precalculated deep sky color
	 */
	static final Color ColorDEEPSKY = new Color(64,96,255);
	/**
	 * Precalculated ice color
	 */
	static final Color ColorICE = new Color(64,96,128);
	/**
	 * Precalculated lilac color
	 */
	static final Color ColorLILAC = new Color(192,128,255);
	/**
	 * Precalculated dark lilac color
	 */
	static final Color ColorDARKLILAC = new Color(96,64,96);
	/**
	 * Precalculated fire color
	 */
	static final Color ColorFIRE = new Color(255,100,0);
	/**
	 * Precalculated dark fire color
	 */
	static final Color ColorDARKFIRE = new Color(232,146,70);
	/**
	 * Precalculated grass color
	 */
	static final Color ColorGRASS = new Color(144,176,64);
	/**
	 * Precalculated c4 color
	 */
	static final Color ColorC4 = new Color(96,192,96);
	/**
	 * Precalculated dark jade color
	 */
	static final Color ColorDARKJADE = new Color(0,84,54);
	/**
	 * Precalculated gold color
	 */
	static final Color ColorGOLD = new Color(212,175,55);
	/**
	 * Precalculated blond color
	 */
	static final Color ColorBLOND = new Color(255,255,128);
	/**
	 * Precalculated auburn color
	 */
	static final Color ColorAUBURN = new Color(128,48,48);
	/**
	 * Precalculated indigo color
	 */
	static final Color ColorINDIGO = new Color(111,0,255);
	/**
	 * Precalculated plague color
	 */
	static final Color ColorPLAGUE = new Color(255,192,255);
	/**
	 * Precalculated spike color
	 */
	static final Color ColorSPIKE = new Color(164,132,100);
	/**
	 * Precalculated spike point color
	 */
	static final Color ColorSPIKEPOINT = new Color(164,132,99);
	/**
	 * Precalculated broken defense color
	 */
	static final Color ColorBROKEN = new Color(100,132,100);
	/**
	 * Precalculated dark color
	 */
	static final Color ColorDARK = new Color(32,16,8);
	/**
	 * Used through all program to calculate random numbers
	 */
	public static Random random = new Random();
	/**
	 * Used to get a random -1 or 1 to create numbers with random sign. 
	 * 
	 * @return  a random -1 or 1
	 */
	public static final int randomSign() {
		return (random.nextInt(2)<<1)-1;
	}
	/**
	 * Calculates the minimum of three integers
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return  The minimum of a, b, and c
	 */
	public static final int min(int a, int b, int c) {
		return Math.min(Math.min(a,b),c);
	}
	/**
	 * Calculates the minimum of three doubles
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return  The minimum of a, b, and c
	 */
	public static final double min(double a, double b, double c) {
		return Math.min(Math.min(a,b),c);
	}
	/**
	 * Calculates the maximum of three integers
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return  The maximum of a, b, and c
	 */
	public static final int max(int a, int b, int c) {
		return Math.max(Math.max(a,b),c);
	}
	/**
	 * Calculates the maximum of three doubles 
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return  The maximum of a, b, and c
	 */
	public static final double max(double a, double b, double c) {
		return Math.max(Math.max(a,b),c);
	}
	/**
	 * Return min if value<min, max if value>max and value otherwise.
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return  min if value<min, max if value>max and value otherwise
	 */
	public static final int between(int value, int min, int max) {
		return Math.max(Math.min(max, value), min);
	}
	/**
	 * Return min if value<min, max if value>max and value otherwise.
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return  min if value<min, max if value>max and value otherwise
	 */
	public static final double between(double value, double min, double max) {
		return Math.max(Math.min(max, value), min);
	}
	/**
	 * Check if a mutation is produced or not, using a random number.
	 * 
	 * @return  true if a mutations is produced and false otherwise
	 */
	public static final boolean randomMutation() {
		if (random.nextDouble() < MUTATION_RATE)
			return true;
		return false;
	}
	/**
	 * Return the localized name of a color.
	 * 
	 * @param c  A color
	 * @return  A String representing the name of the color
	 */
	public static final String colorToString(Color c) {
		if (c.equals(Color.RED)) return Messages.getString("T_RED"); //$NON-NLS-1$
		if (c.equals(Utils.ColorFIRE)) return Messages.getString("T_FIRE"); //$NON-NLS-1$
		if (c.equals(Color.ORANGE)) return Messages.getString("T_ORANGE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorMAROON)) return Messages.getString("T_MAROON"); //$NON-NLS-1$
		if (c.equals(Color.PINK)) return Messages.getString("T_PINK"); //$NON-NLS-1$
		if (c.equals(Utils.ColorCREAM)) return Messages.getString("T_CREAM"); //$NON-NLS-1$
		if (c.equals(Utils.ColorCORAL)) return Messages.getString("T_CORAL"); //$NON-NLS-1$
		if (c.equals(Color.GREEN)) return Messages.getString("T_GREEN"); //$NON-NLS-1$
		if (c.equals(Utils.ColorFOREST)) return Messages.getString("T_FOREST"); //$NON-NLS-1$
		if (c.equals(Utils.ColorSPRING)) return Messages.getString("T_SPRING"); //$NON-NLS-1$
		if (c.equals(Utils.ColorLIME)) return Messages.getString("T_LIME"); //$NON-NLS-1$
		if (c.equals(Utils.ColorC4)) return Messages.getString("T_C4"); //$NON-NLS-1$
		if (c.equals(Utils.ColorJADE)) return Messages.getString("T_JADE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorGRASS)) return Messages.getString("T_GRASS"); //$NON-NLS-1$
		if (c.equals(Utils.ColorBARK)) return Messages.getString("T_BARK"); //$NON-NLS-1$
		if (c.equals(Color.BLUE)) return Messages.getString("T_BLUE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorSKY)) return Messages.getString("T_SKY"); //$NON-NLS-1$
		if (c.equals(Utils.ColorOLIVE)) return Messages.getString("T_OLIVE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorOCHRE)) return Messages.getString("T_OCHRE"); //$NON-NLS-1$
		if (c.equals(Color.CYAN)) return Messages.getString("T_CYAN"); //$NON-NLS-1$
		if (c.equals(Utils.ColorTEAL)) return Messages.getString("T_TEAL"); //$NON-NLS-1$
		if (c.equals(Color.WHITE)) return Messages.getString("T_WHITE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorPLAGUE)) return Messages.getString("T_PLAGUE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorMINT)) return Messages.getString("T_MINT"); //$NON-NLS-1$
		if (c.equals(Color.MAGENTA)) return Messages.getString("T_MAGENTA"); //$NON-NLS-1$
		if (c.equals(Utils.ColorROSE)) return Messages.getString("T_ROSE"); //$NON-NLS-1$
		if (c.equals(Utils.ColorVIOLET)) return Messages.getString("T_VIOLET"); //$NON-NLS-1$
		if (c.equals(Color.GRAY)) return Messages.getString("T_GRAY"); //$NON-NLS-1$
		if (c.equals(Utils.ColorLILAC)) return Messages.getString("T_LILAC"); //$NON-NLS-1$
		if (c.equals(Utils.ColorSPIKE)) return Messages.getString("T_SPIKE"); //$NON-NLS-1$
		if (c.equals(Color.LIGHT_GRAY)) return Messages.getString("T_SILVER"); //$NON-NLS-1$
		if (c.equals(Color.YELLOW)) return Messages.getString("T_YELLOW"); //$NON-NLS-1$
		if (c.equals(Utils.ColorAUBURN)) return Messages.getString("T_AUBURN"); //$NON-NLS-1$
		if (c.equals(Utils.ColorINDIGO)) return Messages.getString("T_INDIGO"); //$NON-NLS-1$
		if (c.equals(Utils.ColorBLOND)) return Messages.getString("T_BLOND"); //$NON-NLS-1$
		if (c.equals(Color.DARK_GRAY)) return Messages.getString("T_DARKGRAY"); //$NON-NLS-1$
		if (c.equals(Utils.ColorDARK)) return Messages.getString("T_DARK"); //$NON-NLS-1$
		if (c.equals(Utils.ColorGOLD)) return Messages.getString("T_GOLD"); //$NON-NLS-1$
		return ""; //$NON-NLS-1$
	}
	/**
	 * Save user preferences to disk
	 */
	public static final void savePreferences() {
		try {
			Preferences prefs = Preferences.userNodeForPackage(Utils.class);
			prefs.putInt("VERSION",FILE_VERSION); //$NON-NLS-1$
			prefs.putInt("INITIAL_ORGANISMS",INITIAL_ORGANISMS); //$NON-NLS-1$
			prefs.putInt("INITIAL_COMPLEXITY",INITIAL_COMPLEXITY); //$NON-NLS-1$
			prefs.putDouble("INITIAL_O2",INITIAL_O2); //$NON-NLS-1$
			prefs.putDouble("INITIAL_CO2",INITIAL_CO2); //$NON-NLS-1$
			prefs.putInt("ORGANISMS_VECTOR_SIZE",ORGANISMS_VECTOR_SIZE); //$NON-NLS-1$
			prefs.putInt("WORLD_WIDTH",WORLD_WIDTH); //$NON-NLS-1$
			prefs.putInt("WORLD_HEIGHT",WORLD_HEIGHT); //$NON-NLS-1$
			prefs.putInt("MAX_AGE",MAX_AGE); //$NON-NLS-1$
			prefs.putInt("AGE_DIVISOR",AGE_DIVISOR); //$NON-NLS-1$
			prefs.putDouble("RUBBING",RUBBING); //$NON-NLS-1$
			prefs.putDouble("MUTATION_RATE",MUTATION_RATE); //$NON-NLS-1$
			prefs.putInt("SEGMENT_COST_DIVISOR",SEGMENT_COST_DIVISOR); //$NON-NLS-1$
			prefs.putDouble("ORGANIC_OBTAINED_ENERGY",ORGANIC_OBTAINED_ENERGY); //$NON-NLS-1$
			prefs.putInt("GREEN_OBTAINED_ENERGY_DIVISOR",GREEN_OBTAINED_ENERGY_DIVISOR); //$NON-NLS-1$
			prefs.putDouble("ORGANIC_SUBS_PRODUCED",ORGANIC_SUBS_PRODUCED); //$NON-NLS-1$
			prefs.putDouble("CREAM_ORGANIC_SUBS_PRODUCED",CREAM_ORGANIC_SUBS_PRODUCED); //$NON-NLS-1$
			prefs.putInt("HEALING",HEALING); //$NON-NLS-1$
			prefs.putInt("IMMUNE_SYSTEM",IMMUNE_SYSTEM); //$NON-NLS-1$
			prefs.putInt("INDIGO_DIVISOR",INDIGO_DIVISOR); //$NON-NLS-1$
			prefs.putDouble("GOLD_DIVISOR",GOLD_DIVISOR); //$NON-NLS-1$
			prefs.putDouble("DODGE_ENERGY_CONSUMPTION",DODGE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putInt("DARKJADE_DELAY",DARKJADE_DELAY); //$NON-NLS-1$
			prefs.putDouble("RED_ENERGY_CONSUMPTION",RED_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("GREEN_ENERGY_CONSUMPTION",GREEN_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("BLUE_ENERGY_CONSUMPTION",BLUE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("CYAN_ENERGY_CONSUMPTION",CYAN_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("WHITE_ENERGY_CONSUMPTION",WHITE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("VIRUS_ENERGY_CONSUMPTION",VIRUS_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("GRAY_ENERGY_CONSUMPTION",GRAY_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("DARKGRAY_ENERGY_CONSUMPTION",DARKGRAY_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("SILVER_ENERGY_CONSUMPTION",SILVER_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("YELLOW_ENERGY_CONSUMPTION",YELLOW_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("MAGENTA_ENERGY_CONSUMPTION",MAGENTA_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("PINK_ENERGY_CONSUMPTION",PINK_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("CORAL_ENERGY_CONSUMPTION",CORAL_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("ORANGE_ENERGY_CONSUMPTION",ORANGE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("FOREST_ENERGY_CONSUMPTION",FOREST_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("SPRING_ENERGY_CONSUMPTION",SPRING_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("LIME_ENERGY_CONSUMPTION",LIME_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("CROWDEDLIME_ENERGY_CONSUMPTION",CROWDEDLIME_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("BARK_ENERGY_CONSUMPTION",BARK_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("JADE_ENERGY_CONSUMPTION",JADE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("GRASS_ENERGY_CONSUMPTION",GRASS_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("C4_ENERGY_CONSUMPTION",C4_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("VIOLET_ENERGY_CONSUMPTION",VIOLET_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("TEAL_ENERGY_CONSUMPTION",TEAL_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("MAROON_ENERGY_CONSUMPTION",MAROON_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("OLIVE_ENERGY_CONSUMPTION",OLIVE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("MINT_ENERGY_CONSUMPTION",MINT_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("CREAM_ENERGY_CONSUMPTION",CREAM_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("ROSE_ENERGY_CONSUMPTION",ROSE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("DARK_ENERGY_CONSUMPTION",DARK_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("OCHRE_ENERGY_CONSUMPTION",OCHRE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("SKY_ENERGY_CONSUMPTION",SKY_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("LILAC_ENERGY_CONSUMPTION",LILAC_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("FIRE_ENERGY_CONSUMPTION",FIRE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("EXPERIENCE_ENERGY_CONSUMPTION",EXPERIENCE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("BLOND_ENERGY_CONSUMPTION",BLOND_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("AUBURN_ENERGY_CONSUMPTION",AUBURN_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("PLAGUE_ENERGY_CONSUMPTION",PLAGUE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("SCOURGE_ENERGY_CONSUMPTION",SCOURGE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("SPIKE_ENERGY_CONSUMPTION",SPIKE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putDouble("INDIGO_ENERGY_CONSUMPTION",INDIGO_ENERGY_CONSUMPTION); //$NON-NLS-1$
			prefs.putInt("RED_PROB",RED_PROB); //$NON-NLS-1$
			prefs.putInt("GREEN_PROB",GREEN_PROB); //$NON-NLS-1$
			prefs.putInt("BLUE_PROB",BLUE_PROB); //$NON-NLS-1$
			prefs.putInt("CYAN_PROB",CYAN_PROB); //$NON-NLS-1$
			prefs.putInt("WHITE_PROB",WHITE_PROB); //$NON-NLS-1$
			prefs.putInt("GRAY_PROB",GRAY_PROB); //$NON-NLS-1$
			prefs.putInt("DARKGRAY_PROB",DARKGRAY_PROB); //$NON-NLS-1$
			prefs.putInt("SILVER_PROB",SILVER_PROB); //$NON-NLS-1$
			prefs.putInt("YELLOW_PROB",YELLOW_PROB); //$NON-NLS-1$
			prefs.putInt("MAGENTA_PROB",MAGENTA_PROB); //$NON-NLS-1$
			prefs.putInt("PINK_PROB",PINK_PROB); //$NON-NLS-1$
			prefs.putInt("CORAL_PROB",CORAL_PROB); //$NON-NLS-1$
			prefs.putInt("ORANGE_PROB",ORANGE_PROB); //$NON-NLS-1$
			prefs.putInt("FOREST_PROB",FOREST_PROB); //$NON-NLS-1$
			prefs.putInt("SPRING_PROB",SPRING_PROB); //$NON-NLS-1$
			prefs.putInt("LIME_PROB",LIME_PROB); //$NON-NLS-1$
			prefs.putInt("BARK_PROB",BARK_PROB); //$NON-NLS-1$
			prefs.putInt("JADE_PROB",JADE_PROB); //$NON-NLS-1$
			prefs.putInt("GRASS_PROB",GRASS_PROB); //$NON-NLS-1$
			prefs.putInt("C4_PROB",C4_PROB); //$NON-NLS-1$
			prefs.putInt("VIOLET_PROB",VIOLET_PROB); //$NON-NLS-1$
			prefs.putInt("TEAL_PROB",TEAL_PROB); //$NON-NLS-1$
			prefs.putInt("MAROON_PROB",MAROON_PROB); //$NON-NLS-1$
			prefs.putInt("OLIVE_PROB",OLIVE_PROB); //$NON-NLS-1$
			prefs.putInt("MINT_PROB",MINT_PROB); //$NON-NLS-1$
			prefs.putInt("CREAM_PROB",CREAM_PROB); //$NON-NLS-1$
			prefs.putInt("ROSE_PROB",ROSE_PROB); //$NON-NLS-1$
			prefs.putInt("DARK_PROB",DARK_PROB); //$NON-NLS-1$
			prefs.putInt("OCHRE_PROB",OCHRE_PROB); //$NON-NLS-1$
			prefs.putInt("SKY_PROB",SKY_PROB); //$NON-NLS-1$
			prefs.putInt("LILAC_PROB",LILAC_PROB); //$NON-NLS-1$
			prefs.putInt("FIRE_PROB",FIRE_PROB); //$NON-NLS-1$
			prefs.putInt("GOLD_PROB",GOLD_PROB); //$NON-NLS-1$
			prefs.putInt("BLOND_PROB",BLOND_PROB); //$NON-NLS-1$
			prefs.putInt("AUBURN_PROB",AUBURN_PROB); //$NON-NLS-1$
			prefs.putInt("PLAGUE_PROB",PLAGUE_PROB); //$NON-NLS-1$
			prefs.putInt("SPIKE_PROB",SPIKE_PROB); //$NON-NLS-1$
			prefs.putInt("INDIGO_PROB",INDIGO_PROB); //$NON-NLS-1$
			prefs.putInt("DRAIN_SUBS_DIVISOR",DRAIN_SUBS_DIVISOR); //$NON-NLS-1$
			prefs.putInt("INITIAL_ENERGY",INITIAL_ENERGY); //$NON-NLS-1$
			prefs.putDouble("MAX_VEL",MAX_VEL); //$NON-NLS-1$
			prefs.putDouble("MAX_ROT",MAX_ROT); //$NON-NLS-1$
			prefs.putDouble("ELASTICITY",ELASTICITY); //$NON-NLS-1$
			prefs.putInt("DELAY",DELAY); //$NON-NLS-1$
			prefs.putBoolean("AUTO_BACKUP",AUTO_BACKUP);
			prefs.putInt("BACKUP_DELAY",BACKUP_DELAY);
			prefs.putInt("LOCAL_PORT",LOCAL_PORT); //$NON-NLS-1$
			prefs.putBoolean("ACCEPT_CONNECTIONS",ACCEPT_CONNECTIONS); //$NON-NLS-1$
			prefs.putBoolean("CONNECT_TO_SERVER",CONNECT_TO_SERVER); //$NON-NLS-1$
			prefs.put("SERVER_ADDRESS",SERVER_ADDRESS); //$NON-NLS-1$
			prefs.putInt("SERVER_PORT",SERVER_PORT); //$NON-NLS-1$
			prefs.putInt("MAX_CONNECTIONS",MAX_CONNECTIONS); //$NON-NLS-1$
			prefs.putInt("HARDWARE_ACCELERATION", HARDWARE_ACCELERATION); //$NON-NLS-1$
			prefs.putDouble("DECAY_ENERGY", DECAY_ENERGY); //$NON-NLS-1$
			prefs.put("LOCALE",Messages.getLanguage()); //$NON-NLS-1$
		}
		catch (SecurityException ex) {
			// If we can't write, don't do it
		}
	}
	/**
	 * Read user preferences from disc
	 */
	public static final void readPreferences() {
		try {
			Preferences prefs = Preferences.userNodeForPackage(Utils.class);
			int previous_version = prefs.getInt("VERSION",0); //$NON-NLS-1$
			if (previous_version != FILE_VERSION)
				try {
					prefs.clear();
				} catch (BackingStoreException e) {
					//do nothing
				}
			WINDOW_X = prefs.getInt("WINDOW_X", DEF_WINDOW_X);
			WINDOW_Y = prefs.getInt("WINDOW_Y", DEF_WINDOW_Y);
			WINDOW_WIDTH = prefs.getInt("WINDOW_WIDTH", DEF_WINDOW_WIDTH);
			WINDOW_HEIGHT = prefs.getInt("WINDOW_HEIGHT", DEF_WINDOW_HEIGHT);
			WINDOW_STATE = prefs.getInt("WINDOW_STATE", DEF_WINDOW_STATE);
			INITIAL_ORGANISMS = prefs.getInt("INITIAL_ORGANISMS",DEF_INITIAL_ORGANISMS); //$NON-NLS-1$
			INITIAL_COMPLEXITY = prefs.getInt("INITIAL_COMPLEXITY",DEF_INITIAL_COMPLEXITY); //$NON-NLS-1$
			INITIAL_O2 = prefs.getDouble("INITIAL_O2",DEF_INITIAL_O2); //$NON-NLS-1$
			INITIAL_CO2 = prefs.getDouble("INITIAL_CO2",DEF_INITIAL_CO2); //$NON-NLS-1$
			ORGANISMS_VECTOR_SIZE = prefs.getInt("ORGANISMS_VECTOR_SIZE",DEF_ORGANISMS_VECTOR_SIZE); //$NON-NLS-1$
			WORLD_WIDTH = prefs.getInt("WORLD_WIDTH",DEF_WORLD_WIDTH); //$NON-NLS-1$
			WORLD_HEIGHT = prefs.getInt("WORLD_HEIGHT",DEF_WORLD_HEIGHT); //$NON-NLS-1$
			MAX_AGE = prefs.getInt("MAX_AGE",DEF_MAX_AGE); //$NON-NLS-1$
			AGE_DIVISOR = prefs.getInt("AGE_DIVISOR",DEF_AGE_DIVISOR); //$NON-NLS-1$
			RUBBING = prefs.getDouble("RUBBING",DEF_RUBBING); //$NON-NLS-1$
			MUTATION_RATE = prefs.getDouble("MUTATION_RATE",DEF_MUTATION_RATE); //$NON-NLS-1$
			SEGMENT_COST_DIVISOR = prefs.getInt("SEGMENT_COST_DIVISOR",DEF_SEGMENT_COST_DIVISOR); //$NON-NLS-1$
			ORGANIC_OBTAINED_ENERGY = prefs.getDouble("ORGANIC_OBTAINED_ENERGY",DEF_ORGANIC_OBTAINED_ENERGY); //$NON-NLS-1$
			GREEN_OBTAINED_ENERGY_DIVISOR = prefs.getInt("GREEN_OBTAINED_ENERGY_DIVISOR",DEF_GREEN_OBTAINED_ENERGY_DIVISOR); //$NON-NLS-1$
			ORGANIC_SUBS_PRODUCED = prefs.getDouble("ORGANIC_SUBS_PRODUCED",DEF_ORGANIC_SUBS_PRODUCED); //$NON-NLS-1$
			CREAM_ORGANIC_SUBS_PRODUCED = prefs.getDouble("CREAM_ORGANIC_SUBS_PRODUCED",DEF_CREAM_ORGANIC_SUBS_PRODUCED); //$NON-NLS-1$
			HEALING = prefs.getInt("HEALING",DEF_HEALING); //$NON-NLS-1$
			IMMUNE_SYSTEM = prefs.getInt("IMMUNE_SYSTEM",DEF_IMMUNE_SYSTEM); //$NON-NLS-1$
			INDIGO_DIVISOR = prefs.getInt("INDIGO_DIVISOR",DEF_INDIGO_DIVISOR); //$NON-NLS-1$
			GOLD_DIVISOR = prefs.getDouble("GOLD_DIVISOR",DEF_GOLD_DIVISOR); //$NON-NLS-1$
			DODGE_ENERGY_CONSUMPTION = prefs.getDouble("DODGE_ENERGY_CONSUMPTION",DEF_DODGE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			DARKJADE_DELAY = prefs.getInt("DARKJADE_DELAY",DEF_DARKJADE_DELAY); //$NON-NLS-1$
			RED_ENERGY_CONSUMPTION = prefs.getDouble("RED_ENERGY_CONSUMPTION",DEF_RED_ENERGY_CONSUMPTION); //$NON-NLS-1$
			GREEN_ENERGY_CONSUMPTION = prefs.getDouble("GREEN_ENERGY_CONSUMPTION",DEF_GREEN_ENERGY_CONSUMPTION); //$NON-NLS-1$
			BLUE_ENERGY_CONSUMPTION = prefs.getDouble("BLUE_ENERGY_CONSUMPTION",DEF_BLUE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			CYAN_ENERGY_CONSUMPTION = prefs.getDouble("CYAN_ENERGY_CONSUMPTION",DEF_CYAN_ENERGY_CONSUMPTION); //$NON-NLS-1$
			WHITE_ENERGY_CONSUMPTION = prefs.getDouble("WHITE_ENERGY_CONSUMPTION",DEF_WHITE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			VIRUS_ENERGY_CONSUMPTION = prefs.getDouble("VIRUS_ENERGY_CONSUMPTION",DEF_VIRUS_ENERGY_CONSUMPTION); //$NON-NLS-1$
			GRAY_ENERGY_CONSUMPTION = prefs.getDouble("GRAY_ENERGY_CONSUMPTION",DEF_GRAY_ENERGY_CONSUMPTION); //$NON-NLS-1$
			DARKGRAY_ENERGY_CONSUMPTION = prefs.getDouble("DARKGRAY_ENERGY_CONSUMPTION",DEF_DARKGRAY_ENERGY_CONSUMPTION); //$NON-NLS-1$
			SILVER_ENERGY_CONSUMPTION = prefs.getDouble("SILVER_ENERGY_CONSUMPTION",DEF_SILVER_ENERGY_CONSUMPTION); //$NON-NLS-1$
			YELLOW_ENERGY_CONSUMPTION = prefs.getDouble("YELLOW_ENERGY_CONSUMPTION",DEF_YELLOW_ENERGY_CONSUMPTION); //$NON-NLS-1$
			MAGENTA_ENERGY_CONSUMPTION = prefs.getDouble("MAGENTA_ENERGY_CONSUMPTION",DEF_MAGENTA_ENERGY_CONSUMPTION); //$NON-NLS-1$
			PINK_ENERGY_CONSUMPTION = prefs.getDouble("PINK_ENERGY_CONSUMPTION",DEF_PINK_ENERGY_CONSUMPTION); //$NON-NLS-1$
			CORAL_ENERGY_CONSUMPTION = prefs.getDouble("CORAL_ENERGY_CONSUMPTION",DEF_CORAL_ENERGY_CONSUMPTION); //$NON-NLS-1$
			ORANGE_ENERGY_CONSUMPTION = prefs.getDouble("ORANGE_ENERGY_CONSUMPTION",DEF_ORANGE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			FOREST_ENERGY_CONSUMPTION = prefs.getDouble("FOREST_ENERGY_CONSUMPTION",DEF_FOREST_ENERGY_CONSUMPTION); //$NON-NLS-1$
			SPRING_ENERGY_CONSUMPTION = prefs.getDouble("SPRING_ENERGY_CONSUMPTION",DEF_SPRING_ENERGY_CONSUMPTION); //$NON-NLS-1$
			LIME_ENERGY_CONSUMPTION = prefs.getDouble("LIME_ENERGY_CONSUMPTION",DEF_LIME_ENERGY_CONSUMPTION); //$NON-NLS-1$
			CROWDEDLIME_ENERGY_CONSUMPTION = prefs.getDouble("CROWDEDLIME_ENERGY_CONSUMPTION",DEF_CROWDEDLIME_ENERGY_CONSUMPTION); //$NON-NLS-1$
			BARK_ENERGY_CONSUMPTION = prefs.getDouble("BARK_ENERGY_CONSUMPTION",DEF_BARK_ENERGY_CONSUMPTION); //$NON-NLS-1$
			JADE_ENERGY_CONSUMPTION = prefs.getDouble("JADE_ENERGY_CONSUMPTION",DEF_JADE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			GRASS_ENERGY_CONSUMPTION = prefs.getDouble("GRASS_ENERGY_CONSUMPTION",DEF_GRASS_ENERGY_CONSUMPTION); //$NON-NLS-1$
			C4_ENERGY_CONSUMPTION = prefs.getDouble("C4_ENERGY_CONSUMPTION",DEF_C4_ENERGY_CONSUMPTION); //$NON-NLS-1$
			VIOLET_ENERGY_CONSUMPTION = prefs.getDouble("VIOLET_ENERGY_CONSUMPTION",DEF_VIOLET_ENERGY_CONSUMPTION); //$NON-NLS-1$
			TEAL_ENERGY_CONSUMPTION = prefs.getDouble("TEAL_ENERGY_CONSUMPTION",DEF_TEAL_ENERGY_CONSUMPTION); //$NON-NLS-1$
			MAROON_ENERGY_CONSUMPTION = prefs.getDouble("MAROON_ENERGY_CONSUMPTION",DEF_MAROON_ENERGY_CONSUMPTION); //$NON-NLS-1$
			OLIVE_ENERGY_CONSUMPTION = prefs.getDouble("OLIVE_ENERGY_CONSUMPTION",DEF_OLIVE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			MINT_ENERGY_CONSUMPTION = prefs.getDouble("MINT_ENERGY_CONSUMPTION",DEF_MINT_ENERGY_CONSUMPTION); //$NON-NLS-1$
			CREAM_ENERGY_CONSUMPTION = prefs.getDouble("CREAM_ENERGY_CONSUMPTION",DEF_CREAM_ENERGY_CONSUMPTION); //$NON-NLS-1$
			ROSE_ENERGY_CONSUMPTION = prefs.getDouble("ROSE_ENERGY_CONSUMPTION",DEF_ROSE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			DARK_ENERGY_CONSUMPTION = prefs.getDouble("DARK_ENERGY_CONSUMPTION",DEF_DARK_ENERGY_CONSUMPTION); //$NON-NLS-1$
			OCHRE_ENERGY_CONSUMPTION = prefs.getDouble("OCHRE_ENERGY_CONSUMPTION",DEF_OCHRE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			SKY_ENERGY_CONSUMPTION = prefs.getDouble("SKY_ENERGY_CONSUMPTION",DEF_SKY_ENERGY_CONSUMPTION); //$NON-NLS-1$
			LILAC_ENERGY_CONSUMPTION = prefs.getDouble("LILAC_ENERGY_CONSUMPTION",DEF_LILAC_ENERGY_CONSUMPTION); //$NON-NLS-1$
			FIRE_ENERGY_CONSUMPTION = prefs.getDouble("FIRE_ENERGY_CONSUMPTION",DEF_FIRE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			EXPERIENCE_ENERGY_CONSUMPTION = prefs.getDouble("EXPERIENCE_ENERGY_CONSUMPTION",DEF_EXPERIENCE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			BLOND_ENERGY_CONSUMPTION = prefs.getDouble("BLOND_ENERGY_CONSUMPTION",DEF_BLOND_ENERGY_CONSUMPTION); //$NON-NLS-1$
			AUBURN_ENERGY_CONSUMPTION = prefs.getDouble("AUBURN_ENERGY_CONSUMPTION",DEF_AUBURN_ENERGY_CONSUMPTION); //$NON-NLS-1$
			PLAGUE_ENERGY_CONSUMPTION = prefs.getDouble("PLAGUE_ENERGY_CONSUMPTION",DEF_PLAGUE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			SCOURGE_ENERGY_CONSUMPTION = prefs.getDouble("SCOURGE_ENERGY_CONSUMPTION",DEF_SCOURGE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			SPIKE_ENERGY_CONSUMPTION = prefs.getDouble("SPIKE_ENERGY_CONSUMPTION",DEF_SPIKE_ENERGY_CONSUMPTION); //$NON-NLS-1$
			INDIGO_ENERGY_CONSUMPTION = prefs.getDouble("INDIGO_ENERGY_CONSUMPTION",DEF_INDIGO_ENERGY_CONSUMPTION); //$NON-NLS-1$
			RED_PROB = prefs.getInt("RED_PROB",DEF_RED_PROB); //$NON-NLS-1$
			GREEN_PROB = prefs.getInt("GREEN_PROB",DEF_GREEN_PROB); //$NON-NLS-1$
			BLUE_PROB = prefs.getInt("BLUE_PROB",DEF_BLUE_PROB); //$NON-NLS-1$
			CYAN_PROB = prefs.getInt("CYAN_PROB",DEF_CYAN_PROB); //$NON-NLS-1$
			WHITE_PROB = prefs.getInt("WHITE_PROB",DEF_WHITE_PROB); //$NON-NLS-1$
			GRAY_PROB = prefs.getInt("GRAY_PROB",DEF_GRAY_PROB); //$NON-NLS-1$
			DARKGRAY_PROB = prefs.getInt("DARKGRAY_PROB",DEF_DARKGRAY_PROB); //$NON-NLS-1$
			SILVER_PROB = prefs.getInt("SILVER_PROB",DEF_SILVER_PROB); //$NON-NLS-1$
			YELLOW_PROB = prefs.getInt("YELLOW_PROB",DEF_YELLOW_PROB); //$NON-NLS-1$
			MAGENTA_PROB = prefs.getInt("MAGENTA_PROB",DEF_MAGENTA_PROB); //$NON-NLS-1$
			PINK_PROB = prefs.getInt("PINK_PROB",DEF_PINK_PROB); //$NON-NLS-1$
			CORAL_PROB = prefs.getInt("CORAL_PROB",DEF_CORAL_PROB); //$NON-NLS-1$
			ORANGE_PROB = prefs.getInt("ORANGE_PROB",DEF_ORANGE_PROB); //$NON-NLS-1$
			FOREST_PROB = prefs.getInt("FOREST_PROB",DEF_FOREST_PROB); //$NON-NLS-1$
			SPRING_PROB = prefs.getInt("SPRING_PROB",DEF_SPRING_PROB); //$NON-NLS-1$
			LIME_PROB = prefs.getInt("LIME_PROB",DEF_LIME_PROB); //$NON-NLS-1$
			BARK_PROB = prefs.getInt("BARK_PROB",DEF_BARK_PROB); //$NON-NLS-1$
			JADE_PROB = prefs.getInt("JADE_PROB",DEF_JADE_PROB); //$NON-NLS-1$
			GRASS_PROB = prefs.getInt("GRASS_PROB",DEF_GRASS_PROB); //$NON-NLS-1$
			C4_PROB = prefs.getInt("C4_PROB",DEF_C4_PROB); //$NON-NLS-1$
			VIOLET_PROB = prefs.getInt("VIOLET_PROB",DEF_VIOLET_PROB); //$NON-NLS-1$
			TEAL_PROB = prefs.getInt("TEAL_PROB",DEF_TEAL_PROB); //$NON-NLS-1$
			MAROON_PROB = prefs.getInt("MAROON_PROB",DEF_MAROON_PROB); //$NON-NLS-1$
			OLIVE_PROB = prefs.getInt("OLIVE_PROB",DEF_OLIVE_PROB); //$NON-NLS-1$
			MINT_PROB = prefs.getInt("MINT_PROB",DEF_MINT_PROB); //$NON-NLS-1$
			CREAM_PROB = prefs.getInt("CREAM_PROB",DEF_CREAM_PROB); //$NON-NLS-1$
			ROSE_PROB = prefs.getInt("ROSE_PROB",DEF_ROSE_PROB); //$NON-NLS-1$
			DARK_PROB = prefs.getInt("DARK_PROB",DEF_DARK_PROB); //$NON-NLS-1$
			OCHRE_PROB = prefs.getInt("OCHRE_PROB",DEF_OCHRE_PROB); //$NON-NLS-1$
			SKY_PROB = prefs.getInt("SKY_PROB",DEF_SKY_PROB); //$NON-NLS-1$
			LILAC_PROB = prefs.getInt("LILAC_PROB",DEF_LILAC_PROB); //$NON-NLS-1$
			FIRE_PROB = prefs.getInt("FIRE_PROB",DEF_FIRE_PROB); //$NON-NLS-1$
			GOLD_PROB = prefs.getInt("GOLD_PROB",DEF_GOLD_PROB); //$NON-NLS-1$
			BLOND_PROB = prefs.getInt("BLOND_PROB",DEF_BLOND_PROB); //$NON-NLS-1$
			AUBURN_PROB = prefs.getInt("AUBURN_PROB",DEF_AUBURN_PROB); //$NON-NLS-1$
			PLAGUE_PROB = prefs.getInt("PLAGUE_PROB",DEF_PLAGUE_PROB); //$NON-NLS-1$
			SPIKE_PROB = prefs.getInt("SPIKE_PROB",DEF_SPIKE_PROB); //$NON-NLS-1$
			INDIGO_PROB = prefs.getInt("INDIGO_PROB",DEF_INDIGO_PROB); //$NON-NLS-1$
			DRAIN_SUBS_DIVISOR = prefs.getInt("DRAIN_SUBS_DIVISOR",DEF_DRAIN_SUBS_DIVISOR); //$NON-NLS-1$
			INITIAL_ENERGY = prefs.getInt("INITIAL_ENERGY",DEF_INITIAL_ENERGY); //$NON-NLS-1$
			MAX_VEL = prefs.getDouble("MAX_VEL",DEF_MAX_VEL); //$NON-NLS-1$
			MAX_ROT = prefs.getDouble("MAX_ROT",DEF_MAX_ROT); //$NON-NLS-1$
			ELASTICITY = prefs.getDouble("ELASTICITY",DEF_ELASTICITY); //$NON-NLS-1$
			DELAY = prefs.getInt("DELAY",DEF_DELAY); //$NON-NLS-1$
			AUTO_BACKUP = prefs.getBoolean("AUTO_BACKUP",DEF_AUTO_BACKUP);
			BACKUP_DELAY = prefs.getInt("BACKUP_DELAY",DEF_BACKUP_DELAY);
			LOCAL_PORT = prefs.getInt("LOCAL_PORT",DEF_LOCAL_PORT); //$NON-NLS-1$
			MAX_CONNECTIONS = prefs.getInt("MAX_CONNECTIONS",DEF_MAX_CONNECTIONS); //$NON-NLS-1$
			ACCEPT_CONNECTIONS = prefs.getBoolean("ACCEPT_CONNECTIONS",DEF_ACCEPT_CONNECTIONS); //$NON-NLS-1$
			CONNECT_TO_SERVER = prefs.getBoolean("CONNECT_TO_SERVER",DEF_CONNECT_TO_SERVER); //$NON-NLS-1$
			SERVER_ADDRESS = prefs.get("SERVER_ADDRESS",DEF_SERVER_ADDRESS); //$NON-NLS-1$
			SERVER_PORT = prefs.getInt("SERVER_PORT",DEF_SERVER_PORT); //$NON-NLS-1$
			DECAY_ENERGY = prefs.getDouble("DECAY_ENERGY", DEF_DECAY_ENERGY); //$NON-NLS-1$
			setHardwareAcceleration(prefs.getInt("HARDWARE_ACCELERATION", DEF_HARDWARE_ACCELERATION)); //$NON-NLS-1$
			if (HARDWARE_ACCELERATION == 1 || HARDWARE_ACCELERATION == 4) {
				prefs.putInt("HARDWARE_ACCELERATION", 0); //$NON-NLS-1$
				HARDWARE_ACCELERATION += 1;
			}
			Messages.setLocale(prefs.get("LOCALE",Messages.getLanguage())); //$NON-NLS-1$
			
		} catch (SecurityException ex) {
			Messages.setLocale(Messages.getLanguage());
		}
	}
	public static void quitProgram(MainWindow window) {
		try {
			Preferences prefs = Preferences.userNodeForPackage(Utils.class);
			if (HARDWARE_ACCELERATION == 2 || HARDWARE_ACCELERATION == 5) {
				String[] options = {Messages.getString("T_YES"), Messages.getString("T_NO")}; //$NON-NLS-1$ //$NON-NLS-2$
				int answer = JOptionPane.showOptionDialog(null, Messages.getString("T_DID_OPENGL_WORK_WELL"), Messages.getString("T_OPENGL_CONFIRMATION"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);  //$NON-NLS-1$//$NON-NLS-2$
				if (answer == JOptionPane.YES_OPTION)
					prefs.putInt("HARDWARE_ACCELERATION", HARDWARE_ACCELERATION+1); //$NON-NLS-1$
			}
		
			prefs.putInt("WINDOW_X", window.getX());
			prefs.putInt("WINDOW_Y", window.getY());
			prefs.putInt("WINDOW_WIDTH", window.getWidth());
			prefs.putInt("WINDOW_HEIGHT", window.getHeight());
			prefs.putInt("WINDOW_STATE", window.getExtendedState());
		} catch (SecurityException ex) {
			// do nothing
		}
		savePreferences();
	}
	
	public static void setHardwareAcceleration(int newValue) {
		try {
			switch (newValue) {
			case 0:
			case 2:
			case 5:
				System.setProperty("sun.java2d.opengl", "false"); //$NON-NLS-1$ //$NON-NLS-2$
				break;
			case 1:
			case 3:
				System.setProperty("sun.java2d.opengl", "True"); //$NON-NLS-1$ //$NON-NLS-2$
				System.setProperty("sun.java2d.noddraw", "true");  //$NON-NLS-1$//$NON-NLS-2$
				break;
			case 4:
			case 6:
				System.setProperty("sun.java2d.opengl", "True"); //$NON-NLS-1$ //$NON-NLS-2$
				System.setProperty("sun.java2d.noddraw", "true");  //$NON-NLS-1$//$NON-NLS-2$
				// Used to workaround problems with some drivers 
				System.setProperty("sun.java2d.opengl.fbobject","false"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			HARDWARE_ACCELERATION = newValue;
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
}
