package com.example.webcinema.Services.Interfaces;

import com.example.webcinema.Entity.Schedule;

public interface IScheduleService {
    Schedule addNew(Schedule newSchedule);

    Schedule remake(Schedule remakeSchedule);

    Schedule delete(String code);
}
