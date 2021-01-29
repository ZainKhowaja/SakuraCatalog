package com.app.sakura.enums;

public enum SakuraScreen {
	LOGIN("Login.fxml"), MAIN_SCREEN("MainView.fxml"), ADD_PRODUCT("AddProduct.fxml"),
	PRODUCT_SEARCH("ProductSearch.fxml"), ADD_REFERENCE("AddReference.fxml") , ADD_BRAND("AddBrand.fxml")
	, ADD_GENERIC_WINDOW("AddGenericWindow.fxml"),UPDATE_PRODUCT("UpdateProduct.fxml");

	private String screenName;

	SakuraScreen(String screenName) {
		this.screenName = screenName;
	}

	public String getScreenName() {
		return this.screenName;
	}
}
