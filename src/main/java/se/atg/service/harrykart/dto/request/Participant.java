package se.atg.service.harrykart.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "participant")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

  @JacksonXmlProperty
  private int lane;

  @JacksonXmlProperty
  private String name;

  @JacksonXmlProperty
  private int baseSpeed;

  private Double timeTakenForAllLoops;
}
