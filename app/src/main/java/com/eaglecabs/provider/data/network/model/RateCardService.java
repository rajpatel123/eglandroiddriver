package com.eaglecabs.provider.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RateCardService {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("provider_name")
    @Expose
    private String providerName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("fixed")
    @Expose
    private Integer fixed;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("night_fare")
    @Expose
    private String nightFare;
    @SerializedName("minute")
    @Expose
    private Integer minute;
    @SerializedName("peak_time_11pm_6am")
    @Expose
    private Integer peakTime11pm6am;
    @SerializedName("peak_time_5pm_9pm")
    @Expose
    private Integer peakTime5pm9pm;
    @SerializedName("peak_time_8am_11am")
    @Expose
    private Integer peakTime8am11am;
    @SerializedName("outstation_km")
    @Expose
    private Integer outstationKm;
    @SerializedName("roundtrip_km")
    @Expose
    private String roundtripKm;
    @SerializedName("rental_fare")
    @Expose
    private Object rentalFare;
    @SerializedName("rental_km_price")
    @Expose
    private String rentalKmPrice;
    @SerializedName("rental_hour_price")
    @Expose
    private String rentalHourPrice;
    @SerializedName("outstation_driver")
    @Expose
    private Integer outstationDriver;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("between_km")
    @Expose
    private Integer betweenKm;
    @SerializedName("calculator")
    @Expose
    private String calculator;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("tourism_price")
    @Expose
    private Object tourismPrice;
    @SerializedName("inclusions")
    @Expose
    private String inclusions;
    @SerializedName("exclusions")
    @Expose
    private String exclusions;
    @SerializedName("facilities")
    @Expose
    private String facilities;
    @SerializedName("outstation_oneway_price")
    @Expose
    private Integer outstationOnewayPrice;
    @SerializedName("outstation_base_km")
    @Expose
    private String outstationBaseKm;
    @SerializedName("daily_rides")
    @Expose
    private Integer dailyRides;
    @SerializedName("rental_rides")
    @Expose
    private Integer rentalRides;
    @SerializedName("outstation_rides")
    @Expose
    private Integer outstationRides;
    @SerializedName("service_type")
    @Expose
    private String serviceType;
    @SerializedName("one_way_hour")
    @Expose
    private Integer oneWayHour;
    @SerializedName("round_way_hour")
    @Expose
    private Integer roundWayHour;
    @SerializedName("rental_minute_price")
    @Expose
    private Integer rentalMinutePrice;
    @SerializedName("rental_hour_package")
    @Expose
    private List<RentalHourPackage> rentalHourPackage = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getNightFare() {
        return nightFare;
    }

    public void setNightFare(String nightFare) {
        this.nightFare = nightFare;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getPeakTime11pm6am() {
        return peakTime11pm6am;
    }

    public void setPeakTime11pm6am(Integer peakTime11pm6am) {
        this.peakTime11pm6am = peakTime11pm6am;
    }

    public Integer getPeakTime5pm9pm() {
        return peakTime5pm9pm;
    }

    public void setPeakTime5pm9pm(Integer peakTime5pm9pm) {
        this.peakTime5pm9pm = peakTime5pm9pm;
    }

    public Integer getPeakTime8am11am() {
        return peakTime8am11am;
    }

    public void setPeakTime8am11am(Integer peakTime8am11am) {
        this.peakTime8am11am = peakTime8am11am;
    }

    public Integer getOutstationKm() {
        return outstationKm;
    }

    public void setOutstationKm(Integer outstationKm) {
        this.outstationKm = outstationKm;
    }

    public String getRoundtripKm() {
        return roundtripKm;
    }

    public void setRoundtripKm(String roundtripKm) {
        this.roundtripKm = roundtripKm;
    }

    public Object getRentalFare() {
        return rentalFare;
    }

    public void setRentalFare(Object rentalFare) {
        this.rentalFare = rentalFare;
    }

    public String getRentalKmPrice() {
        return rentalKmPrice;
    }

    public void setRentalKmPrice(String rentalKmPrice) {
        this.rentalKmPrice = rentalKmPrice;
    }

    public String getRentalHourPrice() {
        return rentalHourPrice;
    }

    public void setRentalHourPrice(String rentalHourPrice) {
        this.rentalHourPrice = rentalHourPrice;
    }

    public Integer getOutstationDriver() {
        return outstationDriver;
    }

    public void setOutstationDriver(Integer outstationDriver) {
        this.outstationDriver = outstationDriver;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getBetweenKm() {
        return betweenKm;
    }

    public void setBetweenKm(Integer betweenKm) {
        this.betweenKm = betweenKm;
    }

    public String getCalculator() {
        return calculator;
    }

    public void setCalculator(String calculator) {
        this.calculator = calculator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getTourismPrice() {
        return tourismPrice;
    }

    public void setTourismPrice(Object tourismPrice) {
        this.tourismPrice = tourismPrice;
    }

    public String getInclusions() {
        return inclusions;
    }

    public void setInclusions(String inclusions) {
        this.inclusions = inclusions;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public Integer getOutstationOnewayPrice() {
        return outstationOnewayPrice;
    }

    public void setOutstationOnewayPrice(Integer outstationOnewayPrice) {
        this.outstationOnewayPrice = outstationOnewayPrice;
    }

    public String getOutstationBaseKm() {
        return outstationBaseKm;
    }

    public void setOutstationBaseKm(String outstationBaseKm) {
        this.outstationBaseKm = outstationBaseKm;
    }

    public Integer getDailyRides() {
        return dailyRides;
    }

    public void setDailyRides(Integer dailyRides) {
        this.dailyRides = dailyRides;
    }

    public Integer getRentalRides() {
        return rentalRides;
    }

    public void setRentalRides(Integer rentalRides) {
        this.rentalRides = rentalRides;
    }

    public Integer getOutstationRides() {
        return outstationRides;
    }

    public void setOutstationRides(Integer outstationRides) {
        this.outstationRides = outstationRides;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getOneWayHour() {
        return oneWayHour;
    }

    public void setOneWayHour(Integer oneWayHour) {
        this.oneWayHour = oneWayHour;
    }

    public Integer getRoundWayHour() {
        return roundWayHour;
    }

    public void setRoundWayHour(Integer roundWayHour) {
        this.roundWayHour = roundWayHour;
    }

    public Integer getRentalMinutePrice() {
        return rentalMinutePrice;
    }

    public void setRentalMinutePrice(Integer rentalMinutePrice) {
        this.rentalMinutePrice = rentalMinutePrice;
    }

    public List<RentalHourPackage> getRentalHourPackage() {
        return rentalHourPackage;
    }

    public void setRentalHourPackage(List<RentalHourPackage> rentalHourPackage) {
        this.rentalHourPackage = rentalHourPackage;
    }

}
