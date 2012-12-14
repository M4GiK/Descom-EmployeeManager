package com.example.descom.view;

import java.text.DateFormat;
import java.util.Locale;

import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import com.vaadin.terminal.Resource;
import com.vaadin.ui.DateField;

/**
 * Class contain custom filter decorator for filter table. 
 * 
 * @author m4gik
 *
 */
public class SearchFilterDecorator implements FilterDecorator {

	/**
	 * Returns the filter display name for the given enum value when filtering the given property id. 
	 * 
	 * Parameters:
	 * @param propertyId 
	 * 				ID of the property the filter is attached to.
	 * @param value 
	 * 				Value of the enum the display name is requested for.
	 * 
	 * Returns:
	 * @return UI Display name for the enum value.
	 * 
	 */
	@Override
	public String getEnumFilterDisplayName(Object propertyId, Object value) {
		return null;
	}

	
	
	/**
	 * Returns the filter icon for the given enum value when filtering the given property id.
	 * 
	 * Parameters:
	 * @param propertyId 
	 * 				ID of the property the filter is attached to.
	 * @param value 
	 * 				Value of the enum the icon is requested for.
	 * 
	 * Returns:
	 * @return Resource for the icon of the enum value.
	 */
	@Override
	public Resource getEnumFilterIcon(Object propertyId, Object value) {
		return null;
	}

	
	
	/**
	 * Returns the filter display name for the given boolean value when filtering the given property id.
	 * 
	 * Parameters:
	 * @param propertyId 
	 * 				ID of the property the filter is attached to.
	 * @param value 
	 * 				Value of boolean the display name is requested for.
	 * 
	 * Returns:
	 * @return UI Display name for the given boolean value.
	 */
	@Override
	public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
        if ("validated".equals(propertyId)) {
            return value ? "Validated" : "Not validated";
        }
		return null;
	}
	
	

	/**
	 * Returns the filter icon for the given boolean value when filtering the given property id.
	 * 
	 * Parameters:
	 * @param propertyId 
	 * 				ID of the property the filter is attached to.
	 * @param value 
	 * 				Value of boolean the icon is requested for.
	 * 
	 * Returns:
	 * @return Resource for the icon of the given boolean value.
	 */
	@Override
	public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
		return null;
	}

	
	
	/**
	 * Returns whether the text filter should update as the user types. 
	 * This uses TextChangeEventMode.LAZY
	 * 
	 * Parameters:
	 * @param propertyId
	 * 
	 * Returns: true if the text field should use a TextChangeListener.
	 */
	@Override
	public boolean isTextFilterImmediate(Object propertyId) {
		return true;
	}

	
	
	/**
	 * The text change timeout dictates how often text change events are communicated to the application, 
	 * and thus how often are the filter values updated.
	 * 
	 * Parameters:
	 * @param propertyId
	 * 
	 * Returns: the timeout in milliseconds.
	 */
	@Override
	public int getTextChangeTimeout(Object propertyId) {	
		return 500;
	}

	
	
	/**
	 * Return display caption for the From field
	 * 
	 * Returns: caption for From field.
	 */
	@Override
	public String getFromCaption() {
		return "Start date:";
	}

	
	
	/**
	 * Return display caption for the To field
	 * 
	 * Returns: caption for To field.
	 */
	@Override
	public String getToCaption() {
		return "End date:";
	}

	
	
	/**
	 * Return display caption for the Set button
	 * 
	 * Returns: caption for Set button.
	 */
	@Override
	public String getSetCaption() {
		return null;
	}
	
	

	/**
	 * Return display caption for the Clear button
	 * 
	 * Returns: caption for Clear button.
	 */
	@Override
	public String getClearCaption() {
		return null;
	}

	
	
	/**
	 * Return DateField resolution for the Date filtering of the property ID. 
	 * This will only be called for Date - typed properties. 
	 * Filtering values output by the FilteringTable will also be truncated to this resolution.
	 * 
	 * Parameters:
	 * @param propertyId 
	 * 				ID of the property the resolution will be applied to
	 * 
	 * Returns: A resolution defined in DateField.
	 */
	@Override
	public int getDateFieldResolution(Object propertyId) {
		return DateField.RESOLUTION_DAY;
	}

	
	
	/**
	 * Returns the DateFormat object to be used for formatting the date/time values 
	 * shown in the filtering field of the given property ID. 
	 * Note that this is completely independent from the resolution set for the property, 
	 * and is used for display purposes only.
	 * 
	 * Parameters:
	 * @param propertyId 
	 * 				ID of the property the format will be applied to
	 * 
	 * Returns: A DateFormat or null to use the default formatting.
	 */
	@Override
	public DateFormat getDateFormat(Object propertyId) {
		return DateFormat.getDateInstance(DateFormat.SHORT, new Locale("fi", "FI"));
	}

	
	
	/**
	 * Return the string that should be used as an "input prompt" when no filtering 
	 * is made on a filter component.
	 * 
	 * Returns: String to show for no filter defined.
	 */
	@Override
	public String getAllItemsVisibleString() {
		return "Show all";
	}

	
	
	/**
	 * Return configuration for the numeric filter field popup.
	 * 
	 * Returns: Configuration for numeric filter.
	 */
	@Override
	public NumberFilterPopupConfig getNumberFilterPopupConfig() {
		return null;
	}
	
	

	/**
	 * Defines whether a popup-style numeric filter should be used for the property with the given ID. 
	 * The types Integer, Long, Float and Double are considered to be 'numeric' within this context.
	 * Specified by: usePopupForNumericProperty(...) in FilterDecorator.
	 * 
	 * Parameters:
	 * @parm propertyId 
	 * 				ID of the property the popup will be applied to.
	 * 
	 * Returns: 
	 * @return true 
	 * 				to use popup-style, false to use a TextField.
	 */
	@Override
	public boolean usePopupForNumericProperty(Object propertyId) {
		return true;
	}
}
