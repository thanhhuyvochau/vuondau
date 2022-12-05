package fpt.capstone.vuondau.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.Instant;

public class PanoDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @Schema(description = "Mã của pano")
    private String name;
    @Schema(description = "Ngày phát hành")
    private Instant publishDate;
    @Schema(description = "Ngày hết hạn")
    private Instant expirationDate;


    @Schema(description = "Mã hình ảnh pano")
    private Long imageId;

    @Schema(description = "Hiển thị")
    private Boolean isVisible;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Instant publishDate) {
        this.publishDate = publishDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }


    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}