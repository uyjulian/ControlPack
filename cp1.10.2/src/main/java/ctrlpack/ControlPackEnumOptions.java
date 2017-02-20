/* Copyright (c) 2011-2016 Julian Uy, Dave Reed
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package ctrlpack;

public enum ControlPackEnumOptions {
	LESSRAIN("options.lessrain", false, true), // dead, but left in here just so the enum is stable...
	LOOKBEHINDBACK("options.lookbehind-withback", false, true),
	FRONTVIEW("options.front3rdperson", false, true),
	WINDOWRESTORE("options.windowrestoration", false, true),
	SMARTFURNACE("options.smartfurnace", false, true),
	HOLDTOATTACK("options.holdtoattack", false, true),
	SOUNDMANAGER("options.soundmanager", false, true),
	CORPSELOCATION("options.corpselocation", false, true),

	VOLUMERAIN("options.volume_rain", true, false),
	VOLUMEPISTON("options.volume_piston", true, false),
	VOLUMESPLASH("options.volume_splash", true, false),
	VOLUMEWATER("options.volume_water", true, false),
	VOLUMEDOOR("options.volume_door", true, false),
	VOLUMEEXPLODE("options.volume_explode", true, false),
	VOLUMEBOW("options.volume_bow", true, false),
	VOLUMEPORTAL("options.volume_portal", true, false),
	VOLUMEEATDRINK("options.volume_eatdrink", true, false),
	// 5.4
	VOLUMEANIMALS("options.volume_animals", true, false),
	VOLUMESLIME("options.volume_slime", true, false),
	VOLUMEHIT("options.volume_hit", true, false),
	VOLUMEDIG("options.volume_dig", true, false),
	VOLUMESTEP("options.volume_step", true, false),
	
	
	AUTOTOOL("options.autotool", false, true),
	AUTOTOOLMODE("options.autotoolmode", false, false), // int
	AUTOTOOLSWORD("options.autotoolsword", false, true),
	AUTOBLOCKMODE("options.autoblockmode", false, false), // int
	AUTOSWORD("options.autosword", false, true),
	AUTOBLOCK("options.autoblock", false, true),
	WELCOMENAG("options.welcomenag", false, true),
	VOIDFOG("options.voidfog", false, true), // dead, but left in here just so the enum is stable...
	USECOUNT("options.usecount", false, true),
	UPDATECHECK("options.updatecheck", false, true),
	STATUSLOCATION("options.statuslocation", false, false), // int
	COORDINATESLOCATION("options.coordinateslocation", false, false), // int

	ITEM_SWORDS("options.item_swords", false, false, true), // string
	COORDINATE_FORMAT("options.coordinateformat", false, false, true), // string
	
	WAYPOINT1("options.waypoint1", false, false, true), // string
	WAYPOINT2("options.waypoint2", false, false, true),
	WAYPOINT3("options.waypoint3", false, false, true),
	WAYPOINT4("options.waypoint4", false, false, true),
	WAYPOINT5("options.waypoint5", false, false, true),
	
	WAYPOINTNETHER1("options.waypointnether1", false, false, true), // string
	WAYPOINTNETHER2("options.waypointnether2", false, false, true),
	WAYPOINTNETHER3("options.waypointnether3", false, false, true),
	WAYPOINTNETHER4("options.waypointnether4", false, false, true),
	WAYPOINTNETHER5("options.waypointnether5", false, false, true),

	WAYPOINTNAME1("options.waypointname1", false, false, true), // string
	WAYPOINTNAME2("options.waypointname2", false, false, true),
	WAYPOINTNAME3("options.waypointname3", false, false, true),
	WAYPOINTNAME4("options.waypointname4", false, false, true),
	WAYPOINTNAME5("options.waypointname5", false, false, true),
	
	WAYPOINTNETHERNAME1("options.waypointnethername1", false, false, true), // string
	WAYPOINTNETHERNAME2("options.waypointnethername2", false, false, true),
	WAYPOINTNETHERNAME3("options.waypointnethername3", false, false, true),
	WAYPOINTNETHERNAME4("options.waypointnethername4", false, false, true),
	WAYPOINTNETHERNAME5("options.waypointnethername5", false, false, true),
	
	WAYPOINTHUD1("options.waypointhud1", false, true), // bool
	WAYPOINTHUD2("options.waypointhud2", false, true),
	WAYPOINTHUD3("options.waypointhud3", false, true),
	WAYPOINTHUD4("options.waypointhud4", false, true),
	WAYPOINTHUD5("options.waypointhud5", false, true),
	
	WAYPOINTNETHERHUD1("options.waypointhudnether1", false, true), // bool
	WAYPOINTNETHERHUD2("options.waypointhudnether2", false, true),
	WAYPOINTNETHERHUD3("options.waypointhudnether3", false, true),
	WAYPOINTNETHERHUD4("options.waypointhudnether4", false, true),
	WAYPOINTNETHERHUD5("options.waypointhudnether5", false, true),
	
	LASTBOUNDSX("options.lastboundsx", false, false),
	LASTBOUNDSY("options.lastboundsy", false, false),
	LASTBOUNDSW("options.lastboundsw", false, false),
	LASTBOUNDSH("options.lastboundsh", false, false),
	LASTFULLSCREEN("options.lastfullscreen", false, true),
	LASTPOSITIONEXISTS("options.lastpositionexists", false, true);

	public static ControlPackEnumOptions getOption(int i) {
		ControlPackEnumOptions aenumoptions[] = values();
		int j = aenumoptions.length;
		for(int k = 0; k < j; k++) {
			ControlPackEnumOptions enumoptions = aenumoptions[k];
			if(enumoptions.getOrdinal() == i) {
				return enumoptions;
			}
		}
		return null;
	}
	
	public static ControlPackEnumOptions getOption(String name) {
		ControlPackEnumOptions aenumoptions[] = values();
		int j = aenumoptions.length;
		for(int k = 0; k < j; k++) {
			ControlPackEnumOptions enumoptions = aenumoptions[k];
			if(enumoptions.getName().equals(name)) {
				return enumoptions;
			}
		}
		return null;
	}

	private ControlPackEnumOptions(String name, boolean isFloat, boolean isBool, boolean isString) {
		this.name = name;
		this.isFloat = isFloat;
		this.isBool = isBool;
		this.isString = isString;
	}
	private ControlPackEnumOptions(String name, boolean isFloat, boolean isBool) {
		this.name = name;
		this.isFloat = isFloat;
		this.isBool = isBool;
		this.isString = false;
	}

	public boolean getIsFloat() {
		return isFloat;
	}

	public boolean getIsBool() {
		return isBool;
	}
	
	public boolean getIsString() {
		return isString;
	}

	public int getOrdinal() {
		return ordinal();
	}

	public String getName() {
		return name;
	}
	
	public String getLocKey() {
		return name;
	}

	private final String name;
	private final boolean isFloat;
	private final boolean isBool;
	private final boolean isString;
}
