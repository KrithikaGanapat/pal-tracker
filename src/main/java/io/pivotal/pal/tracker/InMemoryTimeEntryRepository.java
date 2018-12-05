package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    public HashMap<Long, TimeEntry> timeEntryMap = new java.util.HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry)  {

        long timeEntryId = (long)timeEntryMap.size() + 1L;
        TimeEntry newEntry= new TimeEntry( timeEntryId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(), timeEntry.getHours());

        timeEntryMap.put(timeEntryId, newEntry);

        return newEntry;


    }

    @Override
    public TimeEntry find(Long timeEntryId){


            return timeEntryMap.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list(){

        return new ArrayList<>(timeEntryMap.values());

    }

    @Override
    public TimeEntry update(Long timeEntryId, TimeEntry timeEntry){

        TimeEntry updatedEntry = new TimeEntry( timeEntryId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(), timeEntry.getHours());

        timeEntryMap.replace(timeEntryId, updatedEntry);
        return updatedEntry;

    }

    @Override
    public void delete(Long timeEntryId){

        timeEntryMap.remove(timeEntryId);

    }
}
