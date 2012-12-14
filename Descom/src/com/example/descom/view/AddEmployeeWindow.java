package com.example.descom.view;


import com.example.descom.DescomApplication;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;


/**
 * This class show Form for add new employee.
 * This class inherits from Window, but also use class EmployeeForm 
 * to present Form.
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class AddEmployeeWindow extends Window {
	
	// Create new button items for window
	private Button save = new Button("Save");
	private Button cancel = new Button("Cancel");
	
	/**
	 * Main object, which combines all the other classes.
	 */
	@SuppressWarnings("unused")
	private DescomApplication application;
	
	/**
	 * Object need to present form into window. 
	 */
	private EmployeeForm form;

	
	
	/**
	 * This constructor create all window with buttons, forms, layouts.
	 * 
	 * @param application
	 */
	public AddEmployeeWindow(DescomApplication application) {
		
		this.application = application;

		// Make the window modal, which will disable all other components while it is visible
		setModal(true);

		// Make the sub window 40% the size of the browser window
		setWidth("40%");

		// Center the window both horizontally and vertically in the browser window
		center();
		
		// React immediately for user events
		setImmediate(true);

		// Set caption for window
		setCaption("Add new Employee");

		// Create VerticalLayout for HorizontalLayout and Form
		VerticalLayout verticalLayout = new VerticalLayout();
		
		// Create HorizontalLayout for buttons
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		 
		// Add buttons to window
		horizontalLayout.addComponent(save);
		horizontalLayout.addComponent(cancel);
		
		// Set parameters for HorizontalLayout
		horizontalLayout.setSpacing(true);
		
		// Add Form to add new employee in Grid
		verticalLayout.addComponent(createForm(application));
		
		// Add Buttons in HorizontalLayout to VerticalLayout
		verticalLayout.addComponent(horizontalLayout);
		
		// Set parameters for VerticalLayout
		verticalLayout.setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);
		
		// Add all to window
		getWindow().setContent(verticalLayout);
		                    		
		// Add shortcut to close application. I chose ESC key for this.
		setCloseShortcut(KeyCode.ESCAPE, null);        
		
		// Set listener method for this item, after click call to save employee
		save.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				form.commit();
				AddEmployeeWindow.this.close();			
			}
		});
		
		// Set listener method for this item, after click call to cancel adding employee
		cancel.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {				 
				form.discard();
				AddEmployeeWindow.this.close();
			}
		});	
	}

	
	
	/**
	 * This function create form for new employee. 
	 * Call method form class EmployeeForm.
	 * 
	 * @param application - DescomApplication
	 * @return (Form) the form - EmployeeForm
	 */
	private Form createForm(DescomApplication application) {
		
		form = new EmployeeForm(application);
		form.addEmployee();
		
		return form;
	}


}
