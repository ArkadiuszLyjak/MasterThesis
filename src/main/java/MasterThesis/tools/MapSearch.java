package MasterThesis.tools;

import MasterThesis.arc.ArcEntity;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapSearch {

    //region getKeyByValueFromArcMap
    public static Long getKeyByValueFromArcMap(Map<Long, ArcEntity> map, Long value) {

        Set<Map.Entry<Long, ArcEntity>> entrySet = map.entrySet();

        for (Map.Entry<Long, ArcEntity> entry : entrySet) {
            if (entry.getValue().getStartNode().equals(value) || entry.getValue().getEndNode().equals(value)) {
                System.out.println(entry.getKey());
            }
        }
        return null;
    }
    //endregion

    //region getKeyByValue
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    //endregion


}
