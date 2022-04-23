package com.mike.pastebox.service;

import com.mike.pastebox.api.request.PasteboxRequest;
import com.mike.pastebox.api.response.PasteboxResponse;
import com.mike.pastebox.api.response.PasteboxeUrlResponse;

import java.util.*;

public interface PasteboxService {

    PasteboxResponse getByHash(String hash);

    List<PasteboxResponse> getFirstPublicPasteBox();

    PasteboxeUrlResponse create(PasteboxRequest request);
}
