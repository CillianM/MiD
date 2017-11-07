package ie.mid.model;

import java.util.ArrayList;

/**
 * Created by Cillian on 22/10/2017.
 */

public class CardType {

    private String title;
    private String description;
    private int defaultColor;
    private String imageUrl;
    private ArrayList<CardData> dataList;
    private String status;

    public CardType() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<CardData> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<CardData> dataList) {
        this.dataList = dataList;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
