package com.xiazidong.hefei;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: xiazidong
 * @Date : 2020/5/15 22:05
 */
public class Test {
    public static void main(String[] args) {
        RoomType r1 = new RoomType(1, "单人间", "101");
        RoomType r2 = new RoomType(1, "单人间", "102");
        RoomType r3 = new RoomType(1, "单人间", "103");
        RoomType r4 = new RoomType(2, "双人间", "201");
        RoomType r5 = new RoomType(2, "双人间", "202");
        RoomType r6 = new RoomType(2, "双人间", "203");
        RoomType r7 = new RoomType(3, "电竞房", "301");
        RoomType r8 = new RoomType(4, "双人房", "203");
        List<RoomType> types = new ArrayList<>();
        types.add(r1);
        types.add(r2);
        types.add(r3);
        types.add(r4);
        types.add(r5);
        types.add(r6);
        types.add(r7);
        types.add(r8);

        Map<String, List<RoomType>> map = types.stream().collect(Collectors.groupingBy(t -> fetchGroupKey(t)));
        System.out.println(map);
        for (String key : map.keySet()) {
            System.out.println("key=" + key + "  value.size=" + map.get(key).size() + "   and value=" + map.get(key));
        }

        System.out.println("========================");
        Map<RoomType, List<RoomType>> map2 = types.stream().collect(Collectors.groupingBy(t -> t));
        System.out.println(map2);
        for (RoomType key : map2.keySet()) {
            System.out.println("key=" + key + "  value.size=" + map2.get(key).size() + "   and value=" + map2.get(key));
        }

    }


    private static String fetchGroupKey(RoomType roomType) {
        return roomType.getId() + "+" + roomType.getName();
    }
}

class RoomType {
    private Integer id;
    private String name;
    private String typeNo;

    public RoomType() {
    }

    public RoomType(Integer id, String name, String typeNo) {
        this.id = id;
        this.name = name;
        this.typeNo = typeNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomType roomType = (RoomType) o;
        return id.equals(roomType.id) &&
                name.equals(roomType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", typeNo='" + typeNo + '\'' +
                '}';
    }
}
