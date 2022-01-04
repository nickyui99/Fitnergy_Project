package com.rtn.fitnergy.workout;

public class CourseDetail {
    private String work;
    private String inst;
    private String url;

    public CourseDetail() {

    }

    public CourseDetail(String work, String inst, String url) {
        this.work = work;
        this.inst = inst;
        this.url = url;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getInst() {
        return inst;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setInst(String inst) {
        this.inst = inst;


    }
}
