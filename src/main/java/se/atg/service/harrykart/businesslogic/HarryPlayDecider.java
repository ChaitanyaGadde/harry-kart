package se.atg.service.harrykart.businesslogic;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import se.atg.service.harrykart.dto.request.HarryKartRequest;
import se.atg.service.harrykart.dto.response.HarryCartPlacement;

@Component
public interface HarryPlayDecider {

  List<HarryCartPlacement> playHarryKart(HttpHeaders httpHeaders, HarryKartRequest harryKartRequest)
      throws JsonProcessingException;

}
