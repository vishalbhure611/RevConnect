package com.rev.model;


public class BusinessProfile extends Profile {

    private String industry;
    private String contactInfo;
    private String address;
    private String businessHours;
    private String externalLinks;
    private String productsServices;

    public BusinessProfile() {}

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(String externalLinks) {
        this.externalLinks = externalLinks;
    }

    public String getProductsServices() {
        return productsServices;
    }

    public void setProductsServices(String productsServices) {
        this.productsServices = productsServices;
    }
}

