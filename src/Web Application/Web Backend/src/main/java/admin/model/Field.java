package admin.model;


import admin.enums.FieldType;

public class Field {
    private String name;
    private String type;

    public Field(){

    }

    public Field(String name, FieldType type) {
        this.name = name;
        this.type = type.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type.toString();
    }
}
