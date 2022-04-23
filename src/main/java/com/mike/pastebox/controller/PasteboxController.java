package com.mike.pastebox.controller;

import com.mike.pastebox.api.request.PasteboxRequest;
import com.mike.pastebox.api.response.PasteboxResponse;
import com.mike.pastebox.api.response.PasteboxeUrlResponse;
import com.mike.pastebox.service.PasteboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class PasteboxController {

    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public List<PasteboxResponse> getPublicPasteList() {
        return pasteboxService.getFirstPublicPasteBox();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteboxeUrlResponse add(@RequestBody PasteboxRequest request) {
        return pasteboxService.create(request);
    }
}

