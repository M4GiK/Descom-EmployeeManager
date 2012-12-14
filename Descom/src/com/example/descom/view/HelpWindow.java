package com.example.descom.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * Class to display window with help.
 * Containing information about functionality 
 * this application  
 * 
 * @author m4gik
 *
 */
@SuppressWarnings("serial")
public class HelpWindow extends Window {
	
	// Some useless information 
    private static final String HELP_HTML_SNIPPET = "This application"
            + " was created as an assignment from Descom. <br />" 
            + " If you have any questions or problems, please"
            + " contact me. <br /> My e-mail address: "
            + " <a href=\"mailto:michal.szczygiel@wp.pl\"> "
            + " <strong> michal.szczygiel@wp.pl </strong></a> "
    		+ " <br /><br /> Application functionality: "
            + " <ul><li>Intuitive graphical interface</li>"
    		+ " <li>Adding new employees</li>"
    		+ " <li>Modifying employees through form or through table's context menu action</li>"
    		+ " <li>Deleteing employees through form or through table's context menu action</li>"
    		+ " <li>Searching and filtring data</li>"
    		+ " <li>Search results could be sortable</li>"    		
    		+ "</ul>";
    		

    /**
     * Create constructor with properties for windows
     */
    public HelpWindow() {
    	
    	//Set title for window
        setCaption("Descom Application help");
        
        // Add text to window
        addComponent(new Label(HELP_HTML_SNIPPET, Label.CONTENT_XHTML));
        
        // Set default size 
        setWidth(500, Sizeable.UNITS_PIXELS);
        setHeight(300, Sizeable.UNITS_PIXELS);
        
        // Add shortcut to close application. I chose ESC key for this.
        setCloseShortcut(KeyCode.ESCAPE, null);
        
        // Center the window both horizontally and vertically in the browser window
        center();
        
        
    }

}
