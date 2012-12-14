package com.example.descom.view;

import java.util.ArrayList;
import java.util.List;

import com.example.descom.DescomApplication;

import com.example.descom.database.DatabaseConnection;
import com.vaadin.addon.sqlcontainer.SQLContainer;
import com.vaadin.addon.sqlcontainer.filters.Like;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;


/**
 * Class to display window with search options.
 * 
 * @author m4gik
 * 
 */
@SuppressWarnings("serial")
public class SearchWindow extends Window implements ItemClickListener {

	private GridLayout grid;
	private TextField textField;
	private TextField searchName;
	private NativeSelect fieldToSearch;
	private CheckBox saveSearch;
	private CheckBox filters; 
	private Button search;
	private Label label; 
	
	/**
	 * Main object, which combines all the other classes.
	 */
	private DescomApplication application;
	
	/**
	 * 	Object need to presented save search results into tree.
	 */
	private final SearchTree tree = new SearchTree(this);
	
	
	/**
	 * Create constructor with properties for windows.
	 * 
	 * @param application
	 */
	public SearchWindow(final DescomApplication application) {

		this.application = application;

		// Make the sub window 40% the size of the browser window.
		setWidth("40%");

		// Center the window both horizontally and vertically in the browser window.
		center();

		// Set caption for window.
		setCaption("Search employees");
		
		// Use a FormLayout as main layout for this Panel.
        FormLayout formLayout = new FormLayout();
        
        setContent(formLayout);
        
        // Create UI components.
        grid = new GridLayout(2,5);
        
        // Create UI components on left which be after add to grid.
        textField = new TextField("Search term");
        fieldToSearch = new NativeSelect("Field to search");
        saveSearch = new CheckBox("Save search");
        searchName = new TextField("Search name");
        search = new Button("Search");
        
        // Create UI components on right which be after add to grid.
        label = new Label("If you want use more advanced search, try filters.");
        filters = new CheckBox("Filters mode");
        filters.setValue(application.getFilterMode());
        filters.setImmediate(true);       
        
        // Create listener which turns options for the filter table.
        filters.addListener(new Property.ValueChangeListener() {
            public void valueChange(Property.ValueChangeEvent event) {
            	application.setFilterMode((Boolean) event.getProperty().getValue());
            }
        });
	
        // Initialize fieldToSearch.
        for (int i = 0; i < DatabaseConnection.NATURAL_COL_ORDER.length; i++) {
            fieldToSearch.addItem(DatabaseConnection.NATURAL_COL_ORDER[i]);
            fieldToSearch.setItemCaption(DatabaseConnection.NATURAL_COL_ORDER[i], DatabaseConnection.COL_HEADERS_ENGLISH[i]);
        }

        // Set parameters for field search.
        fieldToSearch.setValue("FIRST_NAME");
        fieldToSearch.setNullSelectionAllowed(false);

        // Initialize save checkbox.
        saveSearch.setValue(false);
        saveSearch.setImmediate(true);
        searchName.setVisible(false);
        saveSearch.addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                searchName.setVisible(event.getButton().booleanValue());
            }
        });

        // Add listener for search button.
        search.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                performSearch();
            }

        });
                        
        // Add shortcut to close application. I chose ESC key for this.
     	setCloseShortcut(KeyCode.ESCAPE, null);

        // Add all the created components to the form.
        addComponent(grid);
        grid.addComponent(textField, 0, 0);
        grid.addComponent(fieldToSearch, 0, 1);
        grid.addComponent(saveSearch, 0, 2);
        grid.addComponent(searchName, 0, 3);
        grid.addComponent(search, 0, 4);
        grid.addComponent(label, 1, 0);
        grid.addComponent(filters, 1, 1);
        grid.addComponent(tree, 1, 4);
        
        // Set properties for grid.
        grid.setWidth("100%");
        grid.setColumnExpandRatio(1, 5);
        grid.setComponentAlignment(filters, Alignment.TOP_CENTER);
        
    }

	
	
	/**
	 * This method perform search. Create object to find result in SQLContainer 
	 * and also save query into tree if  this option was selected. 
	 */
    private void performSearch() {
    	
        String searchTerm = (String) textField.getValue();
        
        if (searchTerm == null || searchTerm.equals("")) {
        	
            getWindow().showNotification("Search term cannot be empty!", Notification.TYPE_WARNING_MESSAGE);
            return;
            
        }
        
        List<SearchFilter> searchFilters = new ArrayList<SearchFilter>();
        searchFilters.add(new SearchFilter(fieldToSearch.getValue(), searchTerm, (String) searchName.getValue(), fieldToSearch.getItemCaption(fieldToSearch.getValue()), searchTerm));
           
	    // If Save is checked, save the search through the window search.
	    if (saveSearch.booleanValue()) {
	    	
	        if (searchName.getValue() == null || searchName.getValue().equals("")) {
	        	
	            getWindow().showNotification("Please enter a name for your search!", Notification.TYPE_WARNING_MESSAGE);            
	            return;
	            
	        }
	        
	        SearchFilter[] sf = {};        
	        saveSearch(searchFilters.toArray(sf));
	    }
	    
	    SearchFilter[] sf = {};
	    search(searchFilters.toArray(sf));
	
	    /*
	     * Clear the save name and check box to prevent multiple unintentional
	     * saves of the same search.
	     */
	    clearSaving();
	}

    
    
    /**
     * This method clear value in search name and field for save.
     * This is need to prepare new searching.
     */
	private void clearSaving() {
	    searchName.setValue("");
	    searchName.setVisible(false);
	    saveSearch.setValue(false);
	}
	
	
	
	/**
     * This method search query into SQLContainer. 
     * Before new search remove previous filter for search.  
     * 
     * @param searchFilter
     * 				Array SearchFilter
     */
    public void search(SearchFilter... searchFilters) {
    	
    	if (searchFilters.length == 0) {
            return;
        }
    	
    	// Create container for search results.
    	SQLContainer container = application.getDataSource().getEmployeeContainer();

        // Clear all (previous) filters from person container. 
    	application.getDataSource().getEmployeeContainer().removeAllContainerFilters();
        
    	// Build an array of filters .
        Filter[] filters = new Filter[searchFilters.length];
        
        int i = 0;
        for (SearchFilter searchFilter : searchFilters) {
        	
            if (Integer.class.equals(container.getType(searchFilter.getPropertyId()))) {
                try {
                    filters[i] = new Equal(searchFilter.getPropertyId(), Integer.parseInt(searchFilter.getTerm()));
                } catch (NumberFormatException nfe) {
                    application.getMainWindow().showNotification("Invalid search term!");
                    return;
                }
            } else {
                filters[i] = new Like((String) searchFilter.getPropertyId(), "%" + searchFilter.getTerm() + "%");
            }
            
            i++;
        }
        
        // Add the filter(s) to the person container.
        container.addContainerFilter(new Or(filters));
        
        // Show notification about search.
        application.getMainWindow().showNotification("Searched for:<br/> "
                        + searchFilters[0].getPropertyIdDisplayName() + " = '"
                        + searchFilters[0].getTermDisplayName()
                        + "'<br/>Found " + container.size() + " item(s).",
                        Notification.TYPE_TRAY_NOTIFICATION);
    	
    }
    
	
	
	/**
	 * This method add saved query into tree. 
	 * 
	 * @param searchFilter
	 * 				Array SearchFilter
	 */				
	public void saveSearch(SearchFilter... searchFilter) {
			
	    tree.addItem(searchFilter);
	    tree.setItemCaption(searchFilter, searchFilter[0].getSearchName());
	    tree.setParent(searchFilter, SearchTree.SEARCH);
	    
	    // mark the saved search as a leaf (cannot have children)
	    tree.setChildrenAllowed(searchFilter, false);
	    
	    // make sure "Search" is expanded
	    tree.expandItem(SearchTree.SEARCH);
	    
	    // select the saved search
	    tree.setValue(searchFilter);
	    
	}



	/**
	 * This function is called when the item is pressed.
	 * 
	 * @param event 
	 * 				ItemClickEvent
	 */
	public void itemClick(ItemClickEvent event) {
		 
	        if (event.getSource() == tree) {
	        	
	            Object itemId = event.getItemId();
	            
	            if (itemId != null) {

	                if (SearchTree.SHOW_ALL.equals(itemId)) {
	                	
	                    // Clear all filters from person container
	                	application.getDataSource().getEmployeeContainer().removeAllContainerFilters();

	                } else if (SearchTree.SEARCH.equals(itemId)) {
	                    return;
	                } else if (itemId instanceof SearchFilter[]) {
	                    search((SearchFilter[]) itemId);
	                }
	            }
	        }
	    }


}
