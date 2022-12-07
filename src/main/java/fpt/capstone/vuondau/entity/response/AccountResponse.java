package fpt.capstone.vuondau.entity.response;

import fpt.capstone.vuondau.entity.dto.RoleDto;

import java.time.Instant;

public class AccountResponse {

    private Long id;

    private String username;

    private String firstName;
    private String lastName;

    private Instant  birthday;

    private String email ;
    private String phoneNumber;

    private RoleDto role;

    private String avatar ;

    private Boolean active;

    private String gender;
    private GenderResponse genderResponse;

    public GenderResponse getGenderResponse() {
        return genderResponse;
    }

    public void setGenderResponse(GenderResponse genderResponse) {
        this.genderResponse = genderResponse;
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
        return active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Instant getBirthday() {
        return birthday;
    }

    public void setBirthday(Instant birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
