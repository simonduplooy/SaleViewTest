package application;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.CurrencyStringConverter;

public class SampleController extends BorderPane {
	
	private Sale activeSale;
	private OrderAccountItem orderAccountItem;
	
	@FXML
	private ScrollPane orderScrollPane;
	@FXML
	private Label totalLabel;

	
	public SampleController(final Stage primaryStage) throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Sample.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		loader.load();
		
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	@FXML
	private void initialize()  {
		
		orderAccountItem = new OrderAccountItem();
		orderScrollPane.setContent(orderAccountItem);
		
		final CurrencyStringConverter converter = new CurrencyStringConverter();
		Bindings.bindBidirectional(totalLabel.textProperty(),orderAccountItem.totalProperty(),converter);
		
		orderAccountItem.heightProperty().addListener((number) -> handleLayout());
		
		}
	
	@FXML
	private void handleAddBurger() {

		final Product product = new Product();
		product.setName("Burger with fries and tamato");
		product.setCost(10.0);
		
		final ProductSale sale = new ProductSale();
		sale.setProduct(product);
		sale.setCount(1);
		
		activeSale = sale;
		orderAccountItem.addProductSale(sale);

	}
	
	@FXML
	private void handleAddOpenItem() {
		final OpenItemSale sale = new OpenItemSale();
		
		activeSale = sale;
		//TODO
		//orderVBox.addSale(sale);
	}

	@FXML
	private void handleIncreaseCount() {

		if(activeSale instanceof ProductSale) {
			final ProductSale productSale = (ProductSale)activeSale;
			productSale.increaseCount(1);
		} else {
			activeSale.setTotal(activeSale.getTotal()+10.0);
		}
		
		
	}
	
	@FXML
	private void handleAddDiscount() {

		if(activeSale instanceof ProductSale) {
			final ProductSale productSale = (ProductSale)activeSale;
			productSale.setDiscount(productSale.getDiscount()+10.0);
		}
		orderScrollPane.setVvalue(1.0);
	}
	
	private void handleLayout() {
		orderScrollPane.setVvalue(orderScrollPane.getVmax());
	}
	
}
