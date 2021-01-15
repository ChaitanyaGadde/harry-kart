package se.atg.service.harrykart.service;

import static se.atg.service.harrykart.util.HarryKartMapper.mapParticipantToResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import se.atg.service.harrykart.businesslogic.HarryPlayDecider;
import se.atg.service.harrykart.dto.request.HarryKartRequest;
import se.atg.service.harrykart.dto.request.Loop;
import se.atg.service.harrykart.dto.request.Participant;
import se.atg.service.harrykart.dto.response.HarryCartPlacement;
import se.atg.service.harrykart.errors.HarryKartException;

@Service
@Slf4j
public class HarryKartService implements HarryPlayDecider {

  private final int loopLength;
  private final ObjectMapper objectMapper;

  public HarryKartService(@Value("${harry-kart.loop.length}") int loopLength,
      ObjectMapper objectMapper) {
    this.loopLength = loopLength;
    this.objectMapper = objectMapper;
  }

  @Override
  public List<HarryCartPlacement> playHarryKart(HttpHeaders httpHeaders, HarryKartRequest harryKartRequest)
      throws JsonProcessingException {

    List<HarryCartPlacement> winner = getWinner(harryKartRequest);
    log.info("winner list for response is  :: {}", objectMapper.writeValueAsString(winner));
    return winner;
  }

  private List<HarryCartPlacement> getWinner(HarryKartRequest harryKartRequest) {

    if (CollectionUtils.isEmpty(harryKartRequest.getStartList())
        || harryKartRequest.getStartList().size() == 1) {
      throw new HarryKartException(HttpStatus.NOT_FOUND,
          "Expected number of participants is at least two, but one or not present");
    }

    List<Participant> topThreeWinners = harryKartRequest.getStartList()
        .parallelStream()
        .filter(Objects::nonNull)
        .map(participant -> calculateTimeTaken(participant, harryKartRequest.getNumberOfLoops(),
            harryKartRequest.getPowerUps()))
        .sorted(Comparator.comparingDouble(Participant::getTimeTakenForAllLoops))
        .limit(3)
        .collect(Collectors.toList());

    List<HarryCartPlacement> harryCartPlacements = new ArrayList<>(3);
    for (int j = 0; j < topThreeWinners.size(); j++) {
      harryCartPlacements.add(mapParticipantToResponse(topThreeWinners.get(j), j + 1));
    }
    return harryCartPlacements;
  }

  private Participant calculateTimeTaken(Participant participant, int numberOfLoops,
      List<Loop> powerUps) {
    int baseSpeed = participant.getBaseSpeed();
    int laneNumberStarted = participant.getLane();
    Double timeTaken = null;

    for (int i = 0; i <= numberOfLoops; i++) {
      if (i == 0) {
        timeTaken = getTime(baseSpeed, loopLength);
      } else {
        int finalI = i;
        timeTaken = timeTaken + powerUps
            .stream()
            .filter(loop -> !ObjectUtils.notEqual(loop.getNumber(), finalI))
            .map(loop -> timeForLoopInLane(loop, laneNumberStarted, baseSpeed, loopLength))
            .mapToDouble(Double::doubleValue)
            .sum();
      }
    }
    participant.setTimeTakenForAllLoops(timeTaken);
    return participant;
  }

  private Double timeForLoopInLane(Loop loop, int laneNumberStarted, int baseSpeed, int loopLength) {
    return loop.getLanes()
        .stream()
        .filter(Objects::nonNull)
        .filter(lane -> !ObjectUtils.notEqual(lane.getNumber(), laneNumberStarted))
        .map(lane -> {
          if (ObjectUtils.isEmpty(lane.getPowerValue())) {
            throw new HarryKartException(HttpStatus.NOT_FOUND, "Cart Power up value cannot be null or empty");
          }
          return getTime(getSpeed(baseSpeed, lane.getPowerValue()), loopLength);
        })
        .mapToDouble(Double::doubleValue)
        .sum();
  }

  private int getSpeed(int baseSpeed, int powerValue) {
    int powerUpSpeedResult = powerValue + baseSpeed;
    if (powerUpSpeedResult < 0) {
      throw new HarryKartException(HttpStatus.NOT_FOUND,
          "Power up sum entered negative values, practically not possible");
    }
    return powerUpSpeedResult;
  }

  private Double getTime(int speed, int loopLength) {
    return (double) loopLength / (double) speed;
  }
}
