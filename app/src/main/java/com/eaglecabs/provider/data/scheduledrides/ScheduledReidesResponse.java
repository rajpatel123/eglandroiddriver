package com.eaglecabs.provider.data.scheduledrides;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduledReidesResponse {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("booking_id")
@Expose
private String bookingId;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("type")
@Expose
private String type;
@SerializedName("ride_option")
@Expose
private String rideOption;
@SerializedName("broadcast")
@Expose
private String broadcast;
@SerializedName("provider_id")
@Expose
private Integer providerId;
@SerializedName("current_provider_id")
@Expose
private Integer currentProviderId;
@SerializedName("service_type_id")
@Expose
private Integer serviceTypeId;
@SerializedName("rental_hours")
@Expose
private Object rentalHours;
@SerializedName("status")
@Expose
private String status;
@SerializedName("invoice_email")
@Expose
private Object invoiceEmail;
@SerializedName("out_leave")
@Expose
private String outLeave;
@SerializedName("out_return")
@Expose
private String outReturn;
@SerializedName("day")
@Expose
private String day;
@SerializedName("cancelled_by")
@Expose
private String cancelledBy;
@SerializedName("cancel_reason")
@Expose
private Object cancelReason;
@SerializedName("payment_mode")
@Expose
private String paymentMode;
@SerializedName("service_required")
@Expose
private String serviceRequired;
@SerializedName("paid")
@Expose
private Integer paid;
@SerializedName("total_amount")
@Expose
private Integer totalAmount;
@SerializedName("is_track")
@Expose
private String isTrack;
@SerializedName("distance")
@Expose
private Double distance;
@SerializedName("travel_time")
@Expose
private Object travelTime;
@SerializedName("s_address")
@Expose
private String sAddress;
@SerializedName("s_latitude")
@Expose
private Double sLatitude;
@SerializedName("s_longitude")
@Expose
private Double sLongitude;
@SerializedName("d_address")
@Expose
private String dAddress;
@SerializedName("otp")
@Expose
private String otp;
@SerializedName("d_latitude")
@Expose
private Double dLatitude;
@SerializedName("track_distance")
@Expose
private Integer trackDistance;
@SerializedName("track_latitude")
@Expose
private Integer trackLatitude;
@SerializedName("track_longitude")
@Expose
private Integer trackLongitude;
@SerializedName("d_longitude")
@Expose
private Double dLongitude;
@SerializedName("assigned_at")
@Expose
private String assignedAt;
@SerializedName("schedule_at")
@Expose
private String scheduleAt;
@SerializedName("started_at")
@Expose
private Object startedAt;
@SerializedName("finished_at")
@Expose
private Object finishedAt;
@SerializedName("is_scheduled")
@Expose
private String isScheduled;
@SerializedName("user_rated")
@Expose
private Integer userRated;
@SerializedName("provider_rated")
@Expose
private Integer providerRated;
@SerializedName("use_wallet")
@Expose
private Integer useWallet;
@SerializedName("surge")
@Expose
private Integer surge;
@SerializedName("route_key")
@Expose
private String routeKey;
@SerializedName("geo_fencing_id")
@Expose
private Integer geoFencingId;
@SerializedName("geo_fencing_distance")
@Expose
private Integer geoFencingDistance;
@SerializedName("deleted_at")
@Expose
private Object deletedAt;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("updated_at")
@Expose
private String updatedAt;
@SerializedName("geo_time")
@Expose
private Object geoTime;
@SerializedName("driver_accept_time")
@Expose
private Object driverAcceptTime;
@SerializedName("driver_reached_time")
@Expose
private Object driverReachedTime;
@SerializedName("arrival_estimate_time")
@Expose
private Object arrivalEstimateTime;
@SerializedName("eta_discount")
@Expose
private Integer etaDiscount;
@SerializedName("tourism_pack_id")
@Expose
private Object tourismPackId;
@SerializedName("driver_end_distance")
@Expose
private Object driverEndDistance;
@SerializedName("service_type")
@Expose
private ServiceType serviceType;
@SerializedName("provider")
@Expose
private Object provider;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getBookingId() {
return bookingId;
}

public void setBookingId(String bookingId) {
this.bookingId = bookingId;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getRideOption() {
return rideOption;
}

public void setRideOption(String rideOption) {
this.rideOption = rideOption;
}

public String getBroadcast() {
return broadcast;
}

public void setBroadcast(String broadcast) {
this.broadcast = broadcast;
}

public Integer getProviderId() {
return providerId;
}

public void setProviderId(Integer providerId) {
this.providerId = providerId;
}

public Integer getCurrentProviderId() {
return currentProviderId;
}

public void setCurrentProviderId(Integer currentProviderId) {
this.currentProviderId = currentProviderId;
}

public Integer getServiceTypeId() {
return serviceTypeId;
}

public void setServiceTypeId(Integer serviceTypeId) {
this.serviceTypeId = serviceTypeId;
}

public Object getRentalHours() {
return rentalHours;
}

public void setRentalHours(Object rentalHours) {
this.rentalHours = rentalHours;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public Object getInvoiceEmail() {
return invoiceEmail;
}

public void setInvoiceEmail(Object invoiceEmail) {
this.invoiceEmail = invoiceEmail;
}

public String getOutLeave() {
return outLeave;
}

public void setOutLeave(String outLeave) {
this.outLeave = outLeave;
}

public String getOutReturn() {
return outReturn;
}

public void setOutReturn(String outReturn) {
this.outReturn = outReturn;
}

public String getDay() {
return day;
}

public void setDay(String day) {
this.day = day;
}

public String getCancelledBy() {
return cancelledBy;
}

public void setCancelledBy(String cancelledBy) {
this.cancelledBy = cancelledBy;
}

public Object getCancelReason() {
return cancelReason;
}

public void setCancelReason(Object cancelReason) {
this.cancelReason = cancelReason;
}

public String getPaymentMode() {
return paymentMode;
}

public void setPaymentMode(String paymentMode) {
this.paymentMode = paymentMode;
}

public String getServiceRequired() {
return serviceRequired;
}

public void setServiceRequired(String serviceRequired) {
this.serviceRequired = serviceRequired;
}

public Integer getPaid() {
return paid;
}

public void setPaid(Integer paid) {
this.paid = paid;
}

public Integer getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(Integer totalAmount) {
this.totalAmount = totalAmount;
}

public String getIsTrack() {
return isTrack;
}

public void setIsTrack(String isTrack) {
this.isTrack = isTrack;
}

public Double getDistance() {
return distance;
}

public void setDistance(Double distance) {
this.distance = distance;
}

public Object getTravelTime() {
return travelTime;
}

public void setTravelTime(Object travelTime) {
this.travelTime = travelTime;
}

public String getSAddress() {
return sAddress;
}

public void setSAddress(String sAddress) {
this.sAddress = sAddress;
}

public Double getSLatitude() {
return sLatitude;
}

public void setSLatitude(Double sLatitude) {
this.sLatitude = sLatitude;
}

public Double getSLongitude() {
return sLongitude;
}

public void setSLongitude(Double sLongitude) {
this.sLongitude = sLongitude;
}

public String getDAddress() {
return dAddress;
}

public void setDAddress(String dAddress) {
this.dAddress = dAddress;
}

public String getOtp() {
return otp;
}

public void setOtp(String otp) {
this.otp = otp;
}

public Double getDLatitude() {
return dLatitude;
}

public void setDLatitude(Double dLatitude) {
this.dLatitude = dLatitude;
}

public Integer getTrackDistance() {
return trackDistance;
}

public void setTrackDistance(Integer trackDistance) {
this.trackDistance = trackDistance;
}

public Integer getTrackLatitude() {
return trackLatitude;
}

public void setTrackLatitude(Integer trackLatitude) {
this.trackLatitude = trackLatitude;
}

public Integer getTrackLongitude() {
return trackLongitude;
}

public void setTrackLongitude(Integer trackLongitude) {
this.trackLongitude = trackLongitude;
}

public Double getDLongitude() {
return dLongitude;
}

public void setDLongitude(Double dLongitude) {
this.dLongitude = dLongitude;
}

public String getAssignedAt() {
return assignedAt;
}

public void setAssignedAt(String assignedAt) {
this.assignedAt = assignedAt;
}

public String getScheduleAt() {
return scheduleAt;
}

public void setScheduleAt(String scheduleAt) {
this.scheduleAt = scheduleAt;
}

public Object getStartedAt() {
return startedAt;
}

public void setStartedAt(Object startedAt) {
this.startedAt = startedAt;
}

public Object getFinishedAt() {
return finishedAt;
}

public void setFinishedAt(Object finishedAt) {
this.finishedAt = finishedAt;
}

public String getIsScheduled() {
return isScheduled;
}

public void setIsScheduled(String isScheduled) {
this.isScheduled = isScheduled;
}

public Integer getUserRated() {
return userRated;
}

public void setUserRated(Integer userRated) {
this.userRated = userRated;
}

public Integer getProviderRated() {
return providerRated;
}

public void setProviderRated(Integer providerRated) {
this.providerRated = providerRated;
}

public Integer getUseWallet() {
return useWallet;
}

public void setUseWallet(Integer useWallet) {
this.useWallet = useWallet;
}

public Integer getSurge() {
return surge;
}

public void setSurge(Integer surge) {
this.surge = surge;
}

public String getRouteKey() {
return routeKey;
}

public void setRouteKey(String routeKey) {
this.routeKey = routeKey;
}

public Integer getGeoFencingId() {
return geoFencingId;
}

public void setGeoFencingId(Integer geoFencingId) {
this.geoFencingId = geoFencingId;
}

public Integer getGeoFencingDistance() {
return geoFencingDistance;
}

public void setGeoFencingDistance(Integer geoFencingDistance) {
this.geoFencingDistance = geoFencingDistance;
}

public Object getDeletedAt() {
return deletedAt;
}

public void setDeletedAt(Object deletedAt) {
this.deletedAt = deletedAt;
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

public Object getGeoTime() {
return geoTime;
}

public void setGeoTime(Object geoTime) {
this.geoTime = geoTime;
}

public Object getDriverAcceptTime() {
return driverAcceptTime;
}

public void setDriverAcceptTime(Object driverAcceptTime) {
this.driverAcceptTime = driverAcceptTime;
}

public Object getDriverReachedTime() {
return driverReachedTime;
}

public void setDriverReachedTime(Object driverReachedTime) {
this.driverReachedTime = driverReachedTime;
}

public Object getArrivalEstimateTime() {
return arrivalEstimateTime;
}

public void setArrivalEstimateTime(Object arrivalEstimateTime) {
this.arrivalEstimateTime = arrivalEstimateTime;
}

public Integer getEtaDiscount() {
return etaDiscount;
}

public void setEtaDiscount(Integer etaDiscount) {
this.etaDiscount = etaDiscount;
}

public Object getTourismPackId() {
return tourismPackId;
}

public void setTourismPackId(Object tourismPackId) {
this.tourismPackId = tourismPackId;
}

public Object getDriverEndDistance() {
return driverEndDistance;
}

public void setDriverEndDistance(Object driverEndDistance) {
this.driverEndDistance = driverEndDistance;
}

public ServiceType getServiceType() {
return serviceType;
}

public void setServiceType(ServiceType serviceType) {
this.serviceType = serviceType;
}

public Object getProvider() {
return provider;
}

public void setProvider(Object provider) {
this.provider = provider;
}

}
