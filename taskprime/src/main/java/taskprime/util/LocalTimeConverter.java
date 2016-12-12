package taskprime.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.time.LocalTime;
import java.sql.Time;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime time) {
        return Time.valueOf(time);
    }

    @Override
    public LocalTime convertToEntityAttribute(Time value) {
        return LocalTime.parse(value.toString());
    }
}
