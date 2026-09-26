package hu.martinez.matchsimulator.career.information;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class InformationMapper {

    @Nonnull
    public Information map(@Nonnull InformationEntity entity) {
        return new Information(
                entity.getId(),
                entity.getSaveName(),
                entity.getLeagueName(),
                entity.getDate(),
                entity.getCountry()
        );
    }

    @Nonnull
    public InformationEntity mapToEntity(@Nonnull Information information) {
        return new InformationEntity(
                null,
                information.saveName(),
                information.leagueName(),
                information.date(),
                information.country()
        );
    }

}
