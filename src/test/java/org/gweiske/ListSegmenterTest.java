package org.gweiske;

import org.gweiske.ListSegmenter;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListSegmenterTest {
    @Test
    public void testIt() {
        LocalDate today = LocalDate.now();
        List<LocalDate> dates = List.of(
                today,
                today.plusDays(2),
                today.plusDays(3),
                today.minusDays(2),
                today.minusDays(3),
                today.minusDays(5)
        );
        ListSegmenter listSegmenter = new ListSegmenter();
        List<LocalDate> dateList = dates.stream().sorted(LocalDate::compareTo).collect(Collectors.toList());
        System.out.println(dateList);
        System.out.println(listSegmenter.getMap(dates));

        dates = List.of();
        dateList = dates.stream().sorted(LocalDate::compareTo).collect(Collectors.toList());
        System.out.println(dateList);
        System.out.println(listSegmenter.getMap(dates));

        dates = List.of(
                today,
                today.minusDays(1),
                today.minusDays(2),
                today.minusDays(3),
                today.minusDays(4),
                today.minusDays(5)
        );
        dateList = dates.stream().sorted(LocalDate::compareTo).collect(Collectors.toList());
        System.out.println(dateList);
        System.out.println(listSegmenter.getMap(dates));
    }
}
