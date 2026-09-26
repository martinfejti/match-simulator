package hu.martinez.matchsimulator.career.information;

import hu.martinez.matchsimulator.selector.save.Save;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InformationService {

    private final InformationMapper informationMapper;
    private final InformationRepository informationRepository;

    @Nonnull
    public Information getCareerInformation() {
        return informationRepository.findFirstByOrderByIdAsc()
                .map(informationMapper::map)
                .orElseThrow();
    }

    public void saveInformation(@Nonnull Save save) {
        informationRepository.save(
                informationMapper.mapToEntity(
                        new Information(
                                save.id(),
                                save.name(),
                                save.season().league(),
                                save.season().date(),
                                save.season().flag()
                        )
                )
        );
    }

}
