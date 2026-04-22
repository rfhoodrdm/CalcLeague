package com.example.calcleague.service.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Thin wrapper around Jackson for storing JSON payloads.
 */
public class FileStorageService {
    private final ObjectMapper mapper;

    public FileStorageService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public synchronized <T> T read(Path path, TypeReference<T> typeReference, T fallback) {
        if (!Files.exists(path)) {
            return fallback;
        }
        try {
            return mapper.readValue(path.toFile(), typeReference);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read " + path, e);
        }
    }

    public synchronized void write(Path path, Object payload) {
        try {
            Files.createDirectories(path.getParent());
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), payload);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write " + path, e);
        }
    }
}
