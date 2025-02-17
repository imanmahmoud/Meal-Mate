package com.example.mealmate.model.categories;

import java.util.List;

public class CategoryResponse {
    private List<CategoryModel> categories;

    public List<CategoryModel> getCategories() {
        return categories;
    }
    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}
