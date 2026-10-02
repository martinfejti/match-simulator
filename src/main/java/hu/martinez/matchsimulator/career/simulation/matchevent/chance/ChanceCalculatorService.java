package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ChanceCalculatorService {

    // TODO these definitely need some review later
    // TODO some randomness will be welcomed
    // TODO and also home team should have a bit more support
    // TODO and maybe formation should matter a bit too

    @Nonnull
    public ChanceContainer calculateChances(@Nonnull Double homeTeamAverage, @Nonnull Double awayTeamAverage) {

        var numberOfHomeTeamBigChances = 1;
        var numberOfAwayTeamBigChances = 1;
        var numberOfHomeTeamSmallChances = 2;
        var numberOfAwayTeamSmallChances = 2;

        if (Math.abs(homeTeamAverage - awayTeamAverage) <= 5) {
            numberOfHomeTeamBigChances += 2;
            numberOfAwayTeamBigChances += 2;
            numberOfHomeTeamSmallChances += 3;
            numberOfAwayTeamSmallChances += 3;
        }

        if (homeTeamAverage - awayTeamAverage > 5 && homeTeamAverage - awayTeamAverage <= 15) {
            numberOfHomeTeamBigChances += 2;
            numberOfAwayTeamBigChances += 1;
            numberOfHomeTeamSmallChances += 3;
            numberOfAwayTeamSmallChances += 2;
        }

        if (awayTeamAverage - homeTeamAverage > 5 && awayTeamAverage - homeTeamAverage <= 15) {
            numberOfHomeTeamBigChances += 1;
            numberOfAwayTeamBigChances += 2;
            numberOfHomeTeamSmallChances += 2;
            numberOfAwayTeamSmallChances += 3;
        }

        if (homeTeamAverage - awayTeamAverage > 15) {
            numberOfHomeTeamBigChances += 3;
            numberOfAwayTeamBigChances += 1;
            numberOfHomeTeamSmallChances += 4;
            numberOfAwayTeamSmallChances += 1;
        }

        if (awayTeamAverage - homeTeamAverage > 15) {
            numberOfHomeTeamBigChances += 1;
            numberOfAwayTeamBigChances += 3;
            numberOfHomeTeamSmallChances += 1;
            numberOfAwayTeamSmallChances += 4;
        }

        log.debug("numberOfHomeTeamBigChances: {}", numberOfHomeTeamBigChances);
        log.debug("numberOfHomeTeamSmallChances: {}", numberOfHomeTeamSmallChances);
        log.debug("numberOfAwayTeamBigChances: {}", numberOfAwayTeamBigChances);
        log.debug("numberOfAwayTeamSmallChances: {}", numberOfAwayTeamSmallChances);

        return new ChanceContainer(
                numberOfHomeTeamBigChances,
                numberOfHomeTeamSmallChances,
                numberOfAwayTeamBigChances,
                numberOfAwayTeamSmallChances
        );
    }

}
