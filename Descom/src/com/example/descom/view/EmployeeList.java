package com.example.descom.view;

import org.tepi.filtertable.FilterTable;

import com.vaadin.event.Action;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.CustomTable;
import com.vaadin.ui.Link;

import com.example.descom.DescomApplication;
import com.example.descom.database.DatabaseConnection;



/**
 * This class create table for data from database.
 * Encapsulated nicely into table.
 * This table also using filter mode, to fast search query.
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class EmployeeList extends FilterTable {
	
	/**
	 * This static final Action is using to display in context menu.
	 * Also is using to choose correct method affected on employee
	 */
	static final Action ACTION_EDIT =  new Action ("Edit");
	
	/**
	 * This static final Action is using to display in context menu.
	 * Also is using to choose correct method affected on employee
	 */
    static final Action ACTION_DELETE =  new Action ("Delete");
    
    /**
     * This static final Action table contain all, possible actions
     * for context menu. The use of this is in the action handler.
     *  
     */
    static final Action[] ACTION_OPTION = new Action[] { ACTION_EDIT, ACTION_DELETE };

    
    /**
     * This constructor contain initialization all of properties for filter table.
     * Also contain action handler for context menu.
     * 
     * @param application
     * 				Main object, which combines all the other classes.				
     * @param employeeForm
     * 				Object is need to using methods from that class. 
     * 				Mainly edit() and delete().
     */
	public EmployeeList(DescomApplication application, final EmployeeForm employeeForm) {
		
		setSizeFull();
		
		/*
		 *  Fill table of elements from the database.
		 *  Get the SQLContainer containing the employee 
		 *  in the descom app an set it as the data source of this filter table.
         */
		setContainerDataSource(application.getDataSource().getEmployeeContainer());
		
		/*
         *	Remove container filters and set container filtering mode to
         *	inclusive. (These are default values but set here just in case)
         */
        // application.getDataSource().getEmployeeContainer().removeAllContainerFilters();
  
        // Set parameters for column
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
		
        // Make table selectable 
		setSelectable(true);
		
		// Set decorator for filter table, like easy choosing date, number in header row.
		setFilterDecorator(new SearchFilterDecorator());
               		
		// Set to give possibility to filtering data
		setFilterBarVisible(false);
		
        // React immediately for user events
		setImmediate(true);
		      
        // Pass events to the controller (main application)
		addListener((ValueChangeListener) application);
        		
        // We don't want to allow users to de-select a row 
		setNullSelectionAllowed(false);

        // Customize email column to have mailto: links using column generator
		addGeneratedColumn("EMAIL", new ColumnGenerator() {

			/**
			 * 	Called by Table when a cell in a generated column needs to be generated
			 * 
			 */
			public Object generateCell(CustomTable source, Object itemId, Object columnId) {
				
				 if (getItem(itemId).getItemProperty("EMAIL").getValue() != null) {
	                    
					 Link link = new Link();                    
	                 link.setResource(new ExternalResource("mailto:" + getItem(itemId).getItemProperty("EMAIL").getValue()));
	                 link.setCaption(getItem(itemId).getItemProperty("EMAIL").getValue().toString());
	                 
	                 return link;
	                 
	                }
				 
	                return null;
	            }
        });
		
		// Set header of column (elements and order)
		setVisibleColumns(DatabaseConnection.NATURAL_COL_ORDER);
		setColumnHeaders(DatabaseConnection.COL_HEADERS_ENGLISH);
		
		// Collapse this column programmatically
		setColumnCollapsed("BIRTH_DATE", true);
		setColumnCollapsed("TAX_IDENTIFICATION_NUMBER", true);
		setColumnCollapsed("TELEPHONE_HOME", true);
		setColumnCollapsed("GENDER", true);

		// Action for right click button, with context menu
        addActionHandler(new Action.Handler() {
        	
        	/**
        	 *  Send Action tab with possible options to handle action
        	 *  
        	 *  @parm target
        	 *  			Object, target handler to list actions for. 
        	 *  			For item containers this is the item id.
			 *	@parm sender 
			 *				Object, the party that would be sending the actions. 
			 *				Most of this is the action container.
			 *
			 *	@return the list of Action
        	 */
            public Action[] getActions(Object target, Object sender) {
                return ACTION_OPTION;
            }


            
            /**
             *	Handles an action for the given target. The handler method may just discard the action if it's not suitable.
             * 
             * 	@parm action
             * 				the action to be handled.
             * 	@parm sender
             * 				the sender of the action. This is most often the action container.
             * 	@parm target
             * 				the target of the action. For item containers this is the item id.
             */
            public void handleAction(Action action, Object sender, Object target) {
                if (ACTION_EDIT == action) {
                	
                	// Select current row
                    select(target);
                    
                    // Set employee form to editable mode
                    employeeForm.setReadOnly(false);
                    
                    // Discards and recreates the internal row cache.
                    refreshRowCache();
                    
                } else if (ACTION_DELETE == action) {
                	
                	// Select current row
                    select(target);
                    
                    // Call method form employee form to delete current item
                    employeeForm.deleteItem();
                    
                    // Discards and recreates the internal row cache.
                    refreshRowCache();
                    
                } 

            }

        });
       
    }
	
	
	
	/**
     *	Checks that selection is not null and that the selection actually exists
     *	in the container. If no valid selection is made, the first item will be
     *	selected. Finally, the selection will be scrolled into view.
     */
    public void fixVisibleAndSelectedItem() {
    	
        if ((getValue() == null || !containsId(getValue())) && size() > 0) {
        	
            Object itemId = getItemIds().iterator().next();
            
            // Select current item
            select(itemId);
            
            // Setter for property currentPageFirstItemId
            setCurrentPageFirstItemId(itemId);
            
        } else {
        	
        	// Setter for property currentPageFirstItemId
            setCurrentPageFirstItemId(getValue());
            
        }
    }

}
