package com.mike.pastebox;

import com.mike.pastebox.api.response.PasteboxResponse;
import com.mike.pastebox.exeption.NotFoundEntityExeption;
import com.mike.pastebox.model.PasteboxEntity;
import com.mike.pastebox.repository.PasteboxRepository;
import com.mike.pastebox.service.PasteboxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteboxServiceTest {

    @MockBean
    private PasteboxRepository pasteboxRepository;

    @Autowired
    private PasteboxService pasteboxService;

    @Test
    public void notExistHash() {
        when(pasteboxRepository.getByHash(anyString())).thenThrow(NotFoundEntityExeption.class);
        assertThrows(NotFoundEntityExeption.class, () -> pasteboxService.getByHash("?????????????????"));
    }

    @Test
    public void getExistHash() {
        PasteboxEntity pasteboxEntity = new PasteboxEntity();
        pasteboxEntity.setHash("1");
        pasteboxEntity.setData("11");
        pasteboxEntity.setPublic(true);
        when(pasteboxRepository.getByHash("1")).thenReturn(pasteboxEntity);
        PasteboxResponse expected = new PasteboxResponse("11", true);
        PasteboxResponse actual = pasteboxService.getByHash("1");
        assertEquals(expected, actual);
    }
}
