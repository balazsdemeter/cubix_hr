package hu.cubix.hr.balage.model.enums;

public enum Qualification {
    HIGH_SCHOOL("High school"),
    COLLAGE("Collage"),
    UNIVERSITY("University"),
    PHD("Phd");
    private String name;
    Qualification(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Qualification findByName(String name) {
        Qualification ret = null;
        for (Qualification qualification : Qualification.values()) {
            if (qualification.name.equals(name)) {
                ret = qualification;
            }
        }

        if (ret == null) {
            throw new RuntimeException("Unknow company name: " + name);
        }
        return ret;
    }
}