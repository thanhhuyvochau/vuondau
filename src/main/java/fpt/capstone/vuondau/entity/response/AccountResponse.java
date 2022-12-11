package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.dto.RoleDto;

import java.time.Instant;

public class AccountResponse {

    private Long id;
    private String username;
    private Boolean isActive = false;
    private String firstName;
    private String lastName;
    private String email;

    private Instant birthday;
    private String introduce;
    private String phoneNumber;

    private RoleDto role;
    private GenderResponse gender;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
//
//    public String getCvUrl() {
//        return cvUrl;
//    }
//
//    public void setCvUrl(String cvUrl) {
//        this.cvUrl = cvUrl;
//    }

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }

    public GenderResponse getGender() {
        return gender;
    }

    public void setGender(GenderResponse gender) {
        this.gender = gender;
    }
}
