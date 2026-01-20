package org.example.galleryback.service;

import lombok.RequiredArgsConstructor;
import org.example.galleryback.dto.PhotoRequest;
import org.example.galleryback.dto.PhotoResponse;
import org.example.galleryback.entity.Photo;
import org.example.galleryback.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PohtoService {
    private final PhotoRepository photoRepository;
    private final FileStorageService fileStorageService;

    public List<PhotoResponse> findAll() {
        return photoRepository.findAll()
                .stream()
                .map(PhotoResponse::from)
                .toList();
    }

    public PhotoResponse findById(long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessError());
        return PhotoResponse.from(photo);
    }

    @Transactional
    public PhotoResponse save(PhotoRequest request, MultipartFile file) {
        String imageUrl = fileStorageService.upload(file);

        Photo photo = Photo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(imageUrl)
                .build();

        Photo saved = photoRepository.save(photo);
        return PhotoResponse.from(saved);
    }

    @Transactional
    public void delete(Long id){
        Photo photo = photoRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException());
        fileStorageService.delete(photo.getImageUrl());
        photoRepository.delete(photo);
    }
}
