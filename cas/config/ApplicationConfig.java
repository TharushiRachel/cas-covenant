package com.itechro.cas.config;

import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.dto.coveringApproval.CovAppCommentDTO;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
