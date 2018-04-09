package ie.mid.identityengine.model;

public class Individual {

    private String $class;
    private String individualId;

    public Individual() {
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getIndividualId() {
        return individualId;
    }

    public void setIndividualId(String individualId) {
        this.individualId = individualId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Individual{");
        sb.append("$class='").append($class).append('\'');
        sb.append(", individualId='").append(individualId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
