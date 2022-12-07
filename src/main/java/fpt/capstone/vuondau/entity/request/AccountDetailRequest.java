package fpt.capstone.vuondau.entity.request;


import fpt.capstone.vuondau.entity.common.EAccountRole;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class AccountDetailRequest implements Serializable {


    private String firstName;
    private String lastName;
    private Instant birthDay;


    private String email;


    private String phone;
    private String gender;

    private String domicile;
    private String voice;

    private String teachingProvince;
    private String currentAddress;

    private String idCard;

    private String trainingSchoolName;

    private String majors;

    private String level;

//    private List<UploadAvatarRequest> uploadFiles ;


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

    public Instant getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Instant birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getTeachingProvince() {
        return teachingProvince;
    }

    public void setTeachingProvince(String teachingProvince) {
        this.teachingProvince = teachingProvince;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTrainingSchoolName() {
        return trainingSchoolName;
    }

    public void setTrainingSchoolName(String trainingSchoolName) {
        this.trainingSchoolName = trainingSchoolName;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

//    public List<UploadAvatarRequest> getUploadFiles() {
//        return uploadFiles;
//    }
//
//    public void setUploadFiles(List<UploadAvatarRequest> uploadFiles) {
//        this.uploadFiles = uploadFiles;
//    }
}