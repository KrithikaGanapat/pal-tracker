package io.pivotal.pal.tracker;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository{


    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry)  {

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours)" +
                        "VALUES (?, ?, ?, ?)", RETURN_GENERATED_KEYS
                );

            ps.setLong(1, timeEntry.getProjectId());
            ps.setLong(2, timeEntry.getUserId());
            ps.setDate(3, Date.valueOf(timeEntry.getDate()));
            ps.setInt(4, timeEntry.getHours());

            return ps;
        },generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(Long timeEntryId){

         return jdbcTemplate.query("Select * from time_entries where id = ?", new Object[]{timeEntryId},
                extractor);

    }

    @Override
    public List<TimeEntry> list(){

        return jdbcTemplate.query("Select * from time_entries", mapper);

    }

    @Override
    public TimeEntry update(Long timeEntryId, TimeEntry timeEntry){

        jdbcTemplate.update("Update time_entries SET project_id = ?, user_id = ?, date = ?, hours = ? where " +
                "id = ?", timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(),
                timeEntryId);

        return find(timeEntryId);

    }

    @Override
    public void delete(Long timeEntryId){

        jdbcTemplate.update("Delete from time_entries where id = ? ", timeEntryId);

    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
