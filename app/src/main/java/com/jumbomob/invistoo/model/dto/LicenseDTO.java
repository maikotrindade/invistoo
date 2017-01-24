package com.jumbomob.invistoo.model.dto;

/**
 * Created by trindade on 1/24/17.
 */

public class LicenseDTO {

    private String libraryName;
    private String libraryVersion;
    private String licenseSummary;

    public LicenseDTO() {
    }

    public LicenseDTO(String libraryName, String libraryVersion, String licenseSummary) {
        this.libraryName = libraryName;
        this.libraryVersion = libraryVersion;
        this.licenseSummary = licenseSummary;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryVersion() {
        return libraryVersion;
    }

    public void setLibraryVersion(String libraryVersion) {
        this.libraryVersion = libraryVersion;
    }

    public String getLicenseSummary() {
        return licenseSummary;
    }

    public void setLicenseSummary(String licenseSummary) {
        this.licenseSummary = licenseSummary;
    }
}
