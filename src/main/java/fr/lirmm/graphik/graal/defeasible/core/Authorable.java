package fr.lirmm.graphik.graal.defeasible.core;

import java.util.HashSet;

public interface Authorable {
	public HashSet<String> getAuthors();
	public void setAuthors(HashSet<String> authors);
}
