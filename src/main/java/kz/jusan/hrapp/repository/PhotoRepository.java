package kz.jusan.hrapp.repository;

import kz.jusan.hrapp.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    public Optional<Photo> findPhotoByUserId(Long userId);
}
