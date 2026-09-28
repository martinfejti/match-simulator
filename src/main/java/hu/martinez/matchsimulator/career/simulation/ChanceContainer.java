package hu.martinez.matchsimulator.career.simulation;

public record ChanceContainer(
        Integer numberOfHomeBigChances,
        Integer numberOfHomeSmallChances,
        Integer numberOfAwayBigChances,
        Integer numberOfAwaySmallChances
) {
}
