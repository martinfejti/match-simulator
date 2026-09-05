package hu.martinez.matchsimulator.saving;

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

    @Nonnull
    public String saveNewGame(@Nonnull String saveName) {

        var directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            throw new IllegalStateException("No saves folder found!");
        }

        var targetPath = SAVE_DIRECTORY + saveName + ".db";
        var targetFile = new File(targetPath);
        var masterResource = new ClassPathResource("master_template.db");

        try {
            Files.copy(masterResource.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Problem during creating new copy!", e);
        }

        // 4. ÁTVÁLTÁS! Ezentúl minden Repository ebből az új fájlból olvas és ide IR
        dataSource.setUrl("jdbc:sqlite:" + targetPath);

        return targetPath;
    }

}
