package com.itechro.cas.model.dto.email;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class AttachmentDTO {

    private List<File> attachements;

    private List<String> unAttachmentFileNames;
}
