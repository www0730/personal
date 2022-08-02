package com.zzptc.twds.pojo;

public class User {
    private Integer userid;
    private Integer roleid;
    private Integer wId;
    private String email;
    private String username;
    private String password;
    private String phone;

    private String name;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    private Integer state;
    private Integer cid;
    private Integer age;
    private Integer education;
    private Integer title;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }


    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }





	public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getwId() {
        return wId;
    }

    public void setwId(Integer wId) {
        this.wId = wId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
  		return name;
  	}

  	public void setName(String name) {
  		this.name = name;
  	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }


}