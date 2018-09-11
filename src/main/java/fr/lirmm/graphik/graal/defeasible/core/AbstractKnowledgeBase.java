package fr.lirmm.graphik.graal.defeasible.core;

import java.io.Reader;
import java.io.StringReader;

import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.api.io.ParseException;

public abstract class AbstractKnowledgeBase {
	
	/**
	 * Parses objects (e.g. atom, rule, etc.) from a reader and adds it to the knowledge base.
	 * @param reader
	 * @throws ParseException * @throws ParseException if input format error.
	 * @throws AtomSetException if the structure used to store atoms throws an error.
	 */
	public abstract void add(Reader reader) throws ParseException, AtomSetException;
	
	/**
	 * Parses the content of a string and adds the parsed objects to the knowledge base.
	 * @param str a string containing a DLGP description of the objects to parse. 
	 * @throws ParseException if input format error.
	 * @throws AtomSetException if the structure used to store atoms throws an error.
	 */
	public void add(String str) throws ParseException, AtomSetException {
		this.add(new StringReader(str));
	}
	
	
}
