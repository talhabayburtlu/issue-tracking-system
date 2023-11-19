package com.its.issuetrackingservice.api.issue.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issue")
@Slf4j
public class IssueController {
    @GetMapping("/test")
    public void testing() {
      log.info("testing");
    }
}
