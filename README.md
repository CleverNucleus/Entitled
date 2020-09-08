# Entitled
 A small mod that brings coloured titles to players by expanding on the vanilla Name Tag item.

# Usage
Players may now *sneak-rightclick* Name Tags to equip them. The Name Tag display text will appear as a title-plate above the player (but below the username if in multiplayer). The display text can be coloured to any one of the sixteen colours in Minecraft: This is done by combining a Name Tag and a Dye item in a crafting table together - the result is an identical Name Tag to the input, but with colour data appended to it. By holding *shift* over the Name Tag the colour is displayed as a tooltip. To unequip a Name Tag, the player just has to *sneak-rightclick* any other Name Tag and the currently equipped one will drop. 

Note that unnamed Name Tags cannot be equipped.

# Commands
Commands introduced by this mod may only be executed by a command block or a player with a permission level of two of more (in creative mode/admin). From now on any player with a permission level of two or more will be referred to as an '*admin*', while the symbol '*@p*' will denote '*target player*' (for the purpose of being concise).

**/entitled @p clear**

Clears the target player's Name Tag (or unequips it), provided that the target player has one equipped in the first place.

**/entitled @p lock**

Removes the target player's ability to equip/unequip a Name Tag. A few notes on this: An admin player is immune to this command and cannot have their ability to equip/unequip Name Tags locked, only players without admin privileges may have that ability locked. If a command block executes this command, it will skip over admin players.

**/entitled @p unlock**

The opposite of '*/entitled @p unlock*'. Reenables the target player to equip/unequip Name Tags. The same rules on admin behaviour are applied here too.

**/entitled @p colour**

If the target player has a Name Tag equipped, this will change that Name Tag's colour to the one specified in the command; e.g. playerA has a blue Name Tag equipped, and an admin uses '*/entitled playerA red*', playerA's Name Tag is now red.

**/entitled @p colour title**

The same functionality as '*/entitled @p colour*', but also specifies the Name Tag's display text ('*title*' argument). This command will apply to the target player regardless of if they already have a Name Tag equipped or not. If they do not, this will create a Name Tag with the colour and display text from the arguments and equip it forcefully onto that player. Should that player then unequip that Name Tag, it will drop as a new item.

# Example use of Commands
**Example 1**

PlayerA exists with admin privileges, PlayerB exists as a survival player: Only PlayerA may use any of the commands listed above. PlayerA would like to force PlayerB to have a particular title (perhaps to represent membership of an ingame guild or rank). PlayerA does the following:

 - */entitled playerB red Skylord*
 - */entitled playerB lock*
 
PlayerB now has a red title displaying "Skylord", and cannot unequip it (ever) unless a player with admin privilege uses the *unlock* command on them.

**Example 2**

PlayerA exists with admin privileges and would like to give themself the title of "*Supreme Leader*". They do the following:

 - */entitled playerA magenta Supreme Leader*
 - */entitled playerA lock*
 
PlayerA now has a magenta coloured title displaying "Supreme Leader". However, the second command that they issued was voided and had no effect - why? Because the player was targetting theirself; rule one: *An admin cannot be locked*; rule two: *To use these commands one must be an admin*. PlayerA issuing the command to lock their own ability to equip/uneqip Name Tags contradicts these rules - so to issue the command in the first place, playerA must be an admin; therefor playerA may not lock theirself.

**Example 3**

A server exists with a spawn point; at the spawn point is a pressure plate with a command block underneath. The server owner wishes to give new players who join the server a beginner rank of sorts: A title dislaying "Noob" in yellow.

The aforementioned command block contains the command "*/entitled @p yellow Noob*". A second command block that activates with the first one contains another command: "*/entitled @p lock*". 

PlayerA joins the server for the first time; they land on the pressure plate at spawn. They instantly have a yellow title displaying "Noob", and may not unequip this title until they are either *unlocked* by an admin or they stand on another command block "upgrading" their rank (or if an admin manually gives them an "upgraded rank").


**This concludes the full introduction to all functionality implemented by this mod.**
