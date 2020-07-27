package clevernucleus.entitled.common.util;

import net.minecraft.nbt.CompoundNBT;

/**
 * Object for a display sig.
 */
public class Display {
	private static final Display NULL = new Display("", 0xFFFFFF);
	
	private String name;
	private int colour;
	
	private Display(final String par0, final int par1) {
		this.name = par0;
		this.colour = par1;
	}
	
	/**
	 * Creates a new Display object.
	 * @param par0 The string to be displayed.
	 * @param par1 The hex colour to display the string with.
	 * @return A new display object.
	 */
	public static Display make(final String par0, final int par1) {
		return new Display(par0, par1);
	}
	
	/**
	 * Reads the data from the nbt tag to create a new display instance.
	 * @param par0 Input tag.
	 * @return A new display instance.
	 */
	public static Display read(final CompoundNBT par0) {
		if(par0.isEmpty()) return NULL;
		
		return make(par0.getString("Name"), par0.getInt("Colour"));
	}
	
	/**
	 * Writes the display data to a compound tag.
	 * @param Input tag.
	 * @return A nbt tag with the display information.
	 */
	public CompoundNBT write(CompoundNBT par0) {
		par0.putString("Name", this.name);
		par0.putInt("Colour", this.colour);
		
		return par0;
	}
	
	/**
	 * @return The string held in this display.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return The hex to display the string with.
	 */
	public int getColour() {
		return this.colour;
	}
	
	@Override
	public String toString() {
		return this.name + ":" + this.colour;
	}
}
