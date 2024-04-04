package com.shinobicoders.teamcodeapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDetails {
    private String message;
    private Long projectId;
}
