/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/

package org.dafoe.ontologiclevel.autocomplete.property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IProperty;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;

public class AutocompleteContentProposalProvider implements IContentProposalProvider {

	/*
	 * The proposals provided.
	 */
	protected List<IProperty> proposals = null;
	protected List<IProperty> filteredProposals = new ArrayList<IProperty>();

	/**
	 * Construct an IContentProposalProvider whose content proposals are
	 * the specified array of Objects.
	 * 
	 * @param proposals
	 *            the array of Strings to be returned whenever proposals are
	 *            requested.
	 */
	
	public AutocompleteContentProposalProvider(List<IProperty> proposals) {
		super();
		this.proposals = proposals;
	}

	/**
	 * Return an array of Objects representing the valid content proposals for a
	 * field. Ignore the current contents of the field.
	 * 
	 * @param contents
	 *            the current contents of the field (ignored)
	 * @param position
	 *            the current cursor position within the field (ignored)
	 * @return the array of Objects that represent valid proposals for the field
	 *         given its current content.
	 */
	
	public IContentProposal [] getProposals(String contents, int position) {
		List contentProposals = getMatchingProposals(this.proposals, contents);
		
		if(contentProposals != null) {
			
			if (contentProposals.get(0) != null) {
				System.out.println(contentProposals.get(0));
			}
			
		}
		
		return (IContentProposal[]) contentProposals.toArray(new IContentProposal[contentProposals.size()]);
	}

	/**
	 * Set the Strings to be used as content proposals.
	 * 
	 * @param items
	 *            the array of Strings to be used as proposals.
	 */
	
	public void setProposals(List<IProperty> items) {
		this.proposals = items;
	}
	
	
	/**
	 * Return a subset List of the proposal objects that
	 * match the input string
	 * 
	 * @param proposals
	 *			the array of Strings to be used as proposals.
	 * @param contents
	 * 			the string to try and match among the propostals
	 * @return
	 * 			the proposals that match the given string
	 */
	
	protected List getMatchingProposals(List<IProperty> proposals, String contents) {
		List contentProposals = new ArrayList();
		filteredProposals = new ArrayList<IProperty>();
		
		List<IProperty> matchingProposals = matches(proposals, contents);
		
		if (matchingProposals != null) {
			
			Iterator<IProperty> it = matchingProposals.iterator();
			
			while(it.hasNext()) {
				final IProperty prop = it.next();
				
				filteredProposals.add(prop);
				
				contentProposals.add(new IContentProposal() {
					public String getContent() {
						return prop.getLabel();
					}
					public String getDescription() {
						return null;
					}
					public String getLabel() {
						return null;
					}
					public int getCursorPosition() {
						return prop.getLabel().length();
					}
				});
				
				
			}
			
		}
		
		
		return contentProposals;
	}

	/**
	 * Returns an array of Strings within the input array that
	 * match the input test string
	 * 
	 * @param items
	 * 			the String array of possible completions
	 * @param prefix
	 * 			the incomplete String to try and match
	 * @return
	 * 			the array of possible completions to the input string
	 */
	private List<IProperty> matches (List<IProperty> items, String prefix) {
		List<IProperty> matches = new ArrayList<IProperty>();
		
		if (items != null) {
			Iterator<IProperty> it = items.iterator();
			
			while(it.hasNext()) {
				IProperty prop = it.next();
	            if (startsWithIgnoreCase(prop.getLabel(), prefix)) {
	                matches.add(prop);
	            }
			}
		}
		
        return matches;
	}
	
	/**
     * This is a method in the style of to {@link String#startsWith(String)}
     * but does a case insensitive match.
     * 
     * @param string - string within which to search
     * @param prefix - prefix to match in the string
     * 
     * @return - boolean value of the success of a match
     */
    private boolean startsWithIgnoreCase(String string, String prefix) {
    	return string.toLowerCase().startsWith(prefix.toLowerCase());
    }

    //
    
    	
}
