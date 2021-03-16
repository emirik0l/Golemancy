# Golemancy

Golemancy is a mod about capturing the souls of mobs, breeding them, and using them to create faithful golem servants to do your bidding. You can create golems that will fight for you, gather items, and otherwise automate your Minecraft world!

This mod is currently in **beta**: only a basic set of golems has been implemented so far, and there are some mechanics (like config files, ingame tutorials, and many more golem variants) which are planned for the future. Things like textures and entity models are in an early state and subject to change, and **bugs/crashes are likely**! If that's a problem for you, I recommend waiting until the mod is more stable.

This mod is for Fabric, and no Forge port is planned - sorry about that!

## Capturing Souls

You can make an empty soulstone by putting nether quartz into a stonecutter:

![empty soulstone recipe](/readme/empty-soulstone.png)

Next you'll need to kill some mobs. If you kill a mob whose soul can be captured and you have an empty soulstone in your inventory, it will be filled up.

![a filled soulstone](/readme/filled-soulstone.png)

## Making Golems

Soulstones aren't useful without a body to inhabit, so the next step is to make a clay effigy:

![clay effigy recipe](/readme/clay-effigy.png)

Right-click to put the effigy in the world. Then right-click on it with a soulstone to turn it into a golem!

![a golem in the world](/readme/golem.png)

## The Golem Wand

The golem wand can be used to interact with your golems:

![golem wand recipe](/readme/golem-wand.png)

Hold the golem wand in your hand and right-click on one of your golems to make them follow you. Right click again to tell them to stop. If you right-click a golem while sneaking, you'll enter "linking mode". You can then right-click on a block to link the golem to that block.

Golems consider their linked block as "home" and will return to it when they're not busy doing something else. Some golems can, if linked to a chest or other inventory, extract or insert items into their linked block.

## The Soul Mirror

Each soul has a set of attributes and genetics that determine its type and abilities. You can check the genes of your soulstones with a soul mirror:

![soul mirror recipe](/readme/soul-mirror.png)

To use the soul mirror, just hold it in your off-hand and right-click with a soulstone in the other hand.

![soul mirror screen](/readme/soul-mirror-screen.png)

## The Soul Grafter

You can breed (or "graft") soulstones together to produce new results. To do so, you'll need to craft a soul grafter using any filled soulstone:

![soul grafter recipe](/readme/soul-grafter.png)

The soul grafter is what you use to breed soulstones. You'll need a good supply of empty soulstones and bone meal to keep it running, though:

![soul grafter screen](/readme/soul-grafter-screen.png)

# Credits

Some textures are adapted from the excellent Malcolm Riley's [unused-textures](https://github.com/malcolmriley/unused-textures) repository, namely:

* The soul mirror item.
* The soul grafter block.
* The soulstone item.

These assets are licensed under [CC BY 4.0](https://creativecommons.org/licenses/by/4.0/).