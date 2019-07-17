package com.apps.mkacik.forRG.DataModel;

import java.util.Objects;

public class LaunchPadModelDetail extends LaunchpadModel {

    public LaunchPadModelDetail(int id, String status, LocationModel location, String details) {
        super(id, status, location);
        this.details = details;
    }

    private String details;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LaunchPadModelDetail that = (LaunchPadModelDetail) o;
        return Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), details);
    }


}
