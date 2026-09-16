package hu.martinez.matchsimulator.career.team;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamMapper teamMapper;
    private final TeamRepository teamRepository;

    @Nonnull
    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(teamMapper::map)
                .toList();
    }

}
