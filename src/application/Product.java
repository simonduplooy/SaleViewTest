package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
	
	final StringProperty nameProperty;
	final DoubleProperty costProperty;
	
	public Product() {
		nameProperty = new SimpleStringProperty();
		costProperty = new SimpleDoubleProperty();
	}
	
	public StringProperty nameProperty() {
		return nameProperty();
	}
	
	public String getName() {
		return nameProperty.getValue();
	}
	
	public void setName(final String name) {
		nameProperty.set(name);
	}
	
	public DoubleProperty costProperty() {
		return costProperty;
	}

	public Double getCost() {
		return costProperty.getValue();
	}
	
	public void setCost(final Double cost) {
		costProperty.setValue(cost);
	}
	
}
