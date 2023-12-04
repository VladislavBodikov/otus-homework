package ru.otus.cache.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CacheRepository<K, V> {

    private static final String PERSISTENT_FILE_NAME ="cache-repository.txt";

    File repository = initPersistentFile();

    ObjectMapper om;

    public void save(Map<K, SoftReference<V>> cache) {
        writeToFile(serialize(unwrapSoftReferences(cache)));
    }

    public Map<K, SoftReference<V>> get() {
        return wrapSoftReferences(deserialize(readFromFile()));
    }





    private Map<K, V> unwrapSoftReferences(Map<K, SoftReference<V>> cache) {
        return cache.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }

    private Map<K, SoftReference<V>> wrapSoftReferences(Map<K, V> deserialize) {
        return deserialize.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new SoftReference<>(e.getValue())));
    }

    private void writeToFile(byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(repository, false)) {
            fos.write(data);
            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFromFile() {
        try (FileInputStream fis = new FileInputStream(repository)) {
            return fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] serialize(Map<K, V> cache) {
        try {
            return om.writeValueAsBytes(cache);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<K, V> deserialize(byte[] data) {
        try {
            if (data.length == 0) {
                return new LinkedHashMap<>();
            }
            return om.readValue(data, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File initPersistentFile() {
        File file;
        Path pathToFile = Path.of(PERSISTENT_FILE_NAME);
        if (Files.notExists(pathToFile)) {
            try {
                Path filePath = Files.createFile(pathToFile);
                file = filePath.toFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            file = pathToFile.toFile();
        }
        return file;
    }


}
