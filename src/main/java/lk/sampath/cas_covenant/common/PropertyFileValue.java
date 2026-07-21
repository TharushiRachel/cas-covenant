package lk.sampath.cas_covenant.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class PropertyFileValue {

  @Value("${apps.covenant.request.uuid}")
  private String requestUUID;
}
