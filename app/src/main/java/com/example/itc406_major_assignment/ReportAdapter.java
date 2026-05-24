package com.example.itc406_major_assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportAdapter extends
        RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    ArrayList<ReportModel> reportList;

    public ReportAdapter(ArrayList<ReportModel> reportList) {
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        ReportModel model = reportList.get(position);

        holder.txtPatientName.setText(
                "Patient: " + model.getPatientName());

        holder.txtReportType.setText(
                "Report: " + model.getReportType());

        holder.txtDoctorName.setText(
                "Doctor: " + model.getDoctorName());

        holder.txtReportDate.setText(
                "Date: " + model.getReportDate());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtPatientName,
                txtReportType,
                txtDoctorName,
                txtReportDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPatientName =
                    itemView.findViewById(R.id.txtPatientName);

            txtReportType =
                    itemView.findViewById(R.id.txtReportType);

            txtDoctorName =
                    itemView.findViewById(R.id.txtDoctorName);

            txtReportDate =
                    itemView.findViewById(R.id.txtReportDate);
        }
    }
}