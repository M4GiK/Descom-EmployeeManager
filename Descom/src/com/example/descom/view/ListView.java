package com.example.descom.view;

import com.vaadin.ui.VerticalSplitPanel;

/**
 * This class split vertically workspace to fill with data about employee.
 * The upper contain list of employees
 * Under contain form of current employee 
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class ListView extends VerticalSplitPanel {
	
	/**
	 * This constructor add to VerticalSplitPanel components like employeeList and employeeForm
	 * 
	 * @param employeeList
	 * @param employeeForm
	 */
    public ListView(EmployeeList employeeList, EmployeeForm employeeForm) {
        
    	// Set name for style
    	addStyleName("view");
    	
    	// Add to upper employeeList
        setFirstComponent(employeeList);
        
        // Add to lower employeeForm
        setSecondComponent(employeeForm);
        
        // Set upper component 60% the size of the VerticalSplitPanel
        setSplitPosition(60);
        
    }
}