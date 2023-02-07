package com.pedroesferreira.inventoryservice;

import com.pedroesferreira.inventoryservice.Model.Inventory;
import com.pedroesferreira.inventoryservice.Repository.InventoryRepository;
import com.pedroesferreira.inventoryservice.Service.InventoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("item1_code");
			inventory.setQuantity(10);

			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("item2_code");
			inventory2.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory2);
		};
	}

}
