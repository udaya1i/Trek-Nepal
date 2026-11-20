package com.trek.core.dto.request;

import com.trek.core.annotation.MaxImages;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BulkImageRequest {

    @MaxImages(value = 10, message = "You can upload a maximum of 10 images per request")
    private List<MultipartFile> images;
    private List<String> altMessage;
    private List<String> description;
    private List<Long> displayOrder;

}
