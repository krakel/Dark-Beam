/**
 * Dark Beam
 * FBlockIds.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.testing.lib;

import java.lang.reflect.Constructor;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;

import de.krakel.darkbeam.testing.core.helper.LogHelper;
import de.krakel.darkbeam.testing.creativetab.ModTabs;
import de.krakel.darkbeam.testing.item.ItemBlockRedWire;
import de.krakel.darkbeam.testing.item.TestItemBlockItem;
import de.krakel.darkbeam.testing.item.TestItemBlockMulti;
import de.krakel.darkbeam.testing.item.TestItemBlockPanel;

public enum BlockType {
	OreDarkening( "oreDarkening"),
	OreBeaming( "oreBeaming"),
	RedWire( "blockRedWire", ItemBlockRedWire.class),
	TestSimple( "testBlockSimple"),
	TestItem( "testBlockItem", TestItemBlockItem.class),
	TestMulti( "testBlockMulti", TestItemBlockMulti.class),
	TestPanel( "testBlockPanel", TestItemBlockPanel.class),
	ContainerSimple( "testContainerSimple");
	private static int sBlockIds = 2400;
	private int mDefault;
	private int mId;
	private Block mBlock;
	private Class<? extends ItemBlock> mItemClass;
	public final String mName;

	private BlockType( String name) {
		mDefault = nextID();
		mName = name;
	}

	private BlockType( String name, Class<? extends ItemBlock> itemClass) {
		mDefault = nextID();
		mName = name;
		mItemClass = itemClass;
	}

	private static int nextID() {
		return sBlockIds++;
	}

	public <T extends Block> T create( Class<T> cls) {
		try {
			Constructor<T> ctor = cls.getConstructor( int.class);
			T blk = ctor.newInstance( mId);
			blk.setUnlocalizedName( mName);
			blk.setCreativeTab( ModTabs.sTabMain);
			mBlock = blk;
			return blk;
		}
		catch (Exception ex) {
			LogHelper.severe( ex, "caught an exception during block creation");
		}
		return null;
	}

	public Block getBlock() {
		return mBlock;
	}

	public int getId() {
		return mId;
	}

	public void register() {
		if (mBlock != null) {
			if (mItemClass != null) {
				GameRegistry.registerBlock( mBlock, mItemClass, mName);
			}
			else {
				GameRegistry.registerBlock( mBlock, mName);
			}
		}
		else {
			LogHelper.severe( "Missing block for registration");
		}
	}

	public void updateID( Configuration config) {
		mId = config.getBlock( mName, mDefault).getInt( mDefault);
	}
}
