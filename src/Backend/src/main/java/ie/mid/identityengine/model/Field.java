package ie.mid.identityengine.model;

import ie.mid.identityengine.enums.FieldType;

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

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Field{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
