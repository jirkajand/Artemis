package cz.uhk.fim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfilePicturesService {

    @Value("${profile-pictures.upload-dir}")
    private String uploadDir;

    public Optional<String> storeProfilePicture(UUID accountId, MultipartFile profilePicture) {
        log.info("Storing profile picture for user: {}", accountId);
        var fileName = accountId.toString() + "_" + profilePicture.getOriginalFilename();
        var destinationFile = new File(uploadDir, fileName);
        try {
            profilePicture.transferTo(destinationFile);
        } catch (IOException e) {
            log.error("Failed to store profile picture for user: {}", accountId, e);
            return Optional.empty();
        }
        return Optional.of(fileName);
    }
}
