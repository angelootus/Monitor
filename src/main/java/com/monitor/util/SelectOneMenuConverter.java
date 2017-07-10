package com.monitor.util;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.monitor.model.dto.CampanaDTO;
import com.monitor.model.dto.PlazaDTO;

@FacesConverter("selectOneMenuConverter")
public class SelectOneMenuConverter implements Converter {
 
    @Override
    public Object getAsObject(final FacesContext arg0, final UIComponent arg1, final String objectString) {
        if (objectString == null) {
            return null;
        }
 
        return fromSelect(arg1, objectString);
    }
 
    private String serialize(final Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass() + "@" + object.hashCode();
    }
 
    private Object fromSelect(final UIComponent currentcomponent, final String objectString) {
 
        if (currentcomponent.getClass() == UISelectItem.class) {
            final UISelectItem item = (UISelectItem) currentcomponent;
            final Object value = item.getValue();
            if (objectString.equals(serialize(value))) {
                return value;
            }
        }
 
        if (currentcomponent.getClass() == UISelectItems.class) {
            final UISelectItems items = (UISelectItems) currentcomponent;
            final List<Object> elements = (List<Object>) items.getValue();
            for (final Object element : elements) {
                if (objectString.equals(serialize(element))) {
                    return element;
                }
            }
        }
 
 
        if (!currentcomponent.getChildren().isEmpty()) {
            for (final UIComponent component : currentcomponent.getChildren()) {
                final Object result = fromSelect(component, objectString);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
 
    @Override
    public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object object) {
        if(object != null) {
        	String value = "";
        	if(object instanceof PlazaDTO){
        		value = String.valueOf(((PlazaDTO) object).getCvePlaza());
        	} else if (object instanceof CampanaDTO){
        		value = String.valueOf(((CampanaDTO) object).getCveCampana());
        	}
    		return value;
        }
        else {
            return null;
        }
//        return serialize(object);
    }
 
}