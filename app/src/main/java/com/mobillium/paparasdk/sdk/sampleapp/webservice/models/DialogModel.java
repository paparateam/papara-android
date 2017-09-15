package com.mobillium.paparasdk.sdk.sampleapp.webservice.models;

/**
 * Created by oguzhandongul on 03/06/2017.
 */

public class DialogModel {
    private String title;
    private String content;
    private String positive;
    private String negative;
    private int color;
    private int icon;

    public DialogModel() {
    }

    public DialogModel(String content, String positive, String negative, int color, int icon) {
        this.content = content;
        this.positive = positive;
        this.negative = negative;
        this.color = color;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public DialogModel setTitle(String title) {
        this.title = title;
        return DialogModel.this;
    }

    public String getContent() {
        return content;
    }

    public DialogModel setContent(String content) {
        this.content = content;
        return DialogModel.this;
    }

    public String getPositive() {
        return positive;
    }

    public DialogModel setPositive(String positive) {
        this.positive = positive;
        return DialogModel.this;
    }

    public String getNegative() {
        return negative;
    }

    public DialogModel setNegative(String negative) {
        this.negative = negative;
        return DialogModel.this;
    }

    public int getColor() {
        return color;
    }

    public DialogModel setColor(int color) {
        this.color = color;
        return DialogModel.this;
    }

    public int getIcon() {
        return icon;
    }

    public DialogModel setIcon(int icon) {
        this.icon = icon;
        return DialogModel.this;
    }
}

