package me.clientastisch.mongodb.filter;

import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

public enum Filter {

    EQUALS {
        @Override
        public Bson getFiler(String key, Object value) {
            return Filters.eq(key, value);
        }
    },
    UNEQUAL {
        @Override
        public Bson getFiler(String key, Object value) {
            return Filters.ne(key, value);
        }
    },
    GREATER {
        @Override
        public Bson getFiler(String key, Object value) {
            return Filters.gt(key, value);
        }
    },
    LOWER {
        @Override
        public Bson getFiler(String key, Object value) {
            return Filters.lt(key, value);
        }
    }

    ;

    public Bson getFiler(String key, Object value) {
        throw new RuntimeException("Not implemented yet");
    }
}
