package kz.jusan.hrapp.service.impl;

import java.io.IOException;
import java.util.List;

import kz.jusan.hrapp.model.Photo;
import kz.jusan.hrapp.model.User;
import kz.jusan.hrapp.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoStorageServiceImpl {

    @Autowired
    private PhotoRepository photoRepository;

    public PhotoStorageServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo store(MultipartFile file, User user) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Photo photo = new Photo(fileName, file.getContentType(), file.getBytes(), user);

        return photoRepository.save(photo);
    }

    public Photo getFile(String id) {
        return photoRepository.findPhotoByUserId(Long.parseLong(id)).get();
    }

    public List<Photo> getAllFiles() {
        return photoRepository.findAll();
    }

}

