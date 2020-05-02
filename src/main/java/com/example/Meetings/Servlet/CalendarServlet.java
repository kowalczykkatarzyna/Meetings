package com.example.Meetings.Servlet;


import com.example.Meetings.Models.CalendarModel;
import com.example.Meetings.Models.MeetingModel;
import com.example.Meetings.Models.Requests.PlanMeetingRequest;
import com.example.Meetings.Service.CalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/planMeeting")
public class CalendarServlet{
    private final Logger logger =  LoggerFactory.getLogger(CalendarServlet.class);
    private List<CalendarModel> list;
    CalendarService service;

    public CalendarServlet(CalendarService service){
        this.service = service;
    }

    @PostMapping
    String planMeeting(@RequestBody PlanMeetingRequest meetingRequest){
        return service.add(meetingRequest.getList(), meetingRequest.getDurationOfTheMeeting());
    }

}