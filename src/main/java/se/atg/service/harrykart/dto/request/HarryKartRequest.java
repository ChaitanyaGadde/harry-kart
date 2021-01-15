package se.atg.service.harrykart.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@JacksonXmlRootElement(localName = "harryKart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HarryKartRequest implements Serializable {

  private int numberOfLoops;
  @NonNull
  private List<Participant> startList;
  private List<Loop> powerUps;
}
