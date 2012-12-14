package com.example.descom.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * Class to display window with Print Option. Basic option is generate results
 * to PDF file.
 * 
 * @author m4gik
 * 
 */
@SuppressWarnings("serial")
public class PrintWindow extends Window {

	private Button print = new Button("PDF");

	/**
	 * Create constructor with properties for windows
	 */
	public PrintWindow() {

		// Make the window modal, which will disable all other components while
		// it is visible
		setModal(true);

		// Make the sub window 30% the size of the browser window
		setWidth("30%");

		// Center the window both horizontally and vertically in the browser window
		center();

		// Set caption for window
		setCaption("Print into PDF");

		// Set information in label
		addComponent(new Label("Save your results into PDF file. Which will be generated automatically."));

		// Add button to window
		addComponent(print);

		// Make the button 100% the size of the current window
		print.setWidth("100%");
		
		// Set listener method for this item, after click call function generated PDF
		print.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				// TODO is waiting for version 2.0
				PrintWindow.this.close(); // Change to function to save into PDF
			}
		});

		// Set image for button
		print.setIcon(new ThemeResource("icons/32/pdf.png"));

		// Add shortcut to close application. I chose ESC key for this.
		setCloseShortcut(KeyCode.ESCAPE, null);

	}

}
