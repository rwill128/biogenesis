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

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

public class ParamDialog extends JDialog {
	private static final long serialVersionUID = Utils.FILE_VERSION;
	private JComboBox localeCombo = null;
	private JTextField widthText = null;
	private JTextField heightText = null;
	private JTextField delayText = null;
	private JCheckBox autoBackupsCheck = null;
	private JTextField backupDelayText = null;
	protected JRadioButton hardwareNoneRadio = null;
	protected JRadioButton hardwareOpenGLRadio = null;
	private ButtonGroup hardwareGroup = null;
	protected JCheckBox hardwareFBObjectCheck = null;
	private JTextField rubbingText = null;
	private JTextField elasticityText = null;
	private JTextField initialnumberText = null;
	private JTextField initialenergyText = null;
	private JTextField initialcomplexityText = null;
	private JTextField initialO2Text = null;
	private JTextField initialCO2Text = null;
	private JTextField maxageText = null;
	private JTextField agedivisorText = null;
	private JTextField mutationrateText = null;
	private JTextField redcostText = null;
	private JTextField greencostText = null;
	private JTextField bluecostText = null;
	private JTextField cyancostText = null;
	private JTextField whitecostText = null;
	private JTextField graycostText = null;
	private JTextField yellowcostText = null;
	private JTextField magentacostText = null;
	private JTextField pinkcostText = null;
	private JTextField coralcostText = null;
	private JTextField orangecostText = null;
	private JTextField forestcostText = null;
	private JTextField springcostText = null;
	private JTextField limecostText = null;
	private JTextField c4costText = null;
	private JTextField jadecostText = null;
	private JTextField grasscostText = null;
	private JTextField barkcostText = null;
	private JTextField violetcostText = null;
	private JTextField tealcostText = null;
	private JTextField marooncostText = null;
	private JTextField olivecostText = null;
	private JTextField mintcostText = null;
	private JTextField creamcostText = null;
	private JTextField rosecostText = null;
	private JTextField darkcostText = null;
	private JTextField ochrecostText = null;
	private JTextField skycostText = null;
	private JTextField lilaccostText = null;
	private JTextField silvercostText = null;
	private JTextField firecostText = null;
	private JTextField darkgraycostText = null;
	private JTextField goldcostText = null;
	private JTextField blondcostText = null;
	private JTextField auburncostText = null;
	private JTextField indigocostText = null;
	private JTextField plaguecostText = null;
	private JTextField spikecostText = null;
	private JTextField segmentcostText = null;
	private JTextField drainText = null;
	private JTextField greenenergyText = null;
	private JTextField organicenergyText = null;
	private JTextField organicsubsText = null;
	private JTextField creamorganicsubsText = null;
	private JTextField healingText = null;
	private JTextField immunesystemText = null;
	private JTextField indigodivisorText = null;
	private JTextField crowdedlimecostText = null;
	private JTextField experiencecostText = null;
	private JTextField dodgecostText = null;
	private JTextField darkjadedelayText = null;
	private JTextField viruscostText = null;
	private JTextField scourgecostText = null;
	private JTextField redprobText = null;
	private JTextField greenprobText = null;
	private JTextField blueprobText = null;
	private JTextField cyanprobText = null;
	private JTextField whiteprobText = null;
	private JTextField grayprobText = null;
	private JTextField yellowprobText = null;
	private JTextField magentaprobText = null;
	private JTextField pinkprobText = null;
	private JTextField coralprobText = null;
	private JTextField orangeprobText = null;
	private JTextField forestprobText = null;
	private JTextField springprobText = null;
	private JTextField limeprobText = null;
	private JTextField c4probText = null;
	private JTextField jadeprobText = null;
	private JTextField grassprobText = null;
	private JTextField barkprobText = null;
	private JTextField violetprobText = null;
	private JTextField tealprobText = null;
	private JTextField maroonprobText = null;
	private JTextField oliveprobText = null;
	private JTextField mintprobText = null;
	private JTextField creamprobText = null;
	private JTextField roseprobText = null;
	private JTextField darkprobText = null;
	private JTextField ochreprobText = null;
	private JTextField skyprobText = null;
	private JTextField lilacprobText = null;
	private JTextField silverprobText = null;
	private JTextField fireprobText = null;
	private JTextField darkgrayprobText = null;
	private JTextField goldprobText = null;
	private JTextField blondprobText = null;
	private JTextField auburnprobText = null;
	private JTextField indigoprobText = null;
	private JTextField plagueprobText = null;
	private JTextField spikeprobText = null;
	private JTextField decayenergyText = null;
	private JButton OKButton = null;
	private JButton cancelButton = null;
	private JButton defaultButton = null;
	
	protected MainWindow mainWindow;
	
	public ParamDialog(MainWindow parent) {
		// Configurem les caracter�stiques generals
		super(parent,Messages.getString("T_PARAMETERS_CONFIGURATION"),true); //$NON-NLS-1$
		mainWindow = parent;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setComponents();
		pack();
		setResizable(false);
		// Configurem les accions dels butons
		OKButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                    	checkParams();
                    	Utils.savePreferences();
                    	dispose();
                    }
                    });
		cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                    	dispose();
                    }
                    });
		defaultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				defaultPreferences();
			}
		});
		// Ja est� tot apunt
		setVisible(true);
	}
	
	protected void defaultPreferences() {
		widthText.setText(String.valueOf(Utils.DEF_WORLD_WIDTH));
		heightText.setText(String.valueOf(Utils.DEF_WORLD_HEIGHT));
		delayText.setText(String.valueOf(Utils.DEF_DELAY));
		autoBackupsCheck.setSelected(Utils.DEF_AUTO_BACKUP);
		backupDelayText.setText(String.valueOf(Utils.DEF_BACKUP_DELAY));
		rubbingText.setText(String.valueOf(Utils.DEF_RUBBING));
		elasticityText.setText(String.valueOf(Utils.DEF_ELASTICITY));
		initialnumberText.setText(String.valueOf(Utils.DEF_INITIAL_ORGANISMS));
		initialenergyText.setText(String.valueOf(Utils.DEF_INITIAL_ENERGY));
		initialcomplexityText.setText(String.valueOf(Utils.DEF_INITIAL_COMPLEXITY));
		initialO2Text.setText(String.valueOf(Utils.DEF_INITIAL_O2));
		initialCO2Text.setText(String.valueOf(Utils.DEF_INITIAL_CO2));
		maxageText.setText(String.valueOf(Utils.DEF_MAX_AGE));
		agedivisorText.setText(String.valueOf(Utils.DEF_AGE_DIVISOR));
		mutationrateText.setText(String.valueOf(Utils.DEF_MUTATION_RATE));
		segmentcostText.setText(String.valueOf(Utils.DEF_SEGMENT_COST_DIVISOR));
		drainText.setText(String.valueOf(Utils.DEF_DRAIN_SUBS_DIVISOR));
		greenenergyText.setText(String.valueOf(Utils.DEF_GREEN_OBTAINED_ENERGY_DIVISOR));
		organicenergyText.setText(String.valueOf(Utils.DEF_ORGANIC_OBTAINED_ENERGY));
		organicsubsText.setText(String.valueOf(Utils.DEF_ORGANIC_SUBS_PRODUCED));
		creamorganicsubsText.setText(String.valueOf(Utils.DEF_CREAM_ORGANIC_SUBS_PRODUCED));
		healingText.setText(String.valueOf(Utils.DEF_HEALING));
		immunesystemText.setText(String.valueOf(Utils.DEF_IMMUNE_SYSTEM));
		indigodivisorText.setText(String.valueOf(Utils.DEF_INDIGO_DIVISOR));
		crowdedlimecostText.setText(String.valueOf(Utils.DEF_CROWDEDLIME_ENERGY_CONSUMPTION));
		experiencecostText.setText(String.valueOf(Utils.DEF_EXPERIENCE_ENERGY_CONSUMPTION));
		dodgecostText.setText(String.valueOf(Utils.DEF_DODGE_ENERGY_CONSUMPTION));
		darkjadedelayText.setText(String.valueOf(Utils.DEF_DARKJADE_DELAY));
		viruscostText.setText(String.valueOf(Utils.DEF_VIRUS_ENERGY_CONSUMPTION));
		scourgecostText.setText(String.valueOf(Utils.DEF_SCOURGE_ENERGY_CONSUMPTION));
		redcostText.setText(String.valueOf(Utils.DEF_RED_ENERGY_CONSUMPTION));
		greencostText.setText(String.valueOf(Utils.DEF_GREEN_ENERGY_CONSUMPTION));
		bluecostText.setText(String.valueOf(Utils.DEF_BLUE_ENERGY_CONSUMPTION));
		cyancostText.setText(String.valueOf(Utils.DEF_CYAN_ENERGY_CONSUMPTION));
		whitecostText.setText(String.valueOf(Utils.DEF_WHITE_ENERGY_CONSUMPTION));
		graycostText.setText(String.valueOf(Utils.DEF_GRAY_ENERGY_CONSUMPTION));
		yellowcostText.setText(String.valueOf(Utils.DEF_YELLOW_ENERGY_CONSUMPTION));
		magentacostText.setText(String.valueOf(Utils.DEF_MAGENTA_ENERGY_CONSUMPTION));
		pinkcostText.setText(String.valueOf(Utils.DEF_PINK_ENERGY_CONSUMPTION));
		coralcostText.setText(String.valueOf(Utils.DEF_CORAL_ENERGY_CONSUMPTION));
		orangecostText.setText(String.valueOf(Utils.DEF_ORANGE_ENERGY_CONSUMPTION));
		forestcostText.setText(String.valueOf(Utils.DEF_FOREST_ENERGY_CONSUMPTION));
		springcostText.setText(String.valueOf(Utils.DEF_SPRING_ENERGY_CONSUMPTION));
		limecostText.setText(String.valueOf(Utils.DEF_LIME_ENERGY_CONSUMPTION));
		c4costText.setText(String.valueOf(Utils.DEF_C4_ENERGY_CONSUMPTION));
		jadecostText.setText(String.valueOf(Utils.DEF_JADE_ENERGY_CONSUMPTION));
		grasscostText.setText(String.valueOf(Utils.DEF_GRASS_ENERGY_CONSUMPTION));
		barkcostText.setText(String.valueOf(Utils.DEF_BARK_ENERGY_CONSUMPTION));
		violetcostText.setText(String.valueOf(Utils.DEF_VIOLET_ENERGY_CONSUMPTION));
		tealcostText.setText(String.valueOf(Utils.DEF_TEAL_ENERGY_CONSUMPTION));
		marooncostText.setText(String.valueOf(Utils.DEF_MAROON_ENERGY_CONSUMPTION));
		olivecostText.setText(String.valueOf(Utils.DEF_OLIVE_ENERGY_CONSUMPTION));
		mintcostText.setText(String.valueOf(Utils.DEF_MINT_ENERGY_CONSUMPTION));
		creamcostText.setText(String.valueOf(Utils.DEF_CREAM_ENERGY_CONSUMPTION));
		rosecostText.setText(String.valueOf(Utils.DEF_ROSE_ENERGY_CONSUMPTION));
		darkcostText.setText(String.valueOf(Utils.DEF_DARK_ENERGY_CONSUMPTION));
		ochrecostText.setText(String.valueOf(Utils.DEF_OCHRE_ENERGY_CONSUMPTION));
		skycostText.setText(String.valueOf(Utils.DEF_SKY_ENERGY_CONSUMPTION));
		lilaccostText.setText(String.valueOf(Utils.DEF_LILAC_ENERGY_CONSUMPTION));
		silvercostText.setText(String.valueOf(Utils.DEF_SILVER_ENERGY_CONSUMPTION));
		firecostText.setText(String.valueOf(Utils.DEF_FIRE_ENERGY_CONSUMPTION));
		darkgraycostText.setText(String.valueOf(Utils.DEF_DARKGRAY_ENERGY_CONSUMPTION));
		goldcostText.setText(String.valueOf(Utils.DEF_GOLD_DIVISOR));
		blondcostText.setText(String.valueOf(Utils.DEF_BLOND_ENERGY_CONSUMPTION));
		auburncostText.setText(String.valueOf(Utils.DEF_AUBURN_ENERGY_CONSUMPTION));
		indigocostText.setText(String.valueOf(Utils.DEF_INDIGO_ENERGY_CONSUMPTION));
		plaguecostText.setText(String.valueOf(Utils.DEF_PLAGUE_ENERGY_CONSUMPTION));
		spikecostText.setText(String.valueOf(Utils.DEF_SPIKE_ENERGY_CONSUMPTION));
		redprobText.setText(String.valueOf(Utils.DEF_RED_PROB));
		greenprobText.setText(String.valueOf(Utils.DEF_GREEN_PROB));
		blueprobText.setText(String.valueOf(Utils.DEF_BLUE_PROB));
		cyanprobText.setText(String.valueOf(Utils.DEF_CYAN_PROB));
		whiteprobText.setText(String.valueOf(Utils.DEF_WHITE_PROB));
		grayprobText.setText(String.valueOf(Utils.DEF_GRAY_PROB));
		yellowprobText.setText(String.valueOf(Utils.DEF_YELLOW_PROB));
		magentaprobText.setText(String.valueOf(Utils.DEF_MAGENTA_PROB));
		pinkprobText.setText(String.valueOf(Utils.DEF_PINK_PROB));
		coralprobText.setText(String.valueOf(Utils.DEF_CORAL_PROB));
		orangeprobText.setText(String.valueOf(Utils.DEF_ORANGE_PROB));
		forestprobText.setText(String.valueOf(Utils.DEF_FOREST_PROB));
		springprobText.setText(String.valueOf(Utils.DEF_SPRING_PROB));
		limeprobText.setText(String.valueOf(Utils.DEF_LIME_PROB));
		c4probText.setText(String.valueOf(Utils.DEF_C4_PROB));
		jadeprobText.setText(String.valueOf(Utils.DEF_JADE_PROB));
		grassprobText.setText(String.valueOf(Utils.DEF_GRASS_PROB));
		barkprobText.setText(String.valueOf(Utils.DEF_BARK_PROB));
		violetprobText.setText(String.valueOf(Utils.DEF_VIOLET_PROB));
		tealprobText.setText(String.valueOf(Utils.DEF_TEAL_PROB));
		maroonprobText.setText(String.valueOf(Utils.DEF_MAROON_PROB));
		oliveprobText.setText(String.valueOf(Utils.DEF_OLIVE_PROB));
		mintprobText.setText(String.valueOf(Utils.DEF_MINT_PROB));
		creamprobText.setText(String.valueOf(Utils.DEF_CREAM_PROB));
		roseprobText.setText(String.valueOf(Utils.DEF_ROSE_PROB));
		darkprobText.setText(String.valueOf(Utils.DEF_DARK_PROB));
		ochreprobText.setText(String.valueOf(Utils.DEF_OCHRE_PROB));
		skyprobText.setText(String.valueOf(Utils.DEF_SKY_PROB));
		lilacprobText.setText(String.valueOf(Utils.DEF_LILAC_PROB));
		silverprobText.setText(String.valueOf(Utils.DEF_SILVER_PROB));
		fireprobText.setText(String.valueOf(Utils.DEF_FIRE_PROB));
		darkgrayprobText.setText(String.valueOf(Utils.DEF_DARKGRAY_PROB));
		goldprobText.setText(String.valueOf(Utils.DEF_GOLD_PROB));
		blondprobText.setText(String.valueOf(Utils.DEF_BLOND_PROB));
		auburnprobText.setText(String.valueOf(Utils.DEF_AUBURN_PROB));
		indigoprobText.setText(String.valueOf(Utils.DEF_INDIGO_PROB));
		plagueprobText.setText(String.valueOf(Utils.DEF_PLAGUE_PROB));
		spikeprobText.setText(String.valueOf(Utils.DEF_SPIKE_PROB));
		decayenergyText.setText(String.valueOf(Utils.DEF_DECAY_ENERGY));
		switch (Utils.DEF_HARDWARE_ACCELERATION) {
		case 0:
		case 2:
		case 5:
			hardwareNoneRadio.setSelected(true);
			hardwareFBObjectCheck.setSelected(false);
			hardwareFBObjectCheck.setEnabled(false);
			break;
		case 1:
		case 3:
			hardwareOpenGLRadio.setSelected(true);
			hardwareFBObjectCheck.setSelected(false);
			break;
		case 6:
			hardwareOpenGLRadio.setSelected(true);
			hardwareFBObjectCheck.setSelected(true);
			break;
		}
	}
	
	protected void setComponents() {
		getContentPane().setLayout(new BorderLayout());
		// Set up buttons
		JPanel buttonsPanel = new JPanel();
		OKButton = new JButton(Messages.getString("T_OK")); //$NON-NLS-1$
		cancelButton = new JButton(Messages.getString("T_CANCEL")); //$NON-NLS-1$
		defaultButton = new JButton(Messages.getString("T_DEFAULT_VALUES")); //$NON-NLS-1$
		buttonsPanel.add(OKButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(defaultButton);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		JTabbedPane tabbedPane = new JTabbedPane();
		// Set up general tab
		tabbedPane.addTab(Messages.getString("T_GENERAL"), setGeneralTab()); //$NON-NLS-1$
		// Set up world tab
		tabbedPane.addTab(Messages.getString("T_WORLD"), setWorldTab()); //$NON-NLS-1$
		// Set up organisms tab
		tabbedPane.addTab(Messages.getString("T_ORGANISMS"), setOrganismsTab()); //$NON-NLS-1$
		// Set up metabolism tab
		tabbedPane.addTab(Messages.getString("T_METABOLISM"), setMetabolismTab()); //$NON-NLS-1$
		// Set up genes tab
		tabbedPane.addTab(Messages.getString("T_GENES"), setGenesTab()); //$NON-NLS-1$
		// Set up genes2 tab
		tabbedPane.addTab(Messages.getString("T_GENES2"), setGenes2Tab()); //$NON-NLS-1$
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
	}
	
	protected JPanel setGeneralTab() {
		JPanel generalPanel = new JPanel();
		generalPanel.setLayout(new BoxLayout(generalPanel,BoxLayout.Y_AXIS));
		// Language
		JPanel panel = new JPanel();
		JLabel label = new JLabel(Messages.getString("T_LANGUAGE")); //$NON-NLS-1$
		panel.add(label);
		localeCombo = new JComboBox(Messages.getSupportedLocalesNames());
		localeCombo.setSelectedIndex(Messages.getLocaleIndex());
		panel.add(localeCombo);
		generalPanel.add(panel);
		//Frames per second
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_TIME_PER_FRAME")); //$NON-NLS-1$
		panel.add(label);
		delayText = new JTextField(Integer.toString(Utils.DELAY),6);
		panel.add(delayText);
		label = new JLabel(Messages.getString("T_MILLISECONDS")); //$NON-NLS-1$
		panel.add(label);
		generalPanel.add(panel);
		//Backups
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		autoBackupsCheck = new JCheckBox(Messages.getString("T_AUTOMATIC_BACKUPS"));
		label = new JLabel(Messages.getString("T_TIME_BETWEEN_BACKUPS")); //$NON-NLS-1$
		backupDelayText = new JTextField(Integer.toString(Utils.BACKUP_DELAY),10);
		if (Utils.AUTO_BACKUP) {
			autoBackupsCheck.setSelected(true);
			backupDelayText.setEnabled(true);
		} else {
			autoBackupsCheck.setSelected(false);
			backupDelayText.setEnabled(false);
		}
		autoBackupsCheck.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (autoBackupsCheck.isSelected())
					backupDelayText.setEnabled(true);
				else
					backupDelayText.setEnabled(false);				
			}
		});
		panel.add(autoBackupsCheck);
		JPanel backupDelayPanel = new JPanel();
		backupDelayPanel.add(label);
		backupDelayPanel.add(backupDelayText);
		panel.add(backupDelayPanel);
		generalPanel.add(panel);
		// OpenGL
		panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				Messages.getString("T_HARDWARE_ACCELERATION"))); //$NON-NLS-1$
		hardwareNoneRadio = new JRadioButton(Messages.getString("T_NONE")); //$NON-NLS-1$
		hardwareOpenGLRadio = new JRadioButton(Messages.getString("T_OPENGL")); //$NON-NLS-1$
		hardwareFBObjectCheck = new JCheckBox(Messages.getString("T_DISABLE_FBOBJECT")); //$NON-NLS-1$
		switch (Utils.HARDWARE_ACCELERATION) {
		case 0:
			hardwareNoneRadio.setSelected(true);
			hardwareFBObjectCheck.setEnabled(false);
			break;
		case 1:
		case 2:
		case 3:
			hardwareOpenGLRadio.setSelected(true);
			break;
		case 4:
		case 5:
		case 6:
			hardwareOpenGLRadio.setSelected(true);
			hardwareFBObjectCheck.setSelected(true);
			break;
		}
		hardwareGroup = new ButtonGroup();
		hardwareGroup.add(hardwareNoneRadio);
		hardwareGroup.add(hardwareOpenGLRadio);
		hardwareNoneRadio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (hardwareNoneRadio.isSelected())
					hardwareFBObjectCheck.setEnabled(false);
				else
					hardwareFBObjectCheck.setEnabled(true);				
			}
		});
		panel.add(hardwareNoneRadio);
		panel.add(hardwareOpenGLRadio);
		panel.add(hardwareFBObjectCheck);
		panel.add(new JLabel(Messages.getString("T_DIRECTX_IS_AUTOMATICALLY_DETECTED_AND_INITIALIZED"))); //$NON-NLS-1$
		panel.add(new JLabel(Messages.getString("T_APPLICATION_MUST_BE_RESTARTED_TO_APPLY_CHANGES"))); //$NON-NLS-1$
		generalPanel.add(panel);
		return generalPanel;
	}
	
	protected JPanel setWorldTab() {
		JPanel worldPanel = new JPanel();
		worldPanel.setPreferredSize(new Dimension(465, 100));
		worldPanel.setLayout(new BoxLayout(worldPanel,BoxLayout.Y_AXIS));
		// World size
		JPanel panel = new JPanel();
		JLabel label = new JLabel(Messages.getString("T_WIDTH")); //$NON-NLS-1$
		panel.add(label);
		widthText = new JTextField(Integer.toString(Utils.WORLD_WIDTH),6);
		panel.add(widthText);
		label = new JLabel(Messages.getString("T_HEIGHT")); //$NON-NLS-1$
		panel.add(label);
		heightText = new JTextField(Integer.toString(Utils.WORLD_HEIGHT),6);
		panel.add(heightText);
		worldPanel.add(panel);
		// Initial O2 - initial CO2
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_INITIAL_OXYGEN")); //$NON-NLS-1$
		panel.add(label);
		initialO2Text = new JTextField(Double.toString(Utils.INITIAL_O2),6);
		panel.add(initialO2Text);
		label = new JLabel(Messages.getString("T_INITIAL_CARBON_DIOXIDE")); //$NON-NLS-1$
		panel.add(label);
		initialCO2Text = new JTextField(Double.toString(Utils.INITIAL_CO2),6);
		panel.add(initialCO2Text);
		worldPanel.add(panel);
		// Rubbing - Elasticity
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_RUBBING_COEFFICIENT")); //$NON-NLS-1$
		panel.add(label);
		rubbingText = new JTextField(Double.toString(Utils.RUBBING),6);
		panel.add(rubbingText);
		worldPanel.add(panel);
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_ELASTICITY_COEFFICIENT")); //$NON-NLS-1$
		panel.add(label);
		elasticityText = new JTextField(Double.toString(Utils.ELASTICITY),6);
		panel.add(elasticityText);
		worldPanel.add(panel);
		
		return worldPanel;
	}
	
	protected JPanel setOrganismsTab() {
		JPanel organismsPanel = new JPanel();
		organismsPanel.setLayout(new BoxLayout(organismsPanel,BoxLayout.Y_AXIS));
		// Initial number - initial energy
		JPanel panel = new JPanel();
		JLabel label = new JLabel(Messages.getString("T_INITIAL_NUMBER")); //$NON-NLS-1$
		panel.add(label);
		initialnumberText = new JTextField(Integer.toString(Utils.INITIAL_ORGANISMS),6);
		panel.add(initialnumberText);
		label = new JLabel(Messages.getString("T_INITIAL_ENERGY")); //$NON-NLS-1$
		panel.add(label);
		initialenergyText = new JTextField(Integer.toString(Utils.INITIAL_ENERGY),6);
		panel.add(initialenergyText);
		organismsPanel.add(panel);
		// Initial complexity - Mutation rate
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_INITIAL_COMPLEXITY")); //$NON-NLS-1$
		panel.add(label);
		initialcomplexityText = new JTextField(Integer.toString(Utils.INITIAL_COMPLEXITY),6);
		panel.add(initialcomplexityText);
		label = new JLabel(Messages.getString("T_MUTATION_PERCENTAGE")); //$NON-NLS-1$
		panel.add(label);
		mutationrateText = new JTextField(Double.toString(Utils.MUTATION_RATE),6);
		panel.add(mutationrateText);
		organismsPanel.add(panel);
		// Max age - Age divisor
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_LIFE_EXPECTANCY")); //$NON-NLS-1$
		panel.add(label);
		maxageText = new JTextField(Integer.toString(Utils.MAX_AGE),6);
		panel.add(maxageText);
		label = new JLabel(Messages.getString("T_LIFE_EXPECTANCY_DIVISOR")); //$NON-NLS-1$
		panel.add(label);
		agedivisorText = new JTextField(Integer.toString(Utils.AGE_DIVISOR),6);
		panel.add(agedivisorText);
		organismsPanel.add(panel);
		// Upkeep cost
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_UPKEEP_COST_DIVISOR")); //$NON-NLS-1$
		panel.add(label);
		segmentcostText = new JTextField(Integer.toString(Utils.SEGMENT_COST_DIVISOR),6);
		panel.add(segmentcostText);
		organismsPanel.add(panel);
		// Drain substance
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_SUBSTANCE_DRAINAGE_DIVISOR")); //$NON-NLS-1$
		panel.add(label);
		drainText = new JTextField(Integer.toString(Utils.DRAIN_SUBS_DIVISOR),6);
		panel.add(drainText);
		organismsPanel.add(panel);
		// Decay energy
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_DECAY_ENERGY")); //$NON-NLS-1$
		panel.add(label);
		decayenergyText = new JTextField(Double.toString(Utils.DECAY_ENERGY),6);
		panel.add(decayenergyText);
		organismsPanel.add(panel);
		return organismsPanel;
	}
	
	protected JPanel setMetabolismTab() {
		JPanel metabolismPanel = new JPanel();
		metabolismPanel.setLayout(new BoxLayout(metabolismPanel,BoxLayout.Y_AXIS));
		// Photosynthetic metabolism
		JPanel panel = new JPanel();
		JLabel label = new JLabel(Messages.getString("T_PHOTOSYNTHETIC_METABOLISM")); //$NON-NLS-1$
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		metabolismPanel.add(label);
		metabolismPanel.add(Box.createVerticalStrut(10));
		// Obtained energy
		label = new JLabel(Messages.getString("T_OBTAINED_ENERGY_DIVISOR")); //$NON-NLS-1$
		panel.add(label);
		greenenergyText = new JTextField(Integer.toString(Utils.GREEN_OBTAINED_ENERGY_DIVISOR),6);
		panel.add(greenenergyText);
		metabolismPanel.add(panel);
		// Chemoorganotrophic metabolism
		label = new JLabel(Messages.getString("T_CHEMOORGANOTROPHIC_METABOLISM")); //$NON-NLS-1$
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		metabolismPanel.add(Box.createVerticalStrut(10));
		metabolismPanel.add(label);
		metabolismPanel.add(Box.createVerticalStrut(10));
		// Obtained energy
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_OBTAINED_ENERGY")); //$NON-NLS-1$
		panel.add(label);
		organicenergyText = new JTextField(Double.toString(Utils.ORGANIC_OBTAINED_ENERGY),6);
		panel.add(organicenergyText);
		metabolismPanel.add(panel);
		// Released energy
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_RELEASED_ENERGY_PROPORTION")); //$NON-NLS-1$
		panel.add(label);
		organicsubsText = new JTextField(Double.toString(Utils.ORGANIC_SUBS_PRODUCED),6);
		panel.add(organicsubsText);
		metabolismPanel.add(panel);
		// Released cream energy
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_CREAM_RELEASED_ENERGY_PROPORTION")); //$NON-NLS-1$
		panel.add(label);
		creamorganicsubsText = new JTextField(Double.toString(Utils.CREAM_ORGANIC_SUBS_PRODUCED),6);
		panel.add(creamorganicsubsText);
		metabolismPanel.add(panel);
		// Healing
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_HEALING")); //$NON-NLS-1$
		panel.add(label);
		healingText = new JTextField(Integer.toString(Utils.HEALING),6);
		panel.add(healingText);
		metabolismPanel.add(panel);
		// Immune System
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_IMMUNE_SYSTEM")); //$NON-NLS-1$
		panel.add(label);
		immunesystemText = new JTextField(Integer.toString(Utils.IMMUNE_SYSTEM),6);
		panel.add(immunesystemText);
		metabolismPanel.add(panel);
		// Jade regeneration
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_DARKJADE_DELAY")); //$NON-NLS-1$
		panel.add(label);
		darkjadedelayText = new JTextField(Integer.toString(Utils.DARKJADE_DELAY),6);
		panel.add(darkjadedelayText);
		metabolismPanel.add(panel);
		// Indigo Divisor
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_INDIGO_DIVISOR")); //$NON-NLS-1$
		panel.add(label);
		indigodivisorText = new JTextField(Integer.toString(Utils.INDIGO_DIVISOR),6);
		panel.add(indigodivisorText);
		metabolismPanel.add(panel);
		// Crowded lime costs
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_CROWDEDLIME_ENERGY_CONSUMPTION")); //$NON-NLS-1$
		panel.add(label);
		crowdedlimecostText = new JTextField(Double.toString(Utils.CROWDEDLIME_ENERGY_CONSUMPTION),6);
		panel.add(crowdedlimecostText);
		metabolismPanel.add(panel);
		// Silver consumer costs
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_EXPERIENCE_ENERGY_CONSUMPTION")); //$NON-NLS-1$
		panel.add(label);
		experiencecostText = new JTextField(Double.toString(Utils.EXPERIENCE_ENERGY_CONSUMPTION),6);
		panel.add(experiencecostText);
		metabolismPanel.add(panel);
		// Dodge costs
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_DODGE_ENERGY_CONSUMPTION")); //$NON-NLS-1$
		panel.add(label);
		dodgecostText = new JTextField(Double.toString(Utils.DODGE_ENERGY_CONSUMPTION),6);
		panel.add(dodgecostText);
		metabolismPanel.add(panel);
		// Virus costs
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_VIRUS_ENERGY_CONSUMPTION")); //$NON-NLS-1$
		panel.add(label);
		viruscostText = new JTextField(Double.toString(Utils.VIRUS_ENERGY_CONSUMPTION),6);
		panel.add(viruscostText);
		metabolismPanel.add(panel);
		// Scourge costs
		panel = new JPanel();
		label = new JLabel(Messages.getString("T_SCOURGE_ENERGY_CONSUMPTION")); //$NON-NLS-1$
		panel.add(label);
		scourgecostText = new JTextField(Double.toString(Utils.SCOURGE_ENERGY_CONSUMPTION),6);
		panel.add(scourgecostText);
		metabolismPanel.add(panel);
		
		return metabolismPanel;
	}
	
	protected JPanel setGenesTab() {
		JPanel genesPanel = new JPanel();
		genesPanel.setLayout(new GridLayout(20,3));
		JLabel label;

		genesPanel.add(new JLabel(Messages.getString("T_COLOR2"),SwingConstants.CENTER)); //$NON-NLS-1$
		genesPanel.add(new JLabel(Messages.getString("T_PROBABILITY"),SwingConstants.CENTER)); //$NON-NLS-1$
		genesPanel.add(new JLabel(Messages.getString("T_COST"),SwingConstants.CENTER)); //$NON-NLS-1$
		
		label = new JLabel(Messages.getString("T_RED"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		redprobText = new JTextField(Integer.toString(Utils.RED_PROB));
		genesPanel.add(redprobText);
		redcostText = new JTextField(Double.toString(Utils.RED_ENERGY_CONSUMPTION));
		genesPanel.add(redcostText);
		
		label = new JLabel(Messages.getString("T_FIRE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		fireprobText = new JTextField(Integer.toString(Utils.FIRE_PROB));
		genesPanel.add(fireprobText);
		firecostText = new JTextField(Double.toString(Utils.FIRE_ENERGY_CONSUMPTION));
		genesPanel.add(firecostText);
		
		label = new JLabel(Messages.getString("T_ORANGE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		orangeprobText = new JTextField(Integer.toString(Utils.ORANGE_PROB));
		genesPanel.add(orangeprobText);
		orangecostText = new JTextField(Double.toString(Utils.ORANGE_ENERGY_CONSUMPTION));
		genesPanel.add(orangecostText);
		
		label = new JLabel(Messages.getString("T_MAROON"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		maroonprobText = new JTextField(Integer.toString(Utils.MAROON_PROB));
		genesPanel.add(maroonprobText);
		marooncostText = new JTextField(Double.toString(Utils.MAROON_ENERGY_CONSUMPTION));
		genesPanel.add(marooncostText);
		
		label = new JLabel(Messages.getString("T_PINK"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		pinkprobText = new JTextField(Integer.toString(Utils.PINK_PROB));
		genesPanel.add(pinkprobText);
		pinkcostText = new JTextField(Double.toString(Utils.PINK_ENERGY_CONSUMPTION));
		genesPanel.add(pinkcostText);
		
		label = new JLabel(Messages.getString("T_CREAM"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		creamprobText = new JTextField(Integer.toString(Utils.CREAM_PROB));
		genesPanel.add(creamprobText);
		creamcostText = new JTextField(Double.toString(Utils.CREAM_ENERGY_CONSUMPTION));
		genesPanel.add(creamcostText);
		
		label = new JLabel(Messages.getString("T_CORAL"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		coralprobText = new JTextField(Integer.toString(Utils.CORAL_PROB));
		genesPanel.add(coralprobText);
		coralcostText = new JTextField(Double.toString(Utils.CORAL_ENERGY_CONSUMPTION));
		genesPanel.add(coralcostText);
		
		label = new JLabel(Messages.getString("T_GREEN"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		greenprobText = new JTextField(Integer.toString(Utils.GREEN_PROB));
		genesPanel.add(greenprobText);
		greencostText = new JTextField(Double.toString(Utils.GREEN_ENERGY_CONSUMPTION));
		genesPanel.add(greencostText);
		
		label = new JLabel(Messages.getString("T_FOREST"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		forestprobText = new JTextField(Integer.toString(Utils.FOREST_PROB));
		genesPanel.add(forestprobText);
		forestcostText = new JTextField(Double.toString(Utils.FOREST_ENERGY_CONSUMPTION));
		genesPanel.add(forestcostText);
		
		label = new JLabel(Messages.getString("T_SPRING"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		springprobText = new JTextField(Integer.toString(Utils.SPRING_PROB));
		genesPanel.add(springprobText);
		springcostText = new JTextField(Double.toString(Utils.SPRING_ENERGY_CONSUMPTION));
		genesPanel.add(springcostText);
		
		label = new JLabel(Messages.getString("T_LIME"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		limeprobText = new JTextField(Integer.toString(Utils.LIME_PROB));
		genesPanel.add(limeprobText);
		limecostText = new JTextField(Double.toString(Utils.LIME_ENERGY_CONSUMPTION));
		genesPanel.add(limecostText);
		
		label = new JLabel(Messages.getString("T_C4"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		c4probText = new JTextField(Integer.toString(Utils.C4_PROB));
		genesPanel.add(c4probText);
		c4costText = new JTextField(Double.toString(Utils.C4_ENERGY_CONSUMPTION));
		genesPanel.add(c4costText);
		
		label = new JLabel(Messages.getString("T_JADE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		jadeprobText = new JTextField(Integer.toString(Utils.JADE_PROB));
		genesPanel.add(jadeprobText);
		jadecostText = new JTextField(Double.toString(Utils.JADE_ENERGY_CONSUMPTION));
		genesPanel.add(jadecostText);
		
		label = new JLabel(Messages.getString("T_GRASS"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		grassprobText = new JTextField(Integer.toString(Utils.GRASS_PROB));
		genesPanel.add(grassprobText);
		grasscostText = new JTextField(Double.toString(Utils.GRASS_ENERGY_CONSUMPTION));
		genesPanel.add(grasscostText);
		
		label = new JLabel(Messages.getString("T_BARK"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		barkprobText = new JTextField(Integer.toString(Utils.BARK_PROB));
		genesPanel.add(barkprobText);
		barkcostText = new JTextField(Double.toString(Utils.BARK_ENERGY_CONSUMPTION));
		genesPanel.add(barkcostText);
		
		label = new JLabel(Messages.getString("T_BLUE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		blueprobText = new JTextField(Integer.toString(Utils.BLUE_PROB));
		genesPanel.add(blueprobText);
		bluecostText = new JTextField(Double.toString(Utils.BLUE_ENERGY_CONSUMPTION));
		genesPanel.add(bluecostText);
		
		label = new JLabel(Messages.getString("T_SKY"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		skyprobText = new JTextField(Integer.toString(Utils.SKY_PROB));
		genesPanel.add(skyprobText);
		skycostText = new JTextField(Double.toString(Utils.SKY_ENERGY_CONSUMPTION));
		genesPanel.add(skycostText);
		
		label = new JLabel(Messages.getString("T_OLIVE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		oliveprobText = new JTextField(Integer.toString(Utils.OLIVE_PROB));
		genesPanel.add(oliveprobText);
		olivecostText = new JTextField(Double.toString(Utils.OLIVE_ENERGY_CONSUMPTION));
		genesPanel.add(olivecostText);
		
		label = new JLabel(Messages.getString("T_OCHRE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		ochreprobText = new JTextField(Integer.toString(Utils.OCHRE_PROB));
		genesPanel.add(ochreprobText);
		ochrecostText = new JTextField(Double.toString(Utils.OCHRE_ENERGY_CONSUMPTION));
		genesPanel.add(ochrecostText);
		
		return genesPanel;
	}
		
	protected JPanel setGenes2Tab() {
		JPanel genesPanel = new JPanel();
		genesPanel.setLayout(new GridLayout(20,3));
		JLabel label;

		genesPanel.add(new JLabel(Messages.getString("T_COLOR2"),SwingConstants.CENTER)); //$NON-NLS-1$
		genesPanel.add(new JLabel(Messages.getString("T_PROBABILITY"),SwingConstants.CENTER)); //$NON-NLS-1$
		genesPanel.add(new JLabel(Messages.getString("T_COST"),SwingConstants.CENTER)); //$NON-NLS-1$
		
		label = new JLabel(Messages.getString("T_CYAN"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		cyanprobText = new JTextField(Integer.toString(Utils.CYAN_PROB));
		genesPanel.add(cyanprobText);
		cyancostText = new JTextField(Double.toString(Utils.CYAN_ENERGY_CONSUMPTION));
		genesPanel.add(cyancostText);
		
		label = new JLabel(Messages.getString("T_TEAL"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		tealprobText = new JTextField(Integer.toString(Utils.TEAL_PROB));
		genesPanel.add(tealprobText);
		tealcostText = new JTextField(Double.toString(Utils.TEAL_ENERGY_CONSUMPTION));
		genesPanel.add(tealcostText);
		
		label = new JLabel(Messages.getString("T_WHITE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		whiteprobText = new JTextField(Integer.toString(Utils.WHITE_PROB));
		genesPanel.add(whiteprobText);
		whitecostText = new JTextField(Double.toString(Utils.WHITE_ENERGY_CONSUMPTION));
		genesPanel.add(whitecostText);
		
		label = new JLabel(Messages.getString("T_PLAGUE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		plagueprobText = new JTextField(Integer.toString(Utils.PLAGUE_PROB));
		genesPanel.add(plagueprobText);
		plaguecostText = new JTextField(Double.toString(Utils.PLAGUE_ENERGY_CONSUMPTION));
		genesPanel.add(plaguecostText);
		
		label = new JLabel(Messages.getString("T_MINT"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		mintprobText = new JTextField(Integer.toString(Utils.MINT_PROB));
		genesPanel.add(mintprobText);
		mintcostText = new JTextField(Double.toString(Utils.MINT_ENERGY_CONSUMPTION));
		genesPanel.add(mintcostText);
		
		label = new JLabel(Messages.getString("T_MAGENTA"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		magentaprobText = new JTextField(Integer.toString(Utils.MAGENTA_PROB));
		genesPanel.add(magentaprobText);
		magentacostText = new JTextField(Double.toString(Utils.MAGENTA_ENERGY_CONSUMPTION));
		genesPanel.add(magentacostText);
		
		label = new JLabel(Messages.getString("T_ROSE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		roseprobText = new JTextField(Integer.toString(Utils.ROSE_PROB));
		genesPanel.add(roseprobText);
		rosecostText = new JTextField(Double.toString(Utils.ROSE_ENERGY_CONSUMPTION));
		genesPanel.add(rosecostText);
		
		label = new JLabel(Messages.getString("T_VIOLET"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		violetprobText = new JTextField(Integer.toString(Utils.VIOLET_PROB));
		genesPanel.add(violetprobText);
		violetcostText = new JTextField(Double.toString(Utils.VIOLET_ENERGY_CONSUMPTION));
		genesPanel.add(violetcostText);
		
		label = new JLabel(Messages.getString("T_GRAY"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		grayprobText = new JTextField(Integer.toString(Utils.GRAY_PROB));
		genesPanel.add(grayprobText);
		graycostText = new JTextField(Double.toString(Utils.GRAY_ENERGY_CONSUMPTION));
		genesPanel.add(graycostText);
		
		label = new JLabel(Messages.getString("T_LILAC"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		lilacprobText = new JTextField(Integer.toString(Utils.LILAC_PROB));
		genesPanel.add(lilacprobText);
		lilaccostText = new JTextField(Double.toString(Utils.LILAC_ENERGY_CONSUMPTION));
		genesPanel.add(lilaccostText);
		
		label = new JLabel(Messages.getString("T_SPIKE"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		spikeprobText = new JTextField(Integer.toString(Utils.SPIKE_PROB));
		genesPanel.add(spikeprobText);
		spikecostText = new JTextField(Double.toString(Utils.SPIKE_ENERGY_CONSUMPTION));
		genesPanel.add(spikecostText);
		
		label = new JLabel(Messages.getString("T_SILVER"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		silverprobText = new JTextField(Integer.toString(Utils.SILVER_PROB));
		genesPanel.add(silverprobText);
		silvercostText = new JTextField(Double.toString(Utils.SILVER_ENERGY_CONSUMPTION));
		genesPanel.add(silvercostText);
	
		label = new JLabel(Messages.getString("T_YELLOW"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		yellowprobText = new JTextField(Integer.toString(Utils.YELLOW_PROB));
		genesPanel.add(yellowprobText);
		yellowcostText = new JTextField(Double.toString(Utils.YELLOW_ENERGY_CONSUMPTION));
		genesPanel.add(yellowcostText);
		
		label = new JLabel(Messages.getString("T_AUBURN"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		auburnprobText = new JTextField(Integer.toString(Utils.AUBURN_PROB));
		genesPanel.add(auburnprobText);
		auburncostText = new JTextField(Double.toString(Utils.AUBURN_ENERGY_CONSUMPTION));
		genesPanel.add(auburncostText);
		
		label = new JLabel(Messages.getString("T_INDIGO"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		indigoprobText = new JTextField(Integer.toString(Utils.INDIGO_PROB));
		genesPanel.add(indigoprobText);
		indigocostText = new JTextField(Double.toString(Utils.INDIGO_ENERGY_CONSUMPTION));
		genesPanel.add(indigocostText);
		
		label = new JLabel(Messages.getString("T_BLOND"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		blondprobText = new JTextField(Integer.toString(Utils.BLOND_PROB));
		genesPanel.add(blondprobText);
		blondcostText = new JTextField(Double.toString(Utils.BLOND_ENERGY_CONSUMPTION));
		genesPanel.add(blondcostText);
		
		label = new JLabel(Messages.getString("T_DARKGRAY"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		darkgrayprobText = new JTextField(Integer.toString(Utils.DARKGRAY_PROB));
		genesPanel.add(darkgrayprobText);
		darkgraycostText = new JTextField(Double.toString(Utils.DARKGRAY_ENERGY_CONSUMPTION));
		genesPanel.add(darkgraycostText);
		
		label = new JLabel(Messages.getString("T_DARK"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		darkprobText = new JTextField(Integer.toString(Utils.DARK_PROB));
		genesPanel.add(darkprobText);
		darkcostText = new JTextField(Double.toString(Utils.DARK_ENERGY_CONSUMPTION));
		genesPanel.add(darkcostText);
		
		label = new JLabel(Messages.getString("T_GOLD"),SwingConstants.CENTER); //$NON-NLS-1$
		genesPanel.add(label);
		goldprobText = new JTextField(Integer.toString(Utils.GOLD_PROB));
		genesPanel.add(goldprobText);
		goldcostText = new JTextField(Double.toString(Utils.GOLD_DIVISOR));
		genesPanel.add(goldcostText);
		
		return genesPanel;
	}
	
	void checkParams() {
		int i;
		double d;
		if (Messages.getLocaleIndex() != localeCombo.getSelectedIndex()) {
			Messages.setLocale(localeCombo.getSelectedIndex());
			mainWindow.changeLocale();
		}
		try {
			i = Integer.parseInt(widthText.getText());
			if (i > 0) Utils.WORLD_WIDTH = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(heightText.getText());
			if (i > 0) Utils.WORLD_HEIGHT = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(delayText.getText());
			if (i > 0) {
				Utils.DELAY = i;
			}
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		if (autoBackupsCheck.isSelected()) {
			Utils.AUTO_BACKUP = true;
		} else {
			Utils.AUTO_BACKUP = false;
		}
		try {
			i = Integer.parseInt(backupDelayText.getText());
			if (i > 0) {
				Utils.BACKUP_DELAY = i;
			}
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(initialO2Text.getText());
			if (d >= 0) Utils.INITIAL_O2 = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(initialCO2Text.getText());
			if (d >= 0) Utils.INITIAL_CO2 = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(rubbingText.getText());
			if (d >= 0 && d <= 1) Utils.RUBBING = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(elasticityText.getText());
			if (d >= 0 && d <= 1) Utils.ELASTICITY = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(initialnumberText.getText());
			if (i >= 0) Utils.INITIAL_ORGANISMS = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(initialenergyText.getText());
			if (i >= 0) Utils.INITIAL_ENERGY = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(initialcomplexityText.getText());
			if (i > 0) Utils.INITIAL_COMPLEXITY = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(maxageText.getText());
			if (i >= 0) Utils.MAX_AGE = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(agedivisorText.getText());
			if (i > 0) Utils.AGE_DIVISOR = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(mutationrateText.getText());
			if (d >= 0 && d <= 1) Utils.MUTATION_RATE = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(redcostText.getText());
			if (d >= 0) Utils.RED_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(greencostText.getText());
			if (d > 0) Utils.GREEN_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(bluecostText.getText());
			if (d >= 0) Utils.BLUE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(cyancostText.getText());
			if (d >= 0) Utils.CYAN_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(whitecostText.getText());
			if (d >= 0) Utils.WHITE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(graycostText.getText());
			if (d >= 0) Utils.GRAY_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(yellowcostText.getText());
			if (d >= 0) Utils.YELLOW_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(magentacostText.getText());
			if (d >= 0) Utils.MAGENTA_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(pinkcostText.getText());
			if (d >= 0) Utils.PINK_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(coralcostText.getText());
			if (d >= 0) Utils.CORAL_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(orangecostText.getText());
			if (d >= 0) Utils.ORANGE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(forestcostText.getText());
			if (d > 0) Utils.FOREST_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(springcostText.getText());
			if (d > 0) Utils.SPRING_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(limecostText.getText());
			if (d > 0) Utils.LIME_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(c4costText.getText());
			if (d > 0) Utils.C4_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(jadecostText.getText());
			if (d > 0) Utils.JADE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(grasscostText.getText());
			if (d > 0) Utils.GRASS_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(barkcostText.getText());
			if (d > 0) Utils.BARK_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(violetcostText.getText());
			if (d >= 0) Utils.VIOLET_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(tealcostText.getText());
			if (d >= 0) Utils.TEAL_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(marooncostText.getText());
			if (d >= 0) Utils.MAROON_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(olivecostText.getText());
			if (d >= 0) Utils.OLIVE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(mintcostText.getText());
			if (d >= 0) Utils.MINT_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(creamcostText.getText());
			if (d >= 0) Utils.CREAM_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(rosecostText.getText());
			if (d >= 0) Utils.ROSE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(darkcostText.getText());
			if (d >= 0) Utils.DARK_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(ochrecostText.getText());
			if (d >= 0) Utils.OCHRE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(skycostText.getText());
			if (d >= 0) Utils.SKY_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(lilaccostText.getText());
			if (d >= 0) Utils.LILAC_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(silvercostText.getText());
			if (d >= 0) Utils.SILVER_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(firecostText.getText());
			if (d >= 0) Utils.FIRE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(darkgraycostText.getText());
			if (d >= 0) Utils.DARKGRAY_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(goldcostText.getText());
			if (d > 0) Utils.GOLD_DIVISOR = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(blondcostText.getText());
			if (d >= 0) Utils.BLOND_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(auburncostText.getText());
			if (d > 0) Utils.AUBURN_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(plaguecostText.getText());
			if (d >= 0) Utils.PLAGUE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(spikecostText.getText());
			if (d >= 0) Utils.SPIKE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(indigocostText.getText());
			if (d >= 0) Utils.INDIGO_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(redprobText.getText());
			if (i >= 0) Utils.RED_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(greenprobText.getText());
			if (i > 0) Utils.GREEN_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(blueprobText.getText());
			if (i >= 0) Utils.BLUE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(cyanprobText.getText());
			if (i >= 0) Utils.CYAN_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(whiteprobText.getText());
			if (i >= 0) Utils.WHITE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(grayprobText.getText());
			if (i >= 0) Utils.GRAY_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(yellowprobText.getText());
			if (i >= 0) Utils.YELLOW_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(magentaprobText.getText());
			if (i >= 0) Utils.MAGENTA_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(pinkprobText.getText());
			if (i >= 0) Utils.PINK_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(coralprobText.getText());
			if (i >= 0) Utils.CORAL_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(orangeprobText.getText());
			if (i >= 0) Utils.ORANGE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(forestprobText.getText());
			if (i >= 0) Utils.FOREST_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(springprobText.getText());
			if (i >= 0) Utils.SPRING_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(limeprobText.getText());
			if (i >= 0) Utils.LIME_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(c4probText.getText());
			if (i >= 0) Utils.C4_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(jadeprobText.getText());
			if (i >= 0) Utils.JADE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(grassprobText.getText());
			if (i >= 0) Utils.GRASS_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(barkprobText.getText());
			if (i >= 0) Utils.BARK_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(violetprobText.getText());
			if (i >= 0) Utils.VIOLET_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(tealprobText.getText());
			if (i >= 0) Utils.TEAL_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(maroonprobText.getText());
			if (i >= 0) Utils.MAROON_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(oliveprobText.getText());
			if (i >= 0) Utils.OLIVE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(mintprobText.getText());
			if (i >= 0) Utils.MINT_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(creamprobText.getText());
			if (i >= 0) Utils.CREAM_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(roseprobText.getText());
			if (i >= 0) Utils.ROSE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(darkprobText.getText());
			if (i >= 0) Utils.DARK_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(ochreprobText.getText());
			if (i >= 0) Utils.OCHRE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(skyprobText.getText());
			if (i >= 0) Utils.SKY_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(lilacprobText.getText());
			if (i >= 0) Utils.LILAC_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(silverprobText.getText());
			if (i >= 0) Utils.SILVER_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(fireprobText.getText());
			if (i >= 0) Utils.FIRE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(darkgrayprobText.getText());
			if (i >= 0) Utils.DARKGRAY_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(goldprobText.getText());
			if (i >= 0) Utils.GOLD_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(blondprobText.getText());
			if (i >= 0) Utils.BLOND_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(auburnprobText.getText());
			if (i >= 0) Utils.AUBURN_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(plagueprobText.getText());
			if (i >= 0) Utils.PLAGUE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(spikeprobText.getText());
			if (i >= 0) Utils.SPIKE_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(indigoprobText.getText());
			if (i >= 0) Utils.INDIGO_PROB = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(segmentcostText.getText());
			if (i > 0) Utils.SEGMENT_COST_DIVISOR = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(drainText.getText());
			if (i > 0) Utils.DRAIN_SUBS_DIVISOR = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		
		try {
			i = Integer.parseInt(greenenergyText.getText());
			if (i > 0) Utils.GREEN_OBTAINED_ENERGY_DIVISOR = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(organicenergyText.getText());
			if (d >= 0) Utils.ORGANIC_OBTAINED_ENERGY = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(organicsubsText.getText());
			if (d >= 0 && d <= 1) Utils.ORGANIC_SUBS_PRODUCED = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(creamorganicsubsText.getText());
			if (d >= 0 && d <= 1) Utils.CREAM_ORGANIC_SUBS_PRODUCED = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(healingText.getText());
			if (i >= 1) Utils.HEALING = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(immunesystemText.getText());
			if (i >= 1) Utils.IMMUNE_SYSTEM = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(indigodivisorText.getText());
			if (i > 0) Utils.INDIGO_DIVISOR = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(crowdedlimecostText.getText());
			if (d > 0) Utils.CROWDEDLIME_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(experiencecostText.getText());
			if (d >= 0) Utils.EXPERIENCE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(dodgecostText.getText());
			if (d >= 0) Utils.DODGE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			i = Integer.parseInt(darkjadedelayText.getText());
			if (i >= 1) Utils.DARKJADE_DELAY = i;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(viruscostText.getText());
			if (d >= 0) Utils.VIRUS_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(scourgecostText.getText());
			if (d >= 0) Utils.SCOURGE_ENERGY_CONSUMPTION = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		try {
			d = Double.parseDouble(decayenergyText.getText());
			if (d >= 0) Utils.DECAY_ENERGY = d;
		} catch (NumberFormatException ex) {
			// Keep old value if there is a problem
		}
		if (hardwareNoneRadio.isSelected()) {
			Utils.setHardwareAcceleration(0);
		}
		if (hardwareOpenGLRadio.isSelected()) {
			if (hardwareFBObjectCheck.isSelected()) {
				if (Utils.HARDWARE_ACCELERATION < 4)
					Utils.setHardwareAcceleration(4);
			} else {
				if (Utils.HARDWARE_ACCELERATION != 0 && Utils.HARDWARE_ACCELERATION > 3)
					Utils.setHardwareAcceleration(1);
			}
		}
	}
}
