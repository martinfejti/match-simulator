package hu.martinez.matchsimulator.selector;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Log4j2
@RequiredArgsConstructor
@Service
public class SaveInitializerService {

    private static final String SAVE_DIRECTORY = "saves/";

    private final DriverManagerDataSource dataSource;

    public void saveNewGame(@Nonnull String saveName, @Nonnull String seasonName) {

        // check saves directory
        var directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            throw new IllegalStateException("No saves folder found!");
        }

        // create new save file
        var targetPath = SAVE_DIRECTORY + saveName + ".db";
        var targetFile = new File(targetPath);

        // get template for new save to clone
        var masterResource = new ClassPathResource("database/templates/" + seasonName + ".db");

        // copy template content into new save
        try {
            Files.copy(masterResource.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Problem during creating new copy!", e);
        }

        // switch to new save as database
        var newUrl = "jdbc:sqlite:" + targetPath;

        log.debug("saveNewGame - Switch to new database URL: {}", newUrl);

        dataSource.setUrl(newUrl);
    }

    public void switchDatabase(@Nonnull String saveName) {

        var newUrl = "jdbc:sqlite:" + SAVE_DIRECTORY + saveName + ".db";

        log.debug("switchDatabase - Switch to new database URL: {}", newUrl);

        dataSource.setUrl(newUrl);
    }

    public void switchToSelectorDatabase() {

        log.debug("switchToSelectorDatabase - Switch to selector database");

        dataSource.setUrl("jdbc:sqlite:src/main/resources/database/selector/selector.db");
    }

}
