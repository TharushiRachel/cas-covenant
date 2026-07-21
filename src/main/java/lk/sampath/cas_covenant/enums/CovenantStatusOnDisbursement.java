package lk.sampath.cas_covenant.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public enum CovenantStatusOnDisbursement {
  PRE("Pre"),
  POST("Post");

  private String description;

  CovenantStatusOnDisbursement(String description) {
    this.description = description;
  }

  public static CovenantStatusOnDisbursement resolveStatus(String statusStr) {
    CovenantStatusOnDisbursement matchingStatus = null;
    if (!StringUtils.isEmpty(statusStr)) {
      matchingStatus = CovenantStatusOnDisbursement.valueOf(statusStr.trim());
    }
    return matchingStatus;
  }

  public static Map<String, String> getCovenantStatusOnDisbursementMap() {
    Map<String, String> result = new HashMap<>();
    Arrays.asList(CovenantStatusOnDisbursement.values())
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
