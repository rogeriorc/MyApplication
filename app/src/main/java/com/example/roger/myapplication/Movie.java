/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.roger.myapplication;

import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * Movie class represents video entity with username, description, image thumbs and video url.
 *
 */
public class Movie implements Serializable {

    static final long serialVersionUID = 727566175075960653L;
    static final String ROOT_URL = "https://pt.cam4.com";

    private static long count = 0;

    private long id;
    private String username;
    private String description;
    private String imageUrl;
    private String streamUrl;

    private boolean broadcasting;
    private int viewers;
    private String gender;
    private String guid;

    public Movie() {
    }

    public static long getCount() {
        return count;
    }

    public static void incCount() {
        count++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public URI getImageURI() {
        try {
            return new URI(getImageUrl());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Uri getVideoUrl() {
        StringBuilder sb = new StringBuilder();
        long timestamp = new Date().getTime();

        //sb.append("https://cs1812.cam4.com/cam4-edge-live/");
        //sb.append(this.username).append("-");
        //sb.append(this.guid).append("/playlist.m3u8?");
        sb.append(this.streamUrl);
        sb.append("?referer=pt.cam4.com");
        sb.append("&timestamp=").append(timestamp);
        //https://cs1812.cam4.com/cam4-edge-live/siskihhhhhhh-246-3fef735f-9d4a-4d38-a8e8-4a6487ecedad_aac/playlist.m3u8?referer=pt.cam4.com&timestamp=1498251315869";

        return Uri.parse(sb.toString());
    }

    public Map<String, String> getVideoHeaders() {
        Map<String, String> headers = new HashMap<>();

        headers.put("Referer", "https://pt.cam4.com/" + this.username);

        return headers;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getPageUrl() {
        return ROOT_URL + "/" + this.username;
    }
}

