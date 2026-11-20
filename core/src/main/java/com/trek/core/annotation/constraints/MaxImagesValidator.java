package com.trek.core.annotation.constraints;


import com.trek.core.annotation.MaxImages;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class MaxImagesValidator implements ConstraintValidator<MaxImages, List<MultipartFile>> {

    private int maxImages;

    @Override
    public void initialize(MaxImages annotation) {
        this.maxImages = annotation.value();
    }

    @Override
    public boolean isValid(List<MultipartFile> images, ConstraintValidatorContext context) {
        if (images == null || images.isEmpty()) return true;
        return images.size() <= maxImages;
    }
}