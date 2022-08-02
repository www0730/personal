package com.zzptc.twds.pojo;

import java.util.Date;

public class Toworkload {
    private Integer toid;

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    private Integer oid;
private Courses courses;

    public void setUser(User user) {
        this.user = user;
    }


    public Double getTotalWorkload() {
        return totalWorkload;
    }

    public void setTotalWorkload(Double totalWorkload) {
        this.totalWorkload = totalWorkload;
    }

    private Double totalWorkload;

    public String getCoName() {
        return coName;
    }

    public void setCoName(String coName) {
        this.coName = coName;
    }

    private String coName;
    private String username;
    private Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String toname;

    public void setOworkload(Oworkload oworkload) {
        this.oworkload = oworkload;
    }

    private String totime;
    
    private Oworkload oworkload;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private User user;

    private String  content;
    

    public User getUser() {
		return user;
	}

	public Oworkload getOworkload() {
		return oworkload;
	}

	public Integer getToid() {
        return toid;
    }

    public void setToid(Integer toid) {
        this.toid = toid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }



    public String getToname() {
        return toname;
    }

    public void setToname(String toname) {
        this.toname = toname == null ? null : toname.trim();
    }

	public String getTotime() {
		return totime;
	}

	public void setTotime(String totime) {
		this.totime = totime;
	}

  
}