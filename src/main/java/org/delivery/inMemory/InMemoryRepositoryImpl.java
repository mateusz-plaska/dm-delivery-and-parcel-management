package org.delivery.inMemory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.core.serializer.support.SerializationFailedException;

import java.io.*;
import java.util.*;

public abstract class InMemoryRepositoryImpl<V extends Serializable>
        implements InMemoryRepository<V> {

    protected final transient File file;
    protected Map<String, V> data;

    public InMemoryRepositoryImpl(String filePath) {
        this.file = new File(filePath);
        this.data = new HashMap<>();
    }

    @Override
    public V get(String key1, String key2) {
        return data.get(compositeKey(key1, key2));
    }

    @Override
    public void add(String key1, String key2, V value) {
        data.put(compositeKey(key1, key2), value);
    }

    @Override
    public void addAll(Set<Map.Entry<String, V>> entries) {
        entries.forEach(e -> data.put(e.getKey(), e.getValue()));
    }

    @Override
    public Collection<V> values() {
        return data.values();
    }

    @Override
    public Set<String> keys() {
        return Collections.unmodifiableSet(data.keySet());
    }

    @Override
    public Set<Map.Entry<String, V>> entries() {
        return data.entrySet();
    }

    @Override
    public void clearAll() {
        data.clear();
    }

    protected abstract void loadInitData();

    protected String compositeKey(String a, String b) {
        return a + ":" + b;
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void init() {
        if (!file.exists()) {
            loadInitData();
            return;
        }

        try(FileInputStream fileInput = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(fileInput)) {

            this.data = (Map<String, V>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationFailedException(createExceptionMessageForIOE("Deserialization", e));
        }
    }

    @PreDestroy
    private void serialize() {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists())
            if (!parent.mkdirs())
                throw new SerializationFailedException(
                        "Failed to create directory for path: " + file.getAbsolutePath());

        try (FileOutputStream fileOutput = new FileOutputStream(file);
             ObjectOutputStream output = new ObjectOutputStream(fileOutput)) {

            output.writeObject(data);
        } catch (IOException e) {
            throw new SerializationFailedException(createExceptionMessageForIOE("Serialization", e));
        }
    }

    private static <E extends Exception> String createExceptionMessageForIOE(String methodType, E e) {
        return methodType + " failed for: " + InMemoryRepositoryImpl.class.getSimpleName() + ". Message: "
                + e.getMessage();
    }
}
