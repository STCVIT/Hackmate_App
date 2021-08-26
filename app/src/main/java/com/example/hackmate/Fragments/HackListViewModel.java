package com.example.hackmate.Fragments;

import androidx.lifecycle.ViewModel;

public class HackListViewModel extends ViewModel {
    private String status = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
