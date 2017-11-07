package ie.mid.model;

/**
 * Created by Cillian on 22/10/2017.
 */

public class CardData {
    private String data;
    private String dataType;
    private int dataIcon;

    public CardData(String data, String dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getDataIcon() {
        return dataIcon;
    }

    public void setDataIcon(int dataIcon) {
        this.dataIcon = dataIcon;
    }
}
