
package biped.data.base;

public enum BipedLimb {
	SWING_LEG("Swing Leg", 0), PLANTED_LEG("Planted Leg", 1), TORSO("Torso", 2);

	public final String name;

	public final int vectorIndex;

	private BipedLimb(String new_name, int index) {

		name = new_name;
		vectorIndex = index;
	}

}