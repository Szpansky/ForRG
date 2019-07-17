package com.apps.mkacik.forRG.DataModel;

import java.util.Objects;

public class LaunchpadModel {

    public static final String ACTIVE = "active";
    public static final String RETIRED = "retired";

    public LaunchpadModel(int id, String status, LocationModel location) {
        this.id = id;
        this.status = status;
        this.location = location;
    }

    private int id;
    private String status;
    private LocationModel location;
    private String wikipedia;
    private String site_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getWikipediaURL() {
        return wikipedia;
    }

    public void setWikipediaURL(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaunchpadModel that = (LaunchpadModel) o;
        return id == that.id &&
                Objects.equals(status, that.status) &&
                Objects.equals(location, that.location) &&
                Objects.equals(wikipedia, that.wikipedia) &&
                Objects.equals(site_id, that.site_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, location, wikipedia, site_id);
    }
}
