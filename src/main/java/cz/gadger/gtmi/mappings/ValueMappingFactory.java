package cz.gadger.gtmi.mappings;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Factory for creating value mapping
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueMappingFactory {

    public static ValueMapping createDefault() {
        return new ValueMapping() {

            @Override
            public char mapToInput(char to) {
                return to;
            }

            @Override
            public char mapFromInput(char from) {
                return from;
            }
        };
    }

    public static ValueMapping createNormalizedSetMappingFromList(List<Character> charList) {
        return new ListMapping(charList);
    }

    private static class ListMapping implements ValueMapping {
        BiMap<Character, Character> map = HashBiMap.create();

        public ListMapping(List<Character> charList) {
            for (int i = 0; i < charList.size(); i++) {
                map.put((char) i, charList.get(i));
            }
        }

        @Override
        public char mapToInput(char to) {
            if(!map.containsKey(to)) {
                return '?';
            }
            return map.get(to);
        }

        @Override
        public char mapFromInput(char from) {
            BiMap<Character, Character> inverse = map.inverse();
            if(!inverse.containsKey(from)) {
                return '?';
            }
            return inverse.get(from);
        }
    }
}
