package hu.martinez.matchsimulator.career.simulation.matchevent.injury;

import hu.martinez.matchsimulator.career.injury.CreateInjury;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedBenchedPlayer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class SimulationInjuryService {

    public List<CreateInjury> handleInjuries(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        List<CreateInjury> createInjuryList = new ArrayList<>();

        createInjuryList.addAll(
                handleInjuriesForTeam(
                        fixtureDataContainer.homeTeamDataContainer(), fixtureDataContainer.simulatedFixture().getId()));
        createInjuryList.addAll(
                handleInjuriesForTeam(
                        fixtureDataContainer.awayTeamDataContainer(), fixtureDataContainer.simulatedFixture().getId()));

        return createInjuryList;
    }

    @Nonnull
    public List<CreateInjury> handleInjuriesForTeam(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer,
            @Nonnull Integer fixtureId
    ) {

        List<Optional<CreateInjury>> injuryList = new ArrayList<>();

        // starters
        injuryList.addAll(
                teamDataContainer.forwardList()
                        .stream()
                        .map(player -> handleInjuryForStarterPlayer(player, fixtureId))
                        .toList()
        );
        injuryList.addAll(
                teamDataContainer.midfielderList()
                        .stream()
                        .map(player -> handleInjuryForStarterPlayer(player, fixtureId))
                        .toList()
        );
        injuryList.addAll(
                teamDataContainer.defenderList()
                        .stream()
                        .map(player -> handleInjuryForStarterPlayer(player, fixtureId))
                        .toList()
        );
        injuryList.add(handleInjuryForStarterPlayer(teamDataContainer.goalkeeper(), fixtureId));

        // bench
        handleInjuryForBenchedPlayers(teamDataContainer.benchedPlayerList());

        return injuryList
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Nonnull
    private Optional<CreateInjury> handleInjuryForStarterPlayer(
            @Nonnull SimulatedStarterPlayer starterPlayer,
            @Nonnull Integer fixtureId
    ) {

        if (Math.random() <= 0.01) { // 1% chance that an injury happens

            var injuryLengthRandom = Math.random();
            if (injuryLengthRandom >= 0.9) { // 10% chance for 5 week injury
                starterPlayer.setInjuredFor(5);
            } else if (injuryLengthRandom >= 0.75) { // 15% chance for 4 week injury
                starterPlayer.setInjuredFor(4);
            } else if (injuryLengthRandom >= 0.5) { // 25% chance for 3 week injury
                starterPlayer.setInjuredFor(3);
            } else if (injuryLengthRandom >= 0.2) { // 30% chance for 2 week injury
                starterPlayer.setInjuredFor(2);
            } else { // 20% chance for 1 week injury
                starterPlayer.setInjuredFor(1);
            }

            log.debug("handleInjuryForStarterPlayer - {} week injury happened to {}!",
                    starterPlayer.getInjuredFor(), starterPlayer.getName());

            return Optional.of(
                    new CreateInjury(
                            fixtureId,
                            starterPlayer.getTeamId(),
                            starterPlayer.getId(),
                            starterPlayer.getInjuredFor())
            );
        }

        return Optional.empty();
    }

    private void handleInjuryForBenchedPlayers(@Nonnull List<SimulatedBenchedPlayer> benchedPlayerList) {
        benchedPlayerList
                .stream()
                .filter(player -> player.getInjuredFor() > 0)
                .forEach(player -> player.setInjuredFor(player.getInjuredFor() - 1));
    }

}
