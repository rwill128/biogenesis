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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;
import java.util.List;

/**
 * This class implements a full organism's genetic code. A genetic code is
 * composed by a number of genes, a symmetry, optional mirroring, optional children
 * dispersing, maximum life age, and energy needed to reproduce it.
 * 
 * Genes are represented with segments that form the organism's body and are
 * drawn one behind the other. This basic set is repeated symmetry times in one
 * of two possible ways:
 * 
 * If mirroring is not applied, each repetition is rotated so they are distributed
 * in a uniform way around the central (starting) point.
 * 
 * When mirrored, segments are calculated as follows:
 * First, third, fifth and septh repetitions are applied a rotation of
 * 0, 180, 90, 270 degrees respectively.
 * 
 * Second repetition is like the first one with opposite x coordinate.
 * Fourth repetition is like the third one with opposite x coordinate.
 * Sixth repetition is like the fifth one with opposite y coordinate.
 * Eigth repetition is like the septh one with opposite y coordinate.
 */
public class GeneticCode implements Cloneable, Serializable {
	/**
	 * The version of this class
	 */
	private static final long serialVersionUID = Utils.FILE_VERSION;
	/**
	 * Maximum number of segments that an organism can have.
	 * The genes number multiplied by the symmetry is the number
	 * of segments.
	 */
	static final int MAX_SEGMENTS = 840;
	/**
	 * Minimum number of segments that an organism can have.
	 * The genes number multiplied by the symmetry is the number
	 * of segments.
	 */
	static final int MIN_SEGMENTS = 1;
	/**
	 * Array with the genes. Every gene is represented by symmetry
	 * segments when drawing the organism.
	 */
	protected Gene[] _genes;
	/**
	 * The symmetry used when drawing the organism. Possible values are
	 * 1 - 8. 
	 */
	protected int _symmetry;
	/**
	 * Mirroring indicates if symmetric segments are drawn in the same
	 * way than the original, only changing their angle, or if they are
	 * drawn like in a mirror. 0 = not mirrored, 1 = mirrored
	 */
	protected int _mirror;
	/**
	 * Version of the plague. Possible values are
	 * true and false.
	 */
	protected boolean _plague;
	/**
	 * Indicates if children will be placed moving towards the same
	 * direction than the father or to a different direction to move
	 * away.
	 */
	protected boolean _disperseChildren;
	/**
	 * Indicates if parents attack children and children attack parents.
	 */
	protected boolean _generationBattle;
	/**
	 * Indicates if siblings battle each other.
	 */
	protected boolean _siblingBattle;
	/**
	 * Indicates if organism will heal other altruist organisms or remove their infections.
	 * Altruists will not attack these segments.
	 */
	protected boolean _altruist;
	/**
	 * Indicates if organism will heal related organisms or remove their infections.
	 */
	protected boolean _familial;
	/**
	 * Indicates if organisms with rose segments are social towards other organisms with rose segments,
	 * if both rose segments have the same length or theta value.
	 */
	protected boolean _social;
	/**
	 * Indicates if organisms with rose segments are peaceful towards other organisms with rose segments.
	 */
	protected boolean _peaceful;
	/**
	 * Indicates if organisms with teal segments will move around the world.
	 * Passive organisms only move, when they are touched.
	 */
	protected boolean _passive;
	/**
	 * Indicates if passive organisms with cyan segments will rotate clockwise or counter-clockwise
	 */
	protected boolean _clockwise;
	/**
	 * Indicates if organisms will change the color of all dark segments or only of the segment, that is touched.
	 */
	protected boolean _mimicall;
	/**
	 * Modifies the function of pink
	 */
	protected boolean _modifiespink;
	/**
	 * Modifies the function of cream 
	 */
	protected boolean _modifiescream;
	/**
	 * Modifies the function of lilac
	 */
	protected boolean _modifieslilac;
	/**
	 * Indicates if organisms will always retain half of their energy if they reproduce.
	 */
	protected boolean _selfish;
	/**
	 * Minimum required energy to reproduce this genetic code.
	 * More genes, means more energy is needed. 
	 */
	protected int _reproduceEnergy;
	/**
	 * The maximum time that the organism can be alive.
	 * At the moment, this is the same for all organisms.
	 */
	protected int _max_age;
	// Getters
	/**
	 * Returns the symmetry applied to organisms with this genetic code
	 * 
	 * @return  a value of 1 - 8.
	 */
	public int getSymmetry() {
		return _symmetry;
	}
	/**
	 * Returns if mirroring is applied to organisms with this genetic code
	 * 
	 * @return  0 if no mirroring is applied, 1 if mirroring is applied.
	 */
	public int getMirror() {
		return _mirror;
	}
	/**
	 * Returns the plague version applied to organisms with this genetic code
	 * 
	 * @return  true or false.
	 */
	public boolean getPlague() {
		return _plague;
	}
	/**
	 * Returns if organisms with this genetic code will disperse their children or not.
	 * 
	 * @return  true if the organism disperses its children, false otherwise.
	 */
	public boolean getDisperseChildren() {
		return _disperseChildren;
	}
	/**
	 * Returns if organisms with this genetic code will attack their parents and children.
	 * 
	 * @return  true if the organism attacks its parent and children, false otherwise.
	 */
	public boolean getGenerationBattle() {
		return _generationBattle;
	}
	/**
	 * Returns if organisms with this genetic code will attack their siblings.
	 * 
	 * @return  true if the organism attacks its siblings, false otherwise.
	 */
	public boolean getSiblingBattle() {
		return _siblingBattle;
	}
	/**
	 * Returns if organisms with this genetic code will be altruistic or not.
	 * 
	 * @return  true if the organism is altruistic, false otherwise.
	 */
	public boolean getAltruist() {
		return _altruist;
	}
	/**
	 * Returns if organisms with this genetic code will be familial or not.
	 * 
	 * @return  true if the organism is familial, false otherwise.
	 */
	public boolean getFamilial() {
		return _familial;
	}
	/**
	 * Returns if organisms with this genetic code will be social or not.
	 * 
	 * @return  true if the organism is social, false otherwise.
	 */
	public boolean getSocial() {
		return _social;
	}
	/**
	 * Returns if organisms with this genetic code will be peaceful or not.
	 * 
	 * @return  true if the organism is peaceful, false otherwise.
	 */
	public boolean getPeaceful() {
		return _peaceful;
	}
	/**
	 * Returns if organisms with this genetic code will be passive or not.
	 * 
	 * @return  true if the organism is passive, false otherwise.
	 */
	public boolean getPassive() {
		return _passive;
	}
	/**
	 * Returns if organisms with this genetic code will turn clockwise or not.
	 * 
	 * @return  true if the organism turns clockwise, false otherwise.
	 */
	public boolean getClockwise() {
		return _clockwise;
	}
	/**
	 * Returns if organisms with this genetic code will change the color of all dark segments or not.
	 * 
	 * @return  true if the organism changes all colors, false otherwise.
	 */
	public boolean getMimicAll() {
		return _mimicall;
	}
	/**
	 * Returns if the function of pink is modified or not.
	 * 
	 * @return  true if the function of pink is modified, false otherwise.
	 */
	public boolean getModifiespink() {
		return _modifiespink;
	}
	/**
	 * Returns if the function of cream is modified or not.
	 * 
	 * @return  true if the function of cream is modified, false otherwise.
	 */
	public boolean getModifiescream() {
		return _modifiescream;
	}
	/**
	 * Returns if the function of lilac is modified or not.
	 * 
	 * @return  true if the function of lilac is modified, false otherwise.
	 */
	public boolean getModifieslilac() {
		return _modifieslilac;
	}
	/**
	 * Returns if organisms with this genetic code will always retain half of their energy if they reproduce or not.
	 * 
	 * @return  true if the organism retains half of its energy, false otherwise.
	 */
	public boolean getSelfish() {
		return _selfish;
	}
	/**
	 * Returns the energy needed to replicate this genetic code.
	 * This energy is equal to 40 plus 3 for each segment.
	 * 
	 * @return  the energy needed to replicate this genetic code.
	 */
	public int getReproduceEnergy() {
		return _reproduceEnergy;
	}
	/**
	 * Returns the maximum age that the organism can be. 
	 * 
	 * @return  The maximum age that the organism can be.
	 */
	public int getMaxAge() {
		return _max_age;
	}
	/**
	 * Return a reference to a gene.
	 * 
	 * @param i  The index of the gene in the genetic code.
	 * @return  A reference to the gene
	 */
	public Gene getGene(int i) {
		return _genes[i];
	}
	/**
	 * Return the number of genes of this code
	 * 
	 * @return  The number of genes
	 */
	public int getNGenes() {
		return _genes.length;
	}
	/**
	 * Gives mirror a random value (0 or 1)
	 */
	private void randomMirror() {
		_mirror = Utils.random.nextInt(2);
	}
	/**
	 * Gives symmetry a random value (1 - 8)
	 */
	private void randomSymmetry() {
		_symmetry = Utils.random.nextInt(8)+1;
	}
	/**
	 * Create a random genes array making sure that there will be more or equal than
	 * MIN_SEGMENTS and less or equal than MAX_SEGMENTS segments.
	 * It needs symmetry to have a valid value. 
	 */
	private void randomGenes() {
		int nSegments = (MIN_SEGMENTS + Utils.random.nextInt(Utils.INITIAL_COMPLEXITY-MIN_SEGMENTS+1)) * _symmetry; // 4 - 64
		if (nSegments % _symmetry != 0)
		    nSegments += (_symmetry - (nSegments % _symmetry));
		int nGenes = nSegments / _symmetry;
		_genes = new Gene[nGenes];
		for (int i=0; i<nGenes; i++) {
			_genes[i] = new Gene();
			_genes[i].randomize();
			if (i == 0) {
				_genes[i].setBranch(-1);						
			} else {
				if (Utils.random.nextInt(2) < 1) {
					_genes[i].setBranch(-1);
				} else {
					_genes[i].setBranch(Utils.random.nextInt(i));
				}
			}
		}
	}
	/**
	 * Gives plague version a random value (true or false)
	 */
	private void randomPlague() {
		_plague = Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will try to
	 * disperse their children or not.
	 */
	private void randomDisperseChildren() {
		_disperseChildren =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will
	 * attack their parents and children or not.
	 */
	private void randomGenerationBattle() {
		_generationBattle =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will
	 * attack their siblings or not.
	 */
	private void randomSiblingBattle() {
		_siblingBattle =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will be
	 * altruistic or not.
	 */
	private void randomAltruist() {
		_altruist =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will be
	 * familial or not.
	 */
	private void randomFamilial() {
		_familial =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will be
	 * social or not.
	 */
	private void randomSocial() {
		_social =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will be
	 * peaceful or not.
	 */
	private void randomPeaceful() {
		_peaceful =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will be
	 * passive or not.
	 */
	private void randomPassive() {
		_passive =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will turn
	 * clockwise or not.
	 */
	private void randomClockwise() {
		_clockwise =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will mimic
	 * all dark segments or not.
	 */
	private void randomMimicAll() {
		_mimicall =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if the function of pink is modified or not.
	 */
	private void randomModifiespink() {
		_modifiespink =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if the function of cream is modified or not.
	 */
	private void randomModifiescream() {
		_modifiescream =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if the function of lilac is modified or not.
	 */
	private void randomModifieslilac() {
		_modifieslilac =  Utils.random.nextBoolean();
	}
	/**
	 * Decide randomly if organisms with this genetic code will always retain half of their energy if they reproduce or not.
	 */
	private void randomSelfish() {
		_selfish =  Utils.random.nextBoolean();
	}
	/**
	 * Calculates the energy required to reproduce this genetic code.
	 * This energy is 40 plus 3 for each segment.
	 */
	private void calculateReproduceEnergy() {
		_reproduceEnergy = 40 + 3 * _genes.length * _symmetry;
	}
	/**
	 * Calculates the maximum age that the organism can be.
	 * This energy is default age plus 1 for 4 segments.
	 */
	private void calculateMaxAge() {
		_max_age = Utils.MAX_AGE + ((_genes.length * _symmetry)/Utils.AGE_DIVISOR);
	}
	/**
	 * Creates a new random genetic code.
	 */
	public GeneticCode() {
		randomMirror(); 
		randomSymmetry();
		randomGenes();
		randomPlague();
		randomDisperseChildren();
		randomGenerationBattle();
		randomSiblingBattle();
		randomAltruist();
		randomFamilial();
		randomSocial();
		randomPeaceful();
		randomPassive();
		randomClockwise();
		randomMimicAll();
		randomModifiespink();
		randomModifiescream();
		randomModifieslilac();
		randomSelfish();
		calculateReproduceEnergy();
		calculateMaxAge();
	}	
	/**
	 * Creates a genetic code given its content.
	 * No check about the validity of the information is done.
	 * 
	 * @param genes  A list containing the genes
	 * @param symmetry  The symmetry that an organism with this genetic code will have.
	 * @param mirror  0 if the organism won't be mirrored, 1 if it will.
	 * @param plague  The plague version that an organism with this genetic code will have.
	 * @param disperseChildren  true if the organism will disperse its children.
	 * @param generationBattle  true if the organism attacks its parent and children.
	 * @param siblingBattle  true if the organism attacks its siblings.
	 * @param altruist  true if the organism is an altruist.
	 * @param familial  true if the organism is familial.
	 * @param social  true if the organism is social.
	 * @param peaceful  true if the organism is peaceful.
	 * @param passive  true if the organism is passive.
	 * @param clockwise  true if the organism turns clockwise.
	 * @param mimicall  true if the organism mimics all dark segments.
	 * @param modifiespink  true if the function of pink is modified.
	 * @param modifiescream  true if the function of cream is modified.
	 * @param modifieslilac  true if the function of lilac is modified.
	 * @param selfish  true if the organism retains half of its energy if it reproduces.
	 */
	public GeneticCode(List<Gene> genes, int symmetry, int mirror, boolean plague, boolean disperseChildren, boolean generationBattle, boolean siblingBattle,
		boolean altruist, boolean familial, boolean social, boolean peaceful, boolean passive, boolean clockwise, boolean mimicall, boolean modifiespink,
		boolean modifiescream, boolean modifieslilac, boolean selfish) {
		int nGenes = genes.size();
		_genes = new Gene[nGenes];
		genes.toArray(_genes);
		calculateMaxAge();
		_mirror = mirror;
		_symmetry = symmetry;
		_plague = plague;
		_disperseChildren = disperseChildren;
		_generationBattle = generationBattle;
		_siblingBattle = siblingBattle;
		_altruist = altruist;
		_familial = familial;
		_social = social;
		_peaceful = peaceful;
		_passive = passive;
		_clockwise = clockwise;
		_mimicall = mimicall;
		_modifiespink = modifiespink;
		_modifiescream = modifiescream;
		_modifieslilac = modifieslilac;
		_selfish = selfish;
		calculateReproduceEnergy();
	}
	/**
	 * Creates a new genetic code based on the father genetic code but
	 * applying random mutations to it.
	 * 
	 * @param parentCode  The genetic code that this code will be based on.
	 */
	public GeneticCode(GeneticCode parentCode) {
		int i,j;
		int addedGene = -1;
		int removedGene = -1;
		int nGenes;
		boolean randomLength;
		boolean randomTheta;
		boolean randomBranch;
		boolean randomredReaction;
		boolean randomgreenReaction;
		boolean randomblueReaction;
		boolean randomplagueReaction;
		boolean randomwhiteReaction;
		boolean randomgrayReaction;
		boolean randomdefaultReaction;
		boolean randommagentaReaction;
		boolean randompinkReaction;
		boolean randomcoralReaction;
		boolean randomorangeReaction;
		boolean randombarkReaction;
		boolean randomvioletReaction;
		boolean randomvirusReaction;
		boolean randommaroonReaction;
		boolean randomoliveReaction;
		boolean randommintReaction;
		boolean randomcreamReaction;
		boolean randomspikeReaction;
		boolean randomspikepointReaction;
		boolean randomlightblueReaction;
		boolean randomochreReaction;
		boolean randomskyReaction;
		boolean randomlilacReaction;
		boolean randomsilverReaction;
		boolean randomfireReaction;
		boolean randomlightbrownReaction;
		boolean randomgreenbrownReaction;
		boolean randombrownReaction;
		boolean randomiceReaction;
		boolean randomsickReaction;
		boolean randomfriendReaction;
		boolean randomColor;
		boolean randomBack;
		
		if (Utils.randomMutation())
			randomMirror();
		else
			_mirror = parentCode.getMirror();
		if (Utils.randomMutation()) {
			// change symmetry
			randomSymmetry();
			nGenes = parentCode.getNGenes();
			if (nGenes * _symmetry > MAX_SEGMENTS) {
				_symmetry = parentCode.getSymmetry();
			}
		} else {
			// keep symmetry
			_symmetry = parentCode.getSymmetry();
			if (Utils.randomMutation()) {
			// change number of segments
				if (Utils.random.nextBoolean()) {
				// increase segments
					if (parentCode.getNGenes() * parentCode.getSymmetry() >= MAX_SEGMENTS)
						nGenes = parentCode.getNGenes();
					else {
						nGenes = parentCode.getNGenes() + 1;
						addedGene = Utils.random.nextInt(nGenes);
					}
				} else {
				// decrease segments
					if (parentCode.getNGenes() * parentCode.getSymmetry() <= MIN_SEGMENTS)
						nGenes = parentCode.getNGenes();
					else {
						nGenes = parentCode.getNGenes() - 1;
						removedGene = Utils.random.nextInt(parentCode.getNGenes());
					}
				}
			} else {
			// keep number of segments
				nGenes = parentCode.getNGenes();
			}
		}
		// Create genes
		_genes = new Gene[nGenes];
		for (i=0,j=0; i<nGenes; i++,j++) {
			if (removedGene == j) {
				i--;
				continue;
			}
			if (addedGene == i) {
				_genes[i] = new Gene();
				_genes[i].randomize();
				if (i == 0) {
					_genes[i].setBranch(-1);						
				} else {
					if (Utils.random.nextInt(2) < 1) {
						_genes[i].setBranch(-1);
					} else {
						_genes[i].setBranch(Utils.random.nextInt(i));
					}
				}
				j--;
				continue;
			}
			randomLength = randomTheta = randomBranch = randomredReaction = randomgreenReaction = randomblueReaction = randomplagueReaction = randomwhiteReaction
			= randomgrayReaction = randomdefaultReaction = randommagentaReaction = randompinkReaction= randomcoralReaction = randomorangeReaction = randombarkReaction
			= randomvioletReaction = randomvirusReaction = randommaroonReaction = randomoliveReaction = randommintReaction = randomcreamReaction = randomspikeReaction
			= randomspikepointReaction = randomlightblueReaction = randomochreReaction = randomskyReaction = randomlilacReaction = randomsilverReaction = randomfireReaction
			= randomlightbrownReaction = randomgreenbrownReaction = randombrownReaction = randomiceReaction = randomsickReaction = randomfriendReaction = randomColor
			= randomBack = false;
			if (Utils.randomMutation())
				randomLength = true;
			if (Utils.randomMutation())
				randomTheta = true;
			if (Utils.randomMutation())
				randomBranch = true;
			if (Utils.randomMutation())
				randomredReaction = true;
			if (Utils.randomMutation())
				randomgreenReaction = true;
			if (Utils.randomMutation())
				randomblueReaction = true;
			if (Utils.randomMutation())
				randomplagueReaction = true;
			if (Utils.randomMutation())
				randomwhiteReaction = true;
			if (Utils.randomMutation())
				randomgrayReaction = true;
			if (Utils.randomMutation())
				randomdefaultReaction = true;
			if (Utils.randomMutation())
				randommagentaReaction = true;
			if (Utils.randomMutation())
				randompinkReaction = true;
			if (Utils.randomMutation())
				randomcoralReaction = true;
			if (Utils.randomMutation())
				randomorangeReaction = true;
			if (Utils.randomMutation())
				randombarkReaction = true;
			if (Utils.randomMutation())
				randomvioletReaction = true;
			if (Utils.randomMutation())
				randomvirusReaction = true;
			if (Utils.randomMutation())
				randommaroonReaction = true;
			if (Utils.randomMutation())
				randomoliveReaction = true;
			if (Utils.randomMutation())
				randommintReaction = true;
			if (Utils.randomMutation())
				randomcreamReaction = true;
			if (Utils.randomMutation())
				randomspikeReaction = true;
			if (Utils.randomMutation())
				randomspikepointReaction = true;
			if (Utils.randomMutation())
				randomlightblueReaction = true;
			if (Utils.randomMutation())
				randomochreReaction = true;
			if (Utils.randomMutation())
				randomskyReaction = true;
			if (Utils.randomMutation())
				randomlilacReaction = true;
			if (Utils.randomMutation())
				randomsilverReaction = true;
			if (Utils.randomMutation())
				randomfireReaction = true;
			if (Utils.randomMutation())
				randomlightbrownReaction = true;
			if (Utils.randomMutation())
				randomgreenbrownReaction = true;
			if (Utils.randomMutation())
				randombrownReaction = true;
			if (Utils.randomMutation())
				randomiceReaction = true;
			if (Utils.randomMutation())
				randomsickReaction = true;
			if (Utils.randomMutation())
				randomfriendReaction = true;
			if (Utils.randomMutation())
				randomColor = true;
			if (Utils.randomMutation())
				randomBack = true;
			if (randomLength || randomTheta || randomBranch || randomredReaction || randomgreenReaction || randomblueReaction || randomplagueReaction
				|| randomwhiteReaction || randomgrayReaction || randomdefaultReaction || randommagentaReaction || randompinkReaction || randomcoralReaction
				|| randomorangeReaction || randombarkReaction || randomvioletReaction || randomvirusReaction || randommaroonReaction || randomoliveReaction
				|| randommintReaction || randomcreamReaction || randomspikeReaction || randomspikepointReaction || randomlightblueReaction || randomochreReaction
				|| randomskyReaction || randomlilacReaction || randomsilverReaction || randomfireReaction || randomlightbrownReaction || randomgreenbrownReaction
				|| randombrownReaction || randomiceReaction || randomsickReaction || randomfriendReaction || randomColor || randomBack) {
				_genes[i] = new Gene();
				if (randomLength)
					_genes[i].randomizeLength();
				else
					_genes[i].setLength(parentCode.getGene(j).getLength());
				if (randomTheta)
					_genes[i].randomizeTheta();
				else
					_genes[i].setTheta(parentCode.getGene(j).getTheta());
				if (randomBranch) {
					if (i == 0) {
						_genes[i].setBranch(-1);						
					} else {
						if (Utils.random.nextInt(2) < 1) {
							_genes[i].setBranch(-1);
						} else {
							_genes[i].setBranch(Utils.random.nextInt(i));
						}
					}
				} else {
					_genes[i].setBranch(parentCode.getGene(j).getBranch());
					if (_genes[i].getBranch() >= i) {
						_genes[i].setBranch(-1);
					}
				}
				if (randomredReaction)
					_genes[i].randomizeredReaction();
				else
					_genes[i].setredReaction(parentCode.getGene(j).getredReaction());
				if (randomgreenReaction)
					_genes[i].randomizegreenReaction();
				else
					_genes[i].setgreenReaction(parentCode.getGene(j).getgreenReaction());
				if (randomblueReaction)
					_genes[i].randomizeblueReaction();
				else
					_genes[i].setblueReaction(parentCode.getGene(j).getblueReaction());
				if (randomplagueReaction)
					_genes[i].randomizeplagueReaction();
				else
					_genes[i].setplagueReaction(parentCode.getGene(j).getplagueReaction());
				if (randomwhiteReaction)
					_genes[i].randomizewhiteReaction();
				else
					_genes[i].setwhiteReaction(parentCode.getGene(j).getwhiteReaction());
				if (randomgrayReaction)
					_genes[i].randomizegrayReaction();
				else
					_genes[i].setgrayReaction(parentCode.getGene(j).getgrayReaction());
				if (randomdefaultReaction)
					_genes[i].randomizedefaultReaction();
				else
					_genes[i].setdefaultReaction(parentCode.getGene(j).getdefaultReaction());
				if (randommagentaReaction)
					_genes[i].randomizemagentaReaction();
				else
					_genes[i].setmagentaReaction(parentCode.getGene(j).getmagentaReaction());
				if (randompinkReaction)
					_genes[i].randomizepinkReaction();
				else
					_genes[i].setpinkReaction(parentCode.getGene(j).getpinkReaction());
				if (randomcoralReaction)
					_genes[i].randomizecoralReaction();
				else
					_genes[i].setcoralReaction(parentCode.getGene(j).getcoralReaction());
				if (randomorangeReaction)
					_genes[i].randomizeorangeReaction();
				else
					_genes[i].setorangeReaction(parentCode.getGene(j).getorangeReaction());
				if (randombarkReaction)
					_genes[i].randomizebarkReaction();
				else
					_genes[i].setbarkReaction(parentCode.getGene(j).getbarkReaction());
				if (randomvioletReaction)
					_genes[i].randomizevioletReaction();
				else
					_genes[i].setvioletReaction(parentCode.getGene(j).getvioletReaction());
				if (randomvirusReaction)
					_genes[i].randomizevirusReaction();
				else
					_genes[i].setvirusReaction(parentCode.getGene(j).getvirusReaction());
				if (randommaroonReaction)
					_genes[i].randomizemaroonReaction();
				else
					_genes[i].setmaroonReaction(parentCode.getGene(j).getmaroonReaction());
				if (randomoliveReaction)
					_genes[i].randomizeoliveReaction();
				else
					_genes[i].setoliveReaction(parentCode.getGene(j).getoliveReaction());
				if (randommintReaction)
					_genes[i].randomizemintReaction();
				else
					_genes[i].setmintReaction(parentCode.getGene(j).getmintReaction());
				if (randomcreamReaction)
					_genes[i].randomizecreamReaction();
				else
					_genes[i].setcreamReaction(parentCode.getGene(j).getcreamReaction());
				if (randomspikeReaction)
					_genes[i].randomizespikeReaction();
				else
					_genes[i].setspikeReaction(parentCode.getGene(j).getspikeReaction());
				if (randomspikepointReaction)
					_genes[i].randomizespikepointReaction();
				else
					_genes[i].setspikepointReaction(parentCode.getGene(j).getspikepointReaction());
				if (randomlightblueReaction)
					_genes[i].randomizelightblueReaction();
				else
					_genes[i].setlightblueReaction(parentCode.getGene(j).getlightblueReaction());
				if (randomochreReaction)
					_genes[i].randomizeochreReaction();
				else
					_genes[i].setochreReaction(parentCode.getGene(j).getochreReaction());
				if (randomskyReaction)
					_genes[i].randomizeskyReaction();
				else
					_genes[i].setskyReaction(parentCode.getGene(j).getskyReaction());
				if (randomlilacReaction)
					_genes[i].randomizelilacReaction();
				else
					_genes[i].setlilacReaction(parentCode.getGene(j).getlilacReaction());
				if (randomsilverReaction)
					_genes[i].randomizesilverReaction();
				else
					_genes[i].setsilverReaction(parentCode.getGene(j).getsilverReaction());
				if (randomfireReaction)
					_genes[i].randomizefireReaction();
				else
					_genes[i].setfireReaction(parentCode.getGene(j).getfireReaction());
				if (randomlightbrownReaction)
					_genes[i].randomizelightbrownReaction();
				else
					_genes[i].setlightbrownReaction(parentCode.getGene(j).getlightbrownReaction());
				if (randomgreenbrownReaction)
					_genes[i].randomizegreenbrownReaction();
				else
					_genes[i].setgreenbrownReaction(parentCode.getGene(j).getgreenbrownReaction());
				if (randombrownReaction)
					_genes[i].randomizebrownReaction();
				else
					_genes[i].setbrownReaction(parentCode.getGene(j).getbrownReaction());
				if (randomiceReaction)
					_genes[i].randomizeiceReaction();
				else
					_genes[i].seticeReaction(parentCode.getGene(j).geticeReaction());
				if (randomsickReaction)
					_genes[i].randomizesickReaction();
				else
					_genes[i].setsickReaction(parentCode.getGene(j).getsickReaction());
				if (randomfriendReaction)
					_genes[i].randomizefriendReaction();
				else
					_genes[i].setfriendReaction(parentCode.getGene(j).getfriendReaction());
				if (randomColor)
					_genes[i].randomizeColor();
				else
					_genes[i].setColor(parentCode.getGene(j).getColor());
			} else
				_genes[i] = parentCode.getGene(j);
		}

		if (Utils.randomMutation())
			randomPlague();
		else
			_plague = parentCode.getPlague();
		if (Utils.randomMutation())
			randomDisperseChildren();
		else
			_disperseChildren = parentCode.getDisperseChildren();
		if (Utils.randomMutation())
			randomGenerationBattle();
		else
			_generationBattle = parentCode.getGenerationBattle();
		if (Utils.randomMutation())
			randomSiblingBattle();
		else
			_siblingBattle = parentCode.getSiblingBattle();
		if (Utils.randomMutation())
			randomAltruist();
		else
			_altruist = parentCode.getAltruist();
		if (Utils.randomMutation())
			randomFamilial();
		else
			_familial = parentCode.getFamilial();
		if (Utils.randomMutation())
			randomSocial();
		else
			_social = parentCode.getSocial();
		if (Utils.randomMutation())
			randomPeaceful();
		else
			_peaceful = parentCode.getPeaceful();
		if (Utils.randomMutation())
			randomPassive();
		else
			_passive = parentCode.getPassive();
		if (Utils.randomMutation())
			randomClockwise();
		else
			_clockwise = parentCode.getClockwise();
		if (Utils.randomMutation())
			randomMimicAll();
		else
			_mimicall = parentCode.getMimicAll();
		if (Utils.randomMutation())
			randomModifiespink();
		else
			_modifiespink = parentCode.getModifiespink();
		if (Utils.randomMutation())
			randomModifiescream();
		else
			_modifiescream = parentCode.getModifiescream();
		if (Utils.randomMutation())
			randomModifieslilac();
		else
			_modifieslilac = parentCode.getModifieslilac();
		if (Utils.randomMutation())
			randomSelfish();
		else
			_selfish = parentCode.getSelfish();
		calculateReproduceEnergy();
		calculateMaxAge();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		GeneticCode newCode = null;
		try {
			newCode = (GeneticCode) super.clone();
			newCode._genes = new Gene[_genes.length];
			for (int i=0; i<_genes.length; i++)
				newCode._genes[i] = (Gene) _genes[i].clone();
		} catch (CloneNotSupportedException e) {// We should never reach this
		}
		return newCode;
	}
	
	/**
	 * Draws a representation of this genetic code. This representation
	 * is equivalent to draw an adult organism with this genetic code and
	 * no rotation.
	 * 
	 * @param g  The place where the representation is drawn.
	 * @param width  The width of the available space. 
	 * @param height  The height of the available space.
	 */
	public void draw(Graphics g, int width, int height) {
		int[][] x0 = new int[_symmetry][_genes.length];
		int[][] y0 = new int[_symmetry][_genes.length];
		int[][] x1 = new int[_symmetry][_genes.length];
		int[][] y1 = new int[_symmetry][_genes.length];
		int maxX = 0;
		int minX = 0;
		int maxY = 0;
		int minY = 0;
		double scale = 1.0;
		Vector2D v = new Vector2D();
		Graphics2D g2 = (Graphics2D) g;

		for (int i=0; i<_symmetry; i++) {
			for (int j=0; j<_genes.length; j++) {
				v.setModulus(_genes[j].getLength());
				if (j==0) {
					x0[i][j]=y0[i][j]=0;
					if (_mirror == 0 || i%2==0)
						v.setTheta(_genes[j].getTheta()+i*2* FastMath.PI/_symmetry);
					else {
						v.setTheta(_genes[j].getTheta()+(i-1)*2*FastMath.PI/_symmetry);
						v.invertX();
					}
				} else {
					if (_genes[j].getBranch() == -1) {
						x0[i][j] = x1[i][j-1];
						y0[i][j] = y1[i][j-1];
						if (_mirror == 0 || i%2==0)
							v.addDegree(_genes[j].getTheta());
						else
							v.addDegree(-_genes[j].getTheta());
					} else {
					if (_genes[j].getBranch() == 0) {
						x0[i][j]=y0[i][j]=0;
						if (_mirror == 0 || i%2==0)
							v.addDegree(_genes[j].getTheta());
						else
							v.addDegree(-_genes[j].getTheta());
					} else {
						x0[i][j] = x1[i][_genes[j].getBranch() - 1];
						y0[i][j] = y1[i][_genes[j].getBranch() - 1];
						if (_mirror == 0 || i%2==0)
							v.addDegree(_genes[j].getTheta());
						else
							v.addDegree(-_genes[j].getTheta());
					    }
					}
				}
				
				x1[i][j] = (int) FastMath.round(v.getX() + x0[i][j]);
				y1[i][j] = (int) FastMath.round(v.getY() + y0[i][j]);
				
				maxX = Math.max(maxX, Math.max(x0[i][j], x1[i][j]));
				maxY = Math.max(maxY, Math.max(y0[i][j], y1[i][j]));
				minX = Math.min(minX, Math.min(x0[i][j], x1[i][j]));
				minY = Math.min(minY, Math.min(y0[i][j], y1[i][j]));
			}
		}
		
		g2.translate(width/2, height/2);
		if (maxX-minX > width)
			scale = (double)width/(double)(maxX-minX);
		if (maxY-minY > height)
			scale = Math.min(scale, (double)height/(double)(maxY-minY));
		g2.scale(scale, scale);
		
		for (int i=0; i<_symmetry; i++) {
			for (int j=0; j<_genes.length; j++) {
				x0[i][j]+=(-minX-maxX)/2;
				x1[i][j]+=(-minX-maxX)/2;
				y0[i][j]+=(-minY-maxY)/2;
				y1[i][j]+=(-minY-maxY)/2;
				g2.setColor(_genes[j].getColor());
				g2.drawLine(x0[i][j],y0[i][j],x1[i][j],y1[i][j]);
			}
		}
	}
}

