package biped.data;

import java.util.ArrayList;

import edu.ucsc.cross.jheq.core.object.DataStructure;

public interface DiscreteController<X extends DataStructure, U, Y>
{

	public Y uD(X state, U input, ArrayList<Object> params);
}
