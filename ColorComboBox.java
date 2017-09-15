import java.awt.Color;

import javax.swing.JComboBox;

public class ColorComboBox extends JComboBox {
	private static final long serialVersionUID = Utils.VERSION;
	private static final String[] colorValues = {Messages.getString("T_RED"),Messages.getString("T_FIRE"),  //$NON-NLS-1$//$NON-NLS-2$
		Messages.getString("T_ORANGE"),Messages.getString("T_MAROON"),Messages.getString("T_PINK"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		Messages.getString("T_CREAM"),Messages.getString("T_CORAL"),Messages.getString("T_GREEN"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_FOREST"),Messages.getString("T_SPRING"),Messages.getString("T_LIME"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_C4"),Messages.getString("T_JADE"),Messages.getString("T_GRASS"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_BARK"),Messages.getString("T_BLUE"),Messages.getString("T_SKY"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_OLIVE"),Messages.getString("T_OCHRE"),Messages.getString("T_CYAN"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_TEAL"),Messages.getString("T_WHITE"),Messages.getString("T_PLAGUE"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_MINT"),Messages.getString("T_MAGENTA"),Messages.getString("T_ROSE"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	    Messages.getString("T_VIOLET"),Messages.getString("T_GRAY"),Messages.getString("T_LILAC"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$  
	    Messages.getString("T_SPIKE"),Messages.getString("T_SILVER"),Messages.getString("T_YELLOW"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$  
	    Messages.getString("T_AUBURN"),Messages.getString("T_INDIGO"),Messages.getString("T_BLOND"),  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$   
	    Messages.getString("T_DARKGRAY"),Messages.getString("T_DARK"),Messages.getString("T_GOLD")};  //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$  
	
	public ColorComboBox(Color c) {
		super(colorValues);
		setSelectedColor(c);
	}
	
	public Color getSelectedColor() {
		switch (getSelectedIndex()) {
		case 0: return Color.RED;
		case 1: return Utils.ColorFIRE;
		case 2: return Color.ORANGE;
		case 3: return Utils.ColorMAROON;
		case 4: return Color.PINK;
		case 5: return Utils.ColorCREAM;
		case 6: return Utils.ColorCORAL;
		case 7: return Color.GREEN;
		case 8: return Utils.ColorFOREST;
		case 9: return Utils.ColorSPRING;
		case 10: return Utils.ColorLIME;
		case 11: return Utils.ColorC4;
		case 12: return Utils.ColorJADE;
		case 13: return Utils.ColorGRASS;
		case 14: return Utils.ColorBARK;
		case 15: return Color.BLUE;
		case 16: return Utils.ColorSKY;
		case 17: return Utils.ColorOLIVE;
		case 18: return Utils.ColorOCHRE;
		case 19: return Color.CYAN;
		case 20: return Utils.ColorTEAL;
		case 21: return Color.WHITE;
		case 22: return Utils.ColorPLAGUE;
		case 23: return Utils.ColorMINT;
		case 24: return Color.MAGENTA;
		case 25: return Utils.ColorROSE;
		case 26: return Utils.ColorVIOLET;
		case 27: return Color.GRAY;
		case 28: return Utils.ColorLILAC;
		case 29: return Utils.ColorSPIKE;
		case 30: return Color.LIGHT_GRAY;
		case 31: return Color.YELLOW;
		case 32: return Utils.ColorAUBURN;
		case 33: return Utils.ColorINDIGO;
		case 34: return Utils.ColorBLOND;
		case 35: return Color.DARK_GRAY;
		case 36: return Utils.ColorDARK;
		case 37: return Utils.ColorGOLD;
		case 38: return Utils.ColorOLDBARK;
		case 39: return Utils.ColorDARKJADE;
		case 40: return Utils.ColorPOISONEDJADE;
		case 41: return Utils.ColorDARKFIRE;
		case 42: return Utils.ColorDARKLILAC;
		case 43: return Utils.ColorDEEPSKY;
		case 44: return Utils.ColorDARKOLIVE;
		case 45: return Utils.ColorSPIKEPOINT;
		case 46: return Utils.ColorICE;
		case 47: return Utils.ColorLIGHT_BLUE;
		case 48: return Utils.ColorLIGHTBROWN;
		case 49: return Utils.ColorGREENBROWN;
		case 50: return Utils.ColorBROKEN;
		case 51: return Utils.ColorDEADBARK;
		default: return Utils.ColorBROWN;
		}
	}
	
	public void setSelectedColor(Color c) {
		if (c.equals(Color.RED)) setSelectedIndex(0);
		if (c.equals(Utils.ColorFIRE)) setSelectedIndex(1);
		if (c.equals(Color.ORANGE)) setSelectedIndex(2);
		if (c.equals(Utils.ColorMAROON)) setSelectedIndex(3);
		if (c.equals(Color.PINK)) setSelectedIndex(4);
		if (c.equals(Utils.ColorCREAM)) setSelectedIndex(5);
		if (c.equals(Utils.ColorCORAL)) setSelectedIndex(6);
		if (c.equals(Color.GREEN)) setSelectedIndex(7);
		if (c.equals(Utils.ColorFOREST)) setSelectedIndex(8);
		if (c.equals(Utils.ColorSPRING)) setSelectedIndex(9);
		if (c.equals(Utils.ColorLIME)) setSelectedIndex(10);
		if (c.equals(Utils.ColorC4)) setSelectedIndex(11);
		if (c.equals(Utils.ColorJADE)) setSelectedIndex(12);
		if (c.equals(Utils.ColorGRASS)) setSelectedIndex(13);
		if (c.equals(Utils.ColorBARK)) setSelectedIndex(14);
		if (c.equals(Color.BLUE)) setSelectedIndex(15);
		if (c.equals(Utils.ColorSKY)) setSelectedIndex(16);
		if (c.equals(Utils.ColorOLIVE)) setSelectedIndex(17);
		if (c.equals(Utils.ColorOCHRE)) setSelectedIndex(18);
		if (c.equals(Color.CYAN)) setSelectedIndex(19);
		if (c.equals(Utils.ColorTEAL)) setSelectedIndex(20);
		if (c.equals(Color.WHITE)) setSelectedIndex(21);
		if (c.equals(Utils.ColorPLAGUE)) setSelectedIndex(22);
		if (c.equals(Utils.ColorMINT)) setSelectedIndex(23);
		if (c.equals(Color.MAGENTA)) setSelectedIndex(24);
		if (c.equals(Utils.ColorROSE)) setSelectedIndex(25);
		if (c.equals(Utils.ColorVIOLET)) setSelectedIndex(26);
		if (c.equals(Color.GRAY)) setSelectedIndex(27);
		if (c.equals(Utils.ColorLILAC)) setSelectedIndex(28);
		if (c.equals(Utils.ColorSPIKE)) setSelectedIndex(29);
		if (c.equals(Color.LIGHT_GRAY)) setSelectedIndex(30);
		if (c.equals(Color.YELLOW)) setSelectedIndex(31);
		if (c.equals(Utils.ColorAUBURN)) setSelectedIndex(32);
		if (c.equals(Utils.ColorINDIGO)) setSelectedIndex(33);
		if (c.equals(Utils.ColorBLOND)) setSelectedIndex(34);
		if (c.equals(Color.DARK_GRAY)) setSelectedIndex(35);
		if (c.equals(Utils.ColorDARK)) setSelectedIndex(36);
		if (c.equals(Utils.ColorGOLD)) setSelectedIndex(37);
		if (c.equals(Utils.ColorOLDBARK)) setSelectedIndex(38);
		if (c.equals(Utils.ColorDARKJADE)) setSelectedIndex(39);
		if (c.equals(Utils.ColorPOISONEDJADE)) setSelectedIndex(40);
		if (c.equals(Utils.ColorDARKFIRE)) setSelectedIndex(41);
		if (c.equals(Utils.ColorDARKLILAC)) setSelectedIndex(42);
		if (c.equals(Utils.ColorDEEPSKY)) setSelectedIndex(43);
		if (c.equals(Utils.ColorDARKOLIVE)) setSelectedIndex(44);
		if (c.equals(Utils.ColorSPIKEPOINT)) setSelectedIndex(45);
		if (c.equals(Utils.ColorICE)) setSelectedIndex(46);
		if (c.equals(Utils.ColorLIGHT_BLUE)) setSelectedIndex(47);
		if (c.equals(Utils.ColorLIGHTBROWN)) setSelectedIndex(48);
		if (c.equals(Utils.ColorGREENBROWN)) setSelectedIndex(49);
		if (c.equals(Utils.ColorBROKEN)) setSelectedIndex(50);
		if (c.equals(Utils.ColorDEADBARK)) setSelectedIndex(51);
	}
}
