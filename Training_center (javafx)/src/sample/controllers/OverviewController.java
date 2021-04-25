package sample.controllers;

import sample.models.ApiModel;

public abstract class OverviewController {
    public ApiModel item;

    public ApiModel getItem() {
        return item;
    }

    public void setItem(ApiModel item) {
        this.item = item;
    }
}
