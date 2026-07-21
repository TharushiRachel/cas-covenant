package lk.sampath.cas_covenant.enums;

public enum ErrorEnums {
  SUCCESS_CODE("Success", "200", true),
  ERROR_CODE("Error", "500", false);
  private String label;
  private String code;

  private Boolean status;

  ErrorEnums(String label, String code, Boolean status) {
    this.label = label;
    this.code = code;
    this.status = status;
  }

  public String getLabel() {
    return label;
  }

  public String getCode() {
    return code;
  }

  public Boolean getStatus() {
    return status;
  }
}
