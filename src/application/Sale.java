package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sale {
	
	private final StringProperty descriptionProperty;
	private final DoubleProperty totalProperty;

	
	public Sale() {
		descriptionProperty = new SimpleStringProperty();
		totalProperty = new SimpleDoubleProperty();
	}
	
	public StringProperty descriptionProperty(){
		return descriptionProperty;
	}
	
	public String getDescription() {
		return descriptionProperty.getValue();
	}
	
	public void setDescription(final String description) {
		descriptionProperty.setValue(description);
	}
	
	public DoubleProperty totalProperty() {
		return totalProperty;
	}
	
	public Double getTotal() {
		return totalProperty.getValue();
	}
	
	public void setTotal(final Double cost) {
		totalProperty.setValue(cost);
	}

}
