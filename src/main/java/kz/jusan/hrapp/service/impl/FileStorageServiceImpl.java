package kz.jusan.hrapp.service.impl;

import java.io.IOException;
import java.util.List;

import kz.jusan.hrapp.model.FileDB;
import kz.jusan.hrapp.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageServiceImpl {

    @Autowired
    private FileDBRepository fileDBRepository;

    public FileStorageServiceImpl(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

        return fileDBRepository.save(FileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public List<FileDB> getAllFiles() {
        return fileDBRepository.findAll();
    }

}
