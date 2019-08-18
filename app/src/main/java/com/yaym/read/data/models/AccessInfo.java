package com.yaym.read.data.models;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessInfo {

    @SerializedName("country")
    @Expose
    private String accessCountry;
    @SerializedName("viewability")
    @Expose
    private String viewability;
    @SerializedName("embeddable")
    @Expose
    private Boolean embeddable;
    @SerializedName("publicDomain")
    @Expose
    private Boolean publicDomain;
    @SerializedName("textToSpeechPermission")
    @Expose
    private String textToSpeechPermission;
    @SerializedName("epub")
    @Embedded
    @Expose
    private Epub epub;
    @SerializedName("pdf")
    @Embedded
    @Expose
    private Pdf pdf;
    @SerializedName("webReaderLink")
    @Expose
    private String webReaderLink;
    @SerializedName("accessViewStatus")
    @Expose
    private String accessViewStatus;
    @SerializedName("quoteSharingAllowed")
    @Expose
    private Boolean quoteSharingAllowed;

    /**
     * No args constructor for use in serialization
     * 
     */
    @Ignore
    public AccessInfo() {
    }

    /**
     * 
     * @param webReaderLink
     * @param textToSpeechPermission
     * @param publicDomain
     * @param viewability
     * @param accessViewStatus
     * @param pdf
     * @param epub
     * @param embeddable
     * @param quoteSharingAllowed
     * @param accessCountry
     */
    public AccessInfo(String accessCountry, String viewability, Boolean embeddable, Boolean publicDomain, String textToSpeechPermission, Epub epub, Pdf pdf, String webReaderLink, String accessViewStatus, Boolean quoteSharingAllowed) {
        super();
        this.accessCountry = accessCountry;
        this.viewability = viewability;
        this.embeddable = embeddable;
        this.publicDomain = publicDomain;
        this.textToSpeechPermission = textToSpeechPermission;
        this.epub = epub;
        this.pdf = pdf;
        this.webReaderLink = webReaderLink;
        this.accessViewStatus = accessViewStatus;
        this.quoteSharingAllowed = quoteSharingAllowed;
    }

    public String getAccessCountry() {
        return accessCountry;
    }

    public void setAccessCountry(String accessCountry) {
        this.accessCountry = accessCountry;
    }

    public String getViewability() {
        return viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public Boolean getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Boolean embeddable) {
        this.embeddable = embeddable;
    }

    public Boolean getPublicDomain() {
        return publicDomain;
    }

    public void setPublicDomain(Boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public String getTextToSpeechPermission() {
        return textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public Epub getEpub() {
        return epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public Pdf getPdf() {
        return pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getAccessViewStatus() {
        return accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    public Boolean getQuoteSharingAllowed() {
        return quoteSharingAllowed;
    }

    public void setQuoteSharingAllowed(Boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }

}
