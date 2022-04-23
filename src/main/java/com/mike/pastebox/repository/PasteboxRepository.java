package com.mike.pastebox.repository;

import com.mike.pastebox.model.PasteboxEntity;

import  java.util.*;

public interface PasteboxRepository {

    PasteboxEntity getByHash(String hash);

    List<PasteboxEntity> getListOfPublicAndAlive(int amount);

    void add(PasteboxEntity pasteboxEntity);
}
