package com.example.descom.view;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.example.descom.DescomApplication;
import com.example.descom.database.DatabaseConnection;
import com.vaadin.data.Buffered;
import com.vaadin.data.Item;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickListener;

/**
 * This class show data at form about current employee.
 * Also contain methods for edit and add employee 
 * which affects changes in the database.
 *   
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class EmployeeForm extends Form implements ClickListener {

	// Create basic items for form
	private Button save   = new Button("Save", (ClickListener) this);
    private Button cancel = new Button("Cancel", (ClickListener) this);
    private Button edit	   = new Button("Edit", (ClickListener) this);
    private Button delete = new Button("Delete", (ClickListener) this);

    /**
     * Object to set current ID. Needed for later removal current object. 
     */
    private Object currentId;
    
    /**
     * Main object, which combines all the other classes.
     */
    private DescomApplication application;

    
    
    /**
     * Set in constructor elements to edit employee 
     * Enable buffering so that commit() must be called for the form before
     * input is written to the data. 
     * Form input is not written immediately through to the underlying object.
     * 
     * @param application
     */
    public EmployeeForm(DescomApplication application) {
        
    	this.application = application;
        
        setWriteThrough(false);

        // Split screen for footer where we can edit current employee
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addComponent(save);
        footer.addComponent(cancel);
        footer.addComponent(edit);
        footer.addComponent(delete);
        footer.setVisible(false);
        
        setFooter(footer);


        /**
         * Field factory for overriding how the component for city selection is created
         * Sets the field factory used by this Form to genarate Fields for properties. 
         * FormFieldFactory is used to create fields for form properties. 
         * DefaultFieldFactory is used by default.
         * 
         * Parameters:
         * @param fieldFactory 
         * 				the new factory used to create the fields.
         */
        setFormFieldFactory(new DefaultFieldFactory() {
        	        
            @Override
            public Field createField(Item item, Object propertyId, Component uiContext) {

                Field field = super.createField(item, propertyId, uiContext);
                
                if (propertyId.equals("ZIP_CODE")) {            	
                                  
                    // If don't want to display "null" to the user when the field is empty
                	TextField textfield = (TextField) field;      
                    textfield.setNullRepresentation("");
                    textfield.setRequired(true);       
                    
                    // Add a validator for postalCode and make it required 
                    textfield.addValidator(new RegexpValidator("[1-9][0-9]{4}", "Postal code must be a five digit number and cannot start with a zero."));            
                  
                } else if (propertyId.equals("EMAIL")) {          
                	
                    // Add a validator for email and make it required 
                    field.addValidator(new EmailValidator("Email must contain '@' and have full domain."));
                    field.setRequired(true);             
                    
                    // If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("FIRST_NAME")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("LAST_NAME")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("GENDER")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("BIRTH_DATE")) {
                	
                	field.setRequired(true);
                    
                } else if (propertyId.equals("HIRE_DATE")) {
                	
                	field.setRequired(true);     	
                    
                } else if (propertyId.equals("POSITION")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("SALARY")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                    // Add validator for salary
                    textfield.addValidator(new RegexpValidator("[1-9][0-9]{2,6}", "Salary must be a positive integer."));
                    
                } else if (propertyId.equals("DEPARTMENT")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("TAX_IDENTIFICATION_NUMBER")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                    // Add validator for salary tax identification numbe
                    textfield.addValidator(new RegexpValidator("[0-9]{2,9}","Tax identification number must be a positive integer."));
                    
                } else if (propertyId.equals("PERSONAL_IDENTIFICATION_NUMBER")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("TELEPHONE_WORK")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("TELEPHONE_HOME")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("ADRESS")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                    
                } else if (propertyId.equals("CITY")) {
                	
                	field.setRequired(true);
                	
                	// If don't want to display "null" to the user when the field is empty
                    TextField textfield = (TextField) field;
                    textfield.setNullRepresentation("");
                } 
                
                // Set the correct caption to each field
                for (int i = 0; i < DatabaseConnection.NATURAL_COL_ORDER.length; i++) {
                    if (DatabaseConnection.NATURAL_COL_ORDER[i].equals(propertyId)) {
                        field.setCaption(DatabaseConnection.COL_HEADERS_ENGLISH[i]);
                    }
                }

                field.setWidth("300px");
                return field;
            }
        });
    }

    
    
    /**
     * Call when the listener noticed activity
     */
    public void buttonClick(ClickEvent event) {
        
        Button source = event.getButton();
        
        if (source == save) {
     	
            // If the given input is not valid there is no point in continuing
            if (!isValid()) {
                return;
            }          
            commit();            
        } else if (source == cancel) {
            discard();
        } else if (source == delete) {
        	deleteItem();
    	}else if (source == edit) {
            setReadOnly(false);
        }
    }
    
    
    
    /**
     * Method deleting item from database
     */
    public void deleteItem() {

    	// Clear the form
    	setItemDataSource(null);

    	// Hide form
    	getFooter().setVisible(false);
    	
    	// Delete current item from Container
    	application.getDataSource().getEmployeeContainer().removeItem(currentId);
    	
    	// Save changes
    	commit();
		
	}



	/**
	 * This method set item for data source
	 * 
	 * @param newDataSource
	 * @param id current ID for element from list
	 */
    public void setItemDataSource(Item newDataSource, Object id) {
	
        if (newDataSource != null) {
        	
            setReadOnly(false);
            List<Object> orderedProperties = Arrays.asList(DatabaseConnection.NATURAL_COL_ORDER);
            
            // Call method from Form
            super.setItemDataSource(newDataSource, orderedProperties);
                 
            // get id current object. Needed for later removal current object.
            currentId = id;
            
            setReadOnly(true);
            getFooter().setVisible(true);
            
        } else {
        	
        	// Call method from Form
            super.setItemDataSource(null);
            getFooter().setVisible(false);
            
        }       
    }

    
    
    /**
     * To hide items in form 
     */
    @Override
    public void setReadOnly(boolean readOnly) {
        
    	// Call method from Form
    	super.setReadOnly(readOnly);
    	
        save.setVisible(!readOnly);
        cancel.setVisible(!readOnly);
        edit.setVisible(readOnly);
        
    }

    
    
    /**
     * Method to add new employee
     */
    public void addEmployee() {
    	 	
    	List<Object> orderedProperties = Arrays.asList(DatabaseConnection.NATURAL_COL_ORDER);
      	                
        // Create a new item and set it as the data source for this form
        Object tempItemId = application.getDataSource().getEmployeeContainer().addItem();
        
        setItemDataSource(application.getDataSource().getEmployeeContainer().getItem(tempItemId));
        setVisibleItemProperties(orderedProperties);
          	
    }
        
    

    /**
     * Commit function which override normal commit. 
     * First - save changes in Container
     * Second - try save results into database
     */
    @Override
    public void commit() throws Buffered.SourceException {
    	
        // Commit the data entered to the person form to the actual item.
        super.commit();
        
        // Commit changes to the database. 
        try {
        	application.getDataSource().getEmployeeContainer().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        setReadOnly(true);
    }

    
    
    /**
     * Back changes if user cancel edit mode 
     */
    @Override
    public void discard() throws Buffered.SourceException {
    	
        super.discard();
        
        // On discard roll back the changes.
        try {
        	application.getDataSource().getEmployeeContainer().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
                
        setReadOnly(true);
    }


}
