package io.pivotal.pal.tracker;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

@Repository
public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry timeEntry);

    public TimeEntry find(Long timeEntryId);

    public List<TimeEntry> list();

    public TimeEntry update(Long timeEntryId, TimeEntry timeEntry);

    public void delete(Long timeEntryId);
}
