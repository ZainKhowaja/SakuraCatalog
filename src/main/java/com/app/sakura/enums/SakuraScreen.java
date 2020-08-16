package com.app.sakura.enums;

public enum SakuraScreen {
	LOGIN("Login.fxml"), MAIN_SCREEN("MainView.fxml"), ADD_PRODUCT("AddProduct.fxml"),
	PRODUCT_SEARCH("ProductSearch.fxml");

	private String screenName;

	SakuraScreen(String screenName) {
		this.screenName = screenName;
	}

	public String getScreenName() {
		return this.screenName;
	}
}
