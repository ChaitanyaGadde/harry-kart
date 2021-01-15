package se.atg.service.harrykart.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = HarryCartPlacement.Builder.class)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HarryCartPlacement {

  private final int position;
  private final String horse;
  private final double timeForTestPurpose;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

  }
}
