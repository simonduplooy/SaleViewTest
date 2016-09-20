package application;

import java.time.LocalDateTime;

public class ProductOrder {
	
	private final LocalDateTime creationTime;
	
	public ProductOrder() {
		creationTime = LocalDateTime.now();
	}
	
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
	

}
