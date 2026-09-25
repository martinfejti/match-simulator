package hu.martinez.matchsimulator.career.player;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;

    @Nonnull
    public List<Player> getPlayersByTeamId(@Nonnull Integer teamId) {
        return playerRepository.findPlayersByTeamId(teamId)
                .stream()
                .map(playerMapper::map)
                .toList();
    }

    @Nonnull
    public List<Player> getGoalkeepersByTeamId(@Nonnull Integer teamId) {
        return playerRepository.findGoalkeepersByTeamId(teamId)
                .stream()
                .map(playerMapper::map)
                .toList();
    }

    @Nonnull
    public List<Player> getDefendersByTeamId(@Nonnull Integer teamId) {
        return playerRepository.findDefendersByTeamId(teamId)
                .stream()
                .map(playerMapper::map)
                .toList();
    }

    @Nonnull
    public List<Player> getMidfieldersByTeamId(@Nonnull Integer teamId) {
        return playerRepository.findMidfieldersByTeamId(teamId)
                .stream()
                .map(playerMapper::map)
                .toList();
    }

    @Nonnull
    public List<Player> getForwardsByTeamId(@Nonnull Integer teamId) {
        return playerRepository.findForwardsByTeamId(teamId)
                .stream()
                .map(playerMapper::map)
                .toList();
    }

}
