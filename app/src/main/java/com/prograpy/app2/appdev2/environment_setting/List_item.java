package com.prograpy.app2.appdev2.environment_setting;

/**
 * Created by User on 2018-05-14.
 */

public class List_item {
    private int setting_image;
    private String setting_text;

    public int getSetting_image() {
        return setting_image;
    }

    public void setSetting_image(int setting_image) {
        this.setting_image = setting_image;
    }

    public String getSetting_text() {
        return setting_text;
    }

    public void setSetting_text(String setting_text) {
        this.setting_text = setting_text;
    }

    public List_item(int setting_image, String setting_text)
    {
        this.setting_image = setting_image;
        this.setting_text = setting_text;

    }
}
