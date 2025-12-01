package ru.mirea.vinokurovazo.fragmentmanagerapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    private final MutableLiveData<String> selectedItem = new MutableLiveData<>();

    public void selectItem(String item) {
        selectedItem.setValue(item);
    }

    public MutableLiveData<String> getSelectedItem() {
        return selectedItem;
    }
}