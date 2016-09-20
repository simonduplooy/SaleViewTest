package application;

public class OpenItemSale extends Sale {
	
	public OpenItemSale() {
		setDescription("Open Item");
	}
	
	@Override
	public String toString() {
		return String.format("%s %s",getDescription(),getTotal());
	}
}
