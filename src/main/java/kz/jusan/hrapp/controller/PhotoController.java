package kz.jusan.hrapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.jusan.hrapp.message.ResponseFile;
import kz.jusan.hrapp.message.ResponseMessage;
import kz.jusan.hrapp.model.Photo;
import kz.jusan.hrapp.service.UserService;
import kz.jusan.hrapp.service.impl.PhotoStorageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@Slf4j
@Tag(name = "Photos")
@RestController
@CrossOrigin("http://localhost:8081")
public class PhotoController {

    @Autowired
    private PhotoStorageServiceImpl storageService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload/photo/{user_id}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long user_id) {
        String message = "";
        try {
            storageService.store(file, userService.findById(user_id));

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/photos")
    public ResponseEntity<List<Photo>> getListFiles() {
        List<Photo> files = storageService.getAllFiles();
        log.debug(files.toString() + " - files");
        files.stream()
                .map(photo -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/photos/")
                            .path(photo.getId().toString())
                            .toUriString();

                    return new ResponseFile(
                            photo.getName(),
                            fileDownloadUri,
                            photo.getType(),
                            photo.getData().length);
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }


    @GetMapping("/photos/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Photo photo = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getName() + "\"")
                .body(photo.getData());
    }
}