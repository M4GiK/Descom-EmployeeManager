package com.example.descom.view;

import java.io.Serializable;

/**
 * This class contain methods to set and gets filter for query.
 * Search filters will be saved into SearchTree. 
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class SearchFilter implements Serializable {

	/**
	 * Stores name for search result.
	 */
    private final String term;
    
    /**
     * Stores property about select category.
     */
    private final Object propertyId;
    
    /**
     * Stores string of characters for search term.
     */
    private String searchName;
    
    /**
     * Stores string of characters about select category.
     */
    private String propertyIdDisplayName;
    
    /**
     * Stores name for display.
     */
    private String termDisplayName;

    
    
    /**
     * That constructor set property and name for tree.
     * 
     * Parameters:
     * @param propertyId
     * 			Object containing property about select category. 
     * @param searchTerm
     * 			String containing string of characters using to search.
     * @param name
     * 			String containing name for search which will be displayed.
     */
    public SearchFilter(Object propertyId, String searchTerm, String name) {
        this.propertyId = propertyId;
        this.term = searchTerm;
        this.searchName = name;
    }
    
    

    /**
     * That constructor set property and name for tree.
     * 
     * Parameters:
     * @param propertyId
     * 			Object containing property about select category. 
     * @param searchTerm
     * 			String containing string of characters using to search.
     * @param name
     * 			String containing name for search which will be displayed.
     * @param propertyIdDisplayName
     * 			String containing string of characters about select category.
     * @param termDisplayName
     * 			String containing name for display that name on tree.
     */
    public SearchFilter(Object propertyId, String searchTerm, String name, String propertyIdDisplayName, String termDisplayName) {
    	
        this(propertyId, searchTerm, name);
        setPropertyIdDisplayName(propertyIdDisplayName);
        setTermDisplayName(termDisplayName);
        
    }
    
    
    
    /**
     * Returns name for search result.
     * 
     * @return (String) the term.
     */
    public String getTerm() {
        return term;
    }
    
    

    /**
     * Returns property about select category.
     * 
     * @return (String) the propertyId.
     */
    public Object getPropertyId() {
        return propertyId;
    }

    
    
    /**
     * Returns name for display.
     * 
     * @return (String) name which will be displayed into tree.
     */
    public String getTermDisplayName() {
        return termDisplayName;
    }
    
    
    
    /**
     * Returns string of characters about select category.
     * 
     * @return (String) containing select category. 
     */
    public String getPropertyIdDisplayName() {
        return propertyIdDisplayName;
    }
    
    
    
    /**
     * Returns string of characters for search term.
     * 
     * @return (String) the name of the search.
     */
    public String getSearchName() {
        return searchName;
    }

  
    
    /**
     * Returns string of characters for search term into string.
     * 
     * @return (String) search name.
     */
    @Override
    public String toString() {
        return getSearchName();
    }
    
    
      
    
    /**
     * This method set string of characters about select category.
     * 
     * @param propertyIdDisplayName 
	 *				String will be set with string of characters about select category.
     */
    public void setPropertyIdDisplayName(String propertyIdDisplayName) {
        this.propertyIdDisplayName = propertyIdDisplayName;
    }

    
    

    /**
     * This method set string name for display.
     * 
     * @param termDisplayName
     * 				String will be set with name to display.
     */
    public void setTermDisplayName(String termDisplayName) {
        this.termDisplayName = termDisplayName;
    }
}