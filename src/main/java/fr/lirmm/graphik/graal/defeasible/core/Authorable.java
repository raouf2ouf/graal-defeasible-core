package fr.lirmm.graphik.graal.defeasible.core;


import java.util.HashSet;

public interface Authorable {
	HashSet<String> getAuthors();
	void setAuthors(HashSet<String> authors);
}
