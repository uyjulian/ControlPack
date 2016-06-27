package ctrlpack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ControlPackOptions {
	
	private static File optionsFile;

	public static Map<ControlPackEnumOptions, Float> floatOptions;
	public static Map<ControlPackEnumOptions, Boolean> booleanOptions;
	public static Map<ControlPackEnumOptions, String> stringOptions;
	public static Map<ControlPackEnumOptions, Integer> intOptions;
	public static Map<ControlPackEnumOptions, Integer> intOptionsMaxValue;
	

	public static ControlPackEnumOptions allOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.AUTOTOOL,
		ControlPackEnumOptions.AUTOTOOLMODE,
		ControlPackEnumOptions.AUTOBLOCK,
		ControlPackEnumOptions.AUTOBLOCKMODE,
		ControlPackEnumOptions.AUTOSWORD,
		ControlPackEnumOptions.LOOKBEHINDBACK,
		ControlPackEnumOptions.FRONTVIEW, 
		ControlPackEnumOptions.WINDOWRESTORE,
		ControlPackEnumOptions.SMARTFURNACE,
		ControlPackEnumOptions.HOLDTOATTACK,
		//ControlPackEnumOptions.LESSRAIN, 
		ControlPackEnumOptions.USECOUNT,
		ControlPackEnumOptions.STATUSLOCATION,
		ControlPackEnumOptions.COORDINATESLOCATION,
		//ControlPackEnumOptions.VOIDFOG,
		ControlPackEnumOptions.CORPSELOCATION
	};
	
	public static ControlPackEnumOptions itemOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.ITEM_SWORDS,
		ControlPackEnumOptions.COORDINATE_FORMAT
	};
	
	public static ControlPackEnumOptions volumeOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.VOLUMERAIN,
		ControlPackEnumOptions.VOLUMEPISTON,
		ControlPackEnumOptions.VOLUMESPLASH,
		ControlPackEnumOptions.VOLUMEWATER,
		ControlPackEnumOptions.VOLUMEDOOR,
		ControlPackEnumOptions.VOLUMEEXPLODE,
		ControlPackEnumOptions.VOLUMEBOW,
		ControlPackEnumOptions.VOLUMEPORTAL,
		ControlPackEnumOptions.VOLUMEEATDRINK,
		ControlPackEnumOptions.VOLUMEANIMALS,
		ControlPackEnumOptions.VOLUMESLIME,
		ControlPackEnumOptions.VOLUMEHIT,
		ControlPackEnumOptions.VOLUMEDIG,
		ControlPackEnumOptions.VOLUMESTEP
	};	
	
	public static ControlPackEnumOptions waypointOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.WAYPOINT1,
		ControlPackEnumOptions.WAYPOINT2,
		ControlPackEnumOptions.WAYPOINT3,
		ControlPackEnumOptions.WAYPOINT4,
		ControlPackEnumOptions.WAYPOINT5
	};

	public static ControlPackEnumOptions waypointNetherOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.WAYPOINTNETHER1,
		ControlPackEnumOptions.WAYPOINTNETHER2,
		ControlPackEnumOptions.WAYPOINTNETHER3,
		ControlPackEnumOptions.WAYPOINTNETHER4,
		ControlPackEnumOptions.WAYPOINTNETHER5
	};
	
	public static ControlPackEnumOptions waypointNameOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.WAYPOINTNAME1,
		ControlPackEnumOptions.WAYPOINTNAME2,
		ControlPackEnumOptions.WAYPOINTNAME3,
		ControlPackEnumOptions.WAYPOINTNAME4,
		ControlPackEnumOptions.WAYPOINTNAME5
	};

	public static ControlPackEnumOptions waypointNetherNameOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.WAYPOINTNETHERNAME1,
		ControlPackEnumOptions.WAYPOINTNETHERNAME2,
		ControlPackEnumOptions.WAYPOINTNETHERNAME3,
		ControlPackEnumOptions.WAYPOINTNETHERNAME4,
		ControlPackEnumOptions.WAYPOINTNETHERNAME5
	};
	
	public static ControlPackEnumOptions waypointHUDOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.WAYPOINTHUD1,
		ControlPackEnumOptions.WAYPOINTHUD2,
		ControlPackEnumOptions.WAYPOINTHUD3,
		ControlPackEnumOptions.WAYPOINTHUD4,
		ControlPackEnumOptions.WAYPOINTHUD5
	};
   
	public static ControlPackEnumOptions waypointNetherHUDOptions[] = new ControlPackEnumOptions[] {
		ControlPackEnumOptions.WAYPOINTNETHERHUD1,
		ControlPackEnumOptions.WAYPOINTNETHERHUD2,
		ControlPackEnumOptions.WAYPOINTNETHERHUD3,
		ControlPackEnumOptions.WAYPOINTNETHERHUD4,
		ControlPackEnumOptions.WAYPOINTNETHERHUD5
	};

	public static void loadOptions() {
		try {
			optionsFile = new File(ControlPackMain.mc.mcDataDir, "controlpack.txt");
			
			// load defaults
			floatOptions = new HashMap<ControlPackEnumOptions, Float>();
			floatOptions.put(ControlPackEnumOptions.VOLUMERAIN, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEPISTON, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMESPLASH, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEWATER, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEDOOR, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEEXPLODE, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEBOW, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEPORTAL, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEEATDRINK, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEANIMALS, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMESLIME, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEHIT, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMEDIG, 1.0F);
			floatOptions.put(ControlPackEnumOptions.VOLUMESTEP, 1.0F);	
			
			stringOptions = new HashMap<ControlPackEnumOptions, String>();
			stringOptions.put(ControlPackEnumOptions.WAYPOINT1, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINT2, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINT3, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINT4, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINT5, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHER1, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHER2, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHER3, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHER4, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHER5, "");
			
			stringOptions.put(ControlPackEnumOptions.ITEM_SWORDS, "");
			stringOptions.put(ControlPackEnumOptions.COORDINATE_FORMAT, "{X}, {Z}, {Y}");

			stringOptions.put(ControlPackEnumOptions.WAYPOINTNAME1, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNAME2, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNAME3, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNAME4, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNAME5, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHERNAME1, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHERNAME2, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHERNAME3, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHERNAME4, "");
			stringOptions.put(ControlPackEnumOptions.WAYPOINTNETHERNAME5, "");
			
			booleanOptions = new HashMap<ControlPackEnumOptions, Boolean>();
			//booleanOptions.put(ControlPackEnumOptions.LESSRAIN, true);
			booleanOptions.put(ControlPackEnumOptions.FRONTVIEW, true);
			booleanOptions.put(ControlPackEnumOptions.WINDOWRESTORE, true);
			booleanOptions.put(ControlPackEnumOptions.SMARTFURNACE, true);
			booleanOptions.put(ControlPackEnumOptions.HOLDTOATTACK, false);
			booleanOptions.put(ControlPackEnumOptions.LOOKBEHINDBACK, true);
			booleanOptions.put(ControlPackEnumOptions.AUTOTOOL, true);
			booleanOptions.put(ControlPackEnumOptions.AUTOSWORD, true);
			booleanOptions.put(ControlPackEnumOptions.AUTOBLOCK, true);
			//booleanOptions.put(ControlPackEnumOptions.VOIDFOG, true);
			booleanOptions.put(ControlPackEnumOptions.USECOUNT, true);
			booleanOptions.put(ControlPackEnumOptions.WELCOMENAG, true);
			//booleanOptions.put(ControlPackEnumOptions.SOUNDMANAGER, true);
			booleanOptions.put(ControlPackEnumOptions.CORPSELOCATION, true);
			booleanOptions.put(ControlPackEnumOptions.LASTFULLSCREEN, false);
			booleanOptions.put(ControlPackEnumOptions.LASTPOSITIONEXISTS, false);
			
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTHUD1, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTHUD2, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTHUD3, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTHUD4, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTHUD5, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTNETHERHUD1, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTNETHERHUD2, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTNETHERHUD3, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTNETHERHUD4, true);
			booleanOptions.put(ControlPackEnumOptions.WAYPOINTNETHERHUD5, true);
			
			intOptions = new HashMap<ControlPackEnumOptions, Integer>();
			intOptions.put(ControlPackEnumOptions.AUTOTOOLMODE, 0);
			intOptions.put(ControlPackEnumOptions.AUTOBLOCKMODE, 0);
			intOptions.put(ControlPackEnumOptions.STATUSLOCATION, 0);
			intOptions.put(ControlPackEnumOptions.COORDINATESLOCATION, 1);
			intOptions.put(ControlPackEnumOptions.LASTBOUNDSX, 0);
			intOptions.put(ControlPackEnumOptions.LASTBOUNDSY, 0);
			intOptions.put(ControlPackEnumOptions.LASTBOUNDSW, 0);
			intOptions.put(ControlPackEnumOptions.LASTBOUNDSH, 0);

			intOptionsMaxValue = new HashMap<ControlPackEnumOptions, Integer>();
			intOptionsMaxValue.put(ControlPackEnumOptions.AUTOTOOLMODE, 3);
			intOptionsMaxValue.put(ControlPackEnumOptions.AUTOBLOCKMODE, 10);
			intOptionsMaxValue.put(ControlPackEnumOptions.STATUSLOCATION, 4);
			intOptionsMaxValue.put(ControlPackEnumOptions.COORDINATESLOCATION, 4);
			
			if(!optionsFile.exists()) {
				return;
			}
			BufferedReader bufferedreader = new BufferedReader(new FileReader(optionsFile));
			for(String s = ""; (s = bufferedreader.readLine()) != null;) {
				try {
					String as[] = s.split(":");
					if (as.length < 2) continue;

						ControlPackEnumOptions option = ControlPackEnumOptions.getOption(as[0]);
						if (option != null) {
							if (option.getIsBool()) {
								booleanOptions.put(option, as[1].equals("true"));
							}
							else if (option.getIsFloat()) {
								floatOptions.put(option, Float.parseFloat(as[1]));
							}
							else if (option.getIsString()) {
								stringOptions.put(option, as[1]);
							}
							else {
								intOptions.put(option, Integer.parseInt(as[1]));
							}
						}

				}
				catch(Exception ex) {
					System.out.println((new StringBuilder("Skipping bad controlpack option: ")).append(s).append(" --> ").append(ex.toString()).toString());
				}
			}

			bufferedreader.close();
		}
		catch(Exception exception)
		{
			System.out.println("Failed to load options");
			exception.printStackTrace();
		}
	}	
	
	public static void saveOptions() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(optionsFile));
			
			Enumeration<ControlPackEnumOptions> keys = Collections.enumeration(floatOptions.keySet());
			while(keys.hasMoreElements()) {
			  Object key = keys.nextElement();
			  ControlPackEnumOptions option = (ControlPackEnumOptions) key;
			  Float value = floatOptions.get(option);
			  printwriter.println((new StringBuilder(option.getName()).append(":").append(value)).toString());
			}
			
			keys = Collections.enumeration(booleanOptions.keySet());
			while(keys.hasMoreElements()) {
			  Object key = keys.nextElement();
			  ControlPackEnumOptions option = (ControlPackEnumOptions) key;
			  Boolean value = booleanOptions.get(option);
			  printwriter.println((new StringBuilder(option.getName()).append(":").append(value)).toString());
			}
			
			keys = Collections.enumeration(intOptions.keySet());
			while(keys.hasMoreElements()) {
			  Object key = keys.nextElement();
			  ControlPackEnumOptions option = (ControlPackEnumOptions) key;
			  int value = intOptions.get(option);
			  printwriter.println((new StringBuilder(option.getName()).append(":").append(value)).toString());
			}
			
			keys = Collections.enumeration(stringOptions.keySet());
			while(keys.hasMoreElements()) {
			  Object key = keys.nextElement();
			  ControlPackEnumOptions option = (ControlPackEnumOptions) key;
			  String value = stringOptions.get(option);
			  printwriter.println((new StringBuilder(option.getName()).append(":").append(value)).toString());
			}
			
			printwriter.close();
		}
		catch(Exception exception) {
			System.out.println("Failed to save controlpack options");
			exception.printStackTrace();
		}
	}	
}
