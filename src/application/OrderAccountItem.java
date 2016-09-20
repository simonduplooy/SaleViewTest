package application;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.CurrencyStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;

public class OrderAccountItem extends VBox {
	
	private final ProductOrder order;
	private final Label dateTimeLabel;
	private final DoubleProperty totalProperty;
	private NumberBinding totalBinding;
	
	public OrderAccountItem() {
		
		totalProperty = new SimpleDoubleProperty();
		
		order = new ProductOrder();
		
		final LocalDateTimeStringConverter converter = new LocalDateTimeStringConverter();
		dateTimeLabel = new Label(converter.toString(order.getCreationTime()));
		getChildren().add(dateTimeLabel);
	}
	
	public DoubleProperty totalProperty() {
		return totalProperty;
	}
	
	public void addProductSale(final ProductSale sale) {
		final ProductSaleAccountLine saleLine = new ProductSaleAccountLine(sale);
		getChildren().add(saleLine);
		
		if(null == totalBinding) {
			totalBinding = sale.totalProperty().add(0.0);
		} else {
			totalBinding = totalBinding.add(sale.totalProperty());
		}
		
		totalProperty.bind(totalBinding);
		
		System.out.println("Total: "+totalProperty.getValue());
	}
	
	
	private class SaleAccountLine extends VBox {
		
		private final Sale sale;
		
		private SaleAccountLine(final Sale sale) {
			this.sale = sale;
		}
		
		public Sale getSale() {
			return sale;
		}
		
	}
	
	private class ProductSaleAccountLine extends SaleAccountLine {
		
		private final DoubleProperty negativeDiscountProperty;
		
		public ProductSaleAccountLine(final ProductSale sale) {
			super(sale);

			negativeDiscountProperty = new SimpleDoubleProperty();
			
			final CurrencyStringConverter currencyConverter = new CurrencyStringConverter();
			
			final HBox productLineHBox = new HBox();
			productLineHBox.getStyleClass().add("account-sale-line");
			productLineHBox.setOnContextMenuRequested((event)-> handleProductContextMenuRequested(event));
			productLineHBox.setSpacing(5.0);
			getChildren().add(productLineHBox);
	
			final Label productDescriptionLabel = new Label();
			productDescriptionLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(productDescriptionLabel,Priority.ALWAYS);
			productDescriptionLabel.textProperty().bind(sale.descriptionProperty());
			productLineHBox.getChildren().add(productDescriptionLabel);
			
			final Label productCostLabel = new Label();
			productCostLabel.setMinWidth(USE_PREF_SIZE);
			productCostLabel.setMaxWidth(USE_PREF_SIZE);
			final StringProperty costProperty = new SimpleStringProperty();
			Bindings.bindBidirectional(costProperty,sale.getProduct().costProperty(),currencyConverter);
			productCostLabel.textProperty().bind(Bindings.concat(" x ",sale.countProperty()," @ ",costProperty));
			productLineHBox.getChildren().add(productCostLabel);
			
			final Label productTotalLabel = new Label();
			productTotalLabel.getStyleClass().add("account-sale-subtotal");
			productTotalLabel.setMinWidth(USE_PREF_SIZE);
			productTotalLabel.setMaxWidth(USE_PREF_SIZE);
			Bindings.bindBidirectional(productTotalLabel.textProperty(),sale.subTotalProperty(),currencyConverter);
			productLineHBox.getChildren().add(productTotalLabel);
			
			final HBox discountLineHBox = new HBox();
			discountLineHBox.getStyleClass().add("account-sale-line");
			discountLineHBox.setOnContextMenuRequested((event)-> handleDiscountContextMenuRequested(event));
			getChildren().add(discountLineHBox);
			
			final Label discountDescriptionLabel = new Label("Discount");
			discountDescriptionLabel.setMaxWidth(Double.MAX_VALUE);
			HBox.setHgrow(discountDescriptionLabel,Priority.ALWAYS);
			discountLineHBox.getChildren().add(discountDescriptionLabel);
			
			final Label discountTotalLabel = new Label();
			discountTotalLabel.getStyleClass().add("account-sale-subtotal");
			discountTotalLabel.setMinWidth(USE_PREF_SIZE);
			discountTotalLabel.setMaxWidth(USE_PREF_SIZE);
			negativeDiscountProperty.bind(sale.discountProperty().multiply(-1.0));
			Bindings.bindBidirectional(discountTotalLabel.textProperty(),negativeDiscountProperty,currencyConverter);
			discountLineHBox.managedProperty().bind(discountLineHBox.visibleProperty());
			discountLineHBox.visibleProperty().bind(Bindings.notEqual(sale.discountProperty,0.0,0.001));
			discountLineHBox.getChildren().add(discountTotalLabel);
			
		}
		
		private void handleProductContextMenuRequested(final ContextMenuEvent event) {
			final ContextMenu contextMenu = new ContextMenu();
			final MenuItem updateMenuItem = new MenuItem("Product Context Menu");
			contextMenu.getItems().add(updateMenuItem);
			contextMenu.show(getScene().getWindow(),event.getScreenX(),event.getScreenY());
		}
		
		private void handleDiscountContextMenuRequested(final ContextMenuEvent event) {
			final ContextMenu contextMenu = new ContextMenu();
			final MenuItem menuItem = new MenuItem("Discount Context Menu");
			contextMenu.getItems().add(menuItem);
			contextMenu.show(getScene().getWindow(),event.getScreenX(),event.getScreenY());
		}
		
	}
}
