package se.atg.service.harrykart.util;

import se.atg.service.harrykart.dto.request.Participant;
import se.atg.service.harrykart.dto.response.HarryCartPlacement;

public class HarryKartMapper {

  public static HarryCartPlacement mapParticipantToResponse(Participant participant, int position) {

    return HarryCartPlacement.builder()
        .horse(participant.getName())
        .position(position)
        .timeForTestPurpose(participant.getTimeTakenForAllLoops())
        .build();
  }

}
