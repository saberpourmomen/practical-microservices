package org.apsystem.product_service.dto;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProductCategory {

    Electronics("Electronics", "Phones, laptops, headphones"),
    Books("Books", "hard book, pdf, epub"),
    Apparel("Apparel", "Tops, jeans, dresses, accessories"),
    Home_Goods("Home_Goods","Furniture, decor, appliances"),
    Beauty_Care("Beauty_Care","Skincare, makeup"),
    Sports("Sports","Gear, equipment");

    private final String title;
    private final String description;

    ProductCategory(String title, String description){
        this.title=title;
        this.description=description;
    }

    public ProductCategory getProduct(String title){
        return Arrays.stream(values())
                .filter(category->category.title.equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Unknown category::"+title));
    }

}
