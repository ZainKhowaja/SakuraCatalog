package com.app.sakura.util;

import com.app.sakura.entity.Filter;
import com.app.sakura.entity.Manufacturer;
import com.app.sakura.entity.TypeDetail;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ComboBoxUtil {

    public static <T> FilteredList<T> addSupportForSearch(ComboBox<T> comboBox, FilteredList<T> filteredItems){

        comboBox.addEventHandler(KeyEvent.KEY_PRESSED, event ->
        {
            if(!comboBox.isShowing()) return;

            comboBox.setEditable(true);
            comboBox.getEditor().setText("");
            comboBox.getEditor().clear();
        });
        T selectedItem[] =  (T[])new Object[1];
        comboBox.showingProperty().addListener((observable, oldValue, newValue) ->
        {
            if(newValue)
            {
                @SuppressWarnings("unchecked")
                ListView<T> lv = ((ComboBoxListViewSkin<T>) comboBox.getSkin()).getListView();
                ComboBoxListViewSkin<T> skin = ((ComboBoxListViewSkin<T>) comboBox.getSkin());
                skin.getPopupContent().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                    if (e.getCode() == KeyCode.SPACE) {
                        e.consume();
                    }
                });
                comboBox.setSkin(skin);

                Platform.runLater(() ->
                {
                    if(selectedItem[0] == null) // first use
                    {
                        double cellHeight = ((Control) lv.lookup(".list-cell")).getHeight();
                        lv.setFixedCellSize(cellHeight);
                    }
                });

                lv.scrollTo(comboBox.getValue());
            }
            else
            {
                T value = comboBox.getValue();
                if(value != null) selectedItem[0] = value;

                comboBox.setEditable(false);

                Platform.runLater(() ->
                {
                    comboBox.getSelectionModel().select(selectedItem[0]);
                    comboBox.setValue(selectedItem[0]);
                });
            }
        });

        // Add a listener to the textProperty of the combobox editor. The
        // listener will simply filter the list every time the input is changed
        // as long as the user hasn't selected an item in the list.
        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = comboBox.getEditor();
            final T selected = comboBox.getSelectionModel().getSelectedItem();

            // This needs run on the GUI thread to avoid the error described
            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
            Platform.runLater(() -> {
                // If the no item in the list is selected or the selected item
                // isn't equal to the current input, we refilter the list.
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        // We return true for any items that starts with the
                        // same letters as the input. We use toUpperCase to
                        // avoid case sensitivity.
                        if(item instanceof Manufacturer){
                            Manufacturer man = (Manufacturer) item;
                            if (man.getName().toUpperCase().startsWith(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        }else if(item instanceof Filter){
                            Filter man = (Filter) item;
                            if (man.getName().toUpperCase().startsWith(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        }else if(item instanceof TypeDetail){
                            TypeDetail man = (TypeDetail) item;
                            if (man.getName().toUpperCase().startsWith(newValue.toUpperCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                        return false;
                    });
                    comboBox.setItems(filteredItems);
                }
            });
        });
        return filteredItems;
    }
}
