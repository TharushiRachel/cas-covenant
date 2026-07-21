package lk.sampath.cas_covenant.controller.base_controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StandardResponse<T> {

  private Boolean success;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Object response;

  public StandardResponse(Boolean success, String message, Object response) {
    this.success = success;
    this.message = message;
    this.response = response;
  }
}
