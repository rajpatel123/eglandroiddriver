package com.eaglecabs.provider.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("avatar")
    @Expose
    private Object avatar;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fleet")
    @Expose
    private Fleet fleet;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("city")
    @Expose
    private String city;

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    @SerializedName("service_type")
    @Expose
    private String service_type;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("city_latitude")
    @Expose
    private Double cityLatitude;
    @SerializedName("city_longitude")
    @Expose
    private Double cityLongitude;
    @SerializedName("referral_id")
    @Expose
    private Object referralId;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("usage_count")
    @Expose
    private Object usageCount;
    @SerializedName("referral_earning")
    @Expose
    private Object referralEarning;
    @SerializedName("usage_limit")
    @Expose
    private Object usageLimit;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;
    @SerializedName("otp")
    @Expose
    private Integer otp;
    @SerializedName("trip_type")
    @Expose
    private String tripType;
    @SerializedName("outstation_type")
    @Expose
    private String outstationType;
    @SerializedName("wallet_balance")
    @Expose
    private Double walletBalance;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("social_unique_id")
    @Expose
    private Object socialUniqueId;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("service")
    @Expose
    private List<Service> service = null;
    @SerializedName("country")
    @Expose
    private Country country;
    @SerializedName("sos")
    @Expose
    private String sos;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device")
    @Expose
    private Device device;
    @SerializedName("provider_vehicle")
    @Expose
    private ProviderVehicle providerVehicle;
    @SerializedName("login_mins")
    @Expose
    private Integer loginMins = 0;
    @SerializedName("wallet_message")
    @Expose
    private String walletMessage;



    @SerializedName("emergency_contact1")
    @Expose
    private String emergencyContact1;
    @SerializedName("emergency_contact2")
    @Expose
    private String emergencyContact2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getCityLatitude() {
        return cityLatitude;
    }

    public void setCityLatitude(Double cityLatitude) {
        this.cityLatitude = cityLatitude;
    }

    public Double getCityLongitude() {
        return cityLongitude;
    }

    public void setCityLongitude(Double cityLongitude) {
        this.cityLongitude = cityLongitude;
    }

    public Object getReferralId() {
        return referralId;
    }

    public void setReferralId(Object referralId) {
        this.referralId = referralId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Object getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Object usageCount) {
        this.usageCount = usageCount;
    }

    public Object getReferralEarning() {
        return referralEarning;
    }

    public void setReferralEarning(Object referralEarning) {
        this.referralEarning = referralEarning;
    }

    public Object getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Object usageLimit) {
        this.usageLimit = usageLimit;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getOutstationType() {
        return outstationType;
    }

    public void setOutstationType(String outstationType) {
        this.outstationType = outstationType;
    }

    public Double getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Double walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public Object getSocialUniqueId() {
        return socialUniqueId;
    }

    public void setSocialUniqueId(Object socialUniqueId) {
        this.socialUniqueId = socialUniqueId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSos() {
        return sos;
    }

    public void setSos(String sos) {
        this.sos = sos;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getEmergencyContact1() {
        return emergencyContact1;
    }

    public void setEmergencyContact1(String emergencyContact1) {
        this.emergencyContact1 = emergencyContact1;
    }

    public String getEmergencyContact2() {
        return emergencyContact2;
    }

    public void setEmergencyContact2(String emergencyContact2) {
        this.emergencyContact2 = emergencyContact2;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public ProviderVehicle getProviderVehicle() {
        return providerVehicle;
    }

    public void setProviderVehicle(ProviderVehicle providerVehicle) {
        this.providerVehicle = providerVehicle;
    }

    public Integer getLoginMins() {
        return loginMins;
    }

    public void setLoginMins(Integer loginMins) {
        this.loginMins = loginMins;
    }

    public String getWalletMessage() {
        return walletMessage;
    }

    public void setWalletMessage(String walletMessage) {
        this.walletMessage = walletMessage;
    }
}
