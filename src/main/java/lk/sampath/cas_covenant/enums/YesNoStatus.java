package lk.sampath.cas_covenant.enums;

public enum YesNoStatus {
  Y("Yes", "Y", true),
  N("No", "N", false);

  private String label;
  private String value;
  private Boolean boolVal;

  YesNoStatus(String label, String value, Boolean boolVal) {
    this.label = label;
    this.value = value;
    this.boolVal = boolVal;
  }

  public String getValue() {
    return value;
  }

  public String getLabel() {
    return label;
  }

  public Boolean getBoolVal() {
    return boolVal;
  }

  public static YesNoStatus getEnum(String value) {
    for (YesNoStatus clusterStatus : YesNoStatus.values()) {
      if (clusterStatus.getValue().equalsIgnoreCase(value)) {
        return clusterStatus;
      }
    }
    return null;
  }
}
