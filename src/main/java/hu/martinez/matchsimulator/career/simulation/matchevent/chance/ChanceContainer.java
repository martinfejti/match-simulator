package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

public record ChanceContainer(
        Integer numberOfHomeBigChances,
        Integer numberOfHomeSmallChances,
        Integer numberOfAwayBigChances,
        Integer numberOfAwaySmallChances
) {
}
