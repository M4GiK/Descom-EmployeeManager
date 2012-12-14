package com.example.descom.view;

import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Tree;

/**
 * This class show in SearchWindow Tree.
 * Search results are saved here.
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class SearchTree extends Tree {
	
	/**
	 * Object containing name for display all employees. 
	 */
    public static final Object SHOW_ALL = "Show all";
    
    /**
     * Object containing name for display search results.
     */
    public static final Object SEARCH = "Search";

    
    
    
    /**
     * Create constructor which add some items to application which will be located on the left side.
     * Basic items: * show all
     * 				* search
     * 				
     * @param searchWindow
     * 				(SearchWindow) Need to combine with window.
     */
    public SearchTree(SearchWindow searchWindow) {
        addItem(SHOW_ALL);
        addItem(SEARCH);

        setChildrenAllowed(SHOW_ALL, false);

        // Admin user can select - selectable(true),
        setSelectable(true);
        
        // But can't unselected.
        setNullSelectionAllowed(false);

        // Make tree handle item click events
        addListener((ItemClickListener) searchWindow);

    }

}
