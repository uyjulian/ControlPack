Control Pack
============

AutoSneak/Run, AutoTool, SmartFurnace, LookBehind and lots more!

*"Your mod is like putting on an old jacket and finding 50 bucks."*
- **the_f0dder**

*"I want to give birth to this mods' babies."*
- **eastwood6510**

*"When minecraft updates to a new version I don't even bother installing it, until ControlPack is updated as well."*
- **Lucid**

*"It's like you gave birth to the new minecraft."*
- **.AT**

Downloads of ControlPack are located at https://github.com/uyjulian/ControlPack/releases, 
while old versions can be found at https://sites.google.com/site/awertyb/minecraft-mods/controlpack/downloads

The source code of ControlPack is available at https://github.com/uyjulian/ControlPack

The Minecraft Forums forum topic is located at http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2469613-controlpack

Compatibility With Other Mods
=============================

Control Pack should be compatible with any mod that is compatible with Liteloader.

Installation Instructions
=========================

Download and install Liteloader, which is located at 
http://www.liteloader.com/download.

After you installed Liteloader, download the latest version of ControlPack at 
https://github.com/uyjulian/ControlPack/releases/latest. If you are not sure
which files to download, simply download all of the .litemod files.

After you downloaded the correct file(s), place the file(s) you downloaded 
into the "mods" folder.

If your computer is running Windows, you can press the Windows key and R 
at the same time, then put ``%APPDATA%/.minecraft`` in the box to go
right to the folder where the "mods" folder is located. If that folder does
not exist, create it.

Control Pack Options Screens
============================

You can turn all the features on and off.
http://dl.dropbox.com/u/10414649/mc/controlpack/4.5/options.png You can
customize all the new key bindings (or even map them to a mouse button).
Right click to disable one.
http://dl.dropbox.com/u/10414649/mc/controlpack/4.5/keybindings.png You
can control the volume level of individual sound effects (plus more in
5.4) http://dl.dropbox.com/u/10414649/mc/controlpack/4.2/SFXVolume.PNG

FEATURE BY FEATURE
==================

Auto Tool Selection
-------------------

You'll wonder how you ever played Minecraft before this. With Auto Tool,
just left click a block. ControlPack will automatically select the best
tool for the job from your toolbar! If the item you are mining does not
require a tool (like a torch), it will switch to your hand (or to a
regular item if you don't have any open slots).

You can customize how the tool selection works by putting it into one of
these modes, right from the included UI:

**Weakest Tool** (this is default value): Select the weakest tool you have
that will do the job. For example: You have a wood pick and a stone
pick, it will normally select the wood pick while mining stone. If you
find Iron Ore, which requires at least a Stone pick, it will use the
stone pick.

**Strongest Tool**: Always pick the fastest tool for the job, even if you
have lesser tools that would work.

**Leftmost**: No magic, just pick the first tool on the toolbar that is
appropriate for the block.

**Rightmost**: No magic, just pick the last tool on the toolbar that is
appropriate for the block.

**Quick Toggle**: Of course, there may be times you want to use a particular
tool. You can very easily turn auto tool on and off by hitting ALT+T.

**Quick Toggle Mode**: You can also quickly cycle through the possible modes
using ALT+R.

**While auto mining**: This works even while using the toggle-mining feature.
That means you can turn on toggle mining, and sit back as you
automatically switch between picks and shovels as you run into dirt or
gravel patches.

**Breaking Tools**: And, after a tool breaks, since you won't be holding a
tool anymore, auto tool will automatically switch to a new tool.

Auto Sword Selection
--------------------

Left click a mob, and it'll select your sword automatically! Don't have
one? It will also make sure you don't have a tool selected so you don't
waste a use.

You can quickly enable and disable Auto Sword with ALT+S.

If you have a mod that provides weapons other than a sword you want to
auto select, you can customize which items ControlPack thinks are swords
in the options screen. Just provide a comma-delimited list of the item
IDs.

Auto Block Selection
--------------------

What happens if you right click while holding a tool, like a pick axe?
Nothing! Until now. Auto Block is like the opposite of Auto Tool. When
you right click while holding a tool, ControlPack automatically switches
to a placeable block or item in your toolbar, and places it!

You can customize how it decides which block to place by putting it into
one of these mods, right from the included UI:

**Leftmost**: (the default) Uses the first placeable block or item that is on
your toolbar.

**Rightmost**: Uses the last placable block or item that is on your toolbar.

**Slot #**: You can set it to a specific slot number (1 through 9). Then it
will place whatever block is in that slot, if any.

**Quick Toggle**: You can quickly enable and disable Auto Block with ALT+B.

**Example**: Remember it applies to any placeable item or block, even
torches. So picture this: In the first 3 slots of your inventory you
have a pick, a shovel, and torches. You mine away, and Auto Tool is
switching between the pick and shovel for you. Now and then, you want to
place a torch. All you do is right click, and BAM, a torch is placed,
then you just continue left-click mining! Can it get ANY easier? Well,
there's also the 'Place Torch' key :)

With Control Pack, you'll almost never have to worry about switching
between inventory items!

Hold to Attack
--------------

When this option is enabled (it is off by default), holding down the
attack button will repeatedly attack mobs instead of forcing you to
click over and over again. This also applies to the Toggle Mining
command. When toggle mining is ON, and this option is ON, you will also
auto attack any mobs you look at.

Stack Preservation
------------------

If you place the last item in a stack, and you have another stack on
your toolbar, it will automatically switch to that item, so you can
continue placing the block or item without having to switch to the new
slot! This feature is enabled/disabled along with Auto Block.

Place Torch
-----------

Now you have a keyboard or mouse key that will always place a torch as
long as you have one on your toolbar. And it switches back to what you
had before right away.

If you don't have any torches, it will also place a Redstone Torch.

Eat Food
--------

Hungry? Just press and hold the 'Eat Food' button. You'll automatically
start eating whatever the first food item is on your toolbar. Release
the button and you'll stop eating and automatically swap back to the
previous item you were holding.

Automatic Window Restoration
----------------------------

ControlPack remembers the size and position of your Minecraft window and
automatically restores it when the game launches (after you log into
minecraft.net in the launcher). It will not only remember the size and
position, but also the fullscreen status of the window.

Better 3rd Person View
----------------------

Minecraft has three camera modes. The normal view, and two 3rd person
views. The two 3rd person views let you view your character from the
front or the back. But, the problem with these views is that you are
ALWAYS either directly in front of or behind your head, and movement of
the camera is locked to the movement of your head.

ControlPack adds another 3rd person view that separates camera control
from head movement. This allows you, for example, to view your character
from the side, from above, or from below. You can see yourself turn 360
degrees around as the camera stays in the same position.

You can rotate the camera up/down and left/right by holding down the
middle mouse button and moving the camera freely. You can also do it
with the keyboard by holding down CONTROL and then using your usual
directional keys. For example, CTRL+LEFT will rotate the camera to the
left.

Just keep tapping F5 to toggle through all the views as usual.

Look at the pretty clouds...
http://dl.dropbox.com/u/10414649/mc/2011-06-14\_17.58.32-small.png

Swap Left and Swap Right
------------------------

These are 'press and hold' commands meant to let you use a tool
temporarily.

Perhaps you are working on a building that needs mostly stone but with
some glass every now and then. Well, swap to the glass temporarily when
you need it. Easy!

Another great use -- if you're mob hunting, keep some pork or whatever
to the left and right of your sword. Healing up in mid-battle is much
easier!

Toggle Sneak
------------

Building something at high altitudes? Holding down shift when you're
doing dangerous work can be tiring and error-prone. Now you can toggle
sneak and rest assured you won't fall down.

Also -- did you know that sneaking while on a ladder will let you stick
to the ladder? Holding down sneak to do that is great, but you can't do
anything else without falling down. You can't chat, get up to go to the
bathroom, or eat Cheez-Its®! Toggle sneak and you're good to go.

Auto Jump
---------

For swimming, of course! Now you can actually chat without drowning!
Also great if you use it in combination with Auto-Run so you can skip
over small hills automatically, or swim across an ocean while taking a
micro break. Stretch those muscles now and then.

Auto Run
--------

For long distances, it makes things easier. Yes, you will have to jump a
lot along the way, but hey, there's auto JUMP too! And if you combine
this with Auto Jump while in the water, well, now you can swim across
the ocean and chat while you're doing it.

NEW IN 5.5: If you want to auto run BACKWARDS, just use auto run while
walking backward. It will continue to run backwards.

Toggle Mining
-------------

Holding down the mouse button for long mining sessions can get tiring.
Auto-mine to the rescue! Just tap it, and you'll be swinging like
there's no tomorrow (What if there is no tomorrow? There wasn't one
today!). Auto tool will even take care of making sure you use the
correct tool along the way.

If you're especially lazy, also turn on auto-run, look just a tad bit
down from the center, and you'll not only auto-mine, you'll blaze a path
while doing it! Afraid you might fall into a cavern? No problem, turn on
toggle sneak too!

This can marathon mining sessions so much more enjoyable.

TIP: You can set your middle mouse button (the 'wheel click') to toggle
mining.

Toggle Use Item
---------------

This is your right-click toggle. If you are building a lot, this might
come in handy. Note that autoblock's "exhasted" feature will still kick
in here. So if you're auto placing a block and your current stack runs
out, it will automatically switch to a fresh stack if you have one and
continue placing them.

This is great for eating a lot of melon slices when your hunger bar is
nearly empty. Just tap it, and then grab a quick bite to eat yourself :)

Run Distance
------------

Stop staring at the ground counting blocks or using F3! Just enter how
many blocks you want to go, and you're off. It automatically stops after
you've traveled that many blocks in any direction. Don't worry about
things in your way, walk around them, jump over them, or whatever you
have to do, it will still stop on the right spot, even if you have to
back track or take turns.

It will stop when you are that many blocks away from where you started,
in a straight line. So if you put "10" for example, imagine there is a
box around your starting point that goes 10 blocks in each N, E, W, S
direction. Your character will stop walking when they hit the wall of
that box, at whatever point it is.

Smart Furnace Drops
-------------------

You'll wonder how you ever lived without this time-saver. When in the
furnace UI, you can shift-click items in your inventory to add them
automatically to the furnace. Shift-click the items in the furnace and
they'll return to your inventory. But there's more to it than that.

ControlPack will figure out the best thing to do with the item: x If it
is a smeltable item, it puts it in the furnace queue or adds it to the
existing queue. x If it is a furnace fuel (coal, etc), it puts it in the
fuel slot, or adds it to the existing fuel. x When placing fuel, it
knows how much is necessary to smelt all the items and only adds that
many. x When placing fuel, if there is already some fuel, it adds only
enough to it to be able to smelt all the items. x You can do all of this
while already holding a completely different item.

What does this mean exactly? Say you have an empty furnace, 64 sand, and
64 coal. Normally, to smelt them you would have to perform a ton of
mouse operations, dragging things around, right clicking a bunch of
times, etc etc. It's kind of a pain.

Here's how you do it with ControlPack! 1. Shift-click the sand. 2.
Shift-click the coal. 3. When it's done smelting, shift-click the glass.
DONE!

Look Behind
-----------

You're down to 1 heart, and a Creeper starts charging you! What do you
do? RUN!!! How do you know if the creeper is still following you? Stop
and turn around? But then you risk him catching up with you!

Look behind makes your middle-mouse button to do something incredibly
useful. It causes your character to turn their head 180 degrees to look
behind you, while you are still running! Just hold it down to look
behind you, then release to look forward again.

Don't have a middle mouse button? You can reconfigure it to any mouse or
keyboard key. You can also hold down the BACK button (that is, whatever
key you have mapped to be your 'move backwards' button) while holding
down the forward button -- thereby pressing forward and back at the same
time.

Looking behind you -- notice there's no hand or tool, and the scene is
tilted a little
http://dl.dropbox.com/u/10414649/mc/2011-06-14\_23.09.17.png

Toggle Full Brightness
----------------------

When you use this command, your Minecraft brightness setting is set to
MAX. Use the command again to automatically restore the brightness to
your previous value. This is a great way of keeping the nice Moody
brightness setting that Minecraft looks great in, but then easily
turning it up when you're in a dark cave, so you can see without even
having any torches. You get the best of both worlds!

Moody Brightness Level
http://dl.dropbox.com/u/10414649/mc/normalbright.png Full Brightness
Level http://dl.dropbox.com/u/10414649/mc/fullbright.png

Sound Effect Volume
-------------------

Rain is awfully loud in Minecraft. So are pistons. And everytime you
splash into some water, that splashing sound can be kind of jarring. Now
you can control the volume of these sound effects, and more,
independently of your main volume! You might enjoy hearing the subtle
sound of rain. You can even make sounds LOUDER than normal if you want
to. Make explosions LOUDER!

-  Some sounds won't get louder than they would be when your Minecraft
   sound setting is set to 100%. To get them louder than other sounds,
   lower your Minecraft volume setting but increase your system volume
   to compensate.
-  NEW IN 5.4: Now you can control the volume of animals, slimes, and
   the step and dig sounds.

Coordinates Overlay
-------------------

You can display your X, Y and Z coordinates constantly so you always
have an idea of where you are. Consider it a very lightweight and simple
minimap. You can decided which corner of the screen to show them in: top
left, top right, bottom left, or bottom right. Or you can turn them off,
of course.

Note that the coordinates display in X, Z, and Y order. Y is your
vertical position.

Waypoints
---------

You can save up to 10 waypoints -- 5 in the Nether and 5 in the normal
world. Each waypoint can optionally be displayed on your HUD, and it
will show the coordinates along with an arrow that will always point in
its direction. You can also provide each waypoint a name if you want.
It's a dead simple way of keeping track of your favorite spots.

NEW IN 5.1: You can customize the display of the coordinates, so you can
decide what order X, Y and Z are in, etc.

http://dl.dropbox.com/u/10414649/mc/controlpack/4.5/waypoints.png

Say your location
-----------------

Ever want to tell someone what your coordinates are, but it's almost
impossible to chat and look at the F3 screen at the same time? Forget
that. Just type the Say Your Locatin key (INSERT by default) while
chatting, and your position will be inserted automatically for you.

NEW IN 5.1: You can customize the display of the coordinates, so you can
decide what order X, Y and Z are in, etc.

Auto Corpse Waypoint
--------------------

OH NOES! You were deep inside a cave... and YOU DIED, dropping all your
diamonds!!

Now with ControlPack you'll find your stuff easily. A waypoint is
automatically set to the location of your untimely death.

Tool Uses Counter
-----------------

While holding a tool, the number of uses remaining will display where
you configure it. Now you always know exactly what to expect.

Arrows Remaining Counter
------------------------

While holding a bow, you'll see the total number of arrows you have in
your inventory! Goes down fast, doesn't it?
