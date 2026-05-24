package com.example.itc406_major_assignment;

public class ReportModel {

    String patientName;
    String reportType;
    String doctorName;
    String reportDate;

    public ReportModel(String patientName,
                       String reportType,
                       String doctorName,
                       String reportDate) {

        this.patientName = patientName;
        this.reportType = reportType;
        this.doctorName = doctorName;
        this.reportDate = reportDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getReportType() {
        return reportType;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getReportDate() {
        return reportDate;
    }
}
