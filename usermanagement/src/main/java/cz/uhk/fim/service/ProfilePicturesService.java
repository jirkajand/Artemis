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

    @Value("${profile-pictures.size-limit-megabytes}")
    private int sizeLimitMB;

    public Optional<String> storeProfilePicture(UUID accountId, MultipartFile profilePicture) {
        log.info("Storing profile picture for user: {}", accountId);
        
        long sizeLimitBytes = (long) sizeLimitMB * 1024 * 1024;
        if (profilePicture.getSize() > sizeLimitBytes) {
            log.warn("Profile picture for user: {} exceeds size limit of {} bytes", accountId, sizeLimitBytes);
            return Optional.empty();
        }
        String contentType = profilePicture.getContentType();
        if (contentType == null ||
                !(contentType.equalsIgnoreCase("image/png") ||
                        contentType.equalsIgnoreCase("image/jpeg") ||
                        contentType.equalsIgnoreCase("image/jpg") ||
                        contentType.equalsIgnoreCase("image/gif") ||
                        contentType.equalsIgnoreCase("image/bmp") ||
                        contentType.equalsIgnoreCase("image/webp"))) {
            log.error("Profile picture for user {} is not a supported image type: {}", accountId, contentType);
            return Optional.empty();
        }

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
            log.error("Failed to create upload directory: {}", uploadDir);
            return Optional.empty();
        }

        var originalFilename = profilePicture.getOriginalFilename();
        String extension = "";
        if (originalFilename != null) {
            int idx = originalFilename.lastIndexOf('.');
            if (idx > 0 && idx < originalFilename.length() - 1) {
                extension = originalFilename.substring(idx + 1);
            }
        }
        var fileName = accountId.toString() + "_profilepicture" + (extension.isEmpty() ? "" : "." + extension);
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
