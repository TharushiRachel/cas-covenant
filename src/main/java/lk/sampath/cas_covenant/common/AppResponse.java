
package lk.sampath.cas_covenant.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppResponse<T> {
  Boolean success;
  String message;
  T response;
  Integer count;

  public AppResponse(Boolean success, String message, T response) {
    this.success = success;
    this.message = message;
    this.response = response;
  }

  public AppResponse(Boolean success, String message, T response, Integer count) {
    this.success = success;
    this.message = message;
    this.response = response;
    this.count = count;
  }
}
