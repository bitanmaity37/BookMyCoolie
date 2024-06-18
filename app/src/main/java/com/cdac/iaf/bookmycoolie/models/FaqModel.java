package com.cdac.iaf.bookmycoolie.models;

import com.google.gson.annotations.SerializedName;

public class FaqModel {

    private int faqId;
    @SerializedName("faqQuestion")
    private String question;
    @SerializedName("faqAnswer")
    private String answer;
    private boolean isExpanded=false;

    public FaqModel(int faqId, String question, String answer) {
        this.faqId = faqId;
        this.question = question;
        this.answer = answer;
    }

    public int getFaqId() {
        return faqId;
    }

    public void setFaqId(int faqId) {
        this.faqId = faqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    @Override
    public String toString() {
        return "FaqModel{" +
                "faqId=" + faqId +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
