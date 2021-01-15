package se.atg.service.harrykart.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loop {

  @JacksonXmlProperty(isAttribute = true)
  private int number;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "lane")
  private List<Lane> lanes;

}
