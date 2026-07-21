package lk.sampath.cas_covenant.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public enum CovenantApplicableType {
  ABU("ABU"),
  AC("AC");

  private String description;

  CovenantApplicableType(String description) {
    this.description = description;
  }

  public static CovenantApplicableType resolveStatus(String statusStr) {
    CovenantApplicableType matchingStatus = null;
    if (!StringUtils.isEmpty(statusStr)) {
      matchingStatus = CovenantApplicableType.valueOf(statusStr.trim());
    }
    return matchingStatus;
  }

  public static Map<String, String> getCovenantApplicableTypeMap() {
    Map<String, String> result = new HashMap<>();
    Arrays.asList(CovenantApplicableType.values())
        .forEach(
            status -> {
              result.put(status.toString(), status.getDescription());
            });
    return result;
  }

  public String getDescription() {
    return description;
  }
}
