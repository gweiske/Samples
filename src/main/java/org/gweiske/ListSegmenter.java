package org.gweiske;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListSegmenter {
    private Map<LocalDate, Integer> map;

    public ListSegmenter() {
    }

    public Map<LocalDate, Integer> getMap(List<LocalDate> dates) {
        Iterator<LocalDate> iterator = dates.stream().sorted(LocalDate::compareTo).iterator();
        map = new HashMap<>();
        if (iterator.hasNext()) {
            LocalDate date = iterator.next();
            map.put(date, 1);
            segment(iterator, date, date, map);
        }
        return map;
    }

    public void segment(Iterator<LocalDate> iterator, LocalDate previousDate, LocalDate mapKey, Map<LocalDate, Integer> map) {
        if (iterator.hasNext()) {
            LocalDate currentDate = iterator.next();
            if (previousDate.plusDays(1).equals(currentDate)) {
                map.put(mapKey, map.get(mapKey) + 1);
                segment(iterator, currentDate, mapKey, map);
            } else {
                map.put(currentDate, 1);
                segment(iterator, currentDate, currentDate, map);
            }
        }
    }
}
