package com.example.demo.models;

public class OtpResponse {
    private final String otp;
    private final long duration;

    public OtpResponse(String otp, long duration) {
        this.otp = otp;
        this.duration = duration;
    }

    public String getOtp() {
        return otp;
    }

    public long getDuration() {
        return duration;
    }
}
