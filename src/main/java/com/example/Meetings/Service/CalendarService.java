package com.example.Meetings.Service;

import com.example.Meetings.Models.CalendarModel;
import com.example.Meetings.Models.MeetingModel;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CalendarService {

    private List<CalendarModel> calendarModels;
    private List<MeetingModel> availableTime;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private int minutes;

    public CalendarService() {
        calendarModels = new ArrayList<>();
        availableTime = new ArrayList<>();
    }

    public String add(List<CalendarModel> models, int durationOfTheMeeting) {
        minutes = durationOfTheMeeting;
        calendarModels = models;
        return planMeeting();
    }

    private String planMeeting() {
        List<MeetingModel> meetings = new ArrayList<>();
        try {
            for (CalendarModel cm : calendarModels) {
                meetings = addAvailableHours(meetings, cm);
            }
            meetings = planFinalMeeting(meetings);

        } catch (ParseException e) {
        }

        return meetings.toString();
    }

    private List<MeetingModel> planFinalMeeting(List<MeetingModel> list) throws ParseException{
        List<MeetingModel> finalList = new ArrayList<>();
        for (int i = 0; i < list.size() - 1 ; i++) {
            Date start = format.parse(list.get(i).getStart());
            Date end = format.parse(list.get(i).getEnd());

            for (int j = i + 1; j < list.size(); j++) {
                Date nextStart = format.parse(list.get(j).getStart());
                Date nextEnd = format.parse(list.get(j).getEnd());

                if(convert(start, nextStart) >= 0 && convert(nextStart, end) >= 0){
                    if(convert(end, nextEnd) >= 0)  {
                        if(convert(nextStart, end) >= minutes)
                            finalList.add(new MeetingModel(list.get(j).getStart(), list.get(i).getEnd()));
                    }else {
                        if(convert(nextStart, nextEnd) >= minutes)
                            finalList.add(new MeetingModel(list.get(j).getStart(), list.get(j).getEnd()));
                    }
                }
            }
        }
        return finalList;
    }

    private List<MeetingModel> addAvailableHours(List<MeetingModel> meetings, CalendarModel calendarModel) throws ParseException {

        List<MeetingModel> currentMeetings = calendarModel.getPlanned_meeting();
        Date earlier = format.parse(calendarModel.getWorking_hours().getStart());
        Date later = format.parse(calendarModel.getPlanned_meeting().get(0).getStart());

        if (convert(earlier, later) > 0 && convert(earlier, later) >= minutes) meetings.add(
                new MeetingModel(calendarModel.getWorking_hours().getStart(), calendarModel.getPlanned_meeting().get(0).getStart()));

        for (int i = 0; i < currentMeetings.size() - 1; i++) {
            later = format.parse(currentMeetings.get(i + 1).getStart());
            earlier = format.parse(currentMeetings.get(i).getEnd());

            if (convert(earlier, later) > 0 && convert(earlier, later) >= minutes)
                meetings.add(new MeetingModel(
                        currentMeetings.get(i).getEnd(),
                        currentMeetings.get(i + 1).getStart())
                );
        }

        earlier = format.parse(calendarModel.getPlanned_meeting().get(calendarModel.getPlanned_meeting().size() - 1).getEnd());
        later = format.parse(calendarModel.getWorking_hours().getEnd());
        if (convert(earlier, later) > 0 && convert(earlier, later) >= minutes) meetings.add(
                new MeetingModel(calendarModel.getPlanned_meeting().get(calendarModel.getPlanned_meeting().size() - 1).getEnd(), calendarModel.getWorking_hours().getEnd()));

        return meetings;
    }

    private long convert(Date earlier, Date later) {
        return TimeUnit.MINUTES.convert((later.getTime() - earlier.getTime()), TimeUnit.MILLISECONDS);
    }
}
