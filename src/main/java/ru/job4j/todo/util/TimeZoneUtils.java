package ru.job4j.todo.util;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

public final class TimeZoneUtils {

    private TimeZoneUtils() {
    }

    private static final String DEFAULT_TIMEZONE = TimeZone.getDefault().getID();

    public final static List<Task> convertTaskListTimeFromTimeZone(List<Task> tasks, String timezone) {
        tasks.stream().forEach(t -> {
            TimeZoneUtils.convertTaskTimeFromTimezone(t, timezone);
        });
        return tasks;
    }

    public final static void convertTaskTimeFromTimezone(Task task, String timezone) {
        if (task != null) {
            task.setCreated(
                    ZonedDateTime.of(task.getCreated(), ZoneOffset.UTC)
                            .withZoneSameInstant(ZoneId.of(timezone)).toLocalDateTime());
        }
    }

    public final static String getUserTimezoneOrDefault(User user) {
        String timezone = user.getTimezone();
        if (timezone == null
                || timezone.isEmpty()) {
            timezone = DEFAULT_TIMEZONE;
        }
        return timezone;
    }
}
