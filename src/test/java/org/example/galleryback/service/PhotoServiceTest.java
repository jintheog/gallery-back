package org.example.galleryback.service;

import org.example.galleryback.dto.PhotoResponse;
import org.example.galleryback.entity.Photo;
import org.example.galleryback.repository.PhotoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PhotoService {
    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private PhotoService photoService;

    @Test
    @DisplayName("사진 목록 조회")
    void findAll() {
        List<Photo> photos = List.of(
                createPhoto("사진1", "/uploads/1.jpg"),
                createPhoto("사진2", "/uploads/2.jpg")
        );

        given(photoRepository.findAll()).willReturn(photos);

        List<PhotoResponse> result = photoService.findAll();

        assertThat(result).hasSize(2);
    }

    private Photo createPhoto(String title, String imageUrl) {
        return Photo.builder().title(title).imageUrl(imageUrl).build();
    }


}
