package com.its.issuetrackingservice.api.issue.controller;

import com.its.issuetrackingservice.domain.issue.service.IssueDomainService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issue")
@AllArgsConstructor
@Slf4j
public class IssueController {

    private final HttpServletRequest request;
    private final IssueDomainService issueDomainService;
}
