package com.trek.trekker.service;

import com.trek.core.admin.service.MediaService;
import com.trek.core.config.ImageUploaderConfig;
import com.trek.core.constants.TrekkerStatus;
import com.trek.core.dto.request.ImageUploadRequest;
import com.trek.core.dto.response.CloudinaryResponse;
import com.trek.core.entity.Role;
import com.trek.core.entity.Trekker;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.messaging.service.MessagingService;
import com.trek.core.repository.RoleRepository;
import com.trek.core.repository.TrekkerRepository;
import com.trek.core.util.ImageUploadUtil;
import com.trek.trekker.dto.command.TrekkerCreateCommand;
import com.trek.trekker.dto.command.TrekkerEditCommand;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrekkerService {

    private final RoleRepository roleRepository;
    private final TrekkerRepository trekkerRepository;
    private final TrekkerSecurityContextHolderHolder trekkerSecurityContextHolderHolder;
    private final MediaService mediaService;
    private final ImageUploaderConfig imageUploaderConfig;
    private final MessagingService messagingService;

    public List<Trekker> getAll() {
        return trekkerRepository.findAll();
    }

    public Trekker getById(Long id) {
        return trekkerRepository.findById(id)
                .orElseThrow(() -> new RequestValidationException("Trekker not found"));
    }

    public Trekker create(TrekkerCreateCommand command) {
        Trekker trekker = new Trekker();
        Role role = roleRepository.findByName("TREKKER")
                .orElseThrow(() -> new RequestValidationException("Trekker role not found"));
        trekker.setRole(role);
        trekker.setFullName(command.getFullName());
        trekker.setUsername(command.getUsername());
        trekker.setEmail(command.getEmail());
        trekker.setAvatar(command.getAvatar());
        trekker.setCoverImage(command.getCoverImage());
        trekker.setBio(command.getBio());
        trekker.setOldPassword(command.getOldPassword());
        trekker.setStatus(TrekkerStatus.ACTIVE);
        trekker.setLocation(command.getLocation());
        trekker.setPassword(command.getPassword());
        Trekker savedTrekker = trekkerRepository.save(trekker);
        messagingService.sendWelcomeEmail(savedTrekker);
        return savedTrekker;
    }

    public void uploadProfile(ImageUploadRequest request) {
        Trekker currentUser = trekkerSecurityContextHolderHolder.getCurrentUser();
        CloudinaryResponse cloudinaryResponse = ImageUploadUtil.uploadImages(request, imageUploaderConfig);
        mediaService.upload(cloudinaryResponse);
        currentUser.setAvatar(cloudinaryResponse.imageUrl());
        trekkerRepository.save(currentUser);
    }

    public void uploadCover(ImageUploadRequest request) {
        Trekker currentUser = trekkerSecurityContextHolderHolder.getCurrentUser();
        CloudinaryResponse cloudinaryResponse = ImageUploadUtil.uploadImages(request, imageUploaderConfig);
        currentUser.setCoverImage(cloudinaryResponse.imageUrl());
        mediaService.upload(cloudinaryResponse);
         trekkerRepository.save(currentUser);
    }

    public Trekker edit(TrekkerEditCommand command) {
        Trekker trekker = command.getTrekker();
        trekker.setFullName(command.getFullName());
        trekker.setUsername(command.getUsername());
        trekker.setBio(command.getBio());
        trekker.setLocation(command.getLocation());
        return trekkerRepository.save(trekker);
    }

    public void delete(Trekker trekker) {
        trekkerRepository.save(trekker);
    }

}

