package com.example.Kamini.myapplication.backend;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Kamini on 3/3/2016.
 */
@Entity
public class ItemPhoto {
    @Id
    Long id;
    Blob blobImage;
    String Name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getBlobImage() {
        return blobImage;
    }

    public void setBlobImage(Blob blobImage) {
        this.blobImage = blobImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void encodeBlobImage(byte[] byteArray) {
    }
}
