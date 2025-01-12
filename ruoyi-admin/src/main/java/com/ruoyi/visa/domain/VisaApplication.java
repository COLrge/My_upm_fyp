package com.ruoyi.visa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Visa Application Form对象 visa_application
 * 
 * @author ruoyi
 * @date 2024-11-30
 */


public class VisaApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Primary Key */
    private Long id;

    /** Applicant ID */
    @Excel(name = "Applicant ID")
    private Long applicantId;

    /** Name as in Passport MRZ format */
    @Excel(name = "Name as in Passport MRZ format")
    private String name;

    /** Matric Number */
    @Excel(name = "Matric Number")
    private String matricNumber;

    /** Passport Number */
    @Excel(name = "Passport Number")
    private String passportNumber;

    /** Email Address */
    @Excel(name = "Email Address")
    private String email;

    /** Full Address in Malaysia */
    @Excel(name = "Full Address in Malaysia")
    private String malaysiaAddress;

    /** Full Address Overseas */
    @Excel(name = "Full Address Overseas")
    private String overseasAddress;

    /** Course Level */
    @Excel(name = "Course Level")
    private String courseLevel;

    /** Nationality */
    @Excel(name = "Nationality")
    private String nationality;

    /** Mobile Number (WhatsApp) */
    @Excel(name = "Mobile Number (WhatsApp)")
    private String mobileNumber;

    /** Location when applying */
    @Excel(name = "Location when applying")
    private String applicationLocation;

    /** Course Name */
    @Excel(name = "Course Name")
    private String courseName;

    /** Faculty/Institute/School Name */
    @Excel(name = "Faculty/Institute/School Name")
    private String facultyName;

    /** Supervisor/Advisor Name */
    @Excel(name = "Supervisor/Advisor Name")
    private String supervisorName;

    /** Sponsorship status */
    @Excel(name = "Sponsorship status")
    private String sponsorshipStatus;

    /** Study Status (Current semester) */
    @Excel(name = "Study Status (Current semester)")
    private String studyStatus;

    /** Marital Status */
    @Excel(name = "Marital Status")
    private String maritalStatus;

    /** Date of Birth */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Date of Birth", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dateOfBirth;

    /** Validity Date of Current Pass */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Validity Date of Current Pass", width = 30, dateFormat = "yyyy-MM-dd")
    private Date currentPassValidity;

    /** Travel Document Issued Date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Travel Document Issued Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date travelDocumentIssueDate;

    /** Travel Document Valid Until */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Travel Document Valid Until", width = 30, dateFormat = "yyyy-MM-dd")
    private Date travelDocumentExpiryDate;

    /** Latest date enter Malaysia */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Latest date enter Malaysia", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastEntryDate;

    /** Travel Document (Passport) Type */
    @Excel(name = "Travel Document (Passport) Type")
    private String travelDocumentType;

    /** Type of Current Pass */
    @Excel(name = "Type of Current Pass")
    private String currentPassType;

    /** Insurance Cover Note Company */
    @Excel(name = "Insurance Cover Note Company")
    private String insuranceCompany;

    /** Application Status: DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED */
    @Excel(name = "Application Status: DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED")
    private String status;

    /** Reviewer ID */
    @Excel(name = "Reviewer ID")
    private Long reviewerId;

    /** Remarks (including rejection reasons) */
    @Excel(name = "Remarks (including rejection reasons)")
    private String remarks;

    /** Created Time */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Created Time", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** Updated Time */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Updated Time", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    /** Boarding Pass Image */
    @Excel(name = "Boarding Pass Image")
    private String boardingPassImg;

    /** Passport Image */
    @Excel(name = "Passport Image")
    private String passportImg;

    /** Old Passport Image */
    @Excel(name = "Old Passport Image")
    private String oldPassportImg;

    /** Registration Slip Image */
    @Excel(name = "Registration Slip Image")
    private String registrationSlipImg;

    /** Academic Result Image */
    @Excel(name = "Academic Result Image")
    private String academicResultImg;

    /** Attendance Report Image */
    @Excel(name = "Attendance Report Image")
    private String attendanceReportImg;

    /** Progress Report Image */
    @Excel(name = "Progress Report Image")
    private String progressReportImg;

    /** Supporting Letter Image */
    @Excel(name = "Supporting Letter Image")
    private String supportingLetterImg;

    /** EMGS Payment Image */
    @Excel(name = "EMGS Payment Image")
    private String emgsPaymentImg;

    /** UPM Payment Image */
    @Excel(name = "UPM Payment Image")
    private String upmPaymentImg;

    /** Insurance Cover Note Image */
    @Excel(name = "Insurance Cover Note Image")
    private String insuranceCoverNoteImg;


    public String getBoardingPassImg() {
        return boardingPassImg;
    }

    public void setBoardingPassImg(String boardingPassImg) {
        this.boardingPassImg = boardingPassImg;
    }

    public String getPassportImg() {
        return passportImg;
    }

    public void setPassportImg(String passportImg) {
        this.passportImg = passportImg;
    }

    public String getOldPassportImg() {
        return oldPassportImg;
    }

    public void setOldPassportImg(String oldPassportImg) {
        this.oldPassportImg = oldPassportImg;
    }

    public String getRegistrationSlipImg() {
        return registrationSlipImg;
    }

    public void setRegistrationSlipImg(String registrationSlipImg) {
        this.registrationSlipImg = registrationSlipImg;
    }

    public String getAcademicResultImg() {
        return academicResultImg;
    }

    public void setAcademicResultImg(String academicResultImg) {
        this.academicResultImg = academicResultImg;
    }

    public String getAttendanceReportImg() {
        return attendanceReportImg;
    }

    public void setAttendanceReportImg(String attendanceReportImg) {
        this.attendanceReportImg = attendanceReportImg;
    }

    public String getProgressReportImg() {
        return progressReportImg;
    }

    public void setProgressReportImg(String progressReportImg) {
        this.progressReportImg = progressReportImg;
    }

    public String getSupportingLetterImg() {
        return supportingLetterImg;
    }

    public void setSupportingLetterImg(String supportingLetterImg) {
        this.supportingLetterImg = supportingLetterImg;
    }

    public String getEmgsPaymentImg() {
        return emgsPaymentImg;
    }

    public void setEmgsPaymentImg(String emgsPaymentImg) {
        this.emgsPaymentImg = emgsPaymentImg;
    }

    public String getUpmPaymentImg() {
        return upmPaymentImg;
    }

    public void setUpmPaymentImg(String upmPaymentImg) {
        this.upmPaymentImg = upmPaymentImg;
    }

    public String getInsuranceCoverNoteImg() {
        return insuranceCoverNoteImg;
    }

    public void setInsuranceCoverNoteImg(String insuranceCoverNoteImg) {
        this.insuranceCoverNoteImg = insuranceCoverNoteImg;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setApplicantId(Long applicantId) 
    {
        this.applicantId = applicantId;
    }

    public Long getApplicantId() 
    {
        return applicantId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setMatricNumber(String matricNumber) 
    {
        this.matricNumber = matricNumber;
    }

    public String getMatricNumber() 
    {
        return matricNumber;
    }
    public void setPassportNumber(String passportNumber) 
    {
        this.passportNumber = passportNumber;
    }

    public String getPassportNumber() 
    {
        return passportNumber;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setMalaysiaAddress(String malaysiaAddress) 
    {
        this.malaysiaAddress = malaysiaAddress;
    }

    public String getMalaysiaAddress() 
    {
        return malaysiaAddress;
    }
    public void setOverseasAddress(String overseasAddress) 
    {
        this.overseasAddress = overseasAddress;
    }

    public String getOverseasAddress() 
    {
        return overseasAddress;
    }
    public void setCourseLevel(String courseLevel) 
    {
        this.courseLevel = courseLevel;
    }

    public String getCourseLevel() 
    {
        return courseLevel;
    }
    public void setNationality(String nationality) 
    {
        this.nationality = nationality;
    }

    public String getNationality() 
    {
        return nationality;
    }
    public void setMobileNumber(String mobileNumber) 
    {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() 
    {
        return mobileNumber;
    }
    public void setApplicationLocation(String applicationLocation) 
    {
        this.applicationLocation = applicationLocation;
    }

    public String getApplicationLocation() 
    {
        return applicationLocation;
    }
    public void setCourseName(String courseName) 
    {
        this.courseName = courseName;
    }

    public String getCourseName() 
    {
        return courseName;
    }
    public void setFacultyName(String facultyName) 
    {
        this.facultyName = facultyName;
    }

    public String getFacultyName() 
    {
        return facultyName;
    }
    public void setSupervisorName(String supervisorName) 
    {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorName() 
    {
        return supervisorName;
    }
    public void setSponsorshipStatus(String sponsorshipStatus) 
    {
        this.sponsorshipStatus = sponsorshipStatus;
    }

    public String getSponsorshipStatus() 
    {
        return sponsorshipStatus;
    }
    public void setStudyStatus(String studyStatus) 
    {
        this.studyStatus = studyStatus;
    }

    public String getStudyStatus() 
    {
        return studyStatus;
    }
    public void setMaritalStatus(String maritalStatus) 
    {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() 
    {
        return maritalStatus;
    }
    public void setDateOfBirth(Date dateOfBirth) 
    {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() 
    {
        return dateOfBirth;
    }
    public void setCurrentPassValidity(Date currentPassValidity) 
    {
        this.currentPassValidity = currentPassValidity;
    }

    public Date getCurrentPassValidity() 
    {
        return currentPassValidity;
    }
    public void setTravelDocumentIssueDate(Date travelDocumentIssueDate) 
    {
        this.travelDocumentIssueDate = travelDocumentIssueDate;
    }

    public Date getTravelDocumentIssueDate() 
    {
        return travelDocumentIssueDate;
    }
    public void setTravelDocumentExpiryDate(Date travelDocumentExpiryDate) 
    {
        this.travelDocumentExpiryDate = travelDocumentExpiryDate;
    }

    public Date getTravelDocumentExpiryDate() 
    {
        return travelDocumentExpiryDate;
    }
    public void setLastEntryDate(Date lastEntryDate) 
    {
        this.lastEntryDate = lastEntryDate;
    }

    public Date getLastEntryDate() 
    {
        return lastEntryDate;
    }
    public void setTravelDocumentType(String travelDocumentType) 
    {
        this.travelDocumentType = travelDocumentType;
    }

    public String getTravelDocumentType() 
    {
        return travelDocumentType;
    }
    public void setCurrentPassType(String currentPassType) 
    {
        this.currentPassType = currentPassType;
    }

    public String getCurrentPassType() 
    {
        return currentPassType;
    }
    public void setInsuranceCompany(String insuranceCompany) 
    {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsuranceCompany() 
    {
        return insuranceCompany;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setReviewerId(Long reviewerId) 
    {
        this.reviewerId = reviewerId;
    }

    public Long getReviewerId() 
    {
        return reviewerId;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("applicantId", getApplicantId())
            .append("name", getName())
            .append("matricNumber", getMatricNumber())
            .append("passportNumber", getPassportNumber())
            .append("email", getEmail())
            .append("malaysiaAddress", getMalaysiaAddress())
            .append("overseasAddress", getOverseasAddress())
            .append("courseLevel", getCourseLevel())
            .append("nationality", getNationality())
            .append("mobileNumber", getMobileNumber())
            .append("applicationLocation", getApplicationLocation())
            .append("courseName", getCourseName())
            .append("facultyName", getFacultyName())
            .append("supervisorName", getSupervisorName())
            .append("sponsorshipStatus", getSponsorshipStatus())
            .append("studyStatus", getStudyStatus())
            .append("maritalStatus", getMaritalStatus())
            .append("dateOfBirth", getDateOfBirth())
            .append("currentPassValidity", getCurrentPassValidity())
            .append("travelDocumentIssueDate", getTravelDocumentIssueDate())
            .append("travelDocumentExpiryDate", getTravelDocumentExpiryDate())
            .append("lastEntryDate", getLastEntryDate())
            .append("travelDocumentType", getTravelDocumentType())
            .append("currentPassType", getCurrentPassType())
            .append("insuranceCompany", getInsuranceCompany())
            .append("status", getStatus())
            .append("reviewerId", getReviewerId())
            .append("remarks", getRemarks())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
