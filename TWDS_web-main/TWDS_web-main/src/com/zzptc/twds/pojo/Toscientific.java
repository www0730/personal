package com.zzptc.twds.pojo;

public class Toscientific {
    private Integer toid;



    private Integer oid;


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


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;
    
    private Scientific scientific;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private User user;

    public Scientific getScientific() {
        return scientific;
    }

    public void setScientific(Scientific scientific) {
        this.scientific = scientific;
    }

    private String  content;
    

    public User getUser() {
		return user;
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



  
}