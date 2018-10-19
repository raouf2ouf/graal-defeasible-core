package fr.lirmm.graphik.graal.defeasible.core.preferences;

import fr.lirmm.graphik.graal.api.core.Atom;
import fr.lirmm.graphik.graal.api.core.Predicate;
import fr.lirmm.graphik.graal.api.core.Term;
import fr.lirmm.graphik.graal.defeasible.core.atoms.FlexibleAtom;

/**
 * @author hamhec
 *
 */
@SuppressWarnings("serial")
public abstract class Preference extends FlexibleAtom {
	
	public static enum Status {
		SUPERIOR, INFERIOR, EQUAL
	}
	
	public Preference(Atom a) {
		super(a);
	}
	
	public Preference(Predicate predicate) {
		super(predicate);
	}
	
	public Preference(Predicate predicate, Term sup, Term inf) {
		this(predicate);
		this.setTerm(0, sup);
		this.setTerm(1, inf);
	}
	
	public String getSuperior() {
		return this.getTerm(0).toString();
	}
	public String getInferior() {
		return this.getTerm(1).toString();
	}
	
	/**
	 * Returns a string in the form sup  inf
	 * @return string sup  inf
	 */
	@Override
	public String toString() {
		String str = this.getSuperior() + " > " + this.getInferior();
		return str;
	}
}
