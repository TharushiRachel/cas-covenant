package lk.sampath.cas_covenant.enums;

public enum CovenantStatus {
  Active("Active"),
  Inactive("Inactive"),
  MODIFIED("Modified"); // Add the new enum value

  private String name;

  CovenantStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
