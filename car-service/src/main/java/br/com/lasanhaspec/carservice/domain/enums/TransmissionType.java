package br.com.lasanhaspec.carservice.domain.enums;

public enum TransmissionType {
    MANUAL,
    AUTOMATIC,
    AUTOMATED_MANUAL, // robotizado (Dualogic, i-Motion)
    DUAL_CLUTCH,      // DSG, PowerShift
    CVT
}