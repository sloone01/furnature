package com.example.market.pojos;


import android.net.Uri;

public class Image{

    private Uri uri;

    private Uri downloadUri;
    private String ref;


    public Image() {
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Uri getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(Uri downloadUri) {
        this.downloadUri = downloadUri;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }
}
