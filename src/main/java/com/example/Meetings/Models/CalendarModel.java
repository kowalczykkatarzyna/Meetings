package com.example.Meetings.Models;

import java.util.List;

public class CalendarModel {
    private MeetingModel working_hours;
    private List<MeetingModel> planned_meeting;

    public MeetingModel getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(MeetingModel working_hours) {
        this.working_hours = working_hours;
    }

    public List<MeetingModel> getPlanned_meeting() {
        return planned_meeting;
    }

    public void setPlanned_meeting(List<MeetingModel> planned_meeting) {
        this.planned_meeting = planned_meeting;
    }
}
