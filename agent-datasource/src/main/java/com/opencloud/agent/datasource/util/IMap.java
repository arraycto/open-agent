package com.opencloud.agent.datasource.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class IMap extends HashMap<String, Object> {

    Map<String, Object> returnMap;

    /**
     * 普通的Map
     */
    public IMap() {
        returnMap = new HashMap<>();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Object get(Object key) {
        return returnMap.get(key);
    }

    public String getString(Object key) {
        Object o = get(key);
        return o == null ? "" : o.toString();
    }

    public String getString(String name, String defaultValue) {
        String value = getString(name);
        return value == null ? defaultValue : value;
    }

    /**
     * get names
     *
     * @return String[]
     */
    public String[] getNames() {
        String[] names = (String[]) keySet().toArray(new String[0]);
        Arrays.sort(names);
        return names;
    }

    /**
     * get int
     *
     * @param name
     * @return int
     */
    public int getInt(String name) {
        return getInt(name, 0);
    }

    /**
     * get int
     *
     * @param name
     * @param defaultValue
     * @return int
     */
    public int getInt(String name, int defaultValue) {
        String value = getString(name, "");
        return "".equals(value) ? defaultValue : Integer.parseInt(value);
    }

    /**
     * get double
     *
     * @param name
     * @return double
     */
    public double getDouble(String name) {
        return getDouble(name, 0);
    }

    /**
     * get double
     *
     * @param name
     * @param defaultValue
     * @return double
     */
    public double getDouble(String name, double defaultValue) {
        String value = getString(name, "");
        return "".equals(value) ? defaultValue : Double.parseDouble(value);
    }

    /**
     * get boolean
     *
     * @param name
     * @return boolean
     */
    public boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    /**
     * get boolean
     *
     * @param name
     * @param defaultValue
     * @return boolean
     */
    public boolean getBoolean(String name, boolean defaultValue) {
        String value = getString(name, "");
        return "".equals(value) ? defaultValue : Boolean.valueOf(value)
                .booleanValue();
    }

    @Override
    public Object put(String key, Object value) {
        return returnMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return returnMap.remove(key);
    }

    @Override
    public void clear() {
        returnMap.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return returnMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return returnMap.containsValue(value);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return returnMap.entrySet();
    }

    @Override
    public boolean isEmpty() {
        return returnMap.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return returnMap.keySet();
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        returnMap.putAll(map);
    }

    @Override
    public int size() {
        return returnMap.size();
    }

    @Override
    public Collection<Object> values() {
        return returnMap.values();
    }

    public static class Builder {
        public static IMap build() {
            return new IMap();
        }
    }

    public IMap add(String key, String value) {
        put(key, value);
        return this;
    }
}
