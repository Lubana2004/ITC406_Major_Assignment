
package com.example.itc406_major_assignment;

public class MedicalReportModel {

    String patientId;
    String reportType;
    String doctor;
    String fileUrl;

    public MedicalReportModel() {}

    public MedicalReportModel(String patientId,
                              String reportType,
                              String doctor,
                              String fileUrl) {

        this.patientId = patientId;
        this.reportType = reportType;
        this.doctor = doctor;
        this.fileUrl = fileUrl;
    }
}