package se.atg.service.harrykart.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder(builderClassName = "Builder")
@JsonDeserialize(builder = HarryKartResponse.Builder.class)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HarryKartResponse {


  List<HarryCartPlacement> rankings;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

  }
}
