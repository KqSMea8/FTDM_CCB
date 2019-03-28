package com.sunyard.sunfintech.listcheck.model;

/**
 * @author heroy
 * @version 2018/6/15
 */
public class SubSubjectDirection {
    //子科目
    String subject_from;
    //对手子科目
    String to_subject;
    //备注
    String remark;

    public String getSubject_from() {
        return subject_from;
    }

    public void setSubject_from(String subject_from) {
        this.subject_from = subject_from;
    }

    public String getTo_subject() {
        return to_subject;
    }

    public void setTo_subject(String to_subject) {
        this.to_subject = to_subject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SubSubjectDirection(){
        super();
    }

    public SubSubjectDirection(String subject_from, String to_subject, String remark) {
        this.subject_from = subject_from;
        this.to_subject = to_subject;
        this.remark = remark;
    }

    public SubSubjectDirection(String subject_from, String to_subject) {
        this(subject_from, to_subject, "转账方向");
    }

}
