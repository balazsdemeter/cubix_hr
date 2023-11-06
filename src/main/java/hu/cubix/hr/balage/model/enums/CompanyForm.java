package hu.cubix.hr.balage.model.enums;

public enum CompanyForm {
    LIMITED_PARTNERSHIP("Limited partnership"),
    LLC("LLC"),
    CORPORATION("Corporation");

    private String name;

    CompanyForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CompanyForm findByName(String name) {
        CompanyForm ret = null;
        for (CompanyForm companyForm : CompanyForm.values()) {
            if (companyForm.name.equals(name)) {
                ret = companyForm;
            }
        }

        if (ret == null) {
            throw new RuntimeException("Unknown company form name: " + name);
        }
        return ret;
    }
}