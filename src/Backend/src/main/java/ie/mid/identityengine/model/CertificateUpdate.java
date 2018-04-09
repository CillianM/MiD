package ie.mid.identityengine.model;

public class CertificateUpdate {

    private String $class;
    private String certificate;
    private String newStatus;

    public CertificateUpdate() {
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CertificateUpdate{");
        sb.append("$class='").append($class).append('\'');
        sb.append(", certificate='").append(certificate).append('\'');
        sb.append(", newStatus='").append(newStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
