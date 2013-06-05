/**
 * Dark Beam
 * TestContainerSimple.java
 * 
 * @author krakel
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
package de.krakel.darkbeam.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import de.krakel.darkbeam.tile.TestTileSimple;

public class TestContainerSimple extends BlockContainer {
	public TestContainerSimple( int id) {
		super( id, Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity( World world) {
		return null;
	}

	@Override
	public TileEntity createTileEntity( World world, int metadata) {
		return new TestTileSimple();
	}

	@Override
	public boolean hasTileEntity( int metadata) {
		return true;
	}
}
