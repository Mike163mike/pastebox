package com.mike.pastebox.service;

import com.mike.pastebox.api.request.PasteboxRequest;
import com.mike.pastebox.api.request.PublicStatus;
import com.mike.pastebox.api.response.PasteboxResponse;
import com.mike.pastebox.api.response.PasteboxeUrlResponse;
import com.mike.pastebox.model.PasteboxEntity;
import com.mike.pastebox.repository.PasteboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:application.yaml")
public class PasteboxServiceImpl implements PasteboxService {

   @Value("${host}")
    private  String host;
   @Value("${public_list_size}")
    private  int publicListSize;
    private final PasteboxRepository repository;
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteboxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteBox() {
        List<PasteboxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream()
                .map(pasteboxEntity -> new PasteboxResponse(pasteboxEntity.getData(),
                        pasteboxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteboxeUrlResponse create(PasteboxRequest request) {
        Integer hash = generateId();
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setId(hash);
        pasteboxEntity.setData(request.getData());
        pasteboxEntity.setHash(Integer.toHexString(hash));
        pasteboxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        pasteboxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);

        repository.add(pasteboxEntity);
        return new PasteboxeUrlResponse(host + "/" + pasteboxEntity.getHash());
    }

    private Integer generateId() {
        return idGenerator.getAndIncrement();
    }
}
