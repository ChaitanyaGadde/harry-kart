package se.atg.service.harrykart.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import se.atg.service.harrykart.businesslogic.HarryPlayDecider;
import se.atg.service.harrykart.dto.request.HarryKartRequest;
import se.atg.service.harrykart.dto.response.HarryCartPlacement;

@WebMvcTest
@ContextConfiguration(classes = HarryKartController.class)
class HarryKartControllerTest {

  @MockBean
  HarryPlayDecider harryPlayDecider;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Initiate Harry Kart Play")
  void should_initiateHarryKart() throws Exception {

    when(harryPlayDecider.playHarryKart(any(HttpHeaders.class), any(HarryKartRequest.class))).thenReturn(
        Collections.singletonList(HarryCartPlacement.builder().build()));

    mockMvc.perform(post("/api/play").header("headers", new HttpHeaders())
        .header("X-Transaction-ID", "dtiofjer-sdfd-dff")
        .content(getHarryKartReq())
        .contentType(MediaType.APPLICATION_XML_VALUE)).andExpect(status().isOk());

  }

  private String getHarryKartReq() {

    return "<harryKart>\n"
        + "    <numberOfLoops>3</numberOfLoops>\n"
        + "    <startList>\n"
        + "        <participant>\n"
        + "            <lane>1</lane>\n"
        + "            <name>TIMETOBELUCKY</name>\n"
        + "            <baseSpeed>10</baseSpeed>\n"
        + "        </participant>\n"
        + "        <participant>\n"
        + "            <lane>2</lane>\n"
        + "            <name>CARGO DOOR</name>\n"
        + "            <baseSpeed>10</baseSpeed>\n"
        + "        </participant>\n"
        + "        <participant>\n"
        + "            <lane>3</lane>\n"
        + "            <name>HERCULES BOKO</name>\n"
        + "            <baseSpeed>10</baseSpeed>\n"
        + "        </participant>\n"
        + "        <participant>\n"
        + "            <lane>4</lane>\n"
        + "            <name>WAIKIKI SILVIO</name>\n"
        + "            <baseSpeed>10</baseSpeed>\n"
        + "        </participant>\n"
        + "    </startList>\n"
        + "    <powerUps>\n"
        + "        <loop number=\"1\">\n"
        + "            <lane number=\"1\">1</lane>\n"
        + "            <lane number=\"2\">1</lane>\n"
        + "            <lane number=\"3\">0</lane>\n"
        + "            <lane number=\"4\">-2</lane>\n"
        + "        </loop>\n"
        + "        <loop number=\"2\">\n"
        + "            <lane number=\"1\">1</lane>\n"
        + "            <lane number=\"2\">-1</lane>\n"
        + "            <lane number=\"3\">2</lane>\n"
        + "            <lane number=\"4\">-2</lane>\n"
        + "        </loop>\n"
        + "    </powerUps>\n"
        + "</harryKart>";
  }

}