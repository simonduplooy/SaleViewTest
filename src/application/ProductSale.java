package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ProductSale extends Sale {

	final ObjectProperty<Product> productProperty;
	final IntegerProperty countProperty;
	final DoubleProperty discountProperty;
	final DoubleProperty subTotalProperty;
	
	public ProductSale() {
		productProperty = new SimpleObjectProperty<Product>();
		countProperty = new SimpleIntegerProperty();
		discountProperty = new SimpleDoubleProperty();
		subTotalProperty = new SimpleDoubleProperty();
	}
	
	public ObjectProperty<Product> productProperty() {
		return productProperty;
	}
	
	public Product getProduct() {
		return productProperty.getValue();
	}
	
	
	public void setProduct(final Product product) {
		productProperty.setValue(product);
		descriptionProperty().bind(product.nameProperty);
		
		subTotalProperty.bind(product.costProperty().multiply(countProperty));
		totalProperty().bind(subTotalProperty.subtract(discountProperty));
	}
	
	public IntegerProperty countProperty() {
		return countProperty;
	}
	
	public Integer getCount() {
		return countProperty.getValue();
	}
	
	public void setCount(final Integer count) {
		countProperty.setValue(count);
	}
	
	public void increaseCount(final Integer count) {
		Integer currentCount = countProperty.getValue();
		currentCount += count;
		countProperty.setValue(currentCount);
	}
	
	public DoubleProperty discountProperty() {
		return discountProperty;
	}
	
	public Double getDiscount() {
		return discountProperty.getValue();
	}
	
	public void setDiscount(final Double count) {
		discountProperty.setValue(count);
	}
	
	public DoubleProperty subTotalProperty() {
		return subTotalProperty;
	}
	
	public Double getSubTotal() {
		return subTotalProperty.getValue();
	}
	
	@Override
	public String toString() {
		return String.format("%s x %d @ %s %s",getDescription(),getCount(),getProduct().getCost(),getTotal());
	}
}
