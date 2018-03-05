package com.hq.yunyi2.bean;

public class PrivateInfoBean {
    String name;
    String motto;
    String birth_date;
    String died_date;
    String relation;
    String education;
    String job;
    String experience_content;

    public PrivateInfoBean(String name, String motto, String birth_date, String died_date, String relation, String education, String job, String experience_content) {
        this.name = name;
        this.motto = motto;
        this.birth_date = birth_date;
        this.died_date = died_date;
        this.relation = relation;
        this.education = education;
        this.job = job;
        this.experience_content = experience_content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDied_date() {
        return died_date;
    }

    public void setDied_date(String died_date) {
        this.died_date = died_date;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getExperience_content() {
        return experience_content;
    }

    public void setExperience_content(String experience_content) {
        this.experience_content = experience_content;
    }
}
