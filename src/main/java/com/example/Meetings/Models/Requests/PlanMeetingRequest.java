package com.example.Meetings.Models.Requests;

import com.example.Meetings.Models.CalendarModel;
import com.example.Meetings.Models.MeetingModel;

import java.util.List;

public class PlanMeetingRequest {
    private int durationOfTheMeeting;
    private List<CalendarModel> list;

    public int getDurationOfTheMeeting() {
        return durationOfTheMeeting;
    }

    public void setDurationOfTheMeeting(int durationOfTheMeeting) {
        this.durationOfTheMeeting = durationOfTheMeeting;
    }

    public List<CalendarModel> getList() {
        return list;
    }

    public void setList(List<CalendarModel> list) {
        this.list = list;
    }
}
