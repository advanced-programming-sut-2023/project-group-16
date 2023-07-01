package org.group16.Model;

import java.util.UUID;

@SuppressWarnings("unchecked")
public class SerializedReference<T extends ReferenceSerializable> {
    private UUID uuid;

    public SerializedReference(T obj) {
        uuid = obj.getUuid();
    }

    public SerializedReference(UUID uuid) {
        this.uuid = uuid;
    }

    public T getValue() {
        return (T) UUIDResolver.getObject(uuid);
    }

    public void setValue(T obj) {
        uuid = obj.getUuid();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
