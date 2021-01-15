package se.atg.service.harrykart.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.atg.service.harrykart.businesslogic.HarryPlayDecider;
import se.atg.service.harrykart.dto.request.HarryKartRequest;
import se.atg.service.harrykart.dto.response.HarryKartResponse;

@RestController
@RequestMapping("/api")
@Slf4j
public class HarryKartController {

  private final HarryPlayDecider harryPlayDecider;

  public HarryKartController(HarryPlayDecider harryPlayDecider) {
    this.harryPlayDecider = harryPlayDecider;
  }

  @PostMapping(
      value = "/play",
      consumes = {MediaType.APPLICATION_XML_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public HarryKartResponse playHarryKart(@RequestBody HarryKartRequest harryKartRequest,
      @RequestHeader HttpHeaders headers) throws JsonProcessingException {
    log.info("request received {}", harryKartRequest);
    return HarryKartResponse.builder()
        .rankings(harryPlayDecider.playHarryKart(headers, harryKartRequest))
        .build();
  }
}
