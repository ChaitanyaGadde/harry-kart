package se.atg.service.harrykart.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import se.atg.service.harrykart.dto.request.HarryKartRequest;
import se.atg.service.harrykart.dto.response.HarryCartPlacement;
import se.atg.service.harrykart.errors.HarryKartException;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class HarryKartServiceTest {

  private ObjectMapper objectMapper;
  private int loopLength;

  private HarryKartService harryKartService;

  static Stream<FileInputStream> getSuccessOrders() {
    try {
 /*     FileInputStream fileInputStream1 = new FileInputStream(
          new File("src/test/resources/input_0.xml"));*/
      FileInputStream fileInputStream2 = new FileInputStream(
          new File("src/test/resources/input_1.xml"));
/*      FileInputStream fileInputStream3 = new FileInputStream(
          new File("src/test/resources/input_2.xml"));*/
      return Stream.of(/*fileInputStream1*/
          fileInputStream2
          /*fileInputStream3*/);
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new RuntimeException();
  }

  static Stream<FileInputStream> getExceptionNoParticipants() {
    try {
      FileInputStream fileInputStream1 = new FileInputStream(
          new File("src/test/resources/input_Exception1.xml"));
      return Stream.of(fileInputStream1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new RuntimeException();
  }

  static Stream<FileInputStream> getNegativePowerUpException() {
    try {
      FileInputStream fileInputStream1 = new FileInputStream(
          new File("src/test/resources/input_Exception2.xml"));
      return Stream.of(fileInputStream1);
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new RuntimeException();
  }

  void setUp() throws IOException {
    objectMapper = new ObjectMapper();
    loopLength = 1000;
  }

  @BeforeEach
  void initialize() throws IOException {
    setUp();
    harryKartService = new HarryKartService(loopLength, objectMapper);
  }

  @DisplayName("Test green flow Harry Kart")
  @ParameterizedTest
  @MethodSource("getSuccessOrders")
  void should_ProcessHarryCart_WhenGreenFilesProvided(FileInputStream harryKartStream) throws IOException {
    HarryKartRequest harryKartRequest = xmlMapper()
        .readValue(harryKartStream, HarryKartRequest.class);

    List<HarryCartPlacement> harryCartPlacements = harryKartService.playHarryKart(new HttpHeaders(), harryKartRequest);
    assertNotNull(harryCartPlacements);
    assertEquals(harryCartPlacements.size(), 3);
  }

  @DisplayName("Test No Participants Exception")
  @ParameterizedTest
  @MethodSource("getExceptionNoParticipants")
  void should_ProcessHarryCart_ThrowException_WhenNoParticipants(FileInputStream harryKartStream) throws IOException {
    HarryKartRequest harryKartRequest = xmlMapper()
        .readValue(harryKartStream, HarryKartRequest.class);

    HarryKartException harryKartException = assertThrows(HarryKartException.class, () -> {
      List<HarryCartPlacement> harryCartPlacements = harryKartService
          .playHarryKart(new HttpHeaders(), harryKartRequest);
    });

    String expectedMessage = "Expected number of participants is at least two, but one or not present";
    String actualMessage = harryKartException.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @DisplayName("Test Negative Power up Exception")
  @ParameterizedTest
  @MethodSource("getNegativePowerUpException")
  void should_ProcessHarryCart_ThrowException_WhenNegativePowerUp(FileInputStream harryKartStream) throws IOException {
    HarryKartRequest harryKartRequest = xmlMapper()
        .readValue(harryKartStream, HarryKartRequest.class);

    HarryKartException harryKartException = assertThrows(HarryKartException.class, () -> {
      List<HarryCartPlacement> harryCartPlacements = harryKartService
          .playHarryKart(new HttpHeaders(), harryKartRequest);
    });

    String expectedMessage = "Power up sum entered negative values, practically not possible";
    String actualMessage = harryKartException.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  private XmlMapper xmlMapper() {
    SimpleFilterProvider filters = new SimpleFilterProvider();
    filters.setFailOnUnknownId(false);
    XmlMapper xmlMapper = new XmlMapper();
    return xmlMapper;
  }
}