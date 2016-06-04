/* Copyright (c) 2014, Julian Uy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
    WAYPOINTNETHERHUD5("options.waypointhudnether5", false, true);

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
