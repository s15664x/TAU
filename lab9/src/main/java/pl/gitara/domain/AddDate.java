package pl.gitara.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddDate {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDate localDate;

    public String getDate() {
        localDate = LocalDate.now();
        return dtf.format(localDate);
    }
}