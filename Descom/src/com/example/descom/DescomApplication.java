package com.example.descom;

import com.example.descom.database.DatabaseConnection;
import com.example.descom.view.AddEmployeeWindow;
import com.example.descom.view.EmployeeForm;
import com.example.descom.view.EmployeeList;
import com.example.descom.view.HelpWindow;
import com.example.descom.view.ListView;
import com.example.descom.view.PrintWindow;
import com.example.descom.view.SearchWindow;

import com.vaadin.Application;
import com.vaadin.addon.sqlcontainer.query.QueryDelegate;
import com.vaadin.addon.sqlcontainer.query.QueryDelegate.RowIdChangeEvent;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

/**
 * Main class serving as core. Used Vaadin Framework with HSQLDB.
 * Add-ons: FilterTable version 0.8.0
 * 			Popupbutton version 1.0.0
 * 
 * @author m4gik
 * 
 */
@SuppressWarnings("serial")
public class DescomApplication extends Application implements ClickListener, ValueChangeListener, QueryDelegate.RowIdChangeListener {

	private Button newEmployee = new Button("Add employee");
	private Button search = new Button("Search");
	private Button print = new Button("Print");
	private Button help = new Button("Help");
	
	/**
	 * Creating the grid in order to hold the the filter table and Form.
	 */
	private GridLayout grid = null;
	
	/**
	 * Creating VerticalSplitPanel to set in split panel EmployeeList and EmployeeForm.
	 */
	private ListView listView = null;
	
	/**
	 * Creating window to show help informations.
	 */
	private HelpWindow helpWindow = null;
	
	/**
	 * Creating window to print reports.
	 */
	private PrintWindow printWindow = null;
	
	/**
	 * Creating window to search queries.
	 */
	private SearchWindow searchWindow = null;
	
	/**
	 * Creating window to add new employee.
	 */
	private AddEmployeeWindow addEmployeeWindow = null;
	
	/**
	 * Creating Filtertable to hold objects from SQLContainer. 
	 */
	private EmployeeList employeeList = null;
	
	/**
	 * Creating Form to present current employee.
	 */
	private EmployeeForm employeeForm = null;

	/**
	 * DatabaseConnection class that creates the tables and SQLContainers. 
	 */
	private final DatabaseConnection dbConnection = new DatabaseConnection();

	
	
	/**
	 * This init() method in application is called every time a new application
	 * needs to be initialized
	 */
	@Override
	public void init() {
		
		initLayout();
		setMainComponent(getListView());
		dbConnection.getEmployeeContainer().addListener(this);
		
	}

	
	
	/**
	 * This function will be initialize graphic part
	 */
	private void initLayout() {

		// Set title for application
		setMainWindow(new Window("Descom - Employees"));

		// Set theme
		setTheme("descomtheme");

		// Create vertical part of layout
		VerticalLayout layout = new VerticalLayout();	
		layout.setSizeFull();
		layout.addComponent(createToolbar());
		
		// Creating grid
		grid = new GridLayout();
		
		// Add grid to layout
		layout.addComponent(grid);
		grid.setSizeFull();
		grid.setHeight("100%");
		
        layout.setExpandRatio(grid, 1);
 
		getMainWindow().setContent(layout);
	}

	
	
	/**
	 * This method create horizontal (up part) toolbar.
	 * Basic items: * new Employee
	 * 				* search
	 * 				* print
	 * 				* help
	 * 
	 * @return the toolbar
	 */
	private HorizontalLayout createToolbar() {
		
		// Add items to toolbar
		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addComponent(newEmployee);
		toolbar.addComponent(search);
		toolbar.addComponent(print);
		toolbar.addComponent(help);

		// Set listeners method for this items menu
		search.addListener((ClickListener) this);
		print.addListener((ClickListener) this);
		help.addListener((ClickListener) this);
		newEmployee.addListener((ClickListener) this);

		// Set graphic from locations
		search.setIcon(new ThemeResource("icons/32/search.png"));
		print.setIcon(new ThemeResource("icons/32/print.png"));
		help.setIcon(new ThemeResource("icons/32/help.png"));
		newEmployee.setIcon(new ThemeResource("icons/32/person-add.png"));

		// Set style for toolbar 
		toolbar.setMargin(true);
		toolbar.setSpacing(true);
		toolbar.setStyleName("toolbar");
		toolbar.setWidth("100%");

		// Set Descom logo  
		Embedded element = new Embedded("", new ThemeResource("images/logo.png"));
		toolbar.addComponent(element);
		toolbar.setComponentAlignment(element, Alignment.TOP_RIGHT);
		toolbar.setExpandRatio(element, 1);
		
		return toolbar;
	}
	
	
	
	/**
	 * Add table with data from database to view
	 * 
	 * @param component
	 */
    private void setMainComponent(Component component) {
        grid.addComponent(component);
    }
    

    
    /**
     * View getters exist so we can lazily generate the views, resulting in
     * faster application startup time.
     * 
     * @return the listView
     */
    private ListView getListView() {
    	
        if (listView == null) {
        	employeeForm = new EmployeeForm(this);
            employeeList = new EmployeeList(this, employeeForm);
            listView = new ListView(employeeList, employeeForm);
        }
        
        return listView;
    }
    
    
    
    /**
	 * This method add new employee when, after pressing button.
	 * Call new window to add employee.
	 */
    private void addNewEmployee() {
    	
    	if (addEmployeeWindow == null) { 		
    		
    		// Clear all filters from person container 
    		getDataSource().getEmployeeContainer().removeAllContainerFilters();
        	getMainWindow().addWindow(getAddEmployeeWindow());    
        	
    	}
    	
    }
    
    
    
    /**
     * This method call new window to print reports purposes.
     */
	private void showPrintWindow() {
		getMainWindow().addWindow(getPrintWindow());		
	}



	/**
	 * This method call new window to show help.
	 */
	private void showHelpWindow() {
		if (helpWindow == null) {
			getMainWindow().addWindow(getHelpWindow());
		}
	}



	/**
	 * This method call new window to search employees.
	 */
	private void showSearchWindow() {
		if (searchWindow == null) {
			getMainWindow().addWindow(getSearchWindow());
		}
	}
	
	
	
	/**
	 * Returns window with search options.
	 * 
	 * @return the searchWindow (SearchWindow)
	 */
	private SearchWindow getSearchWindow() {
		
		if (searchWindow == null){
			
			searchWindow = new SearchWindow(this);
			
			// Add listener, to listen for close window. 
			// When is closed set window to null in order to protect against exceptions  
			searchWindow.addListener(new CloseListener() {
				   public void windowClose(CloseEvent e) {
					      searchWindow = null;
					   }
					});
			
		}
		
		return searchWindow;
	}



	/**
	 * Returns window with help.
	 * 
	 * @return the helpWindow (HelpWindow)
	 */
    private HelpWindow getHelpWindow() {
        if (helpWindow == null) {
        	
            helpWindow = new HelpWindow();
            
            // Add listener, to listen for close window. 
         	// When is closed set window to null in order to protect against exceptions  
            helpWindow.addListener(new CloseListener() {
				public void windowClose(CloseEvent e) {
					helpWindow = null;
				}
			});
        } 
        return helpWindow;
    }
    
    
    
    /**
     * Returns window with print options.
     * 
     * @return the printWindow (PrintWindow)
     */
    private PrintWindow getPrintWindow() {
        if (printWindow == null) {
        	printWindow = new PrintWindow();
        }
        return printWindow;
    }
    
    
    
    /**
     * Returns window with Form to add new employee.
     * 
     * @return the addEmployeeWindow (AddEmployeeWindow)
     */
    private AddEmployeeWindow getAddEmployeeWindow() {

    	addEmployeeWindow = new AddEmployeeWindow(this);
    	
    	addEmployeeWindow.addListener(new CloseListener() {
			   public void windowClose(CloseEvent e) {
				      addEmployeeWindow = null;
				   }
				});

    	return addEmployeeWindow;
    }
    
    
    
    /**
     * This function get information about status of filter bar
     * 
     * @return true if filter bar is visible another way return false 
     */
    public Boolean getFilterMode() {
    	return employeeList.isFilterBarVisible();
    }
    
    
    
    /**
     * This function change visible of filter bar.
     * If get true, show filter bar
     * If get false, hide filter bar
     * 
     * @param mode Boolean
     */
    public void setFilterMode(Boolean mode) {
    	employeeList.setFilterBarVisible(mode);
    }

    
    
	/**
	 * Returns helper to the connections to the database.
	 * 
	 * @return the dataSource - DatabaseConnection 
	 */
	public DatabaseConnection getDataSource() {
		return dbConnection;
	}

	
    
	/**
	 * Listening for a new event, complete the form if any catch 
	 */
	public void valueChange(ValueChangeEvent event) {
		
	       Property property = event.getProperty();
	       
	        if (property == employeeList) {
	            Item item = employeeList.getItem(employeeList.getValue());
	            if (item != employeeForm.getItemDataSource()) {
	                employeeForm.setItemDataSource(item, employeeList.getValue());
	            }
	        }
	}

	
	
	/**
	 * Event method which calls the appropriate functions. 
	 */
	public void buttonClick(ClickEvent event) {
		
		final Button source = event.getButton();

        if (source == search) {
        	showSearchWindow();
        } else if (source == help) {
            showHelpWindow();
        } else if (source == print) {
            showPrintWindow();
        } else if (source == newEmployee) {
            addNewEmployee();
        }

	}
    
	
	
    /**
     * Event method which select to current row.
     */
	@Override
	public void rowIdChange(RowIdChangeEvent event) {
		
		// Select the added item and fix the table scroll position
        employeeList.select(event.getNewRowId());
        employeeList.fixVisibleAndSelectedItem();
	}
	
}
