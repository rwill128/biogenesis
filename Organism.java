/* Copyright (C) 2006-2010  Joan Queralt Molina
 * Color Mod (C) 2012-2016  MarcoDBAA
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

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
/**
 * This class implements an organism.
 * The body of the organism is drawn inside the Rectangle from which it inherits.
 */
public class Organism extends Rectangle {
	/**
	 * The version of this class
	 */
	private static final long serialVersionUID = Utils.FILE_VERSION;
	/**
	 * A reference to the genetic code of this organism
	 */
	protected GeneticCode _geneticCode;
	/**
	 * If this organism has been infected by a white segment, here we have the
	 * genetic code that this organism will reproduce.
	 */
	protected GeneticCode _infectedGeneticCode = null;
	/**
	 * Number of children that this organism will produce at once. This
	 * is the number of yellow segments in its genetic code with a
	 * maximum of 8 and a minimum of 1.
	 */
	protected int _nChildren;
	/**
	 * Amount of indigo an organism has
	 */
	protected double _indigo;
	/**
	 * Organisms with gold, dark and rose segments have lower maintenance costs
	 */
	protected int _lowmaintenance;
	/**
	 * If rose segments of two organisms have the same length they are friends
	 */
	protected double _lengthfriend=-1;
	/**
	 * If rose segments of two organisms have the same theta they are friends
	 */
	protected double _thetafriend=-1;
	/**
	 * Indicates if this organism is a plant
	 */
	protected boolean _isaplant;
	/**
	 * Indicates if this organism is a consumer
	 */
	protected boolean _isaconsumer;
	/**
	 * Indicates if this organism is a killer
	 */
	protected int _isakiller;
	/**
	 * Indicates if this organism is poisonous
	 */
	protected boolean _ispoisonous;
	/**
	 * Indicates if this organism is a freezer
	 */
	protected boolean _isafreezer;
	/**
	 * Indicates if this organism is frozen
	 */
	protected boolean _isfrozen;
	/**
	 * Indicates if this organism has plague segments.
	 */
	protected boolean _isplague;
	/**
	 * Indicates if this organism has coral segments.
	 */
	protected boolean _iscoral;
	/**
	 * Indicates if this organism has auburn segments.
	 */
	protected boolean _isauburn;
	/**
	 * Indicates if this organism has dark gray segments.
	 */
	protected boolean _isenhanced;
	/**
	 * Indicates if this organism has jade segments.
	 */
	protected boolean _isjade;
	/**
	 * Indicates if this organism has mint segments.
	 */
	protected boolean _isantiviral;
	/**
	 * Indicates if this organism has magenta segments.
	 */
	protected boolean _isregenerative;
	/**
	 * Indicates if this organism has rose segments.
	 */
	protected boolean _transfersenergy;
	/**
	 * Indicates if this organism is near other organisms.
	 */
	protected boolean _crowded;
	/**
	 * Indicates if an organism can dodge an attack
	 */
	protected boolean _dodge;
	/**
	 * Indicates if an organism already dodged an attack
	 */
	protected boolean _hasdodged;
	/**
	 * Takes a note that this segment was poisoned.
	 */
	protected boolean _remember;
	/**
	 * Reference to the world where the organism lives.
	 */
	protected World _world;
	/**
	 * Reference to the visual part of the world where the organism lives.
	 */
	transient protected VisibleWorld _visibleWorld;
	/**
	 * Identification number of this organism's parent.
	 */
	protected int _parentID;
	/**
	 * Identification number of this organism.
	 */
	protected int _ID;
	/**
	 * Generation number
	 */
	protected int _generation;
	/**
	 * Generation number for infections
	 */
	protected int _infectedGeneration;
	/**
	 * Number of children it has produced.
	 */
	protected int _nTotalChildren=0;
	/**
	 * Number of organism that has killed
	 */
	protected int _nTotalKills=0;
	/**
	 * Number of organism that has infected
	 */
	protected int _nTotalInfected=0;
	/**
	 * X coordinates of the starting point of each organism's segments.
	 */
	protected int[] _startPointX;
	/**
	 * Y coordinates of the starting point of each organism's segments.
	 */
	protected int[] _startPointY;
	/**
	 * X coordinates of the ending point of each organism's segments.
	 */
	protected int[] _endPointX;
	/**
	 * Y coordinates of the ending point of each organism's segments.
	 */
	protected int[] _endPointY;
	/**
	 * Precalculated distance from the origin to the starting point of each segment.
	 * Used to calculate rotations.
	 */
	protected double[] _m1;
	/**
	 * Precalculated distance from the origin to the ending point of each segment.
	 * Used to calculate rotations.
	 */
	protected double[] _m2;
	/**
	 * Precalculated modulus of each segment.
	 */
	protected double[] _m;
	/**
	 * Precalculated modulus of each segment, divided by symmetry, used for photosynthesis.
	 */
	protected double[] _mphoto;
	/**
	 * X coordinate of this organim's center of gravity.
	 */
	protected int _centerX;
	/**
	 * Y coordinate of this organim's center of gravity.
	 */
	protected int _centerY;
	/**
	 * Like _centerX but with double precision to be able to make movements slower than a pixel.
	 */
	protected double _dCenterX;
	/**
	 * Like _centerY but with double precision to be able to make movements slower than a pixel.
	 */
	protected double _dCenterY;
	/**
	 * Effective segment colors, taken from the genetic code if alive or brown if dead.
	 */
	protected Color[] _segColor;
	/**
	 * Effective branching points, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segBranch;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segredReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _seggreenReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segblueReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segplagueReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segwhiteReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _seggrayReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segdefaultReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segmagentaReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segpinkReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segcoralReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segorangeReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segbarkReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segvioletReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segvirusReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segmaroonReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segoliveReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segmintReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segcreamReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segspikeReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segspikepointReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _seglightblueReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segochreReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segskyReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _seglilacReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segsilverReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segfireReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _seglightbrownReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _seggreenbrownReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segbrownReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segiceReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segsickReaction;
	/**
	 * Effective segment reactions, taken from the genetic code if alive or brown if dead.
	 */
	protected int[] _segfriendReaction;
	/**
	 * The total number of segments of the organism
	 */
	protected int _segments;
	/**
	 * Growth ratio of the organism. Used to calculate segments when the organism is not
	 * fully grown.
	 */
	protected int _growthRatio;
	/**
	 * Total mass of this organism. The mass is calculated as the sum of all segment lengths.
	 * Used to calculate the effect of collisions.
	 */
	protected double _mass = 0;
	/**
	 * Moment of inertia of this organism, used to calculate the effect of collisions.
	 */
	protected double _I = 0;
	/**
	 * Chemical energy stored by this organism
	 */
	protected double _energy;
	/**
	 * Organism size independent on its position in the world.
	 * Let p be a point in the organism. Then, p.x + x - _sizeRect.x is the x coordinate
	 * of p representation in the world.
	 */
	protected Rectangle _sizeRect = new Rectangle();
	/**
	 * Rotation angle that this organism has at a given moment.
	 */
	protected double _theta;
	/**
	 * Last frame angle, used to avoid calculating point rotations when the angle doesn't
	 * change between two consecutive frames.
	 */
	protected double _lastTheta = -1;
	/**
	 * Rotated segments of the last frame, to use when _theta == _lastTheta
	 */
	protected int x1[],y1[],x2[],y2[];
	/**
	 * Speed. Variation applied to organism coordinates at every frame.
	 */
	protected double dx=0d, dy=0d;
	/**
	 * Angular speed. Organism angle variation at every frame.
	 */
	protected double dtheta = 0d;
	/**
	 * Number of frames of life of this organism
	 */
	protected int _age=0;
	/**
	 * Color used to draw the organism when a collision occurs. We save the color that
	 * should be shown and the number of frames that it should be shown. If the number
	 * if frames is 0, each segment is shown in its color.
	 */
	protected Color _color;
	/**
	 * Number of frames in which the organism will be drawn in _color.
	 */
	protected int _framesColor = 0;
	/**
	 * Number of frame that need to pass between two reproductions, even they are not
	 * successfully.
	 */
	protected int _timeToReproduce = 0;
	/**
	 * Indicates if the organism has grown at the last frame. If it has grown it is
	 * necessary to recalculate its segments.
	 */
	protected int hasGrown;
	/**
	 * Indicates if it has moved at the last frame. If it has moved it is necessary
	 * to repaint it.
	 */
	protected boolean hasMoved = true;
	/**
	 * The place that this organism occupies at the last frame. If the organism
	 * moves, this rectangle must be painted too.
	 */
	protected Rectangle lastFrame = new Rectangle();
	/**
	 * Indicates if the organism is alive.
	 */
	protected boolean alive = true;
	private static transient Vector2D v = new Vector2D();
	/**
	 * Returns true if this organism is alive, false otherwise.
	 * 
	 * @return  true if this organism is alive, false otherwise.
	 */
	public boolean isAlive() {
		return alive;
	}
	/**
	 * Returns the amount of chemical energy stored by this organism.
	 * 
	 * @return  The amount of chemical energy stored by this organism.
	 */
	public double getEnergy() {
		return _energy;
	}
	/**
	 * Returns the identification number of this organism.
	 * 
	 * @return  The identification number of this organism.
	 */
	public int getID() {
		return _ID;
	}
	/**
	 * Returns the identification number of this organism's parent.
	 * 
	 * @return  The identification number of this organism's parent.
	 */
	public int getParentID() {
		return _parentID;
	}
	/**
	 * Returns the generation number of this organism.
	 * 
	 * @return  The generation number of this organism.
	 */
	public int getGeneration() {
		return _generation;
	}
	/**
	 * Returns the generation number of this organism for infections.
	 * 
	 * @return  The generation number of this organism for infections.
	 */
	public int getInfectedGeneration() {
		return _infectedGeneration;
	}
	/**
	 * Returns the age of this organism.
	 * 
	 * @return  The age of this organism, in number of frames.
	 */
	public int getAge() {
		return _age;
	}
	/**
	 * Returns the number of children that this organism produced.
	 * 
	 * @return  The number of children that this organism produced.
	 */
	public int getTotalChildren() {
		return _nTotalChildren;
	}
	/**
	 * Returns the number of organisms killed by this organism.
	 * 
	 * @return  The number of organisms killed by this organism.
	 */
	public int getTotalKills() {
		return _nTotalKills;
	}
	/**
	 * Returns the number of organisms infected by this organism.
	 * 
	 * @return  The number of organisms infected by this organism.
	 */
	public int getTotalInfected() {
		return _nTotalInfected;
	}
	/**
	 * Returns a reference to this organism's genetic code.
	 * 
	 * @return  A reference to this organism's genetic code.
	 */
	public GeneticCode getGeneticCode() {
		return _geneticCode;
	}
	/**
	 * Returns the total mass of this organism.
	 * 
	 * @return  The total mass of this organism calculated as the sum
	 * of all its segments length.
	 */
	public double getMass() {
		return _mass;
	}
	/**
	 * Basic constructor. Doesn't initialize it: use {@link randomCreate}
	 * or {@link inherit} to do this.
	 * 
	 * @param world  A reference to the world where this organism is in.
	 */
	public Organism(World world) {
		_world = world;
		_visibleWorld = world._visibleWorld;
		_theta = Utils.random.nextDouble() * FastMath.PI * 2d;
	}
	/**
	 * Construct an organism with a given genetic code. Doesn't initialize it:
	 * use {@link pasteOrganism} to do it. Use {@link World.addOrganism} to add
	 * it to the world.
	 * 
	 * @param world  A reference to the world where this organism is in.
	 * @param geneticCode  A reference to the genetic code of this organism.
	 */
	public Organism(World world, GeneticCode geneticCode) {
		_world = world;
		_visibleWorld = world._visibleWorld;
		_theta = Utils.random.nextDouble() * FastMath.PI * 2d;
		_geneticCode = geneticCode;
	}
	/**
	 * Creates all data structures of this organism. Must be used after the organism
	 * has a genetic code assigned.
	 */
	protected void create() {
		_segments = _geneticCode.getNGenes() * _geneticCode.getSymmetry();
		_segColor = new Color[_segments];
		for (int i = 0; i < _segments; i++)
			_segColor[i] = _geneticCode.getGene(i%_geneticCode.getNGenes()).getColor();
		_segBranch = new int[_segments];
		for (int j = 0; j < _segments; j++)
			_segBranch[j] = _geneticCode.getGene(j%_geneticCode.getNGenes()).getBranch();
		_segredReaction = new int[_segments];
		for (int a = 0; a < _segments; a++)
			_segredReaction[a] = _geneticCode.getGene(a%_geneticCode.getNGenes()).getredReaction();
		_seggreenReaction = new int[_segments];
		for (int b = 0; b < _segments; b++)
			_seggreenReaction[b] = _geneticCode.getGene(b%_geneticCode.getNGenes()).getgreenReaction();
		_segblueReaction = new int[_segments];
		for (int c = 0; c < _segments; c++)
			_segblueReaction[c] = _geneticCode.getGene(c%_geneticCode.getNGenes()).getblueReaction();
		_segplagueReaction = new int[_segments];
		for (int d = 0; d < _segments; d++)
			_segplagueReaction[d] = _geneticCode.getGene(d%_geneticCode.getNGenes()).getplagueReaction();
		_segwhiteReaction = new int[_segments];
		for (int e = 0; e < _segments; e++)
			_segwhiteReaction[e] = _geneticCode.getGene(e%_geneticCode.getNGenes()).getwhiteReaction();
		_seggrayReaction = new int[_segments];
		for (int f = 0; f < _segments; f++)
			_seggrayReaction[f] = _geneticCode.getGene(f%_geneticCode.getNGenes()).getgrayReaction();
		_segdefaultReaction = new int[_segments];
		for (int g = 0; g < _segments; g++)
			_segdefaultReaction[g] = _geneticCode.getGene(g%_geneticCode.getNGenes()).getdefaultReaction();
		_segmagentaReaction = new int[_segments];
		for (int h = 0; h < _segments; h++)
			_segmagentaReaction[h] = _geneticCode.getGene(h%_geneticCode.getNGenes()).getmagentaReaction();
		_segpinkReaction = new int[_segments];
		for (int i = 0; i < _segments; i++)
			_segpinkReaction[i] = _geneticCode.getGene(i%_geneticCode.getNGenes()).getpinkReaction();
		_segcoralReaction = new int[_segments];
		for (int j = 0; j < _segments; j++)
			_segcoralReaction[j] = _geneticCode.getGene(j%_geneticCode.getNGenes()).getcoralReaction();
		_segorangeReaction = new int[_segments];
		for (int k = 0; k < _segments; k++)
			_segorangeReaction[k] = _geneticCode.getGene(k%_geneticCode.getNGenes()).getorangeReaction();
		_segbarkReaction = new int[_segments];
		for (int l = 0; l < _segments; l++)
			_segbarkReaction[l] = _geneticCode.getGene(l%_geneticCode.getNGenes()).getbarkReaction();
		_segvioletReaction = new int[_segments];
		for (int m = 0; m < _segments; m++)
			_segvioletReaction[m] = _geneticCode.getGene(m%_geneticCode.getNGenes()).getvioletReaction();
		_segvirusReaction = new int[_segments];
		for (int n = 0; n < _segments; n++)
			_segvirusReaction[n] = _geneticCode.getGene(n%_geneticCode.getNGenes()).getvirusReaction();
		_segmaroonReaction = new int[_segments];
		for (int o = 0; o < _segments; o++)
			_segmaroonReaction[o] = _geneticCode.getGene(o%_geneticCode.getNGenes()).getmaroonReaction();
		_segoliveReaction = new int[_segments];
		for (int p = 0; p < _segments; p++)
			_segoliveReaction[p] = _geneticCode.getGene(p%_geneticCode.getNGenes()).getoliveReaction();
		_segmintReaction = new int[_segments];
		for (int q = 0; q < _segments; q++)
			_segmintReaction[q] = _geneticCode.getGene(q%_geneticCode.getNGenes()).getmintReaction();
		_segcreamReaction = new int[_segments];
		for (int r = 0; r < _segments; r++)
			_segcreamReaction[r] = _geneticCode.getGene(r%_geneticCode.getNGenes()).getcreamReaction();
		_segspikeReaction = new int[_segments];
		for (int s = 0; s < _segments; s++)
			_segspikeReaction[s] = _geneticCode.getGene(s%_geneticCode.getNGenes()).getspikeReaction();
		_seglightblueReaction = new int[_segments];
		for (int t = 0; t < _segments; t++)
			_seglightblueReaction[t] = _geneticCode.getGene(t%_geneticCode.getNGenes()).getlightblueReaction();
		_segochreReaction = new int[_segments];
		for (int u = 0; u < _segments; u++)
			_segochreReaction[u] = _geneticCode.getGene(u%_geneticCode.getNGenes()).getochreReaction();
		_seglightbrownReaction = new int[_segments];
		for (int v = 0; v < _segments; v++)
			_seglightbrownReaction[v] = _geneticCode.getGene(v%_geneticCode.getNGenes()).getlightbrownReaction();
		_segbrownReaction = new int[_segments];
		for (int w = 0; w < _segments; w++)
			_segbrownReaction[w] = _geneticCode.getGene(w%_geneticCode.getNGenes()).getbrownReaction();
		_segsickReaction = new int[_segments];
		for (int x = 0; x < _segments; x++)
			_segsickReaction[x] = _geneticCode.getGene(x%_geneticCode.getNGenes()).getsickReaction();
		_segskyReaction = new int[_segments];
		for (int y = 0; y < _segments; y++)
			_segskyReaction[y] = _geneticCode.getGene(y%_geneticCode.getNGenes()).getskyReaction();
		_seglilacReaction = new int[_segments];
		for (int z = 0; z < _segments; z++)
			_seglilacReaction[z] = _geneticCode.getGene(z%_geneticCode.getNGenes()).getlilacReaction();
		_segiceReaction = new int[_segments];
		for (int a = 0; a < _segments; a++)
			_segiceReaction[a] = _geneticCode.getGene(a%_geneticCode.getNGenes()).geticeReaction();
		_segsilverReaction = new int[_segments];
		for (int b = 0; b < _segments; b++)
			_segsilverReaction[b] = _geneticCode.getGene(b%_geneticCode.getNGenes()).getsilverReaction();
		_segfireReaction = new int[_segments];
		for (int c = 0; c < _segments; c++)
			_segfireReaction[c] = _geneticCode.getGene(c%_geneticCode.getNGenes()).getfireReaction();
		_segfriendReaction = new int[_segments];
		for (int d = 0; d < _segments; d++)
			_segfriendReaction[d] = _geneticCode.getGene(d%_geneticCode.getNGenes()).getfriendReaction();
		_seggreenbrownReaction = new int[_segments];
		for (int e = 0; e < _segments; e++)
			_seggreenbrownReaction[e] = _geneticCode.getGene(e%_geneticCode.getNGenes()).getgreenbrownReaction();
		_segspikepointReaction = new int[_segments];
		for (int f = 0; f < _segments; f++)
			_segspikepointReaction[f] = _geneticCode.getGene(f%_geneticCode.getNGenes()).getspikepointReaction();
		_startPointX = new int[_segments];
		_startPointY = new int[_segments];
		_endPointX = new int[_segments];
		_endPointY = new int[_segments];
		_m1 = new double[_segments];
		_m2 = new double[_segments];
		_m = new double[_segments];
		_mphoto = new double[_segments];
		x1 = new int[_segments];
		y1 = new int[_segments];
		x2 = new int[_segments];
		y2 = new int[_segments];
	}
	/**
	 * Initializes variables for a new random organism and finds a place
	 * to put it in the world.
	 * 
	 * @return  true if it found a place for this organism or false otherwise.
	 */
	public boolean randomCreate() {
		// Generates a random genetic code
		_geneticCode = new GeneticCode();
		// it has no parent
		_parentID = -1;
		_generation = 1;
		_growthRatio = 16;
		// initial energy
		_energy = Math.min(Utils.INITIAL_ENERGY,_world.getCO2());
		_world.decreaseCO2(_energy);
		_world.addO2(_energy);
		// initialize
		create();
		symmetric();
		// put it in the world
		return placeRandom();
	}
	/**
	 * Initializes variables for a new organism born from an existing
	 * organism. Generates a mutated genetic code based on the parent's one
	 * and finds a place in the world to put it.
	 * 
	 * @param parent  The organism from which this organism is born. 
	 * @return  true if it found a place for this organism or false otherwise.
	 */
	public boolean inherit(Organism parent, boolean first) {
		GeneticCode inheritGeneticCode;
		boolean ok = true;
		
		// Create the inherited genetic code
		if (parent._infectedGeneticCode != null) {
			inheritGeneticCode = parent._infectedGeneticCode;
		    // Add 1 to the generation number
		    _generation = parent.getInfectedGeneration() + 1;
		} else {
			inheritGeneticCode = parent._geneticCode;
		    // Add 1 to the generation number
			_generation = parent.getGeneration() + 1;
		}
		_geneticCode = new GeneticCode(inheritGeneticCode);
		// Take a reference to the parent
		_parentID = parent.getID();
		_growthRatio = 16;
		// Initial energy: minimum energy required to reproduce is divided
		// between all children and the parent.
		if ((parent._geneticCode.getSelfish()) && (parent._infectedGeneticCode == null)) {
		    _energy = Math.min(((inheritGeneticCode._reproduceEnergy / 2) / (double)parent._nChildren), parent._energy);
		} else if ((parent._indigo > 0) && (parent._infectedGeneticCode != null)) {
			if (parent._geneticCode.getSymmetry() != 3) {
			if ((parent._isjade) && (!parent._isaconsumer) && (inheritGeneticCode._reproduceEnergy > parent._geneticCode._reproduceEnergy)) {
			_energy = Math.min(((parent._geneticCode._reproduceEnergy / ((parent._indigo/(Utils.INDIGO_DIVISOR)) + 1)) / (double)(parent._nChildren + 1)), parent._energy);
			} else {
			_energy = Math.min(((inheritGeneticCode._reproduceEnergy / ((parent._indigo/(Utils.INDIGO_DIVISOR)) + 1)) / (double)(parent._nChildren + 1)), parent._energy);
			}
			} else {
			if ((parent._isjade) && (!parent._isaconsumer) && (inheritGeneticCode._reproduceEnergy > parent._geneticCode._reproduceEnergy)) {
			_energy = Math.min(((parent._geneticCode._reproduceEnergy / ((parent._indigo/(1.5*Utils.INDIGO_DIVISOR)) + 1)) / (double)(parent._nChildren + 1)), parent._energy);
			} else {
			_energy = Math.min(((inheritGeneticCode._reproduceEnergy / ((parent._indigo/(1.5*Utils.INDIGO_DIVISOR)) + 1)) / (double)(parent._nChildren + 1)), parent._energy);
			}
			}
		} else {
			if ((parent._isjade) && (!parent._isaconsumer) && (inheritGeneticCode._reproduceEnergy > parent._geneticCode._reproduceEnergy)) {
			_energy = Math.min((parent._geneticCode._reproduceEnergy / (double)(parent._nChildren + 1)), parent._energy);
			} else {
		    _energy = Math.min((inheritGeneticCode._reproduceEnergy / (double)(parent._nChildren + 1)), parent._energy);
			}
		}
		if (first || parent._energy >= _energy+Utils.YELLOW_ENERGY_CONSUMPTION) {
			// Initialize
			create();
			symmetric();
			// Put it in the world, near its parent
			ok = placeNear(parent);
			if (ok && !first)
				parent.useEnergy(Utils.YELLOW_ENERGY_CONSUMPTION);
		} else
			ok = false;
		
		return ok;
	}
	/**
	 * Initializes variables for a new organism born from an existing
	 * transformed organism. Generates a mutated genetic code based on the transformers's one
	 * and finds a place in the world to put it.
	 * 
	 * @param victim  The organism from which this organism is transformed. 
	 * @return  true if it found a place for this organism or false otherwise.
	 */
	public boolean inheritTransformation(Organism victim, boolean first) {
		GeneticCode inheritGeneticCode;
		boolean ok = true;
		
		// Create the transformed genetic code
		inheritGeneticCode = victim._infectedGeneticCode;
		victim._infectedGeneticCode = null;
		// Add 1 to the generation number
		_generation = victim.getInfectedGeneration() + 1;
		_geneticCode = new GeneticCode(inheritGeneticCode);
		// Take a reference to the victim
		_parentID = victim.getID();
		_growthRatio = 16;
		// Initial energy is total energy of the victim
		_energy = victim._energy;
		if (first) {
			// Initialize
			create();
			symmetric();
			// Put it in the world, near its parent
			ok = placeNear(victim);
		} else
			ok = false;
		
		return ok;
	}
	/**
	 * Places the organism at the specified position in the world and initializes its
	 * variables. The organism must has an assigned genetic code.
	 * 
	 * @param posx  The x coordinate of the position in the world we want to put this organism.
	 * @param posy  The y coordinate of the position in the world we want to put this organism.
	 * @return  true if there were enough space to put the organism, false otherwise.
	 */
	public boolean pasteOrganism(int posx, int posy) {
		boolean isacheater =false;
		_parentID = -1;
		_generation = 1;
		_growthRatio = 16;
		create();
		symmetric();
		for (int i = 0; i < _geneticCode.getNGenes(); i++) {
			if (_geneticCode.getGene(i).getBranch() >= i) {
				isacheater =true;
			}
		}
		if (isacheater == false) {
		_geneticCode._max_age = Utils.MAX_AGE + ((_geneticCode.getNGenes() * _geneticCode.getSymmetry())/Utils.AGE_DIVISOR);
		_dCenterX = _centerX = posx;
		_dCenterY = _centerY = posy;
		calculateBounds(true);
		// Check that the position is inside the world
		if (isInsideWorld()) {
			// Check that the organism will not overlap other organisms
			if (_world.fastCheckHit(this) == null) {
				// Generem identificador
				_ID = _world.getNewId();
				_energy = Math.min(Utils.INITIAL_ENERGY,_world.getCO2());
				_world.decreaseCO2(_energy);
				_world.addO2(_energy);
				return true;
			}
		}}
		// It can't be placed		
		return false;
	}
	/**
	 * Translates the genetic code of this organism to its segments representation in the world.
	 * Also, calculates some useful information like segments length, inertia, etc.
	 * This method must be called when an organism is firstly displayed on the world and every
	 * time it changes its size.
	 * inherit, randomCreate and pasteOrganism are the standard ways to add an organism to a world
	 * and they already call this method.
	 */
	public void symmetric() {
		int i,j,segment=0;
		int symmetry = _geneticCode.getSymmetry();
		int mirror = _geneticCode.getMirror();
		int sequence = _segments / symmetry;
		int left=0, right=0, top=0, bottom=0;
		int centerX, centerY;
		double cx, cy;

		for (i=0; i<symmetry; i++) {
			for (j=0; j<sequence; j++,segment++) {
				// Here, we take the vector that forms the segment, scale it depending on
				// the relative size of the organism and rotate it depending on the
				// symmetry and mirroring.
				v.setModulus(_geneticCode.getGene(j).getLength()/Utils.scale[_growthRatio-1]);
				if (j==0) {
					_startPointX[segment] = 0;
					_startPointY[segment] = 0;
					if (mirror == 0 || i%2==0)
						v.setTheta(_geneticCode.getGene(j).getTheta()+i*2*FastMath.PI/symmetry);
					else {
						v.setTheta(_geneticCode.getGene(j).getTheta()+(i-1)*2*FastMath.PI/symmetry);
						v.invertX();
					}
				} else {
					if (_geneticCode.getGene(j).getBranch() == -1) {
						_startPointX[segment] = _endPointX[segment - 1];
						_startPointY[segment] = _endPointY[segment - 1];
						if (mirror == 0 || i%2==0)
							v.addDegree(_geneticCode.getGene(j).getTheta());
						else
							v.addDegree(-_geneticCode.getGene(j).getTheta());
					} else {
					if (_geneticCode.getGene(j).getBranch() == 0) {
						_startPointX[segment] = 0;
					    _startPointY[segment] = 0;
					    if (mirror == 0 || i%2==0)
							v.addDegree(_geneticCode.getGene(j).getTheta());
						else
							v.addDegree(-_geneticCode.getGene(j).getTheta());
					} else {
						_startPointX[segment] = _endPointX[(i * sequence) + _geneticCode.getGene(j).getBranch() - 1];
					    _startPointY[segment] = _endPointY[(i * sequence) + _geneticCode.getGene(j).getBranch() - 1];
					    if (mirror == 0 || i%2==0)
							v.addDegree(_geneticCode.getGene(j).getTheta());
						else
							v.addDegree(-_geneticCode.getGene(j).getTheta());
					    }
					}
				}
				// Apply the vector to the starting point to get the ending point.
				_endPointX[segment] = (int) FastMath.round(v.getX() + _startPointX[segment]);
				_endPointY[segment] = (int) FastMath.round(v.getY() + _startPointY[segment]);
			    // Calculate the bounding rectangle of this organism
			    left = Math.min(left, _endPointX[segment]);
			    right = Math.max(right, _endPointX[segment]);
			    top = Math.min(top, _endPointY[segment]);
			    bottom = Math.max(bottom, _endPointY[segment]);
			}
		}
		_sizeRect.setBounds(left, top, right-left+1, bottom-top+1);
		// image center
		centerX = (left+right)>>1;
		centerY = (top+bottom)>>1;
		_mass = 0;
		_I = 0;
		for (i=0; i<_segments; i++) {
			// express points relative to the image center
			_startPointX[i]-=centerX;
			_startPointY[i]-=centerY;
			_endPointX[i]-=centerX;
			_endPointY[i]-=centerY;
			// calculate points distance of the origin and modulus
			_m1[i] = FastMath.sqrt(_startPointX[i]*_startPointX[i]+_startPointY[i]*_startPointY[i]);
			_m2[i] = FastMath.sqrt(_endPointX[i]*_endPointX[i]+_endPointY[i]*_endPointY[i]);
			_m[i] = FastMath.sqrt(FastMath.pow(_endPointX[i]-_startPointX[i],2) +
                    FastMath.pow(_endPointY[i]-_startPointY[i],2));
			_mass += _m[i];
			_mphoto[i] = (0.6 + (0.48 / (double)sequence) + (1.44 / (double)symmetry)) * _m[i];
			// calculate inertia moment
			// the mass center of a segment is its middle point
			cx = (_startPointX[i] + _endPointX[i]) / 2d;
			cy = (_startPointY[i] + _endPointY[i]) / 2d;
			// add the effect of this segment, following the parallel axis theorem
			_I += FastMath.pow(_m[i],3)/12d +
				_m[i] * cx*cx + cy*cy;// mass * length^2 (center is at 0,0)
		}
	}
	/**
	 * Given a vector, calculates the resulting vector after a rotation, a scalation and possibly
	 * after mirroring it.
	 * The rotation degree and the mirroring is found using the Utils.degree array, where parameter
	 * mirror is the row and step is the column. The step represents the repetition of this vector
	 * following the organism symmetry.
	 * The scalation is calculated using the Utils.scale coefficients, using the organism's
	 * _growthRatio to find the appropriate value. 
	 * 
	 * @param p  The end point of the vector. The starting point is (0,0).
	 * @param step  The repetition of the vectors pattern  we are calculating.
	 * @param mirror  If mirroring is applied to this organism 1, otherwise 0.
	 * @return  The translated vector.
	 */
/*	private Vector2D translate(Point p, int step, int mirror) {
		if (p.x == 0 && p.y == 0)
			return new Vector2D();

		double px = p.x;
		double py = p.y;

		px /= Utils.scale[_growthRatio - 1];
		py /= Utils.scale[_growthRatio - 1];

		Vector2D v = new Vector2D(px,py);
		v.addDegree(Utils.degree[mirror][step]);

		if (Utils.invertX[mirror][step] != 0)
			v.invertX();
		if (Utils.invertY[mirror][step] != 0)
			v.invertY();

		return v;
	}*/
	/**
	 * Tries to find a spare place in the world for this organism and place it.
	 * It also generates an identification number for the organism if it can be placed
	 * somewhere.
	 * 
	 * @return  true if a suitable place has been found, false if not.
	 */
	private boolean placeRandom() {
		/* We try to place the organism in 12 different positions. If all of them
		 * are occupied, we return false.
		 */
		for (int i=12; i>0; i--) {
			/* Get a random point for the top left corner of the organism
			 * making sure it is inside the world.
			 */
			Point origin = new Point(
				Utils.random.nextInt(_world.getWidth()-_sizeRect.width),
				Utils.random.nextInt(_world.getHeight()-_sizeRect.height));
			setBounds(origin.x,origin.y,_sizeRect.width,_sizeRect.height);
			_dCenterX = _centerX = origin.x + (_sizeRect.width>>1);
			_dCenterY = _centerY = origin.y + (_sizeRect.height>>1);
			// Check that the position is not occupied.
			if (_world.fastCheckHit(this) == null) {
				// Generate an identification
				_ID = _world.getNewId();
				return true;
			}
		}
		// If we get here, we haven't find a place for this organism.
		return false;
	}
	/**
	 * Tries to find a spare place near its parent for this organism and place it.
	 * It also generates an identification number for the organism if it can be placed
	 * somewhere and substracts its energy from its parent's energy.
	 * 
	 * @return  true if a suitable place has been found, false if not.
	 */
	private boolean placeNear(Organism parent) {
		int nPos = Utils.random.nextInt(8);
		// Try to put it in any possible position, starting from a randomly chosen one.
		for (int nSide = 0; nSide < 8; nSide++) {
			// Calculate candidate position
			_dCenterX = parent._dCenterX + (parent.width / 2 + width / 2+ 1) * Utils.side[nPos][0]; 
			_dCenterY = parent._dCenterY + (parent.height / 2 + height / 2 + 1) * Utils.side[nPos][1];
			_centerX = (int) _dCenterX;
			_centerY = (int) _dCenterY;
			calculateBounds(true);
			// Check this position is inside the world.
			if (isInsideWorld()) {
				// Check that it doesn't overlap with other organisms.
				if (_world.fastCheckHit(this) == null) {
					if (parent._geneticCode.getDisperseChildren()) {
						dx = Utils.side[nPos][0];
						dy = Utils.side[nPos][1];
					} else {
						dx = parent.dx;
						dy = parent.dy;
					}
					// Generate an identification
					_ID = _world.getNewId();
					// Substract the energy from the parent
					parent._energy -= _energy;
					return true;
				}
			}
			nPos = (nPos + 1) % 8;
		}
		// It can't be placed.
		return false;
	}
	/**
	 * Draws this organism to a graphics context.
	 * The organism is drawn at its position in the world.
	 * 
	 * @param g  The graphics context to draw to.
	 */
	public void draw(Graphics g) {
		int i;
		if (_framesColor > 0) {
			// Draw all the organism in the same color
			g.setColor(_color);
			_framesColor--;
			for (i=0; i<_segments; i++)
				g.drawLine(
					x1[i] + _centerX,
					y1[i] + _centerY,
					x2[i] + _centerX,
					y2[i] + _centerY);
		} else {
			if (alive) {
				for (i=0; i<_segments; i++) {
					g.setColor(_segColor[i]);
					g.drawLine(
							x1[i] + _centerX,
							y1[i] + _centerY,
							x2[i] + _centerX,
							y2[i] + _centerY);
				}
			} else {
				g.setColor(Utils.ColorBROWN);
				for (i=0; i<_segments; i++) {
					g.drawLine(
							x1[i] + _centerX,
							y1[i] + _centerY,
							x2[i] + _centerX,
							y2[i] + _centerY);
				}
			}
		}
	}
	/**
	 * Calculates the position of all organism points in the world, depending on
	 * its rotation. It also calculates the bounding rectangle of the organism.
	 * This method must be called from outside this class only when doing
	 * manual drawing.  
	 * 
	 * @param force  To avoid calculations, segments position are only calculated
	 * if the organism's rotation has changed in the last frame. If it is necessary
	 * to calculate them even when the rotation hasn't changed, assign true to this
	 * parameter.
	 */
	public void calculateBounds(boolean force) {
		double left=java.lang.Double.MAX_VALUE, right=java.lang.Double.MIN_VALUE, 
		top=java.lang.Double.MAX_VALUE, bottom=java.lang.Double.MIN_VALUE;
		
		double theta;
		for (int i=_segments-1; i>=0; i--) {
			/* Save calculation: if rotation hasn't changed and it is not forced,
			 * don't calculate points again.
			 */
			if (_lastTheta != _theta || force) {
				theta=_theta+FastMath.atan2(_startPointY[i] ,_startPointX[i]);
				x1[i]=(int)(_m1[i]*FastMath.cos(theta));
				y1[i]=(int)(_m1[i]*FastMath.sin(theta));
				theta=_theta+ FastMath.atan2(_endPointY[i], _endPointX[i]);
				x2[i]=(int)(_m2[i]*FastMath.cos(theta));
				y2[i]=(int)(_m2[i]*FastMath.sin(theta));
			}
			// Finds the rectangle that comprises the organism
			left = Utils.min(left, x1[i]+ _dCenterX, x2[i]+ _dCenterX);
			right = Utils.max(right, x1[i]+ _dCenterX, x2[i]+ _dCenterX);
			top = Utils.min(top, y1[i]+ _dCenterY, y2[i]+ _dCenterY);
			bottom = Utils.max(bottom, y1[i]+ _dCenterY, y2[i]+ _dCenterY);
		}
		setBounds((int)left, (int)top, (int)(right-left+1)+1, (int)(bottom-top+1)+1);
		_lastTheta = _theta;
	}
	/**
	 * If its the time for this organism to grow, calculates its new segments and speed.
	 * An alive organism can grow once every 8 frames until it gets its maximum size.
	 */
	private void grow() {
		if (_growthRatio > 1 && (_age & 0x07) == 0x07 && alive && _energy >= _mass/10) {
			_growthRatio--;
			double m = _mass;
			double I = _I;
			symmetric();
			// Cynetic energy is constant. If mass changes, speed must also change.
			m = FastMath.sqrt(m/_mass);
			dx *= m;
			dy *= m;
			dtheta *= FastMath.sqrt(I/_I);
			hasGrown = 1;
		} else {
			if (_growthRatio < 15 && _energy < _mass/12) {
				_growthRatio++;
				double m = _mass;
				double I = _I;
				symmetric();
				// Cynetic energy is constant. If mass changes, speed must also change.
				m = FastMath.sqrt(m/_mass);
				dx *= m;
				dy *= m;
				dtheta *= FastMath.sqrt(I/_I);
				hasGrown = -1;
			} else
				hasGrown = 0;
		}
	}
	
	/**
	 * Makes this organism reproduce. It tries to create at least one
	 * child and at maximum 8 (depending on the number of yellow segments
	 * of the organism) and put them in the world.
	 */
	public void reproduce() {
		Organism newOrg;
		
		for (int i=0; i < Utils.between(_nChildren,1,8); i++) {
			newOrg = new Organism(_world);
			if (newOrg.inherit(this, i==0)) {
				// It can be created
				_nTotalChildren++;
				_world.addOrganism(newOrg,this);
				_infectedGeneticCode = null;
			}
			_timeToReproduce = 20;
		}
	}
	/**
	 * Makes this organism create a virus and put it in the world.
	 */
	public void reproduceVirus() {
		Organism newOrg;
		
		for (int i=0; i < 1; i++) {
			newOrg = new Organism(_world);
			if (newOrg.inherit(this, i==0)) {
				// It can be created
				_nTotalChildren++;
				_world.addOrganism(newOrg,this);
				_infectedGeneticCode = null;
			}
			_timeToReproduce = 20;
		}
	}
	/**
	 * Makes this organism transform into its killer
	 */
	public void transform() {
		Organism newOrg;
		
		for (int i=0; i < 1; i++) {
			newOrg = new Organism(_world);
			if (newOrg.inheritTransformation(this, i==0)) {
				// It can be created
				_world.addOrganism(newOrg,this);
			}
		}
	}

	public double[] movePreProcessing() {
        hasMoved = false;
        lastFrame.setBounds(this);
        if (FastMath.abs(dx) < Utils.tol) dx = 0;
        if (FastMath.abs(dy) < Utils.tol) dy = 0;
        if (FastMath.abs(dtheta) < Utils.tol) dtheta = 0;
        // Apply segment effects for this frame.
        segmentsFrameEffects();
        // Apply rubbing effects
        rubbingFramesEffects();
        // Check if it can grow or shrink
        grow();
        // Movement
        double dxbak=dx, dybak=dy, dthetabak=dtheta;
        offset(dx,dy,dtheta);
        calculateBounds(hasGrown!=0);

        double[] returnValues = new double[3];
        returnValues[0] = dxbak;
        returnValues[1] = dybak;
        returnValues[2] = dthetabak;
        return returnValues;
    }

	/**
	 * Executes the organism's movement for this frame.
	 * This includes segments upkeep and activation,
	 * movement, growth, collision detection, reproduction,
	 * respiration and death.
	 */
	public boolean move() {
	    double[] movePre = movePreProcessing();

        double dxbak=movePre[0], dybak=movePre[1], dthetabak=movePre[2];
        boolean collision = false;
		
		if (hasGrown!=0 || dx!=0 || dy!=0 || dtheta!=0) {
			hasMoved = true;
			// Check it is inside the world
			collision = !isInsideWorld();
			// Collision detection with biological corridors
			if (alive) {
				OutCorridor c = _world.checkHitCorridor(this);
				if (c != null && c.canSendOrganism()) {
					if (c.sendOrganism(this))
						return false;
				}
			}

			// Collision detection with other organisms.
            Organism otherOrganism = _world.checkHit(this);
			if (otherOrganism != null) {
			    if (this.contact(otherOrganism)) {
                    collision = true;
                }
            }


			// If there is a collision, undo movement.
			if (collision) {
				hasMoved = false;
				offset(-dxbak,-dybak,-dthetabak);
				if (hasGrown!=0) {
					_growthRatio+=hasGrown;
					symmetric();
				}
				calculateBounds(hasGrown!=0);
			}
		}
		// Substract one to the time needed to reproduce
		if (_timeToReproduce > 0)
			_timeToReproduce--;
		// Check if it can reproduce: it needs enough energy and to be adult
		if (_energy > _geneticCode.getReproduceEnergy() + Utils.YELLOW_ENERGY_CONSUMPTION*(_nChildren-1)
				&& _growthRatio==1 && _timeToReproduce==0 && alive)
			reproduce();
		// Check that it don't exceed the maximum chemical energy
		if (_energy > _geneticCode.getReproduceEnergy()) {
			if (_energy > 2*_geneticCode.getReproduceEnergy()) {
				useEnergy(_energy - 2*_geneticCode.getReproduceEnergy());
			} else {
			    useEnergy((_energy - _geneticCode.getReproduceEnergy()) / 300);
			}
		}
		// Maintenance
		breath();
		// Check that the organism has energy after this frame
		return _energy > Utils.tol;
	}
	/**
	 * Makes the organism spend an amount of energy using the
	 * respiration process.
	 * 
	 * @param q  The quantity of energy to spend.
	 * @return  true if the organism has enough energy and there are
	 * enough oxygen in the atmosphere, false otherwise.
	 */
	public boolean useEnergy(double q) {
		if (_energy < q) {
			return false;
		}
		double respiration = _world.respiration(q);
		_energy -= respiration;
		if (respiration < q)
			return false;
		return true;
	}
	/**
	 * Realize the respiration process to maintain its structure.
	 * Aging is applied here too.
	 */
	public void breath() {
		if (alive) {
			_age++;
			// Respiration process
			boolean canBreath = useEnergy(Math.min((_mass - _lowmaintenance) / Utils.SEGMENT_COST_DIVISOR, _energy));
			if ((_age >> 8) > _geneticCode.getMaxAge() || !canBreath) {
				// It's dead, but still may have energy
				die(null);
			} else {
				if (_energy <= Utils.tol) {
					alive = false;
					_world.decreasePopulation();
					_world.organismHasDied(this, null);
				}
			}
		} else {
			// The corpse slowly decays
			useEnergy(Math.min(_energy, Utils.DECAY_ENERGY));
		}
	}
	/**
	 * Kills the organism. Sets its segments to brown and tells the world
	 * about the event.
	 * 
	 * @param killingOrganism  The organism that has killed this organism,
	 * or null if it has died of natural causes.
	 */
	public void die(Organism killingOrganism) {
		alive = false;
		hasMoved = true;
		for (int i=0; i<_segments; i++) {
			_segColor[i] = Utils.ColorBROWN;
		}
		_world.decreasePopulation();
		if (killingOrganism != null)
			killingOrganism._nTotalKills++;
		_world.organismHasDied(this, killingOrganism);
	}
	/**
	 * Infects this organism with a genetic code.
	 * Tells the world about this event.
	 * Not currently used.
	 * 
	 * @param infectingCode  The genetic code that infects this organism.
	 */
	public void infectedBy(GeneticCode infectingCode) {
		_infectedGeneticCode = infectingCode;
		_world.organismHasBeenInfected(this, null);
	}
	/**
	 * Infects this organism with the genetic code of another organism.
	 * Tells the world about this event.
	 * 
	 * @param infectingOrganism  The organism that is infecting this one.
	 */
	public void infectedBy(Organism infectingOrganism) {
		infectingOrganism._nTotalInfected++;
		_infectedGeneticCode = infectingOrganism.getGeneticCode();
		_infectedGeneration = infectingOrganism.getGeneration();
		_world.organismHasBeenInfected(this, infectingOrganism);
	}
	/**
	 * Calculates the resulting speeds after a collision between two organisms, following
	 * physical rules.
	 * 
	 * @param org  The other organism in the collision.
	 * @param p  Intersection point between the organisms.
	 * @param l  Line that has collided. Of the two lines, this is the one that collided
	 * on the center, not on the vertex.
	 * @param thisOrganism  true if l is a line of this organism, false if l is a line of org.
	 */
	private final void touchMove(Organism org, Point2D.Double p, Line2D l, boolean thisOrganism) {
		// Distance vector between centers of mass and p
		double rapx = p.x - _dCenterX;
		double rapy = p.y - _dCenterY;
		double rbpx = p.x - org._dCenterX;
		double rbpy = p.y - org._dCenterY;
		// Speeds of point p in the body A and B, before collision.
		double vap1x = dx - dtheta * rapy + hasGrown*rapx/10d;
		double vap1y = dy + dtheta * rapx + hasGrown*rapy/10d;
		double vbp1x = org.dx - org.dtheta * rbpy;
		double vbp1y = org.dy + org.dtheta * rbpx;
		// Relative speeds between the two collision points.
		double vab1x = vap1x - vbp1x;
		double vab1y = vap1y - vbp1y;
		// Normal vector to the impact line
		//First: perpendicular vector to the line
		double nx = l.getY1() - l.getY2();
		double ny = l.getX2() - l.getX1();
		//Second: normalize, modulus 1
		double modn = FastMath.sqrt(nx * nx + ny * ny);
		if (modn == 0) {
			modn = 0.000000000000001;
		}
		nx /= modn;
		ny /= modn;
		/*Third: of the two possible normal vectors we need the one that points to the
		 * outside; we choose the one that its final point is the nearest to the center
		 * of the other line.
		 */
		if (thisOrganism) {
			if ((p.x+nx-org._dCenterX)*(p.x+nx-org._dCenterX)+(p.y+ny-org._dCenterY)*(p.y+ny-org._dCenterY) <
				(p.x-nx-org._dCenterX)*(p.x-nx-org._dCenterX)+(p.y-ny-org._dCenterY)*(p.y-ny-org._dCenterY)) {
				nx = -nx;
				ny = -ny;
			}
		} else {
			if ((p.x+nx-_dCenterX)*(p.x+nx-_dCenterX)+(p.y+ny-_dCenterY)*(p.y+ny-_dCenterY) >
				(p.x-nx-_dCenterX)*(p.x-nx-_dCenterX)+(p.y-ny-_dCenterY)*(p.y-ny-_dCenterY)) {
				nx = -nx;
				ny = -ny;
			}
		}
		// This is the j in the parallel axis theorem
		double j = (-(1+Utils.ELASTICITY) * (vab1x * nx + vab1y * ny)) / 
			(1/_mass + 1/org._mass + FastMath.pow(rapx * ny - rapy * nx, 2) / _I +
                    FastMath.pow(rbpx * ny - rbpy * nx, 2) / org._I);
		// Final speed
		dx = Utils.between(dx + j*nx/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
		dy = Utils.between(dy + j*ny/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
		org.dx = Utils.between(org.dx - j*nx/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
		org.dy = Utils.between(org.dy - j*ny/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
		dtheta = Utils.between(dtheta + j * (rapx * ny - rapy * nx) / _I, -Utils.MAX_ROT, Utils.MAX_ROT);
		org.dtheta = Utils.between(org.dtheta - j * (rbpx * ny - rbpy * ny) / org._I, -Utils.MAX_ROT, Utils.MAX_ROT);
	}
	/**
	 * Checks if the organism is inside the world. If it is not, calculates its
	 * speed after the collision with the world border.
	 * This calculation should be updated to follow the parallel axis theorem, just
	 * like the collision between two organisms.
	 * 
	 * @return  true if the organism is inside the world, false otherwise.
	 */
	private final boolean isInsideWorld() {
		// Check it is inside the world
		if (x<=0 || y<=0 || x+width>=_world.getWidth() || y+height>=_world.getHeight()) {
			if (_mass == 0 && alive && x == 0 && y == 0)
				die(this);
			// Adjust direction
			if (x <= 0 || x + width >= _world.getWidth())
				dx = -dx;
			if (y <= 0 || y + height >= _world.getHeight())
				dy = -dy;
			dtheta = 0;
			return false;
		}
		return true;
	}
	/**
	 * Moves the organism and rotates it.
	 * 
	 * @param offsetx  displacement on the x axis.
	 * @param offsety  displacement on the y axis.
	 * @param offsettheta  rotation degree.
	 */
	private final void offset(double offsetx, double offsety, double offsettheta) {
		_dCenterX += offsetx; _dCenterY += offsety; _theta += offsettheta;
		_centerX = (int)_dCenterX; _centerY = (int)_dCenterY; 
	}
	/**
	 * Finds if two organism are touching and if so applies the effects of the
	 * collision.
	 * 
	 * @param org  The organism to check for collisions.
	 * @return  true if the two organisms are touching, false otherwise.
	 */
	public final boolean contact(Organism org) {
		int i,j;
		ExLine2DDouble line = new ExLine2DDouble();
		ExLine2DDouble bline = new ExLine2DDouble();
		// Check collisions for all segments
		for (i = _segments-1; i >= 0; i--) {
			// Consider only segments with modulus greater than 1
			if (_m[i]>=1) { 
				line.setLine(x1[i]+_centerX, y1[i]+_centerY, x2[i]+_centerX, y2[i]+_centerY);
				// First check if the line intersects the bounding box of the other organism
				if (org.intersectsLine(line)) {
					// Do the same for the other organism's segments.
					for (j = org._segments-1; j >= 0; j--) {
						if (org._m[j]>=1) {
							bline.setLine(org.x1[j] + org._centerX, org.y1[j] + org._centerY,
									org.x2[j] + org._centerX, org.y2[j] + org._centerY);
							if (intersectsLine(bline) && line.intersectsLine(bline)) {
								// If we found two intersecting segments, apply effects
								touchEffects(org,i,j,true);
								// Intersection point
								Point2D.Double intersec= line.getIntersection(bline);
								/* touchMove needs to know which is the line that collides from the middle (not
								 * from a vertex). Try to guess it by finding the vertex nearest to the
								 * intersection point.
								 */
								double dl1, dl2, dbl1, dbl2;
								dl1 = intersec.distanceSq(line.getP1());
								dl2 = intersec.distanceSq(line.getP2());
								dbl1 = intersec.distanceSq(bline.getP1());
								dbl2 = intersec.distanceSq(bline.getP2());
								// Use this to send the best choice to touchMove
								if (Math.min(dl1, dl2) < Math.min(dbl1, dbl2))
									touchMove(org,intersec,bline,false);
								else
									touchMove(org,intersec,line,true);
								// Find only one collision to speed up.
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * Applies the effects produced by two touching segments.
	 * 
	 * @param org  The organism which is touching.
	 * @param seg  Index of this organism's segment. 
	 * @param oseg  Index of the other organism's segment.
	 * @param firstCall  Indicates if this organism is the one that has detected the collision
	 * or this method is called by this same method in the other organism. 
	 */
	private final void touchEffects(Organism org, int seg, int oseg, boolean firstCall) {
		double takenEnergy = 0;
		int i;
		handleMySegment(org, seg, oseg);
		if ((((_parentID == org._ID || _ID == org._parentID) && !_geneticCode.getGenerationBattle() && !org._geneticCode.getGenerationBattle()) ||
			(_parentID == org._parentID && !_geneticCode.getSiblingBattle() && !org._geneticCode.getSiblingBattle() && _parentID != -1 ) ||
			(_transfersenergy && org._transfersenergy && ((_geneticCode.getPeaceful() && org._geneticCode.getPeaceful()) ||
			((_lengthfriend == org._lengthfriend || _thetafriend == org._thetafriend) && _geneticCode.getSocial() && org._geneticCode.getSocial())))) && org.alive) {
			for (i=_segments-1; i>=0; i--) {
				switch (getTypeColor(_segColor[i])) {
				// Teal segment: React on other organisms
				case TEAL:
					switch (getTypeColor(_segColor[seg])) {
					default:
		                  switch (getTypeColor(org._segColor[oseg])) {
		                  default:
							    if (_segfriendReaction[seg] == -2) {
							    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
							    	dx=0;
							    	dy=0;
							    	dtheta=0;
							    }} else
							    if (_segfriendReaction[seg] == -1) {
							    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
								    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
								    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
								    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
			                    }} else 
			                    if (_segfriendReaction[seg] == 1) {
			                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
								    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
								    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
								    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
			                    }} else
			                    if (_segfriendReaction[seg] == 2) {
			                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
									dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
									dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
									dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
				                }} else
			    	            break;	                    
						  }
						  break;
					}
					break;
				}}
			switch (getTypeColor(_segColor[seg])) {
			case MINT:
				// Mint segment: Remove an infection
				switch (getTypeColor(org._segColor[oseg])) {
				case MINT:
					if ((_infectedGeneticCode != null) || (org._infectedGeneticCode != null)) {
					    if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
							_infectedGeneticCode = null;
							org._infectedGeneticCode = null;
							org.setColor(Utils.ColorMINT);
							setColor(Utils.ColorMINT);
						}
					}
					break;
				default:
					if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
						if ((org._infectedGeneticCode != _geneticCode) && (org._infectedGeneticCode != null)) {
							if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
								org._infectedGeneticCode = null;
								org.setColor(Color.CYAN);
								setColor(Utils.ColorMINT);
							}
						}
					} else {
						if ((_geneticCode.getFamilial()) && ((_lengthfriend == org._lengthfriend) || (_thetafriend == org._thetafriend) ||
							(_parentID == org._ID) || (_ID == org._parentID) || (_parentID == org._parentID && _parentID != -1 ))) {
							if ((org._infectedGeneticCode != _geneticCode) && (org._infectedGeneticCode != null)) {
								if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
									org._infectedGeneticCode = null;
									org.setColor(Color.CYAN);
									setColor(Utils.ColorMINT);
								}
							}
						}
					}
				}
				break;
			case MAGENTA:
				// Magenta segment: Heal all sick segments
				switch (getTypeColor(org._segColor[oseg])) {
				case MAGENTA:
					for (int j = 0; j < org._segments; j++) {
				    	if ((org._segColor[j] == Utils.ColorLIGHTBROWN) || (org._segColor[j] == Utils.ColorGREENBROWN) || (org._segColor[j] == Utils.ColorPOISONEDJADE)
							|| (org._segColor[j] == Utils.ColorBROKEN) || (org._segColor[j] == Utils.ColorLIGHT_BLUE) || (org._segColor[j] == Utils.ColorICE)
							|| (org._segColor[j] == Utils.ColorDARKJADE) || (org._segColor[j] == Utils.ColorDARKFIRE)) {
						    if (useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
								org._segColor[j] = org._geneticCode.getGene(j%org._geneticCode.getNGenes()).getColor();  
								org.setColor(Color.MAGENTA);
								setColor(Color.MAGENTA);
						    }
						}
				    }
					break;
				default:
				    for (int j = 0; j < org._segments; j++) {
				    	if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
				    		if ((org._segColor[j] == Utils.ColorLIGHTBROWN) || (org._segColor[j] == Utils.ColorGREENBROWN) || (org._segColor[j] == Utils.ColorPOISONEDJADE)
								|| (org._segColor[j] == Utils.ColorBROKEN) || (org._segColor[j] == Utils.ColorLIGHT_BLUE) || (org._segColor[j] == Utils.ColorICE)
								|| (org._segColor[j] == Utils.ColorDARKJADE) || (org._segColor[j] == Utils.ColorDARKFIRE)) {
						        if (useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
								    org._segColor[j] = org._geneticCode.getGene(j%org._geneticCode.getNGenes()).getColor();  
								    setColor(Color.MAGENTA);
						        }
						    }
						} else {
							if ((_geneticCode.getFamilial()) && ((_lengthfriend == org._lengthfriend) || (_thetafriend == org._thetafriend) ||
								(_parentID == org._ID) || (_ID == org._parentID) || (_parentID == org._parentID && _parentID != -1 ))) {
								if ((org._segColor[j] == Utils.ColorLIGHTBROWN) || (org._segColor[j] == Utils.ColorGREENBROWN) || (org._segColor[j] == Utils.ColorPOISONEDJADE)
								    || (org._segColor[j] == Utils.ColorBROKEN) || (org._segColor[j] == Utils.ColorLIGHT_BLUE) || (org._segColor[j] == Utils.ColorICE)
								    || (org._segColor[j] == Utils.ColorDARKJADE) || (org._segColor[j] == Utils.ColorDARKFIRE)) {
								    if (useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
										org._segColor[j] = org._geneticCode.getGene(j%org._geneticCode.getNGenes()).getColor();  
										setColor(Color.MAGENTA);
								    }
								}
							}
						}
				    }
				}
				break;
			}
			if ((_transfersenergy) && (alive)) {
				if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
					if ((_growthRatio==1) && (_energy > (org._energy+1)) && (useEnergy(Utils.ROSE_ENERGY_CONSUMPTION))) {
						// Transfers energy
						takenEnergy = Utils.between(Utils.ORGANIC_OBTAINED_ENERGY, 0, (_energy - (org._energy+1)));
						// The other organism will be shown in cyan
						org.setColor(Color.CYAN);
						// This organism will be shown in rose
						setColor(Utils.ColorROSE);
						org._energy += takenEnergy;
						_energy -= takenEnergy;
					}
				} else {
					if ((_geneticCode.getFamilial()) && ((_lengthfriend == org._lengthfriend) || (_thetafriend == org._thetafriend) ||
						(_parentID == org._ID) || (_ID == org._parentID) || (_parentID == org._parentID && _parentID != -1 ))) {
						if ((_growthRatio==1) && (_energy > (org._energy+1)) && (useEnergy(Utils.ROSE_ENERGY_CONSUMPTION))) {
							// Transfers energy
							takenEnergy = Utils.between(Utils.ORGANIC_OBTAINED_ENERGY, 0, (_energy - (org._energy+1)));
							// The other organism will be shown in cyan
							org.setColor(Color.CYAN);
							// This organism will be shown in rose
							setColor(Utils.ColorROSE);
							org._energy += takenEnergy;
							_energy -= takenEnergy;
						}
					} else {
						if ((_geneticCode.getPeaceful()) && (org._transfersenergy) && (!org._isaplant) && (!org._isaconsumer)) {
							if ((_isaplant) || (_isaconsumer)) {
								if ((_growthRatio==1) && (_energy > (org._energy+1)) && (useEnergy(Utils.ROSE_ENERGY_CONSUMPTION))) {
									// Transfers energy
									takenEnergy = Utils.between(Utils.ORGANIC_OBTAINED_ENERGY, 0, (_energy - (org._energy+1)));
									// The other organism will be shown in cyan
									org.setColor(Color.CYAN);
									// This organism will be shown in rose
									setColor(Utils.ColorROSE);
									org._energy += takenEnergy;
									_energy -= takenEnergy;
								}
							}
						}
					}
				}
			}
		} else {
		for (i=_segments-1; i>=0; i--) {
		switch (getTypeColor(_segColor[i])) {
		// Teal segment: React on other organisms
		case TEAL:
			switch (getTypeColor(_segColor[seg])) {
			case SPIKE:
			case DARKOLIVE:
			case BROKEN:
			case DARKJADE:
			case POISONEDJADE:
			case DARKFIRE:
			case LIGHTBROWN:
			case GREENBROWN:
			case LIGHT_BLUE:
			case DEADBARK:
			case ICE:
				  switch (getTypeColor(org._segColor[oseg])) {
				  default:
					    if (_segsickReaction[seg] == -2) {
					    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
					    if (_segsickReaction[seg] == -1) {
					    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segsickReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segsickReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {	                    
							dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
		                }} else
	    	            break;	                    
				  }
				  break;
			default:
                  switch (getTypeColor(org._segColor[oseg])) {
                  case RED:
                	    if (_segredReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                  	    if (_segredReaction[seg] == -1) {
                  	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segredReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segredReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {	                    
	  						dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	  						dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	  						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
		                }} else
	    	            break;	                    
                  case GREEN:
                  case SPRING:
                  case LIME:
                  case FOREST:
                  case C4:
                  case JADE:
                  case DARKJADE:
                  case GRASS:
            	        if (_seggreenReaction[seg] == -2) {
            	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                        if (_seggreenReaction[seg] == -1) {
                        if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					        dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					        dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					        dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else   
	                    if (_seggreenReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {	                    
					        dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					        dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					        dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_seggreenReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	    	            break;    
                  case BLUE:
                	    if (_segblueReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segblueReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else 
  	                    if (_segblueReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segblueReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	    	            break;
                  case WHITE:
                  case PLAGUE:
                  case BROKEN:
                	  if (org._isaplant) {
                	    if (_segwhiteReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segwhiteReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segwhiteReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segwhiteReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	    	            break;
                	  } else if ((org._isfrozen) || (org._isplague) || (org._isenhanced) || (org._isaconsumer)) {
                  		if (_segplagueReaction[seg] == -2) {
                  		if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    					    dx=0;
    					    dy=0;
    					    dtheta=0;
    					}} else
                        if (_segplagueReaction[seg] == -1) {
                        if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
        					dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
      	                }} else
      	                if (_segplagueReaction[seg] == 1) {
      	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
        					dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
      	                }} else
      	                if (_segplagueReaction[seg] == 2) {
      	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
      	    			    dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      	    				dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      	    				dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
      	  	            }} else
      	    	        break;
                	  } else {
                		if (_segvirusReaction[seg] == -2) {
                		if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  					    	dx=0;
  					    	dy=0;
  					    	dtheta=0;
  					    }} else
                      	if (_segvirusReaction[seg] == -1) {
                      	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
      						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
    	                }} else
    	                if (_segvirusReaction[seg] == 1) {
    	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
      						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
    	                }} else
    	                if (_segvirusReaction[seg] == 2) {
    	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    	    			    dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    	    				dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    	    				dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
    	  	            }} else
    	    	        break;
                	  }
                	  break;
                  case SILVER:
                	  if (org._isaconsumer) {
                	    if (_segsilverReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segsilverReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segsilverReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segsilverReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	    	            break;
                	  } else if (org._isaplant) {
                  		if (_segwhiteReaction[seg] == -2) {
                  		if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    					    dx=0;
    					    dy=0;
    					    dtheta=0;
    					}} else
                        if (_segwhiteReaction[seg] == -1) {
                        if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
        					dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
      	                }} else
      	                if (_segwhiteReaction[seg] == 1) {
      	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
        					dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
        					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
      	                }} else
      	                if (_segwhiteReaction[seg] == 2) {
      	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
      	    			    dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      	    				dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      	    				dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
      	  	            }} else
      	    	        break;
                	  } else {
                		if (_segplagueReaction[seg] == -2) {
                		if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  					    	dx=0;
  					    	dy=0;
  					    	dtheta=0;
  					    }} else
                      	if (_segplagueReaction[seg] == -1) {
                      	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
      						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
    	                }} else
    	                if (_segplagueReaction[seg] == 1) {
    	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
      						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
      						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
    	                }} else
    	                if (_segplagueReaction[seg] == 2) {
    	                if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    	    			    dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    	    				dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    	    				dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
    	  	            }} else
    	    	        break;
                	  }
                	  break;
                  case GRAY:
                	    if (_seggrayReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_seggrayReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else  	                    
  	                    if (_seggrayReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_seggrayReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	     	            break;
                  case MAGENTA:
                  case ROSE:
                	    if (_segmagentaReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segmagentaReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else   	                    
  	                    if (_segmagentaReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segmagentaReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	     	            break;
                  case PINK:
                	    if (_segpinkReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segpinkReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else   	                    
  	                    if (_segpinkReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segpinkReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case ORANGE:
                	    if (_segorangeReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segorangeReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else 
  	                    if (_segorangeReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segorangeReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case BARK:
                  case OLDBARK:
              	        if (_segbarkReaction[seg] == -2) {
              	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                  	    if (_segbarkReaction[seg] == -1) {
                  	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segbarkReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segbarkReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	                    break;
                  case VIOLET:
                	    if (_segvioletReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segvioletReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segvioletReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segvioletReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case MAROON:
                	    if (_segmaroonReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segmaroonReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segmaroonReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segmaroonReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case OLIVE:
                	    if (_segoliveReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
              	        if (_segoliveReaction[seg] == -1) {
              	        if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segoliveReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segoliveReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
							dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
		                }} else
	                    break;
                  case MINT:
                	    if (_segmintReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segmintReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segmintReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segmintReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case CREAM:
                	    if (_segcreamReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segcreamReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segcreamReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segcreamReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case SPIKEPOINT:
                	    if (_segspikepointReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segspikepointReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segspikepointReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segspikepointReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case SPIKE:
                	    if (_segspikeReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segspikeReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segspikeReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segspikeReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case DARKOLIVE:
                  case LIGHT_BLUE:
                	    if (_seglightblueReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_seglightblueReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else 
  	                    if (_seglightblueReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_seglightblueReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case OCHRE:
                	    if (_segochreReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segochreReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else 
  	                    if (_segochreReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segochreReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  case SKY:
                  case DEEPSKY:
                	    if (_segskyReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                	    if (_segskyReaction[seg] == -1) {
                	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segskyReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segskyReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
							dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
		                }} else
	                    break;
                  case ICE:
                  case DEADBARK:
            	        if (_segiceReaction[seg] == -2) {
            	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                	    if (_segiceReaction[seg] == -1) {
                	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segiceReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segiceReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	                    break;
                  case LILAC:
                  case DARKLILAC:
                	    if (_seglilacReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                	    if (_seglilacReaction[seg] == -1) {
                	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_seglilacReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_seglilacReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
							dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
							dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
		                }} else
	                    break;
                  case CORAL:
              	        if (_segcoralReaction[seg] == -2) {
              	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                  	    if (_segcoralReaction[seg] == -1) {
                  	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segcoralReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segcoralReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	                    break;
                  case FIRE:
              	        if (_segfireReaction[seg] == -2) {
              	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                	    if (_segfireReaction[seg] == -1) {
                	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segfireReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segfireReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	  						dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	  						dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	  						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
		                }} else
	                    break;
                  case LIGHTBROWN:
                  case DARKFIRE:
            	        if (_seglightbrownReaction[seg] == -2) {
            	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                	    if (_seglightbrownReaction[seg] == -1) {
                	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_seglightbrownReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_seglightbrownReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	                    break;
                  case GREENBROWN:
                  case POISONEDJADE:
            	        if (_seggreenbrownReaction[seg] == -2) {
            	        if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                	    if (_seggreenbrownReaction[seg] == -1) {
                	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_seggreenbrownReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_seggreenbrownReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	                    break;
                  case BROWN:
                	    if (_segbrownReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                    	if (_segbrownReaction[seg] == -1) {
                    	if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segbrownReaction[seg] == 1) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
    						dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
    						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	                    }} else
  	                    if (_segbrownReaction[seg] == 2) {
  	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
  	  	                }} else
  	                    break;
                  default:
                	    if (_segdefaultReaction[seg] == -2) {
                	    if (useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
					    	dx=0;
					    	dy=0;
					    	dtheta=0;
					    }} else
                  	    if (_segdefaultReaction[seg] == -1) {
                  	    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x1[seg]-x2[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y1[seg]-y2[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else 
	                    if (_segdefaultReaction[seg] == 1) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
  						    dx=Utils.between((x2[seg]-x1[seg])*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dy=Utils.between((y2[seg]-y1[seg])*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
  						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	                    }} else
	                    if (_segdefaultReaction[seg] == 2) {
	                    if (Utils.random.nextInt(2)<1 && useEnergy(Utils.TEAL_ENERGY_CONSUMPTION)) {
	    					dx=Utils.between((org._dCenterX-_dCenterX)*(_m[i]*_m[i]/_mass)+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dy=Utils.between((org._dCenterY-_dCenterY)*(_m[i]*_m[i]/_mass)+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
	    					dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
	  	                }} else
	    	            break;
                  }
                  break;
            }
			break;
		}}
		switch (getTypeColor(_segColor[seg])) {
		case ORANGE:
		// Orange segment: try to get energy from the other organism
			// If the other segment is blue, it acts as a shield
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case JADE:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case INDIGO:
			case BLOND:
			case DARKGRAY:
			case GOLD:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Color.ORANGE);
				} else {
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
				    	// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
				    }
				}
				break;
			case GRASS:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Color.ORANGE);
				} else {
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
				    	// Get energy depending on segment length
						takenEnergy = Utils.between((0.5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in green brown
						org.setColor(Utils.ColorGREENBROWN);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
				    }
				}
				break;
			case BLUE:
				if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					if (org._isenhanced) {
					    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
						setColor(Utils.ColorDARKLILAC);
					} else {
					    setColor(Color.ORANGE);
					}
					org.setColor(Color.BLUE);
				} else {
					// Doesn't have energy to use the shield
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
					}	
				}
				break;
			case LIGHT_BLUE:
				if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					setColor(Color.ORANGE);
					org.setColor(Utils.ColorLIGHT_BLUE);
				} else {
					// Doesn't have energy to use the shield
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
					}	
				}
				break;
			case SKY:
				if (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
					org._segColor[oseg] = Utils.ColorDEEPSKY;
					setColor(Color.ORANGE);
					org.setColor(Utils.ColorDEEPSKY);
				} else {
					// Doesn't have energy to use the shield
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
					}	
				}
				break;
			case DEEPSKY:
					setColor(Color.ORANGE);
					org.setColor(Utils.ColorDEEPSKY);
				break;
			case ORANGE:
				if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
					// Get energy depending on segment length
					takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					// The other organism will be shown in orange
					org.setColor(Color.ORANGE);
					// This organism will be shown in orange
					setColor(Color.ORANGE);
				}
				break;
			case FIRE:
				if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
					// Get energy depending on segment length
					takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					// The other organism will be shown in fire
					org.setColor(Utils.ColorFIRE);
					// This organism will be shown in orange
					setColor(Color.ORANGE);
				}
				break;
			case SILVER:
            	if ((org._nTotalKills > 0) || (org._isenhanced)) {
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in gold
						org.setColor(Utils.ColorGOLD);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
					}
				} else {
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);
					}
				}
				break;
			case MINT:
			case MAGENTA:
			case ROSE:
				if (_geneticCode.getAltruist()) {
                break;
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Color.ORANGE);
					} else {
						if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
					    	// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in orange
							setColor(Color.ORANGE);
					    }
					}
				}
				break;
			case WHITE:
				if ((org._isaplant) || (org._isaconsumer) || (org._isplague) || (org._isenhanced) || (org._isfrozen)) {			    
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);					    
				    }
			    }
				break;
			case ICE:
			case DEADBARK:
				if ((_isafreezer) || (_isenhanced)) {
					if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in orange
						setColor(Color.ORANGE);					    
				    }
			    }
				break;
			case BARK:
		    	org._segColor[oseg] = Utils.ColorOLDBARK;
		    	break;
			case OLDBARK:
				break;
			case RED:
				break;
			case MAROON:
				break;
			case CREAM:
				break;
			case OCHRE:
				break;
			case OLIVE:
				break;
			case DARKOLIVE:
				break;
			case SPIKEPOINT:
				break;
			case BROWN:
				break;	
			default:
				if (useEnergy(Utils.ORANGE_ENERGY_CONSUMPTION)) {
					// Get energy depending on segment length
					takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					// The other organism will be shown in yellow
					org.setColor(Color.YELLOW);
					// This organism will be shown in orange
					setColor(Color.ORANGE);
				}
			}
			// energy interchange
			org._energy -= takenEnergy;
			_energy += takenEnergy;
			double CO2freed = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
			useEnergy(CO2freed);
			org._hasdodged =true;
			break;
		case FIRE:
			// Fire segment: omnivore between red and orange
				// If the other segment is blue, it acts as a shield
				switch (getTypeColor(org._segColor[oseg])) {
				case GREEN:
				case FOREST:
				case SPRING:
				case LIME:
				case C4:
				case JADE:
				case TEAL:
				case CYAN:
				case YELLOW:
				case AUBURN:
				case INDIGO:
				case BLOND:
				case DARKGRAY:
				case GOLD:
					if (org._isaconsumer) {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in fire
							setColor(Utils.ColorFIRE);
						}
					} else {
						if ((org._dodge) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorDARKFIRE);
						} else {
							if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in dark fire
								setColor(Utils.ColorDARKFIRE);
							}
						}
					}
					break;
				case GRASS:
					if (org._isaconsumer) {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in green brown
							org.setColor(Utils.ColorGREENBROWN);
							// This organism will be shown in fire
							setColor(Utils.ColorFIRE);
						}
					} else {
						if ((org._dodge) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorDARKFIRE);
						} else {
							if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((0.1 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in green brown
								org.setColor(Utils.ColorGREENBROWN);
								// This organism will be shown in dark fire
								setColor(Utils.ColorDARKFIRE);
							}
						}
					}
					break;
				case BLUE:
					if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
						if (org._isenhanced) {
						    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
							setColor(Utils.ColorDARKLILAC);
						} else {
							setColor(Utils.ColorDARKFIRE);
						}
						org.setColor(Color.BLUE);
					} else {
						// Doesn't have energy to use the shield
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
						}	
					}
					break;
				case LIGHT_BLUE:
					if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
						setColor(Utils.ColorDARKFIRE);
						org.setColor(Utils.ColorLIGHT_BLUE);
					} else {
						// Doesn't have energy to use the shield
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
						}	
					}
				    break;
				case SKY:
					if (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDEEPSKY;
						setColor(Utils.ColorDARKFIRE);
						org.setColor(Utils.ColorDEEPSKY);
					} else {
						// Doesn't have energy to use the shield
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
						}	
					}
					break;
				case DEEPSKY:
						setColor(Utils.ColorDARKFIRE);
						org.setColor(Utils.ColorDEEPSKY);
					break;
	            case RED:
	            	if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in red
						org.setColor(Color.RED);
						// This organism will be shown in fire
						setColor(Utils.ColorFIRE);
					}
					break;
	            case FIRE:
	            	if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in fire
						org.setColor(Utils.ColorFIRE);
						// This organism will be shown in fire
						setColor(Utils.ColorFIRE);
					}
	            	break;
	            case ORANGE:
	            	if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in orange
						org.setColor(Color.ORANGE);
						// This organism will be shown in fire
						setColor(Utils.ColorFIRE);
					}
					break;
	            case SILVER:
	            	if ((org._nTotalKills > 0) || (org._isenhanced)) {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in gold
							org.setColor(Utils.ColorGOLD);
							// This organism will be shown in fire
							setColor(Utils.ColorFIRE);
						}
					} else
						if (org._isaconsumer) {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in fire
							setColor(Utils.ColorFIRE);
						}
					} else  
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
					}					
					break;
	            case PINK:
	            	if (org._geneticCode.getModifiespink()) {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in fire
							setColor(Utils.ColorFIRE);
						}
					} else {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
						}
					}
	            	break;
				case MAROON:
				case CREAM:
					if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in dark fire
						setColor(Utils.ColorDARKFIRE);
					}
					break;
				case ICE:
				case DEADBARK:
					if ((_isafreezer) && (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in fire
						setColor(Utils.ColorFIRE);
					}
					break;
				case BARK:
			    	org._segColor[oseg] = Utils.ColorOLDBARK;
			    	break;
				case OLDBARK:
					break;
				case OCHRE:
					break;
				case OLIVE:
					break;
				case DARKOLIVE:
					break;
				case SPIKEPOINT:
					break;
				case BROWN:
				case GREENBROWN:
				case POISONEDJADE:
					if (_isenhanced) {
						if (org._isaconsumer) {
							if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in fire
								setColor(Utils.ColorFIRE);
							}
						} else {
							if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in dark fire
								setColor(Utils.ColorDARKFIRE);
							}
						}
				    }
					break;
				case MINT:
				case MAGENTA:
				case ROSE:
					if (_geneticCode.getAltruist()) {
	                break;
					} else {
						if (org._isaconsumer) {
							if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in fire
								setColor(Utils.ColorFIRE);
							}
						} else {
							if ((org._dodge) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
								org.setColor(Utils.ColorTEAL);
								setColor(Utils.ColorDARKFIRE);
							} else {
								if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
									// Get energy depending on segment length
									takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
									// The other organism will be shown in yellow
									org.setColor(Color.YELLOW);
									// This organism will be shown in dark fire
									setColor(Utils.ColorDARKFIRE);
								}
							}
						}
					}
					break;
				default:
					if (org._isaconsumer) {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in fire
							setColor(Utils.ColorFIRE);
						}
					} else {
						if (useEnergy(Utils.FIRE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
						}
					}
			    }
				// energy interchange
				org._energy -= takenEnergy;
				_energy += takenEnergy;
				double CO2freed7 = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
				useEnergy(CO2freed7);
				org._hasdodged =true;
				break;
		case RED:
			// Red segment: try to get energy from other consumers
				switch (getTypeColor(org._segColor[oseg])) {
				case RED:
					if (useEnergy(Utils.RED_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in red
						org.setColor(Color.RED);
						// This organism will be shown in red
						setColor(Color.RED);
					}
					break;
				case FIRE:
					if (useEnergy(Utils.RED_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in fire
						org.setColor(Utils.ColorFIRE);
						// This organism will be shown in red
						setColor(Color.RED);
					}
					break;
				case ORANGE:
				case MAROON:
				case PINK:
					if (useEnergy(Utils.RED_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in red
						setColor(Color.RED);
					}
					break;
				case SILVER:
					if ((org._nTotalKills > 0) || (org._isenhanced)) {
						if (useEnergy(Utils.RED_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in gold
							org.setColor(Utils.ColorGOLD);
							// This organism will be shown in red
							setColor(Color.RED);
						}
					}
					break;
				case SPIKE:
					if ((org._isenhanced) && (useEnergy(Utils.RED_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in red
						setColor(Color.RED);
					}
					break;
				case SPIKEPOINT:
					if ((_isenhanced) && (org._isenhanced) && (useEnergy(Utils.RED_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in red
						setColor(Color.RED);
					}
					break;
				case LILAC:
				case DARKLILAC:
				case GRAY:
					if ((_isenhanced) && (org._isaconsumer) && (useEnergy(Utils.RED_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in dark fire
						setColor(Utils.ColorDARKFIRE);
					}
					break;
				case CREAM:
					if ((_isenhanced) && (useEnergy(Utils.RED_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in dark fire
						setColor(Utils.ColorDARKFIRE);
					}
					break;
				case BARK:
			    	org._segColor[oseg] = Utils.ColorOLDBARK;
			    	break;
				default:
					break;
				}
				// energy interchange
				org._energy -= takenEnergy;
				_energy += takenEnergy;
				double CO2freed2 = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
				useEnergy(CO2freed2);
				break;	
		case PINK:
			// Pink segment: try to get energy from corpses, weak organisms, viruses and parasites
				switch (getTypeColor(org._segColor[oseg])) {
				case PINK:
					if (_geneticCode.getModifiespink()) {
						if (org._geneticCode.getModifiespink()) {
							if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in pink
							    org.setColor(Color.PINK);
								// This organism will be shown in pink
								setColor(Color.PINK);
							}
						} else {
							if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in dark fire
								org.setColor(Utils.ColorDARKFIRE);
								// This organism will be shown in pink
								setColor(Color.PINK);
							}
						}
					} else {
						if (org._geneticCode.getModifiespink()) {
							if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in pink
							    org.setColor(Color.PINK);
								// This organism will be shown in dark fire
								setColor(Utils.ColorDARKFIRE);
							}
						} else {
							if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in dark fire
								org.setColor(Utils.ColorDARKFIRE);
								// This organism will be shown in dark fire
								setColor(Utils.ColorDARKFIRE);
							}
						}
					}
					break;
				case CREAM:
					if (_geneticCode.getModifiespink()) {
						if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in pink
							setColor(Color.PINK);
						}
					} else {
						if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.2 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in dark fire
							setColor(Utils.ColorDARKFIRE);
						}
					}
					break;
				case SILVER:
					if (org._nTotalInfected > 0) {
						if ((org._nTotalKills > 0) || (org._isenhanced)) {
							if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in gold
								org.setColor(Utils.ColorGOLD);
								// This organism will be shown in pink
								setColor(Color.PINK);
							}
						} else {
							if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in pink
								setColor(Color.PINK);
							}
						}
					}
					break;
				case WHITE:
				case PLAGUE:
				case CORAL:
				case DARKOLIVE:
				case DARKFIRE:
				case LIGHTBROWN:
				case GREENBROWN:
				case POISONEDJADE:
				case BROKEN:
				case LIGHT_BLUE:
				case DEEPSKY:
				case ICE:
				case DEADBARK:
					if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in pink
						setColor(Color.PINK);
					}
					break;
				case BROWN:
					if ((_isakiller < 2) || (_geneticCode.getModifiespink())) {
					    if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in pink
						    setColor(Color.PINK);
					    }
					}
					break;
				case OLDBARK:
					if (_isenhanced) {
						if (useEnergy(Utils.PINK_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
							if (org._isenhanced) {
								takenEnergy = Utils.between((0.1 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in green brown
								org.setColor(Utils.ColorGREENBROWN);
							} else {
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
							    org.setColor(Color.YELLOW);
							}
						    // This organism will be shown in pink
						    setColor(Color.PINK);
					    }
					}
					break;
				case BARK:
			    	org._segColor[oseg] = Utils.ColorOLDBARK;
					break;
				default:
					break;	
				}
				// energy interchange
				org._energy -= takenEnergy;
				_energy += takenEnergy;
				double CO2freed3 = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
				useEnergy(CO2freed3);
				break;
		case MAROON:
			// Maroon segment: try to get energy from plants and feed on ochre, sky, darkolive and cracked light blue segments
				switch (getTypeColor(org._segColor[oseg])) {
				case BLUE:
					if (org._isaplant) {
					    if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					    	if (org._isenhanced) {
							    useEnergy(Utils.between((0.1 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
								setColor(Utils.ColorDARKLILAC);
							} else {
								setColor(Utils.ColorMAROON);
							}
						    org.setColor(Color.BLUE);
					    } else {
							// Doesn't have energy to use the shield
							if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in maroon
								setColor(Utils.ColorMAROON);
							}	
						}
					}
					break;
				case WHITE:
				case PLAGUE:
				case SPIKE:
				case BROKEN:
				case LIGHT_BLUE:
				case DEEPSKY:
				case DARKOLIVE:
				case OCHRE:
				case DARKJADE:
					if (org._isaplant) {
						if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) { 
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in maroon
						    setColor(Utils.ColorMAROON);
						}
					}
					break;
				case OLDBARK:
					if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) { 
					    // Get energy depending on segment length
						if (org._isenhanced) {
							takenEnergy = Utils.between((0.1 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in green brown
							org.setColor(Utils.ColorGREENBROWN);
						} else {
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						}
					    // This organism will be shown in maroon
					    setColor(Utils.ColorMAROON);
					}
					break;
				case BARK:
			    	org._segColor[oseg] = Utils.ColorOLDBARK;
			    	break;
				case GREEN:
				case FOREST:
				case SPRING:
				case LIME:
				case C4:
				case JADE:
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorMAROON);
					} else {
						if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in maroon
							setColor(Utils.ColorMAROON);
						}
					}
					break;
				case GRASS:
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorMAROON);
					} else {
						if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((0.5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in green brown
							org.setColor(Utils.ColorGREENBROWN);
							// This organism will be shown in maroon
							setColor(Utils.ColorMAROON);
						}
					}
					break;
				case CYAN:
				case TEAL:
				case YELLOW:
				case AUBURN:
				case INDIGO:
				case BLOND:
				case DARKGRAY:
				case GOLD:
					if ((_isenhanced) && (org._isaplant)) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorMAROON);
						} else {
							if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in maroon
								setColor(Utils.ColorMAROON);
							}
						}					
					}
					break;
				case ICE:
				case DEADBARK:
					if ((_isafreezer) && (org._isaplant) && (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in maroon
						setColor(Utils.ColorMAROON);
					}
					break;
				case RED:
					break;
				case FIRE:
					break;
				case ORANGE:
					break;
				case MAROON:
					break;
				case PINK:
					break;
				case CREAM:
					break;				
				case LILAC:
					break;
				case DARKLILAC:
					break;
				case SPIKEPOINT:
					break;
				case SKY:
					break;
				case OLIVE:
					break;
				case BROWN:
					break;
				case SILVER:
					if ((org._nTotalKills > 0) || (org._isenhanced)) {
		                break;
						} else {
						if ((org._isaplant) && (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION))) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in maroon
							setColor(Utils.ColorMAROON);								    
						}
					}
					break;
				case MINT:
				case MAGENTA:
				case ROSE:
					if (_geneticCode.getAltruist()) {
					break;
					} else {
						if ((_isenhanced) && (org._isaplant)) {
							if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
								org.setColor(Utils.ColorTEAL);
								setColor(Utils.ColorMAROON);
							} else {
								if (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION)) {
									// Get energy depending on segment length
									takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
									// The other organism will be shown in yellow
									org.setColor(Color.YELLOW);
									// This organism will be shown in maroon
									setColor(Utils.ColorMAROON);
								}
							}					
						}
					}
					break;
				default:
					if ((_isenhanced) && (org._isaplant) && (useEnergy(Utils.MAROON_ENERGY_CONSUMPTION))) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in yellow
						org.setColor(Color.YELLOW);
						// This organism will be shown in maroon
						setColor(Utils.ColorMAROON);								    
					}		
				}
				// energy interchange
				org._energy -= takenEnergy;
				_energy += takenEnergy;
				double CO2freed4 = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
				useEnergy(CO2freed4);
				org._hasdodged =true;
				break;
		case CREAM:
			// Cream segment: Parasitize on other organisms
			takenEnergy = handleCream(org, seg, oseg, takenEnergy);
			// energy interchange
				org._energy -= takenEnergy;
				_energy += takenEnergy;
				double CO2freed5 = takenEnergy * Utils.CREAM_ORGANIC_SUBS_PRODUCED;
				useEnergy(CO2freed5);
				org._hasdodged =true;
				break;
		case LILAC:
			// Lilac segment: Weaken organisms
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case JADE:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case INDIGO:
			case BLOND:
			case DARKGRAY:
			case GOLD:
		    	if ((_geneticCode.getModifieslilac()) || (org._isaconsumer)) {
		    		if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorLILAC);
					} else {
						if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
							org.setColor(Utils.ColorDARKLILAC);
							// This organism will be shown in lilac
							setColor(Utils.ColorLILAC);
							// Organism has to recharge
							if (!_isenhanced) {
								_segColor[seg] = Utils.ColorDARKLILAC;
							}
						}
					}
		    	}
		    	break;
			case GRASS:
				if ((_geneticCode.getModifieslilac()) || (org._isaconsumer)) {
		    		if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorLILAC);
					} else {
						if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
							org.setColor(Utils.ColorDARKLILAC);
						    // This organism will be shown in lilac
							setColor(Utils.ColorLILAC);
							// Organism has to recharge
							if (!_isenhanced) {
								_segColor[seg] = Utils.ColorDARKLILAC;
							}
						}
					}
		    	}
		    	break;
		    case BLUE:
				if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					if (org._isenhanced) {
					    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
						setColor(Utils.ColorDARKLILAC);
					} else {
						setColor(Utils.ColorLILAC);
					}
					org.setColor(Color.BLUE);
				} else {
					// Doesn't have energy to use the shield
					if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in dark lilac
						org.setColor(Utils.ColorDARKLILAC);
						// This organism will be shown in lilac
						setColor(Utils.ColorLILAC);
						// Organism has to recharge
						if (!_isenhanced) {
							_segColor[seg] = Utils.ColorDARKLILAC;
						}
					}	
				}
				break;
		    case DARKJADE:
		    case OLDBARK:
		    case WHITE:
		    case BROWN:
		    	if ((_geneticCode.getModifieslilac()) || ((org._isaconsumer) && (org.alive))) {
		    		if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in dark lilac
						org.setColor(Utils.ColorDARKLILAC);
						// This organism will be shown in lilac
						setColor(Utils.ColorLILAC);
						// Organism has to recharge
						if (!_isenhanced) {
							_segColor[seg] = Utils.ColorDARKLILAC;
						}
					}
		    	}
				break;
		    case RED:
		    	if (org._isenhanced) {
				break;
				} else {
					if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in dark lilac
						org.setColor(Utils.ColorDARKLILAC);
						// This organism will be shown in lilac
						setColor(Utils.ColorLILAC);
						// Organism has to recharge
						if (!_isenhanced) {
							_segColor[seg] = Utils.ColorDARKLILAC;
						}
					}	
				}
				break;
		    case OLIVE:
				if ((!org._isaplant) || (org._isenhanced)) {
	            break;
				} else {
					if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in dark lilac
						org.setColor(Utils.ColorDARKLILAC);
						// This organism will be shown in lilac
						setColor(Utils.ColorLILAC);
						// Organism has to recharge
						if (!_isenhanced) {
							_segColor[seg] = Utils.ColorDARKLILAC;
						}
					}	
				}
				break;				
		    case MINT:
		    case MAGENTA:
		    case ROSE:
				if (_geneticCode.getAltruist()) {
                break;
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorLILAC);
					} else {
						if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
							org.setColor(Utils.ColorDARKLILAC);
							// This organism will be shown in lilac
							setColor(Utils.ColorLILAC);
							// Organism has to recharge
							if (!_isenhanced) {
								_segColor[seg] = Utils.ColorDARKLILAC;
							}
						}
					}
				}
				break;
		    case SPIKEPOINT:
		    	break;
		    case BARK:
				org._segColor[oseg] = Utils.ColorOLDBARK;
				break;
		    default:
		    	if (useEnergy(Utils.LILAC_ENERGY_CONSUMPTION)) {
					// Get energy depending on segment length
					takenEnergy = Utils.between((10 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					// The other organism will be shown in dark lilac
					org.setColor(Utils.ColorDARKLILAC);
					// This organism will be shown in lilac
					setColor(Utils.ColorLILAC);
					// Organism has to recharge
					if (!_isenhanced) {
						_segColor[seg] = Utils.ColorDARKLILAC;
					}
				}	
			}
			// energy interchange
			org._energy -= takenEnergy;
			_energy += takenEnergy;
			double CO2freed6 = takenEnergy;
			useEnergy(CO2freed6);
			org._hasdodged =true;
			break;
		case SPIKEPOINT:
			// Spike segment: Hurts organisms, if it hits with its end point, enhanced SPIKE can feed on other organisms
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case JADE:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case INDIGO:
			case BLOND:
			case DARKGRAY:
			case GOLD:
				if (_isenhanced) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorSPIKE);
					} else {
						if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorSPIKE);
					} else {
						if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
						    org.setColor(Utils.ColorDARKLILAC);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				}
				break;
			case GRASS:
				if (_isenhanced) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorSPIKE);
					} else {
						if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((0.375 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in green brown
						    org.setColor(Utils.ColorGREENBROWN);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorSPIKE);
					} else {
						if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((2.5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
						    org.setColor(Utils.ColorDARKLILAC);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				}
				break;
			case BLUE:
				if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					if (org._isenhanced) {
					    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
						setColor(Utils.ColorDARKLILAC);
					} else {
						setColor(Utils.ColorSPIKE);
					}
					org.setColor(Color.BLUE);
				} else {
					// Doesn't have energy to use the shield
					if (_isenhanced) {
						if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					} else {
						if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
						    org.setColor(Utils.ColorDARKLILAC);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				}
				break;
			case SKY:
				if (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
					org._segColor[oseg] = Utils.ColorDEEPSKY;
					setColor(Utils.ColorSPIKE);
					org.setColor(Utils.ColorDEEPSKY);
				} else {
					// Doesn't have energy to use the shield
					if (_isenhanced) {
						if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					} else {
						if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
						    org.setColor(Utils.ColorDARKLILAC);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				}
				break;
			case DEEPSKY:
				    setColor(Utils.ColorSPIKE);
			        org.setColor(Utils.ColorDEEPSKY);
				break;
			case RED:
		    	if (org._isenhanced) {
				break;
				} else {
					if (_isenhanced) {
						if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					} else {
						if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length
							takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in dark lilac
						    org.setColor(Utils.ColorDARKLILAC);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				}
		    	break;
			case MINT:
		    case MAGENTA:
		    case ROSE:
		    	if (_geneticCode.getAltruist()) {
                break;
				} else {
					if (_isenhanced) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorSPIKE);
						} else {
							if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
							    // Get energy depending on segment length
							    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							    // The other organism will be shown in yellow
							    org.setColor(Color.YELLOW);
							    // This organism will be shown in spike
								setColor(Utils.ColorSPIKE);
							}
						}
					} else {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorSPIKE);
						} else {
							if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length
								takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in dark lilac
							    org.setColor(Utils.ColorDARKLILAC);
							    // This organism will be shown in spike
								setColor(Utils.ColorSPIKE);
							}
						}
					}
				}
				break;
		    case WHITE:
		    	if (_isenhanced) {
		    		if ((org._isaplant) || (org._isaconsumer) || (org._isplague) || (org._isenhanced) || (org._isfrozen)) {
		    			if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length
						    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in spike
							setColor(Utils.ColorSPIKE);
						}
					}
				} else {
					if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in dark lilac
					    org.setColor(Utils.ColorDARKLILAC);
					    // This organism will be shown in spike
						setColor(Utils.ColorSPIKE);
					}
				}
		    	break;
		    case BARK:
		    	org._segColor[oseg] = Utils.ColorOLDBARK;
				break;
		    case OLDBARK:
		    	break;
		    case CREAM:
		    	break;
		    case SPIKEPOINT:
		    	break;
		    case OLIVE:
		    	break;
		    case BROWN:
		    	break;
			default:
				if (_isenhanced) {
					if (useEnergy(Utils.DARKGRAY_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length
					    takenEnergy = Utils.between((0.75 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in yellow
					    org.setColor(Color.YELLOW);
					    // This organism will be shown in spike
						setColor(Utils.ColorSPIKE);
					}
				} else {
					if (useEnergy(Utils.SPIKE_ENERGY_CONSUMPTION)) {
						// Get energy depending on segment length
						takenEnergy = Utils.between((5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						// The other organism will be shown in dark lilac
					    org.setColor(Utils.ColorDARKLILAC);
					    // This organism will be shown in spike
						setColor(Utils.ColorSPIKE);
					}
				}
			}
			// energy interchange
			org._energy -= takenEnergy;
			_energy += takenEnergy;
			if (_isenhanced) {
				double CO2freed9 = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
				useEnergy(CO2freed9);
			} else {
			    double CO2freed9 = takenEnergy;
			    useEnergy(CO2freed9);
			}
			org._hasdodged =true;
			break;
		case OCHRE:
			// Ochre segment: Push other organisms away
			switch (getTypeColor(org._segColor[oseg])) {
			case BROWN:
		    	break;
			case OLIVE:
				if ((!org._isaplant) || (org._isenhanced)) {
	            break;
				} else {
					if (Utils.random.nextInt(2)<1 && useEnergy(Utils.OCHRE_ENERGY_CONSUMPTION)) {
						dx=0;
						dy=0;
						dtheta=0;
						org.dx=Utils.between((org._dCenterX-_dCenterX)*_m[seg]*_m[seg]*_m[seg]/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						org.dy=Utils.between((org._dCenterY-_dCenterY)*_m[seg]*_m[seg]*_m[seg]/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						org.dtheta=0;
						if (_isenhanced) {
							org.useEnergy(Utils.between((FastMath.abs(org.dx) + FastMath.abs(org.dy)) * (Utils.ORGANIC_OBTAINED_ENERGY/14), 0, org._energy));
							org.setColor(Utils.ColorDARKLILAC);
						}
						setColor(Utils.ColorOCHRE);
					}
				}
				break;
			case OCHRE:
				if (Utils.random.nextInt(2)<1 && useEnergy(Utils.OCHRE_ENERGY_CONSUMPTION)) {
					dx=0;
					dy=0;
					dtheta=0;
					org.dx=Utils.between((org._dCenterX-_dCenterX)*_m[seg]*_m[seg]*_m[seg]/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					org.dy=Utils.between((org._dCenterY-_dCenterY)*_m[seg]*_m[seg]*_m[seg]/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					org.dtheta=0;
					if (_isenhanced) {
						org.useEnergy(Utils.between((FastMath.abs(org.dx) + FastMath.abs(org.dy)) * (Utils.ORGANIC_OBTAINED_ENERGY/140), 0, org._energy));
						org.setColor(Utils.ColorDARKLILAC);
					}
					setColor(Utils.ColorOCHRE);
				}
				break;
			default:
				if (Utils.random.nextInt(2)<1 && useEnergy(Utils.OCHRE_ENERGY_CONSUMPTION)) {
					dx=0;
					dy=0;
					dtheta=0;
					org.dx=Utils.between((org._dCenterX-_dCenterX)*_m[seg]*_m[seg]*_m[seg]/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					org.dy=Utils.between((org._dCenterY-_dCenterY)*_m[seg]*_m[seg]*_m[seg]/org._mass, -Utils.MAX_VEL, Utils.MAX_VEL);
					org.dtheta=0;
					if (_isenhanced) {
						org.useEnergy(Utils.between((FastMath.abs(org.dx) + FastMath.abs(org.dy)) * (Utils.ORGANIC_OBTAINED_ENERGY/14), 0, org._energy));
						org.setColor(Utils.ColorDARKLILAC);
					}
					setColor(Utils.ColorOCHRE);
				}				
			}
			_hasdodged =true;
			break;
		case WHITE:
			// White segment: try to infect the other organism
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case GRASS:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case BLOND:
			case DARKGRAY:
			case GOLD:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
					        setColor(Color.WHITE);
					        if (!org._isenhanced) {
								org._hasdodged =true;
							}
						} else {
							if (_isaplant) {
							    if (!_dodge) {
							    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.WHITE);
							    	}
							    } else {
							    	_hasdodged =true;
							    	if (!org._isenhanced) {
										org._hasdodged =true;
									}
							    }
						    } else {
						    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
						            org.setColor(Utils.ColorLIGHTBROWN);
						            setColor(Color.WHITE);
						        }
							}
						}
					}
				}
				break;
			case JADE:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
				        setColor(Color.WHITE);
				        if (!org._isenhanced) {
							org._hasdodged =true;
						}
					} else {
						if (_isaplant) {
						    if (!_dodge) {
						    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
						    		org._segColor[oseg] = Utils.ColorDARKJADE;
								    setColor(Color.WHITE);
						    	}
						    } else {
						    	_hasdodged =true;
						    	if (!org._isenhanced) {
									org._hasdodged =true;
								}
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
					    		org._segColor[oseg] = Utils.ColorDARKJADE;
						        setColor(Color.WHITE);
					        }
						}
					}
				}
				break;
			case DARKJADE:
			case POISONEDJADE:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((_nChildren > 1) && (!_isaplant) && (!_isaconsumer)) {
						if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						    org.infectedBy(this);
					        org.setColor(Utils.ColorLIGHTBROWN);
					        setColor(Color.WHITE);
						}
					}
				}
				break;
			case BLUE:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
				        if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
				        	if (org._isenhanced) {
							    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
								setColor(Utils.ColorDARKLILAC);
							} else {
								setColor(Color.WHITE);
							}
					        org.setColor(Color.BLUE);
				        } else {
				        	if (_isaplant) {
				        		if (!_dodge) {
							    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.WHITE);
							    	}
							    } else {
							    	_hasdodged =true;
							    	if (!org._isenhanced) {
										org._hasdodged =true;
									}
							    }
						    } else {
						    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
						            org.setColor(Utils.ColorLIGHTBROWN);
						            setColor(Color.WHITE);
						        }
							}
						}
					}
				}
				break;
			case LIGHT_BLUE:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if (_isaplant) {
							if ((!_isenhanced) && (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION))) {
						        setColor(Color.WHITE);
						        org.setColor(Utils.ColorLIGHT_BLUE);
					        } else {
					        	if (!_dodge) {
							    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.WHITE);
							    	}
							    } else {
							    	_hasdodged =true;
							    	if (!org._isenhanced) {
										org._hasdodged =true;
									}
							    }
					        }
						} else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				break;
			case SKY:
				if (!_isfrozen) {
					if (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDEEPSKY;
						setColor(Color.WHITE);
						org.setColor(Utils.ColorDEEPSKY);
			        } else {
			        	if (org._infectedGeneticCode != _geneticCode) {
			        		if ((org._isaplant) || (org._isaconsumer)) {
							    if (_isaplant) {
							    	if (!_dodge) {
								    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
									        org.infectedBy(this);
									        org.setColor(Utils.ColorBLOND);
									        setColor(Color.WHITE);
								    	}
								    } else {
								    	_hasdodged =true;
								    	if (!org._isenhanced) {
											org._hasdodged =true;
										}
								    }
							    } else {
							    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
							            org.setColor(Utils.ColorLIGHTBROWN);
							            setColor(Color.WHITE);
							        }
								}
							}
						}
					}
				}
				break;
			case DEEPSKY:
				if (!_isfrozen) {
					setColor(Color.WHITE);
					org.setColor(Utils.ColorDEEPSKY);
				}
				break;
			case OLIVE:
			case DARKOLIVE:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
					    if (_isaplant) {
					    	if (_isenhanced) {
					    		if (!_dodge) {
							    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.WHITE);
							    	}
							    } else {
							    	_hasdodged =true;
							    	if (!org._isenhanced) {
										org._hasdodged =true;
									}
							    }
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				break;
			case WHITE:
			case PLAGUE:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if (_isaplant) {
							if (!_dodge) {
						    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
							        org.setColor(Utils.ColorBLOND);
							        setColor(Color.WHITE);
						    	}
						    } else {
						    	_hasdodged =true;
						    	if (!org._isenhanced) {
									org._hasdodged =true;
								}
						    }
						} else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				if ((org._indigo > 0) && (!org._isaplant) && (!org._isaconsumer)) {
					if (_isaplant) {
						if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
							if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
				    	        org.setColor(Utils.ColorINDIGO);
				    	        setColor(Color.WHITE);
						    }
						}
				    } else {
				    	if ((_indigo == 0) || (_isaconsumer)) {
				    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
				    			if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
				    				org.setColor(Utils.ColorINDIGO);
					    	        setColor(Color.WHITE);
				    			}
				    		}
				    	}
				    }
				}
				break;
			case SILVER:
				if ((org._infectedGeneticCode != _geneticCode) && (_nTotalInfected >= org._nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
					    if (_isaplant) {
					    	if (!_dodge) {
						    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
							        org.setColor(Utils.ColorBLOND);
							        setColor(Color.WHITE);
						    	}
						    } else {
						    	_hasdodged =true;
						    	if (!org._isenhanced) {
									org._hasdodged =true;
								}
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				if ((org._indigo > 0) && (!org._isaplant) && (!org._isaconsumer)) {
					if (_isaplant) {
						if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
							if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
				    	        org.setColor(Utils.ColorINDIGO);
				    	        setColor(Color.WHITE);
						    }
						}
				    } else {
				    	if ((_indigo == 0) || (_isaconsumer)) {
				    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
				    			if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
				    				org.setColor(Utils.ColorINDIGO);
					    	        setColor(Color.WHITE);
				    			}
				    		}
				    	}
				    }
				}
				break;
			case INDIGO:
				if (_isaplant) {
					if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
						if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
			    	        org.setColor(Utils.ColorINDIGO);
			    	        setColor(Color.WHITE);
					    }
					}
			    } else {
			    	if ((_indigo == 0) || (_isaconsumer)) {
			    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
			    			if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
			    				org.setColor(Utils.ColorINDIGO);
				    	        setColor(Color.WHITE);
			    			}
			    		}
			    	}
			    }
				break;
			case CORAL:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
					    if (_isaplant) {
					    	if (!_dodge) {
					    		if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
							        org.setColor(Utils.ColorBLOND);
							        setColor(Color.WHITE);
						    	}
						    } else {
						    	_hasdodged =true;
						    	if (!org._isenhanced) {
									org._hasdodged =true;
								}
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        if ((!org._isenhanced) && (!_isaconsumer) && (org.useEnergy(Utils.CORAL_ENERGY_CONSUMPTION))) {
						        	org.setColor(Utils.ColorCORAL);
								    org._energy += _energy;
									_energy -= _energy;
						        } else {
						        	org.setColor(Utils.ColorLIGHTBROWN);
						        }
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				break;
			case MAGENTA:
			case ROSE:
				if ((org._infectedGeneticCode != _geneticCode) && (!_geneticCode.getAltruist())) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
					        setColor(Color.WHITE);
					        if (!org._isenhanced) {
								org._hasdodged =true;
							}
						} else {
							if (_isaplant) {
								if (!_dodge) {
							    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.WHITE);
							    	}
							    } else {
							    	_hasdodged =true;
							    	if (!org._isenhanced) {
										org._hasdodged =true;
									}
							    }
						    } else {
						    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
						            org.setColor(Utils.ColorLIGHTBROWN);
						            setColor(Color.WHITE);
						        }
							}
						}
					}
				}
				break;
			case PINK:
				if (org._infectedGeneticCode != _geneticCode) {
					if (org._geneticCode.getModifiespink()) {
					    if (_isaplant) {
					    	if (!_dodge) {
						    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
							        org.setColor(Utils.ColorBLOND);
							        setColor(Color.WHITE);
						    	}
						    } else {
						    	_hasdodged =true;
						    	if (!org._isenhanced) {
									org._hasdodged =true;
								}
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					} else {
						if ((!org._isantiviral) && (_isenhanced) && (!_isaplant) && (!_isaconsumer)) {
							if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				break;				
			case CREAM:
				if (org._infectedGeneticCode != _geneticCode) {
					if (org._isaplant) {
					    if (_isaplant) {
					    	if (_isenhanced) {
					    		if (!_dodge) {
							    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.WHITE);
							    	}
							    } else {
							    	_hasdodged =true;
							    	if (!org._isenhanced) {
										org._hasdodged =true;
									}
							    }
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					} else {
						if ((!org._isantiviral) && (_isenhanced) && (!_isaplant) && (!_isaconsumer)) {
							if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				break;
			case SPIKEPOINT:
				if (org._infectedGeneticCode != _geneticCode) {
					if (org._isenhanced) {
						if ((!org._isantiviral) && (_isenhanced) && (!_isaplant) && (!_isaconsumer)) {
							if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
				break;
			case MINT:
				break;
			case BARK:
				org._segColor[oseg] = Utils.ColorOLDBARK;
				break;
			case OLDBARK:
				break;
			case OCHRE:
				break;
			case BROWN:
				break;
			default:
				if (org._infectedGeneticCode != _geneticCode) {
					if ((org._isaplant) || (org._isaconsumer)) {
					    if (_isaplant) {
					    	if (!_dodge) {
						    	if (useEnergy(Utils.WHITE_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
							        org.setColor(Utils.ColorBLOND);
							        setColor(Color.WHITE);
						    	}
						    } else {
						    	_hasdodged =true;
						    	if (!org._isenhanced) {
									org._hasdodged =true;
								}
						    }
					    } else {
					    	if (useEnergy(Utils.VIRUS_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
					            org.setColor(Utils.ColorLIGHTBROWN);
					            setColor(Color.WHITE);
					        }
						}
					}
				}
			}
			break;
		case PLAGUE:
			// Force reproduction of infected victims
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case GRASS:
				if ((org._infectedGeneticCode == _geneticCode) && (!org._isantiviral)) {
					if (!_geneticCode.getPlague()) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorPLAGUE);
							org._hasdodged =true;
						} else {
							if (_isaplant) {
								if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
							        org.reproduceVirus();
							        org.setColor(Utils.ColorDARKLILAC);
							        setColor(Utils.ColorPLAGUE);
								}
							} else {
								if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
						            org.reproduceVirus();
						            org.setColor(Utils.ColorDARKLILAC);
						            setColor(Utils.ColorPLAGUE);
					            }
							}
						}
					}
				}
				break;
			case JADE:
				if ((org._infectedGeneticCode == _geneticCode) && (!org._isantiviral)) {
					if (!_geneticCode.getPlague()) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorPLAGUE);
							org._hasdodged =true;
						} else {
							if (_isaplant) {
								if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
									org._segColor[oseg] = Utils.ColorDARKJADE;
								    setColor(Utils.ColorPLAGUE);
								}
							} else {
								if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
									org._segColor[oseg] = Utils.ColorDARKJADE;
								    setColor(Utils.ColorPLAGUE);
					            }
							}
						}
					}
				}
				break;
			case DARKJADE:
			case POISONEDJADE:
				if ((org._infectedGeneticCode == _geneticCode) && (!org._isantiviral)) {
					if (!_geneticCode.getPlague()) {
						if ((_nChildren > 1) && (!_isaplant) && (!_isaconsumer)) {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case BLUE:
				if ((org._infectedGeneticCode == _geneticCode) && (!org._isantiviral)) {
					if ((!_geneticCode.getPlague()) && (org._isaplant)) {
				        if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
				        	if (org._isenhanced) {
							    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
								setColor(Utils.ColorDARKLILAC);
							} else {
								setColor(Utils.ColorPLAGUE);
							}
					        org.setColor(Color.BLUE);
				        } else {
				        	if (_isaplant) {
								if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
							        org.reproduceVirus();
							        org.setColor(Utils.ColorDARKLILAC);
							        setColor(Utils.ColorPLAGUE);
								}
							} else {
								if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
						            org.reproduceVirus();
						            org.setColor(Utils.ColorDARKLILAC);
						            setColor(Utils.ColorPLAGUE);
					            }
							}
				        }
					}
				}
				break;
			case OCHRE:
			case BARK:
			case OLDBARK:
			case LIGHT_BLUE:
				if ((org._infectedGeneticCode == _geneticCode) && (!org._isantiviral)) {
					if ((!_geneticCode.getPlague()) && (org._isaplant)) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case SKY:
			case DEEPSKY:
			case OLIVE:
			case DARKOLIVE:
				if ((org._infectedGeneticCode == _geneticCode) && (_isenhanced) && (!org._isantiviral)) {
					if ((!_geneticCode.getPlague()) && (org._isaplant)) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case MAROON:
			case ORANGE:
			case FIRE:
				if ((org._infectedGeneticCode == _geneticCode) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (_geneticCode.getPlague()) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case RED:
			case CREAM:
				if ((org._infectedGeneticCode == _geneticCode) && (_isenhanced) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (_geneticCode.getPlague()) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case PINK:
				if ((org._infectedGeneticCode == _geneticCode) && ((org._geneticCode.getModifiespink()) || (_isenhanced)) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (_geneticCode.getPlague()) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case LIGHTBROWN:
			case GREENBROWN:
			case DARKFIRE:
			case BROKEN:
			case ICE:
			case DEADBARK:
				if ((org._infectedGeneticCode == _geneticCode) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (((_geneticCode.getPlague()) && (org._isaconsumer)) || ((!_geneticCode.getPlague()) && (org._isaplant))) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				break;
			case WHITE:
			case PLAGUE:
				if ((org._infectedGeneticCode == _geneticCode) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (((_geneticCode.getPlague()) && (org._isaconsumer)) || ((!_geneticCode.getPlague()) && (org._isaplant))) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				if ((org._indigo > 0) && (!org._isaplant) && (!org._isaconsumer)) {
					if (_isaplant) {
						if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
				    	        org.setColor(Utils.ColorINDIGO);
				    	        setColor(Utils.ColorPLAGUE);
						    }
						}
				    } else {
				    	if ((_indigo == 0) || (_isaconsumer)) {
				    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
				    			if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
				    				org.setColor(Utils.ColorINDIGO);
				    				setColor(Utils.ColorPLAGUE);
				    			}
				    		}
				    	}
				    }
				}
				break;
			case SILVER:
				if ((org._infectedGeneticCode == _geneticCode) && (_nTotalInfected >= org._nTotalInfected) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (((_geneticCode.getPlague()) && (org._isaconsumer)) || ((!_geneticCode.getPlague()) && (org._isaplant))) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
				if ((org._indigo > 0) && (!org._isaplant) && (!org._isaconsumer)) {
					if (_isaplant) {
						if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
				    	        org.setColor(Utils.ColorINDIGO);
				    	        setColor(Utils.ColorPLAGUE);
						    }
						}
				    } else {
				    	if ((_indigo == 0) || (_isaconsumer)) {
				    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
				    			if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
				    				org.setColor(Utils.ColorINDIGO);
				    				setColor(Utils.ColorPLAGUE);
				    			}
				    		}
				    	}
				    }
				}
				break;
			case INDIGO:
				if (_isaplant) {
					if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
						if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
			    	        org.setColor(Utils.ColorINDIGO);
			    	        setColor(Utils.ColorPLAGUE);
					    }
					}
			    } else {
			    	if ((_indigo == 0) || (_isaconsumer)) {
			    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
			    			if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
			    				org.setColor(Utils.ColorINDIGO);
			    				setColor(Utils.ColorPLAGUE);
			    			}
			    		}
			    	}
			    }
				break;
			case CYAN:
			case TEAL:
			case YELLOW:
			case AUBURN:
			case BLOND:
			case DARKGRAY:
			case GOLD:
				if ((org._infectedGeneticCode == _geneticCode) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (((_geneticCode.getPlague()) && (org._isaconsumer)) || ((!_geneticCode.getPlague()) && (org._isaplant))) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorPLAGUE);
							org._hasdodged =true;
						} else {
							if (_isaplant) {
								if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
							        org.reproduceVirus();
							        org.setColor(Utils.ColorDARKLILAC);
							        setColor(Utils.ColorPLAGUE);
								}
							} else {
								if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
						            org.reproduceVirus();
						            org.setColor(Utils.ColorDARKLILAC);
						            setColor(Utils.ColorPLAGUE);
					            }
							}
						}
					}
				}
				break;
			case MAGENTA:
			case ROSE:
				if ((org._infectedGeneticCode == _geneticCode) && (!_geneticCode.getAltruist()) && ((!org._isantiviral) || (org._indigo == 0))) {
					if (((_geneticCode.getPlague()) && (org._isaconsumer)) || ((!_geneticCode.getPlague()) && (org._isaplant))) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Utils.ColorPLAGUE);
							org._hasdodged =true;
						} else {
							if (_isaplant) {
								if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
							        org.reproduceVirus();
							        org.setColor(Utils.ColorDARKLILAC);
							        setColor(Utils.ColorPLAGUE);
								}
							} else {
								if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
						            org.reproduceVirus();
						            org.setColor(Utils.ColorDARKLILAC);
						            setColor(Utils.ColorPLAGUE);
					            }
							}
						}
					}
				}
				break;				
			case MINT:
				break;
			case SPIKEPOINT:
				break;
			case BROWN:
				break;
			default:
				if ((org._infectedGeneticCode == _geneticCode) && (!org._isantiviral)) {
					if (((_geneticCode.getPlague()) && (org._isaconsumer)) || ((!_geneticCode.getPlague()) && (org._isaplant))) {
						if (_isaplant) {
							if (useEnergy(Utils.PLAGUE_ENERGY_CONSUMPTION)) {
						        org.reproduceVirus();
						        org.setColor(Utils.ColorDARKLILAC);
						        setColor(Utils.ColorPLAGUE);
							}
						} else {
							if (useEnergy(Utils.SCOURGE_ENERGY_CONSUMPTION)) {
					            org.reproduceVirus();
					            org.setColor(Utils.ColorDARKLILAC);
					            setColor(Utils.ColorPLAGUE);
				            }
						}
					}
				}
			}
			break;
		case SILVER:
			// Silver segment: infects all other organism, if it has more infections , duels with other absorbing segments
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case BLOND:
			case DARKGRAY:
			case GOLD:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
							setColor(Color.LIGHT_GRAY);
							org._hasdodged =true;
						} else {
							if ((!_dodge) || (!_isaplant)) {
								if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
							        org.infectedBy(this);
							        org.setColor(Utils.ColorBLOND);
							        setColor(Color.LIGHT_GRAY);
								}
							} else {
						    	_hasdodged =true;						    	
								org._hasdodged =true;
						    } 
						}
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
					    setColor(Utils.ColorGOLD);
					} else {
					    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length and relation between kills of both organisms
							takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in gold
							setColor(Utils.ColorGOLD);
						}
					}
				}
				break;
			case JADE:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Color.LIGHT_GRAY);
						org._hasdodged =true;
					} else {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
					    		org._segColor[oseg] = Utils.ColorDARKJADE;
							    setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    } 
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
					    setColor(Utils.ColorGOLD);
					} else {
					    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length and relation between kills of both organisms
							takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in gold
							setColor(Utils.ColorGOLD);
						}
					}
				}
				break;
			case DARKJADE:
			case POISONEDJADE:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((_nChildren > 1) && (!_isaplant) && (!_isaconsumer)) {
						if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						    org.infectedBy(this);
						    org.setColor(Utils.ColorBLOND);
						    setColor(Color.LIGHT_GRAY);
					    }
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
				    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in yellow
					    org.setColor(Color.YELLOW);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
				break;
			case GRASS:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Color.LIGHT_GRAY);
						org._hasdodged =true;
					} else {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    } 
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
					    setColor(Utils.ColorGOLD);
					} else {
					    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length and relation between kills of both organisms
							takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(0.5 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in green brown
							org.setColor(Utils.ColorGREENBROWN);
							// This organism will be shown in gold
							setColor(Utils.ColorGOLD);
						}
					}
				}
				break;
			case RED:
			    if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
			    	if ((!_dodge) || (!_isaplant)) {
						if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
					        org.infectedBy(this);
					        org.setColor(Utils.ColorBLOND);
					        setColor(Color.LIGHT_GRAY);
						}
					} else {
				    	_hasdodged =true;						    	
						org._hasdodged =true;
				    }
				}
			    if ((_nTotalKills > 0) || (_isenhanced)) {
				    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in red
					    org.setColor(Color.RED);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
				break;
			case FIRE:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((!_dodge) || (!_isaplant)) {
						if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
					        org.infectedBy(this);
					        org.setColor(Utils.ColorBLOND);
					        setColor(Color.LIGHT_GRAY);
						}
					} else {
				    	_hasdodged =true;						    	
						org._hasdodged =true;
				    }
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
				    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in fire
					    org.setColor(Utils.ColorFIRE);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
				break;
			case ORANGE:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((!_dodge) || (!_isaplant)) {
						if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
					        org.infectedBy(this);
					        org.setColor(Utils.ColorBLOND);
					        setColor(Color.LIGHT_GRAY);
						}
					} else {
				    	_hasdodged =true;						    	
						org._hasdodged =true;
				    }
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
				    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in orange
					    org.setColor(Color.ORANGE);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
				break;
			case PINK:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if (org._geneticCode.getModifiespink()) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
			    	}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if (_nTotalInfected > 0) {
						if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length and relation between kills of both organisms
						    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in pink
						    org.setColor(Color.PINK);
						    // This organism will be shown in gold
						    setColor(Utils.ColorGOLD);
						}
					} else {
						if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length and relation between kills of both organisms
						    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in gold
						    setColor(Utils.ColorGOLD);
						}
				    }
				}
				break;
			case SILVER:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
			    	}
				}
				if ((org._indigo > 0) && (_nTotalInfected > 0) && (!org._isaplant) && (!org._isaconsumer)) {
					if ((_indigo == 0) || (_isaplant) || (_isaconsumer)) {
			    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
			    			if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
			    				org.setColor(Utils.ColorINDIGO);
			    				setColor(Color.LIGHT_GRAY);
			    			}
			    		}
			    	}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if ((org._nTotalKills > 0) || (org._isenhanced)) {
						if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length and relation between kills of both organisms
						    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in gold
						    org.setColor(Utils.ColorGOLD);
						    // This organism will be shown in gold
						    setColor(Utils.ColorGOLD);
						}
					} else {
						if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
						    // Get energy depending on segment length and relation between kills of both organisms
						    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
						    // The other organism will be shown in yellow
						    org.setColor(Color.YELLOW);
						    // This organism will be shown in gold
						    setColor(Utils.ColorGOLD);
						}
				    }
				}
				break;
			case WHITE:
			case PLAGUE:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
			    	}
				}
				if ((org._indigo > 0) && (_nTotalInfected > 0) && (!org._isaplant) && (!org._isaconsumer)) {
					if ((_indigo == 0) || (_isaplant) || (_isaconsumer)) {
			    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
			    			if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
			    				org.setColor(Utils.ColorINDIGO);
			    				setColor(Color.LIGHT_GRAY);
			    			}
			    		}
			    	}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if ((org._isaplant) || (org._isaconsumer) || (org._isplague) || (org._isenhanced) || (org._isfrozen)) {
						if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					        // Get energy depending on segment length and relation between kills of both organisms
					        takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					        // The other organism will be shown in yellow
					        org.setColor(Color.YELLOW);
					        // This organism will be shown in gold
					        setColor(Utils.ColorGOLD);
					    }
					}
				}
				break;
			case INDIGO:
				if (_nTotalInfected > 0) {
					if ((_indigo == 0) || (_isaplant) || (_isaconsumer)) {
			    		if (org.useEnergy(Utils.INDIGO_ENERGY_CONSUMPTION)) {
			    			if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
			    				org.setColor(Utils.ColorINDIGO);
			    				setColor(Color.LIGHT_GRAY);
			    			}
			    		}
			    	}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
					    setColor(Utils.ColorGOLD);
				    } else {
					    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
							// Get energy depending on segment length and relation between kills of both organisms
							takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
							// The other organism will be shown in yellow
							org.setColor(Color.YELLOW);
							// This organism will be shown in gold
							setColor(Utils.ColorGOLD);
						}
					}
				}
				break;
			case CORAL:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        if ((!org._isenhanced) && (!_isaplant) && (!_isaconsumer) && (org.useEnergy(Utils.CORAL_ENERGY_CONSUMPTION))) {
						        	org.setColor(Utils.ColorCORAL);
								    org._energy += _energy;
									_energy -= _energy;
						        } else {
						        	org.setColor(Utils.ColorBLOND);
						        }
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
				    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in yellow
					    org.setColor(Color.YELLOW);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
		        break;
			case ICE:
			case DEADBARK:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
				    if ((_isafreezer) && (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION))) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in yellow
					    org.setColor(Color.YELLOW);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
				break;
			case MINT:
			case MAGENTA:
			case ROSE:
				if (_geneticCode.getAltruist()) {
                break;
				} else {
					if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
						if ((org._isaplant) || (org._isaconsumer)) {
							if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
								org.setColor(Utils.ColorTEAL);
								setColor(Color.LIGHT_GRAY);
								org._hasdodged =true;
							} else {
								if ((!_dodge) || (!_isaplant)) {
									if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
								        org.infectedBy(this);
								        org.setColor(Utils.ColorBLOND);
								        setColor(Color.LIGHT_GRAY);
									}
								} else {
							    	_hasdodged =true;						    	
									org._hasdodged =true;
							    }
							}
						}
					}
					if ((_nTotalKills > 0) || (_isenhanced)) {
						if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
							org.setColor(Utils.ColorTEAL);
						    setColor(Utils.ColorGOLD);
						} else {
						    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
								// Get energy depending on segment length and relation between kills of both organisms
								takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
								// The other organism will be shown in yellow
								org.setColor(Color.YELLOW);
								// This organism will be shown in gold
								setColor(Utils.ColorGOLD);
							}
						}
					}
				}
				break;
			case CREAM:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
			    	if (org._isaplant) {
			    		if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
					}
				}
				break;
			case MAROON:
			case BLUE:
			case LIGHT_BLUE:
			case SKY:
			case DEEPSKY:
			case OCHRE:
			case OLIVE:
			case DARKOLIVE:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
					}
				}
				break;
			case BARK:
			case OLDBARK:
				org._segColor[oseg] = Utils.ColorOLDBARK;
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((!_isaplant) && (!_isaconsumer)) {
						if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						    org.infectedBy(this);
						    org.setColor(Utils.ColorBLOND);
						    setColor(Color.LIGHT_GRAY);
					    }
					}
				}
				break;
			case SPIKEPOINT:
				break;
			case BROWN:
				break;
			default:
				if ((org._infectedGeneticCode != _geneticCode) && (org._nTotalInfected < _nTotalInfected)) {
					if ((org._isaplant) || (org._isaconsumer)) {
						if ((!_dodge) || (!_isaplant)) {
							if (useEnergy(Utils.SILVER_ENERGY_CONSUMPTION)) {
						        org.infectedBy(this);
						        org.setColor(Utils.ColorBLOND);
						        setColor(Color.LIGHT_GRAY);
							}
						} else {
					    	_hasdodged =true;						    	
							org._hasdodged =true;
					    }
					}
				}
				if ((_nTotalKills > 0) || (_isenhanced)) {
				    if (useEnergy(Utils.EXPERIENCE_ENERGY_CONSUMPTION)) {
					    // Get energy depending on segment length and relation between kills of both organisms
					    takenEnergy = Utils.between(((_nTotalKills+18)/(org._nTotalKills+18))*(FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
					    // The other organism will be shown in yellow
					    org.setColor(Color.YELLOW);
					    // This organism will be shown in gold
					    setColor(Utils.ColorGOLD);
				    }
				}
			}
			// energy interchange
			org._energy -= takenEnergy;
			_energy += takenEnergy;
			double CO2freed8 = takenEnergy * Utils.ORGANIC_SUBS_PRODUCED;
			useEnergy(CO2freed8);
			if ((_nTotalKills > 0) || (_isenhanced)) {
				org._hasdodged =true;
			}
			break;
		case CORAL:
			// Transform viruses and particles into children
			switch (getTypeColor(org._segColor[oseg])) {
			case WHITE:
			case PLAGUE:
			case SILVER:
				if ((!org._isaplant) && (!org._isaconsumer) && (!org._isauburn)) {
					if (((!_isaplant) && (!_isaconsumer)) || (_isenhanced)) {
						if (useEnergy(Utils.CORAL_ENERGY_CONSUMPTION)) {
							setColor(Utils.ColorCORAL);
							org.infectedBy(this);
							org.transform();
						}
					}
				}
				break;
			case MAGENTA:
			case ROSE:
				if ((!org._isaplant) && (!org._isaconsumer) && (!_geneticCode.getAltruist())) {
					if (((!_isaplant) && (!_isaconsumer)) || (_isenhanced)) {
						if (useEnergy(Utils.CORAL_ENERGY_CONSUMPTION)) {
							setColor(Utils.ColorCORAL);
							org.infectedBy(this);
							org.transform();
						}
					}
				}
				break;
			case BLUE:
			case SKY:
			case DEEPSKY:
			case OCHRE:
			case CORAL:
			case VIOLET:
			case GRAY:
			case LILAC:
			case DARKLILAC:
			case SPIKEPOINT:
			case MINT:
			case BROWN:
				break;
			default:
				if ((!org._isaplant) && (!org._isaconsumer)) {
					if (((!_isaplant) && (!_isaconsumer)) || (_isenhanced)) {
						if (useEnergy(Utils.CORAL_ENERGY_CONSUMPTION)) {
							setColor(Utils.ColorCORAL);
							org.infectedBy(this);
							org.transform();
						}
					}
				}
			}
			break;
		case MINT:
			// Mint segment: Remove an infection, corrupt all white and cream segments.
			switch (getTypeColor(org._segColor[oseg])) {
			case BROWN:
				break;
			case MINT:
				if ((_infectedGeneticCode != null) || (org._infectedGeneticCode != null)) {
				    if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
						_infectedGeneticCode = null;
						org._infectedGeneticCode = null;
						org.setColor(Utils.ColorMINT);
						setColor(Utils.ColorMINT);
					}
				}
				break;
			case CREAM:
				if (org._isaplant) {
					if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
					    org._segColor[oseg] = Utils.ColorBROKEN;
					    setColor(Utils.ColorMINT);
					}
				} else {
					if (org._isregenerative) {
						if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
						    org._segColor[oseg] = Utils.ColorDARKFIRE;
						    setColor(Utils.ColorMINT);
						}
				    } else {
				    	if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
						    org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						    setColor(Utils.ColorMINT);
				    	}
				    }
				}					
				if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
					if ((org._infectedGeneticCode != _geneticCode) && (org._infectedGeneticCode != null)) {
						if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
							org._infectedGeneticCode = null;
							org.setColor(Color.CYAN);
							setColor(Utils.ColorMINT);
						}
					}
				}
				break;				
			case WHITE:
			case PLAGUE:
			case CORAL:
				for (int a = 0; a < org._segments; a++) {
					switch (getTypeColor(org._segColor[a])) {
					case WHITE:
					case PLAGUE:
					case CORAL:
						if (org._isaplant) {
							if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
							    org._segColor[a] = Utils.ColorBROKEN;
							    setColor(Utils.ColorMINT);
							}
						} else {
							if (org._isaconsumer) {
								if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
								    org._segColor[a] = Utils.ColorLIGHTBROWN;
								    setColor(Utils.ColorMINT);
								}
							} else {
								if ((!org._isantiviral) || (_isaplant) || (_isaconsumer) || (_iscoral)) {
									if ((org._isregenerative) && (!org._iscoral)) {
										if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
										    org._segColor[a] = Utils.ColorDARKFIRE;
										    setColor(Utils.ColorMINT);
										}
								    } else {
								    	if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
										    org._segColor[a] = Utils.ColorLIGHTBROWN;
										    setColor(Utils.ColorMINT);
								    	}
								    }
							    }
							}
						}
					}
				}
				if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
					if ((org._infectedGeneticCode != _geneticCode) && (org._infectedGeneticCode != null)) {
						if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
							org._infectedGeneticCode = null;
							org.setColor(Color.CYAN);
							setColor(Utils.ColorMINT);
						}
					}
				}
				break;
			default:
				if ((!_isaplant) || (_isenhanced)) {
					for (int a = 0; a < org._segments; a++) {
						switch (getTypeColor(org._segColor[a])) {
						case WHITE:
						case PLAGUE:
						case CORAL:
							if (org._isaplant) {
								if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
								    org._segColor[a] = Utils.ColorBROKEN;
								    setColor(Utils.ColorMINT);
								}
							} else {
								if (org._isaconsumer) {
									if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
									    org._segColor[a] = Utils.ColorLIGHTBROWN;
									    setColor(Utils.ColorMINT);
									}
								} else {
									if ((!org._isantiviral) || (_isaplant) || (_isaconsumer) || (_iscoral)) {
										if ((org._isregenerative) && (!org._iscoral)) {
											if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
											    org._segColor[a] = Utils.ColorDARKFIRE;
											    setColor(Utils.ColorMINT);
											}
									    } else {
									    	if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION/8)) {
											    org._segColor[a] = Utils.ColorLIGHTBROWN;
											    setColor(Utils.ColorMINT);
									    	}
									    }
								    }
								}
							}
						}
					}
				}
				if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
					if ((org._infectedGeneticCode != _geneticCode) && (org._infectedGeneticCode != null)) {
						if (useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
							org._infectedGeneticCode = null;
							org.setColor(Color.CYAN);
							setColor(Utils.ColorMINT);
						}
					}
				}
			}
			break;
		case MAGENTA:
			// Magenta segment: Heal all sick segments
			switch (getTypeColor(org._segColor[oseg])) {
			case BROWN:
				break;
			case MAGENTA:
			    for (int j = 0; j < org._segments; j++) {
			    	if ((org._segColor[j] == Utils.ColorLIGHTBROWN) || (org._segColor[j] == Utils.ColorGREENBROWN) || (org._segColor[j] == Utils.ColorPOISONEDJADE)
						|| (org._segColor[j] == Utils.ColorBROKEN) || (org._segColor[j] == Utils.ColorLIGHT_BLUE) || (org._segColor[j] == Utils.ColorICE)
						|| (org._segColor[j] == Utils.ColorDARKJADE) || (org._segColor[j] == Utils.ColorDARKFIRE)) {
					    if (useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
							org._segColor[j] = org._geneticCode.getGene(j%org._geneticCode.getNGenes()).getColor();  
							org.setColor(Color.MAGENTA);
							setColor(Color.MAGENTA);
					    }
					}
			    }
				break;
			default:
			    for (int j = 0; j < org._segments; j++) {
			    	if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
						if ((org._segColor[j] == Utils.ColorLIGHTBROWN) || (org._segColor[j] == Utils.ColorGREENBROWN) || (org._segColor[j] == Utils.ColorPOISONEDJADE)
						    || (org._segColor[j] == Utils.ColorBROKEN) || (org._segColor[j] == Utils.ColorLIGHT_BLUE) || (org._segColor[j] == Utils.ColorICE)
						    || (org._segColor[j] == Utils.ColorDARKJADE) || (org._segColor[j] == Utils.ColorDARKFIRE)) {
				            if (useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
						        org._segColor[j] = org._geneticCode.getGene(j%org._geneticCode.getNGenes()).getColor();  
						        setColor(Color.MAGENTA);
				            }
				        }
					}
				}			    
			}
			break;
		case ROSE:
			// Rose segment: Transfers energy
			switch (getTypeColor(org._segColor[oseg])) {
			case BROWN:
				break;
			default:
				if (_geneticCode.getAltruist() && org._geneticCode.getAltruist()) {
					if ((_energy > (org._energy+1)) && (_energy > (_geneticCode._reproduceEnergy/2)) && (org._energy < (org._geneticCode._reproduceEnergy/2))) {
						if ((_growthRatio==1) && (useEnergy(Utils.ROSE_ENERGY_CONSUMPTION))) {
						    // Transfers energy
						    takenEnergy = Utils.between(0.1 * Utils.ORGANIC_OBTAINED_ENERGY, 0, (_energy - (org._energy+1)));
						    // The other organism will be shown in cyan
						    org.setColor(Color.CYAN);
						    // This organism will be shown in rose
						    setColor(Utils.ColorROSE);
						    org._energy += takenEnergy;
						    _energy -= takenEnergy;
						}
					}
				}
			}
			break;
		case SKY:
			// Sky segment: Freeze another organism by disabling its photosynthetic and movement segments, protects itself with a frost shield
			switch (getTypeColor(org._segColor[oseg])) {
			case BROWN:
				break;
			case SPIKEPOINT:
				break;
			case BLUE:
				if ((!_isenhanced) && (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION))) {
					if (org._isenhanced) {
					    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
						setColor(Utils.ColorDARKLILAC);
					} else {
						setColor(Utils.ColorSKY);
					}
					org.setColor(Color.BLUE);
				} else {
					for (int y = 0; y < org._segments; y++) {
					switch (getTypeColor(org._segColor[y])) {
					case SKY:
					case DEEPSKY:
						if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
							org._segColor[y] = Utils.ColorLIGHT_BLUE;
							setColor(Utils.ColorSKY);
						}
						break;
					case CYAN:
					case TEAL:
					case SPRING:
					case LIME:
					case GREEN:
					case FOREST:
					case C4:
					case GRASS:
					case JADE:
					case DARKJADE:
					case POISONEDJADE:
					case GREENBROWN:
						if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
							org._segColor[y] = Utils.ColorICE;
							setColor(Utils.ColorSKY);
						}
						break;
					case OLDBARK:
					case BARK:
						if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
							org._segColor[y] = Utils.ColorDEADBARK;
							setColor(Utils.ColorSKY);
						}
						break;
			    }}}
			    break;
			case SKY:
				if ((useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) && (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION))) {
					_segColor[seg] = Utils.ColorDEEPSKY;
					org._segColor[oseg] = Utils.ColorDEEPSKY;
					setColor(Utils.ColorDEEPSKY);
				    org.setColor(Utils.ColorDEEPSKY);
				} else {
					if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDEEPSKY;
						org.setColor(Utils.ColorDEEPSKY);
					}
				}
			    break;
			case OLIVE:
				if ((!org._isaplant) || (org._isenhanced)) {
	                break;
					} else {
						for (int y = 0; y < org._segments; y++) {
						switch (getTypeColor(org._segColor[y])) {
						case SKY:
						case DEEPSKY:
							if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
								org._segColor[y] = Utils.ColorLIGHT_BLUE;
								setColor(Utils.ColorSKY);
							}
							break;
						case CYAN:
						case TEAL:
						case SPRING:
						case LIME:
						case GREEN:
						case FOREST:
						case C4:
						case GRASS:
						case JADE:
						case DARKJADE:
						case POISONEDJADE:
						case GREENBROWN:
							if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
								org._segColor[y] = Utils.ColorICE;
								setColor(Utils.ColorSKY);
							}
							break;
						case OLDBARK:
						case BARK:
							if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
								org._segColor[y] = Utils.ColorDEADBARK;
								setColor(Utils.ColorSKY);
							}
							break;
				}}}
				break;
			case MINT:
			case MAGENTA:
			case ROSE:
				if (_geneticCode.getAltruist()) {
	                break;
					} else {
						for (int y = 0; y < org._segments; y++) {
						switch (getTypeColor(org._segColor[y])) {
						case SKY:
						case DEEPSKY:
							if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
								org._segColor[y] = Utils.ColorLIGHT_BLUE;
								setColor(Utils.ColorSKY);
							}
							break;
						case CYAN:
						case TEAL:
						case SPRING:
						case LIME:
						case GREEN:
						case FOREST:
						case C4:
						case GRASS:
						case JADE:
						case DARKJADE:
						case POISONEDJADE:
						case GREENBROWN:
							if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
								org._segColor[y] = Utils.ColorICE;
								setColor(Utils.ColorSKY);
							}
							break;
						case OLDBARK:
						case BARK:
							if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
								org._segColor[y] = Utils.ColorDEADBARK;
								setColor(Utils.ColorSKY);
							}
							break;
				}}}
				break;
			case JADE:
				if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
					org._segColor[oseg] = Utils.ColorDARKJADE;
					setColor(Utils.ColorSKY);
				}
				break;
			default:
				for (int y = 0; y < org._segments; y++) {
				switch (getTypeColor(org._segColor[y])) {
				case SKY:
				case DEEPSKY:
					if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
						org._segColor[y] = Utils.ColorLIGHT_BLUE;
						setColor(Utils.ColorSKY);
					}
					break;
				case CYAN:
				case TEAL:
				case SPRING:
				case LIME:
				case GREEN:
				case FOREST:
				case C4:
				case GRASS:
				case JADE:
				case DARKJADE:
				case POISONEDJADE:
				case GREENBROWN:
					if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
						org._segColor[y] = Utils.ColorICE;
						setColor(Utils.ColorSKY);
					}
					break;
				case OLDBARK:
				case BARK:
					if (useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
						org._segColor[y] = Utils.ColorDEADBARK;
						setColor(Utils.ColorSKY);
					}
					break;
		    }}}
		    break;							
		case GRAY:
			// Gray segment: Kill an organism
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case GRASS:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case INDIGO:
			case BLOND:
			case DARKGRAY:
			case GOLD:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Color.GRAY);
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case JADE:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Color.GRAY);
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDARKJADE;
						setColor(Color.GRAY);
					}
				}
				break;
			case BLUE:
				if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					if (org._isenhanced) {
					    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
						setColor(Utils.ColorDARKLILAC);
					} else {
						setColor(Color.GRAY);
					}
					org.setColor(Color.BLUE);
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case SKY:
				if (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
					org._segColor[oseg] = Utils.ColorDEEPSKY;
					org.setColor(Utils.ColorDEEPSKY);
					setColor(Color.GRAY);
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case DEEPSKY:
				if (_isaconsumer) {
					org.setColor(Utils.ColorDEEPSKY);
					setColor(Color.GRAY);
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case MINT:
			case ROSE:
				if (_geneticCode.getAltruist()) {
                break;
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Color.GRAY);
					} else {
						if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
							org.die(this);
							setColor(Color.GRAY);
						}
					}
				}
				break;
			case MAGENTA:
				if (_geneticCode.getAltruist()) {
	            break;
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Color.GRAY);
					} else {
						if ((org._isenhanced) && (org.useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION))) {
						    org.setColor(Color.MAGENTA);
						    setColor(Color.GRAY);
						} else {
							if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
								org.die(this);
								setColor(Color.GRAY);
							}
						}
					}
				}
				break;
			case RED:
				if (org._isenhanced) {
				break;
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case SILVER:
				if (_nTotalKills >= org._nTotalKills) {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case OLIVE:
				if (org.useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
					if (_isaplant) {
					    _segColor[seg] = Utils.ColorBROKEN;
					} else {
						_segColor[seg] = Utils.ColorLIGHTBROWN;
					}
					org.setColor(Utils.ColorDARKOLIVE);
					org._segColor[oseg] = Utils.ColorDARKOLIVE;
				} else {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDARKOLIVE;
						setColor(Color.GRAY);
					}
				}
				break;
			case OLDBARK:
				if ((_isenhanced) && (!_isaconsumer)) {
					if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
						org.die(this);
						setColor(Color.GRAY);
					}
				}
				break;
			case BARK:
				org._segColor[oseg] = Utils.ColorOLDBARK;
				break;
			case PINK:
				break;
			case SPIKEPOINT:
				break;
			case BROWN:
				break;
			default:
				if (useEnergy(Utils.GRAY_ENERGY_CONSUMPTION)) {
					org.die(this);
					setColor(Color.GRAY);
				}
			}
			org._hasdodged =true;
			break;				
		case VIOLET:
			// Violet segment: Poison another segment and make it useless
			switch (getTypeColor(org._segColor[oseg])) {
			case GREEN:
			case FOREST:
			case SPRING:
			case LIME:
			case C4:
			case GRASS:
			case TEAL:
			case CYAN:
			case YELLOW:
			case AUBURN:
			case INDIGO:
			case BLOND:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Utils.ColorVIOLET);
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
						setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case JADE:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Utils.ColorVIOLET);
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
					    org._segColor[oseg] = Utils.ColorPOISONEDJADE;
						setColor(Utils.ColorVIOLET);
						org._remember =true;
					}
				}
				break;
			case DARKJADE:
				if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
				    org._segColor[oseg] = Utils.ColorPOISONEDJADE;
					setColor(Utils.ColorVIOLET);
					org._remember =true;
				}
				break;
			case BLUE:
				if (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION)) {
					if (org._isenhanced) {
					    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
						setColor(Utils.ColorDARKLILAC);
					} else {
						setColor(Utils.ColorVIOLET);
					}
					org.setColor(Color.BLUE);
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
						setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case SKY:
				if (org.useEnergy(Utils.SKY_ENERGY_CONSUMPTION)) {
					org._segColor[oseg] = Utils.ColorDEEPSKY;
					org.setColor(Utils.ColorDEEPSKY);
					setColor(Utils.ColorVIOLET);
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
						setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case DEEPSKY:
					org.setColor(Utils.ColorDEEPSKY);
					setColor(Utils.ColorVIOLET);
				break;
			case ROSE:
				if (_geneticCode.getAltruist()) {
                break;
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorVIOLET);
					} else {
						if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
							if (org._isaplant) {
							    org._segColor[oseg] = Utils.ColorGREENBROWN;
							} else {
								org._segColor[oseg] = Utils.ColorLIGHTBROWN;
							}
							setColor(Utils.ColorVIOLET);
						}
					}
				}
				break;
			case MINT:
			case MAGENTA:
				if (_geneticCode.getAltruist()) {
				break;
				} else {
					if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
						org.setColor(Utils.ColorTEAL);
						setColor(Utils.ColorVIOLET);
					} else {
						if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
							if (org._isaplant) {
							    org._segColor[oseg] = Utils.ColorGREENBROWN;
							} else {
								org._segColor[oseg] = Utils.ColorLIGHTBROWN;
							}
							setColor(Utils.ColorVIOLET);
							org._remember =true;
						}
					}
				}
				break;
			case DARKGRAY:
			case GOLD:
				if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
					org.setColor(Utils.ColorTEAL);
					setColor(Utils.ColorVIOLET);
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
						setColor(Utils.ColorVIOLET);
						org._remember =true;
					}
				}
				break;
			case OLIVE:
				if (org.useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
					if (_isaplant) {
					    _segColor[seg] = Utils.ColorBROKEN;
					} else {
						_segColor[seg] = Utils.ColorLIGHTBROWN;
					}
					org.setColor(Utils.ColorDARKOLIVE);
					org._segColor[oseg] = Utils.ColorDARKOLIVE;
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDARKOLIVE;
						setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case WHITE:
				if ((!_isaconsumer) && ((_isaplant) || (!org._ispoisonous) || (_iscoral) || (org._iscoral))) {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
						setColor(Utils.ColorVIOLET);
					}
				} else {
					if ((org._isaplant) || (org._isaconsumer) || (org._isplague) || (org._isenhanced) || (org._isfrozen)) {
					    if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
					    	if (org._isaplant) {
							    org._segColor[oseg] = Utils.ColorGREENBROWN;
							} else {
								org._segColor[oseg] = Utils.ColorLIGHTBROWN;
							}
						    setColor(Utils.ColorVIOLET);
					    }
					}
				}
				break;
			case SILVER:
				if ((!_isaconsumer) || (_nTotalKills >= org._nTotalKills)) {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
					    setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case FIRE:
				if ((!_isaconsumer) || (org._isenhanced)) {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
						setColor(Utils.ColorVIOLET);
					}
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDARKFIRE;
						setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case CREAM:
				if ((!_isaconsumer) && (org._isaplant)) {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
					    setColor(Utils.ColorVIOLET);
				    }
				} else {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
						org._segColor[oseg] = Utils.ColorDARKFIRE;
						setColor(Utils.ColorVIOLET);
					}
				}
				break;
			case LILAC:
			case DARKLILAC:
			case DARKOLIVE:
				if ((!_isaconsumer) || (_isenhanced)) {
				    if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
				    	if (org._isaplant) {
						    org._segColor[oseg] = Utils.ColorGREENBROWN;
						} else {
							org._segColor[oseg] = Utils.ColorLIGHTBROWN;
						}
					    setColor(Utils.ColorVIOLET);
				    }
				}
				break;
			case BARK:
			case OLDBARK:
				org._segColor[oseg] = Utils.ColorOLDBARK;
				if ((_isenhanced) || ((!_isaplant) && (!_isaconsumer))) {
					if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {			
						org._segColor[oseg] = Utils.ColorDEADBARK;
					    setColor(Utils.ColorVIOLET);
				    }
				}
				break;
			case OCHRE:
				break;
			case SPIKEPOINT:
				break;
			case ICE:
				break;
			case DEADBARK:
				break;
			case DARKFIRE:
				break;
			case LIGHTBROWN:
				break;
			case GREENBROWN:
				break;
			case POISONEDJADE:
				break;
			case BROWN:
				break;
			default:
				if (useEnergy(Utils.VIOLET_ENERGY_CONSUMPTION)) {
					if (org._isaplant) {
					    org._segColor[oseg] = Utils.ColorGREENBROWN;
					} else {
						org._segColor[oseg] = Utils.ColorLIGHTBROWN;
					}
					setColor(Utils.ColorVIOLET);
				}
			}
			org._hasdodged =true;
			break;
		case OLIVE:
			// Olive segment: Crack defense
			switch (getTypeColor(org._segColor[oseg])) {			
			case OLIVE:
				if (org._isaplant) {
					if (!_isaplant) {
						if (useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
							org._segColor[oseg] = Utils.ColorLIGHT_BLUE;
							setColor(Utils.ColorOLIVE);
						}
					} else {
					    if (useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
				            org._segColor[oseg] = Utils.ColorDARKOLIVE;
				            setColor(Utils.ColorOLIVE);
					    }
				    }
				}
				break;
			case DARKOLIVE:
				if (org._isaplant) {
					if (!_isaplant) {
						if (useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
							org._segColor[oseg] = Utils.ColorLIGHT_BLUE;
							setColor(Utils.ColorOLIVE);
						}
					}
				}
				break;
			case BLUE:
			case OCHRE:
			case SKY:
			case DEEPSKY:
				if (useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
					org._segColor[oseg] = Utils.ColorLIGHT_BLUE;
					setColor(Utils.ColorOLIVE);
				}
				break;
			case GRAY:
			case VIOLET:
				if (useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
					if (org._isaplant) {
					    org._segColor[oseg] = Utils.ColorBROKEN;
					} else {
						org._segColor[oseg] = Utils.ColorLIGHTBROWN;
					}
					setColor(Utils.ColorDARKOLIVE);
					_segColor[seg] = Utils.ColorDARKOLIVE;
				}
				break;
			case LILAC:
			case DARKLILAC:
			case SPIKE:
			case SPIKEPOINT:
				if (useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
					if (org._isaplant) {
					    org._segColor[oseg] = Utils.ColorBROKEN;
					} else {
						org._segColor[oseg] = Utils.ColorLIGHTBROWN;
					}
					setColor(Utils.ColorOLIVE);
				}
				break;
			case BARK:
				org._segColor[oseg] = Utils.ColorOLDBARK;
				break;
			default:
				break;
			}
			org._hasdodged =true;
			break;
		}}
		if ((_isaplant) && ((org._isaplant) || ((org._transfersenergy) && (!org._isaconsumer)))) {
		for (i=_segments-1; i>=0; i--) {
			switch (getTypeColor(_segColor[i])) {
			case FOREST:
				// Enhance photosynthesis in a colony
				double photosynthesis = 0;
				switch (getTypeColor(_segColor[seg])) {
				case DARKGRAY:
					switch (getTypeColor(org._segColor[oseg])) {
					case DARKGRAY:
					case BROWN:
						break;
					default:
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 4 * _mphoto[i];
					}
					break;
				case FOREST:
					switch (getTypeColor(org._segColor[oseg])) {
					case FOREST:						
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * _mphoto[i];
						break;
					case GREEN:
					case SPRING:
					case LIME:
					case C4:
					case JADE:						
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.9 * _mphoto[i];
						break;
					case GRASS:
					case BARK:
					case OLDBARK:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.7 * _mphoto[i];
						break;
					case DARKGRAY:
					case BROWN:
						break;
					default:						
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.6 * _mphoto[i];
					}
					break;
				case GREEN:
				case SPRING:
				case LIME:
				case C4:
				case JADE:
					switch (getTypeColor(org._segColor[oseg])) {
					case FOREST:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.9 * _mphoto[i];
						break;
					case GREEN:
					case SPRING:
					case LIME:
					case C4:
					case JADE:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.8 * _mphoto[i];
						break;
					case GRASS:
					case BARK:
					case OLDBARK:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.6 * _mphoto[i];
						break;
					case DARKGRAY:
					case BROWN:
						break;
					default:						
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.5 * _mphoto[i];
					}
					break;
				case GRASS:
				case BARK:
				case OLDBARK:
					switch (getTypeColor(org._segColor[oseg])) {
					case FOREST:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.7 * _mphoto[i];
						break;
					case GREEN:
					case SPRING:
					case LIME:
					case C4:
					case JADE:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.6 * _mphoto[i];
						break;
					case GRASS:
					case BARK:
					case OLDBARK:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.4 * _mphoto[i];
						break;
					case DARKGRAY:
					case BROWN:
						break;
					default:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.3 * _mphoto[i];
					}
					break;
				default:
					switch (getTypeColor(org._segColor[oseg])) {
					case FOREST:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.6 * _mphoto[i];
						break;
					case GREEN:
					case SPRING:
					case LIME:
					case C4:
					case JADE:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.5 * _mphoto[i];
						break;
					case GRASS:
					case BARK:
					case OLDBARK:					
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.3 * _mphoto[i];
						break;
					case DARKGRAY:
					case BROWN:
						break;
					default:				
						photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * 0.3 * _mphoto[i];
					}
					break;		
				}
				_energy += _world.photosynthesis(photosynthesis);
				break;	
			}}}
		// Check if the other organism has died
		if (org.isAlive() && org._energy < Utils.tol) {
			org.die(this);
		}
		if (firstCall)
			org.touchEffects(this, oseg, seg, false);
	}

	private double handleCream(Organism org, int seg, int oseg, double takenEnergy) {
		switch (getTypeColor(org._segColor[oseg])) {
        case GREEN:
        case FOREST:
        case SPRING:
        case LIME:
        case C4:
        case JADE:
        case GRASS:
        case TEAL:
        case CYAN:
        case YELLOW:
        case AUBURN:
        case INDIGO:
        case BLOND:
        case DARKGRAY:
        case GOLD:
            if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
                org.setColor(Utils.ColorTEAL);
                setColor(Utils.ColorCREAM);
            } else {
                if (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION)) {
                    // Get energy depending on segment length
                    takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                    // The other organism will be shown in green brown
                    org.setColor(Utils.ColorGREENBROWN);
                    // This organism will be shown in cream
                    setColor(Utils.ColorCREAM);
                }
            }
            break;
        case BLUE:
            if (((!_isenhanced) || (_geneticCode.getModifiescream())) && (org.useEnergy(Utils.BLUE_ENERGY_CONSUMPTION))) {
                if (org._isenhanced) {
                    useEnergy(Utils.between((0.5 * FastMath.log10(org._m[oseg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, _energy));
                    setColor(Utils.ColorDARKLILAC);
                } else {
                    setColor(Utils.ColorCREAM);
                }
                org.setColor(Color.BLUE);
            } else {
                if (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION)) {
                    // Get energy depending on segment length
                    takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                    // The other organism will be shown in green brown
                    org.setColor(Utils.ColorGREENBROWN);
                    if ((_isenhanced) && (!_geneticCode.getModifiescream())) {
                        // This organism will be shown in dark gray
                        setColor(Color.DARK_GRAY);
                    } else {
                        // This organism will be shown in cream
                        setColor(Utils.ColorCREAM);
                    }
                }
            }
            break;
        case SKY:
        case DEEPSKY:
        case OLDBARK:
            if ((_isenhanced) && (!_geneticCode.getModifiescream()) && (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION))) {
                // Get energy depending on segment length
                takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                // The other organism will be shown in green brown
                org.setColor(Utils.ColorGREENBROWN);
                // This organism will be shown in dark gray
                setColor(Color.DARK_GRAY);
            }
            break;
        case BARK:
            org._segColor[oseg] = Utils.ColorOLDBARK;
            if ((_isenhanced) && (!_geneticCode.getModifiescream()) && (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION))) {
                // Get energy depending on segment length
                takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                // The other organism will be shown in green brown
                org.setColor(Utils.ColorGREENBROWN);
                // This organism will be shown in dark gray
                setColor(Color.DARK_GRAY);
            }
            break;
        case SILVER:
            if ((org._nTotalKills > 0) || (org._isenhanced)) {
                if ((_isenhanced) && (_geneticCode.getModifiescream()) && (_nTotalKills >= org._nTotalKills) && (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION))) {
                    // Get energy depending on segment length
                    takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                    // The other organism will be shown in green brown
                    org.setColor(Utils.ColorGREENBROWN);
                    // This organism will be shown in dark gray
                    setColor(Color.DARK_GRAY);
                }
            } else {
                if (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION)) {
                    // Get energy depending on segment length
                    takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                    // The other organism will be shown in green brown
                    org.setColor(Utils.ColorGREENBROWN);
                    // This organism will be shown in cream
                    setColor(Utils.ColorCREAM);
                }
            }
            break;
        case RED:
        case FIRE:
        case ORANGE:
        case MAROON:
        case PINK:
            if ((_isenhanced) && (_geneticCode.getModifiescream()) && (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION))) {
                // Get energy depending on segment length
                takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                // The other organism will be shown in green brown
                org.setColor(Utils.ColorGREENBROWN);
                // This organism will be shown in dark gray
                setColor(Color.DARK_GRAY);
            }
            break;
        case WHITE:
            if ((org._isaplant) || (org._isaconsumer) || (org._isplague) || (org._isenhanced) || (org._isfrozen)) {
                if (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION)) {
                    // Get energy depending on segment length
                    takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                    // The other organism will be shown in green brown
                    org.setColor(Utils.ColorGREENBROWN);
                    // This organism will be shown in cream
                    setColor(Utils.ColorCREAM);
                }
            }
            break;
        case MAGENTA:
        case ROSE:
            if (_geneticCode.getAltruist()) {
            break;
            } else {
                if ((org._dodge) && (!org._isaconsumer) && (org._isakiller == 0) && (org.useEnergy(Utils.DODGE_ENERGY_CONSUMPTION))) {
                    org.setColor(Utils.ColorTEAL);
                    setColor(Utils.ColorCREAM);
                } else {
                    if (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION)) {
                        // Get energy depending on segment length
                        takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                        // The other organism will be shown in green brown
                        org.setColor(Utils.ColorGREENBROWN);
                        // This organism will be shown in cream
                        setColor(Utils.ColorCREAM);
                    }
                }
            }
            break;
        case CREAM:
            break;
        case MINT:
            break;
        case SPIKEPOINT:
            break;
        case BROWN:
            break;
        default:
            if (useEnergy(Utils.CREAM_ENERGY_CONSUMPTION)) {
                // Get energy depending on segment length
                takenEnergy = Utils.between((0.09 * FastMath.log10(_m[seg])) * Utils.ORGANIC_OBTAINED_ENERGY, 0, org._energy);
                // The other organism will be shown in green brown
                org.setColor(Utils.ColorGREENBROWN);
                // This organism will be shown in cream
                setColor(Utils.ColorCREAM);
            }
        }
		return takenEnergy;
	}

	private void handleMySegment(Organism org, int seg, int oseg) {
		switch (getTypeColor(_segColor[seg])) {
		case DARK:
			// Dark segment: Mimic other segments
			switch (getTypeColor(org._segColor[oseg])) {
			case DARK:
			case BROKEN:
			case POISONEDJADE:
			case DARKFIRE:
			case LIGHTBROWN:
			case GREENBROWN:
			case LIGHT_BLUE:
			case ICE:
			case DEADBARK:
			case BROWN:
				break;
			default:
				if (_geneticCode.getMimicAll()) {
					for (int x = 0; x < _segments; x++) {
						switch (getTypeColor(_segColor[x])) {
						case DARK:
							if (useEnergy(Utils.DARK_ENERGY_CONSUMPTION)) {
								if (org._segColor[oseg] == Utils.ColorOLDBARK) {
									_segColor[x] = Utils.ColorBARK;
								} else {
								    _segColor[x] = org._segColor[oseg];
								}
							    _segredReaction[x] = org._segredReaction[oseg];
							    _seggreenReaction[x] = org._seggreenReaction[oseg];
							    _segblueReaction[x] = org._segblueReaction[oseg];
							    _segplagueReaction[x] = org._segplagueReaction[oseg];
							    _segwhiteReaction[x] = org._segwhiteReaction[oseg];
							    _seggrayReaction[x] = org._seggrayReaction[oseg];
							    _segdefaultReaction[x] = org._segdefaultReaction[oseg];
							    _segmagentaReaction[x] = org._segmagentaReaction[oseg];
							    _segpinkReaction[x] = org._segpinkReaction[oseg];
							    _segcoralReaction[x] = org._segcoralReaction[oseg];
							    _segorangeReaction[x] = org._segorangeReaction[oseg];
							    _segbarkReaction[x] = org._segbarkReaction[oseg];
							    _segvioletReaction[x] = org._segvioletReaction[oseg];
							    _segvirusReaction[x] = org._segvirusReaction[oseg];
							    _segmaroonReaction[x] = org._segmaroonReaction[oseg];
							    _segoliveReaction[x] = org._segoliveReaction[oseg];
							    _segmintReaction[x] = org._segmintReaction[oseg];
							    _segcreamReaction[x] = org._segcreamReaction[oseg];
							    _segspikeReaction[x] = org._segspikeReaction[oseg];
							    _segspikepointReaction[x] = org._segspikepointReaction[oseg];
							    _seglightblueReaction[x] = org._seglightblueReaction[oseg];
							    _segochreReaction[x] = org._segochreReaction[oseg];
							    _segskyReaction[x] = org._segskyReaction[oseg];
							    _seglilacReaction[x] = org._seglilacReaction[oseg];
							    _segsilverReaction[x] = org._segsilverReaction[oseg];
							    _segfireReaction[x] = org._segfireReaction[oseg];
							    _seglightbrownReaction[x] = org._seglightbrownReaction[oseg];
							    _seggreenbrownReaction[x] = org._seggreenbrownReaction[oseg];
							    _segbrownReaction[x] = org._segbrownReaction[oseg];
							    _segiceReaction[x] = org._segiceReaction[oseg];
							    _segsickReaction[x] = org._segsickReaction[oseg];
							    _segfriendReaction[x] = org._segfriendReaction[oseg];
							}
						}
					}
				} else {
				if (useEnergy(Utils.DARK_ENERGY_CONSUMPTION)) {
					if (org._segColor[oseg] == Utils.ColorOLDBARK) {
						_segColor[seg] = Utils.ColorBARK;
					} else {
					    _segColor[seg] = org._segColor[oseg];
					}
				    _segredReaction[seg] = org._segredReaction[oseg];
				    _seggreenReaction[seg] = org._seggreenReaction[oseg];
				    _segblueReaction[seg] = org._segblueReaction[oseg];
				    _segplagueReaction[seg] = org._segplagueReaction[oseg];
				    _segwhiteReaction[seg] = org._segwhiteReaction[oseg];
				    _seggrayReaction[seg] = org._seggrayReaction[oseg];
				    _segdefaultReaction[seg] = org._segdefaultReaction[oseg];
				    _segmagentaReaction[seg] = org._segmagentaReaction[oseg];
				    _segpinkReaction[seg] = org._segpinkReaction[oseg];
				    _segcoralReaction[seg] = org._segcoralReaction[oseg];
				    _segorangeReaction[seg] = org._segorangeReaction[oseg];
				    _segbarkReaction[seg] = org._segbarkReaction[oseg];
				    _segvioletReaction[seg] = org._segvioletReaction[oseg];
				    _segvirusReaction[seg] = org._segvirusReaction[oseg];
				    _segmaroonReaction[seg] = org._segmaroonReaction[oseg];
				    _segoliveReaction[seg] = org._segoliveReaction[oseg];
				    _segmintReaction[seg] = org._segmintReaction[oseg];
				    _segcreamReaction[seg] = org._segcreamReaction[oseg];
				    _segspikeReaction[seg] = org._segspikeReaction[oseg];
				    _segspikepointReaction[seg] = org._segspikepointReaction[oseg];
				    _seglightblueReaction[seg] = org._seglightblueReaction[oseg];
				    _segochreReaction[seg] = org._segochreReaction[oseg];
				    _segskyReaction[seg] = org._segskyReaction[oseg];
				    _seglilacReaction[seg] = org._seglilacReaction[oseg];
				    _segsilverReaction[seg] = org._segsilverReaction[oseg];
				    _segfireReaction[seg] = org._segfireReaction[oseg];
				    _seglightbrownReaction[seg] = org._seglightbrownReaction[oseg];
				    _seggreenbrownReaction[seg] = org._seggreenbrownReaction[oseg];
				    _segbrownReaction[seg] = org._segbrownReaction[oseg];
				    _segiceReaction[seg] = org._segiceReaction[oseg];
				    _segsickReaction[seg] = org._segsickReaction[oseg];
				    _segfriendReaction[seg] = org._segfriendReaction[oseg];
				  }
				}
			}
			break;
		case SPIKE:
			// Spike segment: Hurts organisms, if it hits with its end point
			switch (getTypeColor(org._segColor[oseg])) {
			default:
				ExLine2DDouble line = new ExLine2DDouble();
				ExLine2DDouble bline = new ExLine2DDouble();
				// Check collisions for all segments
				line.setLine(x1[seg] + _centerX, y1[seg] + _centerY, x2[seg] + _centerX, y2[seg] + _centerY);
				bline.setLine(org.x1[oseg] + org._centerX, org.y1[oseg] + org._centerY, org.x2[oseg] + org._centerX, org.y2[oseg] + org._centerY);
				if (intersectsLine(bline) && line.intersectsLine(bline)) {
					// Intersection point
					Point2D.Double intersec= line.getIntersection(bline);
					double dl1, dl2, dbl1, dbl2;
					dl1 = intersec.distanceSq(line.getP1());
					dl2 = intersec.distanceSq(line.getP2());
					dbl1 = intersec.distanceSq(bline.getP1());
					dbl2 = intersec.distanceSq(bline.getP2());
					if (Math.min(dl1, dl2) < Math.min(dbl1, dbl2)) {
					    _segColor[seg] = Utils.ColorSPIKEPOINT;
					}
				}
			}
			break;
		default:
			// Spike segment: Hurts organisms, if it hits with its end point
			switch (getTypeColor(org._segColor[oseg])) {
			case SPIKE:
				ExLine2DDouble line = new ExLine2DDouble();
				ExLine2DDouble bline = new ExLine2DDouble();
				// Check collisions for all segments
				line.setLine(x1[seg] + _centerX, y1[seg] + _centerY, x2[seg] + _centerX, y2[seg] + _centerY);
				bline.setLine(org.x1[oseg] + org._centerX, org.y1[oseg] + org._centerY, org.x2[oseg] + org._centerX, org.y2[oseg] + org._centerY);
				if (intersectsLine(bline) && line.intersectsLine(bline)) {
					// Intersection point
					Point2D.Double intersec= line.getIntersection(bline);
					double dl1, dl2, dbl1, dbl2;
					dl1 = intersec.distanceSq(line.getP1());
					dl2 = intersec.distanceSq(line.getP2());
					dbl1 = intersec.distanceSq(bline.getP1());
					dbl2 = intersec.distanceSq(bline.getP2());
					if (Math.min(dl1, dl2) < Math.min(dbl1, dbl2)) {
						break;
					} else {
					    org._segColor[oseg] = Utils.ColorSPIKEPOINT;
					}
				}
			}
		}
	}

	/*
	 * Perd velocitat pel fregament.
	 */
	private final void rubbingFramesEffects() {
		dx *= Utils.RUBBING;
		if (FastMath.abs(dx) < Utils.tol) dx=0;
		dy *= Utils.RUBBING;
		if (FastMath.abs(dy) < Utils.tol) dy = 0;
		dtheta *= Utils.RUBBING;
		if (FastMath.abs(dtheta) < Utils.tol) dtheta = 0;
	}
	
	/*
	 * Perd el cost de manteniment dels segments
	 * Aplica l'efecte de cadascun dels segments
	 */
	private final void segmentsFrameEffects() {
		if (alive) {
			int i;
			// Energy obtained through photosynthesis
			double photosynthesis = 0;
			_nChildren = 1;
			_indigo =0;
			_lowmaintenance =0;
			int fertility =0;
			int yellowCounter =0;
			int reproduceearly =0;
			double goldenage =0;
			_isfrozen =false;
			boolean trigger =false;
			for (i=_segments-1; i>=0; i--) {
				// Manteniment
				switch (getTypeColor(_segColor[i])) {
				// 	Movement
				case CYAN:
					if (Utils.random.nextInt(100)<8 && useEnergy(Utils.CYAN_ENERGY_CONSUMPTION)) {
						dx=Utils.between(dx+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						dy=Utils.between(dy+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
					}
					break;
				case TEAL:
					if (_geneticCode.getPassive()) {
						if (_hasdodged == false) {
							_dodge =true;
						}
						_lowmaintenance += 0.9 * _m[i];
					} else 
					    if (Utils.random.nextInt(100)<8 && useEnergy(Utils.CYAN_ENERGY_CONSUMPTION)) {
						    dx=Utils.between(dx+12d*(x2[i]-x1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dy=Utils.between(dy+12d*(y2[i]-y1[i])/_mass, -Utils.MAX_VEL, Utils.MAX_VEL);
						    dtheta=Utils.between(dtheta+Utils.randomSign()*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
					}
					break;
				// Photosynthesis
				case SPRING:
					       if (_geneticCode.getClockwise()) {
						       dtheta=Utils.between(dtheta+0.1*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
						       photosynthesis += Utils.SPRING_ENERGY_CONSUMPTION * _mphoto[i];
					} else {
					           dtheta=Utils.between(dtheta-0.1*_m[i]*FastMath.PI/_I, -Utils.MAX_ROT, Utils.MAX_ROT);
					           photosynthesis += Utils.SPRING_ENERGY_CONSUMPTION * _mphoto[i];
					}
					break;
				case LIME:
					if (trigger == false) {
						trigger =true;
					    if (_world.fastCheckHit(this) != null) {
					    	_crowded =true;
					    } else {
					    	_crowded =false;
					    }
					}
					if (_crowded == true) {
					    photosynthesis += Utils.CROWDEDLIME_ENERGY_CONSUMPTION * _mphoto[i];
					} else {
						photosynthesis += Utils.LIME_ENERGY_CONSUMPTION * _mphoto[i];
					}
					break;
				case JADE:
					_isjade =true;
					photosynthesis += Utils.JADE_ENERGY_CONSUMPTION * _mphoto[i];
					break;
				case GREEN:
					photosynthesis += Utils.GREEN_ENERGY_CONSUMPTION * _mphoto[i];
					break;
				case FOREST:
					photosynthesis += Utils.FOREST_ENERGY_CONSUMPTION * _mphoto[i];
					break;
				case BARK:
					photosynthesis += Utils.BARK_ENERGY_CONSUMPTION * _mphoto[i];
					break;
				case GRASS:
					photosynthesis += Utils.GRASS_ENERGY_CONSUMPTION * _mphoto[i];
					break;
				case C4:
					_lowmaintenance += _m[i];
					photosynthesis += Utils.C4_ENERGY_CONSUMPTION * _mphoto[i];
					break;
				// is a consumer
				case RED:
					_isaconsumer =true;
					break;
				case FIRE:
					_isaconsumer =true;
					break;
				case ORANGE:
					_isaconsumer =true;
					break;
				case MAROON:
					_isaconsumer =true;
					_lowmaintenance += 0.9 * _m[i];
					break;
				case PINK:
					_isaconsumer =true;
					if (_isakiller < 2) {
						_lowmaintenance += 0.8 * _m[i];
					}
					break;
				case CREAM:
					_isaconsumer =true;
					break;
				// Organisms with yellow segments have more children
				case YELLOW:
					yellowCounter++;
					fertility += _m[i];
				    break;
				// Experienced parents have more children
				case SILVER:
					if (_isaconsumer == false) {
					for (int c = 0; c < _segments; c++) {
						switch (getTypeColor(_segColor[c])) {
						case DARKGRAY:
						case GRAY:
						case LILAC:
						case SPIKE:
						case PLAGUE:
						case CORAL:
							_isaconsumer =true;
					}}}
					       if (_nTotalChildren >=   1 && _nTotalChildren <   5) {
						_nChildren = 2; 
					} else if (_nTotalChildren >=   5 && _nTotalChildren <  14) {
						_nChildren = 3; 
					} else if (_nTotalChildren >=  14 && _nTotalChildren <  30) {
						_nChildren = 4; 
					} else if (_nTotalChildren >=  30 && _nTotalChildren <  55) {
						_nChildren = 5;
					} else if (_nTotalChildren >=  55 && _nTotalChildren <  91) {
					    _nChildren = 6;
					} else if (_nTotalChildren >=  91 && _nTotalChildren < 140) {
						_nChildren = 7; 
					} else if (_nTotalChildren >= 140) {
						_nChildren = 8;
					}
					break;
				// Auburn always has one real child if infected
				case AUBURN:
					_isauburn =true;
					_lowmaintenance += _m[i] - (Utils.AUBURN_ENERGY_CONSUMPTION * _m[i]);
					if (_infectedGeneticCode != null && _nChildren == 1) {
						_nChildren = 2;
					}
					break;
				// Organisms with blond segments reproduce earlier
				case BLOND:
					reproduceearly += 3 + _m[i];
					break;
				// Organisms with indigo segments reduce the energy the new born virus receives
				case INDIGO:
					_indigo += _m[i];
					_lowmaintenance += 0.8 * _m[i];
					break;
				// Plague forces an organism to reproduce the virus
				case PLAGUE:
					_isplague =true;
					break;
				// Coral transforms particles and viruses
				case CORAL:
					_iscoral =true;
					break;
				// Mint immunity against infections	
				case MINT:
					_isantiviral =true;
					if (_infectedGeneticCode != null && Utils.random.nextInt(Utils.IMMUNE_SYSTEM)<=_m[i] && useEnergy(Utils.MINT_ENERGY_CONSUMPTION)) {
						_infectedGeneticCode = null;
						setColor(Utils.ColorMINT);
					}
					break;
				// Healing
				case MAGENTA:
				    _isregenerative =true;
				    for (int j = 0; j < _segments; j++) {
				    if ((_segColor[j] == Utils.ColorLIGHTBROWN) || (_segColor[j] == Utils.ColorGREENBROWN) || (_segColor[j] == Utils.ColorPOISONEDJADE)
						|| (_segColor[j] == Utils.ColorBROKEN) || (_segColor[j] == Utils.ColorLIGHT_BLUE) || (_segColor[j] == Utils.ColorICE)
						|| (_segColor[j] == Utils.ColorDARKJADE) || (_segColor[j] == Utils.ColorDARKFIRE)) {
					if (Utils.random.nextInt(Utils.HEALING)<=_m[i] && useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
					    _segColor[j] = _geneticCode.getGene(j%_geneticCode.getNGenes()).getColor();  
					}}}
					break;
				case DARKFIRE:
					if (Utils.random.nextInt(100)<_geneticCode.getSymmetry() && useEnergy(Utils.MAGENTA_ENERGY_CONSUMPTION)) {
						_segColor[i] = _geneticCode.getGene(i%_geneticCode.getNGenes()).getColor();  
					}
					break;
				case DARKJADE:
					_isjade =true;
					if (Utils.random.nextInt(Utils.DARKJADE_DELAY * _geneticCode.getSymmetry() * _geneticCode.getSymmetry())<8) {
						_segColor[i] = Utils.ColorJADE;  
					}
					break;
				// Normalize spike
				case SPIKEPOINT:
					_segColor[i] = Utils.ColorSPIKE;
					break;
				// is a killer
				case SPIKE:
					if (_isenhanced) {
						_isaconsumer =true;
					}
					if (_isakiller == 0) {
						_isakiller = 1;
					}
					break;
				case LILAC:
					if (_isakiller == 0) {
						_isakiller = 1;
					}
					break;
				case GRAY:
					if (_isakiller < 2) {
					    _isakiller = 2;
					}
					break;
				// is poisonous
				case VIOLET:
					_ispoisonous =true;
					break;
				// is a freezer
				case SKY:
					_isafreezer =true;
					break;
				// is enhanced
				case DARKGRAY:
					_isenhanced =true;
					break;
				// Energy transfer
				case ROSE:
					_lowmaintenance += 0.99 * _m[i];
					if (_transfersenergy == false) {
						_transfersenergy =true;
					    _lengthfriend = _geneticCode.getGene(i%_geneticCode.getNGenes()).getLength();
					    _thetafriend = _geneticCode.getGene(i%_geneticCode.getNGenes()).getTheta();
					}
					break;
				// Low maintenance
				case DARK:
					_lowmaintenance += 0.99 * _m[i];
					break;
				// Organisms with gold segments live longer
				case GOLD:
					_lowmaintenance += 0.9 * _m[i];
					goldenage += (_m[i]/Utils.GOLD_DIVISOR);
					_geneticCode._max_age = Utils.MAX_AGE + ((_geneticCode.getNGenes() * _geneticCode.getSymmetry())/Utils.AGE_DIVISOR) + (int)goldenage;
					break;
				// is weakened
				case LIGHTBROWN:
				case GREENBROWN:
				case POISONEDJADE:
					if (_remember) {
						_isjade =false;
						_isenhanced =false;
						_isantiviral =false;
						_isregenerative =false;
						for (int c = 0; c < _segments; c++) {
							switch (getTypeColor(_segColor[c])) {
							case JADE:
								_isjade =true;
								break;
							case DARKJADE:
								_isjade =true;
								break;
							case DARKGRAY:
								_isenhanced =true;
								break;
							case MINT:
								_isantiviral =true;
								break;
							case MAGENTA:
								_isregenerative =true;
								break;
						    }
						}
					    _geneticCode._max_age = Utils.MAX_AGE + ((_geneticCode.getNGenes() * _geneticCode.getSymmetry())/Utils.AGE_DIVISOR) + (int)goldenage;
					    _remember =false;
					}
					break;
				case LIGHT_BLUE:
					if (_isafreezer) {
						_isafreezer =false;
						for (int c = 0; c < _segments; c++) {
							switch (getTypeColor(_segColor[c])) {
							case SKY:
								_isafreezer =true;
								break;
							case DEEPSKY:
								_isafreezer =true;
								break;
						    }
						}
					}
					break;
				// is frozen
				case ICE:
					_isfrozen =true;
					if (_isjade) {
						_isjade =false;
						for (int c = 0; c < _segments; c++) {
							switch (getTypeColor(_segColor[c])) {
							case JADE:
								_isjade =true;
								break;
							case DARKJADE:
								_isjade =true;
								break;
						    }
						}
					}
					break;
				case DEADBARK:
					_isfrozen =true;
					break;
				// Restore abilities
				case DARKLILAC:
					if (_isakiller == 0) {
						_isakiller = 1;
					}
					if (Utils.random.nextInt(100)<8) {
						_segColor[i] = Utils.ColorLILAC;
					}
					break;
				case DEEPSKY:
					_isafreezer =true;
					if (Utils.random.nextInt(100)<8) {
						_segColor[i] = Utils.ColorSKY;
					}
					break;
				case DARKOLIVE:
					if (Utils.random.nextInt(100)<8 && useEnergy(Utils.OLIVE_ENERGY_CONSUMPTION)) {
						_segColor[i] = Utils.ColorOLIVE;
					}
					break;
				}
			}
			// Reset dodging
			if (_hasdodged == true) {
				_dodge =false;
				_hasdodged =false;
			}
			//Get sun's energy
			if (photosynthesis > 0) {
				_isaplant =true;
			    _energy += _world.photosynthesis(photosynthesis);			
			}
			// Calculate number of children
			if (fertility > 0) {
				if (_geneticCode.getSymmetry() != 3) {
				    _nChildren += (yellowCounter / _geneticCode.getSymmetry()) + (fertility / 23);
			    } else {
			    	_nChildren += (yellowCounter / _geneticCode.getSymmetry()) + (fertility / 34);
			    }
			}
			// Calculate reproduction energy for blond segments
			if ((reproduceearly > 0) && (_infectedGeneticCode == null)) {
				if (_energy > _geneticCode.getReproduceEnergy() - reproduceearly + Utils.YELLOW_ENERGY_CONSUMPTION*(_nChildren-1)) {
					if ((!_isaplant) && (!_isaconsumer)) {
						if ((_energy >= 10) && (_growthRatio<16) && (useEnergy(Utils.BLOND_ENERGY_CONSUMPTION))) {
							_nChildren = 1;
					        _geneticCode._reproduceEnergy = Math.max((40 + 3 * _geneticCode.getNGenes() * _geneticCode.getSymmetry()) - reproduceearly, 10);
					        reproduce();
					        _geneticCode._reproduceEnergy = 40 + 3 * _geneticCode.getNGenes() * _geneticCode.getSymmetry();							
						}
					} else {
						if ((_energy >= 30) && (_growthRatio==1) && (_timeToReproduce==0) && (useEnergy(Utils.BLOND_ENERGY_CONSUMPTION))) {
						    _geneticCode._reproduceEnergy = Math.max((40 + 3 * _geneticCode.getNGenes() * _geneticCode.getSymmetry()) - reproduceearly, 30);
						    reproduce();
						    _geneticCode._reproduceEnergy = 40 + 3 * _geneticCode.getNGenes() * _geneticCode.getSymmetry();							
						} 
					}
			    }
			}
		}
	}
	
	private static final int NOCOLOR=-1;
	private static final int RED=0;
	private static final int FIRE=1;
	private static final int ORANGE=2;
	private static final int MAROON=3;
	private static final int PINK=4;
	private static final int CREAM=5;
	private static final int CORAL=6;
	private static final int GREEN=7;
	private static final int FOREST=8;
	private static final int SPRING=9;
	private static final int LIME=10;
	private static final int C4=11;
	private static final int JADE=12;
	private static final int GRASS=13;
	private static final int BARK=14;
	private static final int BLUE=15;
	private static final int SKY=16;
	private static final int OLIVE=17;
	private static final int OCHRE=18;
	private static final int CYAN=19;
	private static final int TEAL=20;
	private static final int WHITE=21;
	private static final int PLAGUE=22;
	private static final int MINT=23;
	private static final int MAGENTA=24;
	private static final int ROSE=25;
	private static final int VIOLET=26;
	private static final int GRAY=27;
	private static final int LILAC=28;
	private static final int SPIKE=29;
	private static final int SILVER=30;
	private static final int YELLOW=31;
	private static final int AUBURN=32;
	private static final int INDIGO=33;
	private static final int BLOND=34;
	private static final int DARKGRAY=35;
	private static final int DARK=36;
	private static final int GOLD=37;
	private static final int OLDBARK=38;
	private static final int DARKJADE=39;
	private static final int POISONEDJADE=40;
	private static final int DARKFIRE=41;
	private static final int DARKLILAC=42;
	private static final int DEEPSKY=43;
	private static final int DARKOLIVE=44;
	private static final int SPIKEPOINT=45;
	private static final int ICE=46;
	private static final int LIGHT_BLUE=47;
	private static final int LIGHTBROWN=48;
	private static final int GREENBROWN=49;
	private static final int BROKEN=50;
	private static final int DEADBARK=51;
	private static final int BROWN=52;
	private static final int getTypeColor(Color c) {
		if (c.equals(Color.RED) || c.equals(Utils.ColorDARK_RED))
			return RED;
		if (c.equals(Utils.ColorFIRE))
			return FIRE;
		if (c.equals(Color.ORANGE) || c.equals(Utils.ColorDARK_ORANGE))
			return ORANGE;
		if (c.equals(Utils.ColorMAROON))
			return MAROON;
		if (c.equals(Color.PINK) || c.equals(Utils.ColorDARK_PINK))
			return PINK;
		if (c.equals(Utils.ColorCREAM))
			return CREAM;
		if (c.equals(Utils.ColorCORAL))
			return CORAL;
		if (c.equals(Color.GREEN) || c.equals(Utils.ColorDARK_GREEN))
			return GREEN;
		if (c.equals(Utils.ColorFOREST))
			return FOREST;
		if (c.equals(Utils.ColorSPRING))
			return SPRING;
		if (c.equals(Utils.ColorLIME))
			return LIME;
		if (c.equals(Utils.ColorC4))
			return C4;
		if (c.equals(Utils.ColorJADE))
			return JADE;
		if (c.equals(Utils.ColorGRASS))
			return GRASS;
		if (c.equals(Utils.ColorBARK))
			return BARK;
		if (c.equals(Color.BLUE) || c.equals(Utils.ColorDARK_BLUE))
			return BLUE;
		if (c.equals(Utils.ColorSKY))
			return SKY;
		if (c.equals(Utils.ColorOLIVE))
			return OLIVE;
		if (c.equals(Utils.ColorOCHRE))
			return OCHRE;
		if (c.equals(Color.CYAN) || c.equals(Utils.ColorDARK_CYAN))
			return CYAN;
		if (c.equals(Utils.ColorTEAL))
			return TEAL;
		if (c.equals(Color.WHITE) || c.equals(Utils.ColorDARK_WHITE))
			return WHITE;
		if (c.equals(Utils.ColorPLAGUE))
			return PLAGUE;
		if (c.equals(Utils.ColorMINT))
			return MINT;
		if (c.equals(Color.MAGENTA) || c.equals(Utils.ColorDARK_MAGENTA))
			return MAGENTA;
		if (c.equals(Utils.ColorROSE))
			return ROSE;
		if (c.equals(Utils.ColorVIOLET))
			return VIOLET;
		if (c.equals(Color.GRAY) || c.equals(Utils.ColorDARK_GRAY))
			return GRAY;
		if (c.equals(Utils.ColorLILAC))
			return LILAC;
		if (c.equals(Utils.ColorSPIKE))
			return SPIKE;
		if (c.equals(Color.LIGHT_GRAY))
			return SILVER;
		if (c.equals(Color.YELLOW) || c.equals(Utils.ColorDARK_YELLOW))
			return YELLOW;
		if (c.equals(Utils.ColorAUBURN))
			return AUBURN;
		if (c.equals(Utils.ColorINDIGO))
			return INDIGO;
		if (c.equals(Utils.ColorBLOND))
			return BLOND;
		if (c.equals(Color.DARK_GRAY))
			return DARKGRAY;
		if (c.equals(Utils.ColorDARK))
			return DARK;
		if (c.equals(Utils.ColorGOLD))
			return GOLD;
		if (c.equals(Utils.ColorOLDBARK))
			return OLDBARK;
		if (c.equals(Utils.ColorDARKJADE))
			return DARKJADE;
		if (c.equals(Utils.ColorPOISONEDJADE))
			return POISONEDJADE;
		if (c.equals(Utils.ColorDARKFIRE))
			return DARKFIRE;
		if (c.equals(Utils.ColorDARKLILAC))
			return DARKLILAC;
		if (c.equals(Utils.ColorDEEPSKY))
			return DEEPSKY;
		if (c.equals(Utils.ColorDARKOLIVE))
			return DARKOLIVE;
		if (c.equals(Utils.ColorSPIKEPOINT))
			return SPIKEPOINT;
		if (c.equals(Utils.ColorICE))
			return ICE;
		if (c.equals(Utils.ColorLIGHT_BLUE))
			return LIGHT_BLUE;
		if (c.equals(Utils.ColorLIGHTBROWN))
			return LIGHTBROWN;
		if (c.equals(Utils.ColorGREENBROWN))
			return GREENBROWN;
		if (c.equals(Utils.ColorBROKEN))
			return BROKEN;
		if (c.equals(Utils.ColorDEADBARK))
			return DEADBARK;
		if (c.equals(Utils.ColorBROWN))
			return BROWN;
		return NOCOLOR;
	}
	
	private final void setColor(Color c) {
		_color = c;
		_framesColor = 10;
	}
	
	public BufferedImage getImage() {
		BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setBackground(Color.BLACK);
		g.clearRect(0,0,width,height);
		for (int i=_segments-1; i>=0; i--) {
				g.setColor(_segColor[i]);
				g.drawLine(x1[i] -x + _centerX, y1[i] - y + _centerY, x2[i] - x + _centerX, y2[i] - y+_centerY);
		}
		return image;
	}
}
