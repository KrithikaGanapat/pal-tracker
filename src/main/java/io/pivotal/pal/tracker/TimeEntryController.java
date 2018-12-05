package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository){

        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry){


        TimeEntry timeEntry1 = timeEntryRepository.create(timeEntry);
        return new ResponseEntity<>(timeEntry1, HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity read(@PathVariable Long id){

        TimeEntry timeEntry1 = timeEntryRepository.find(id);

        if (timeEntry1 == null)
            return new ResponseEntity<>(timeEntry1, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(timeEntry1, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list(){

        List<TimeEntry> expected = timeEntryRepository.list();
        return new ResponseEntity<>(expected, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntry){

        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, timeEntry);

        if (updatedTimeEntry == null)
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(updatedTimeEntry, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id){

        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
