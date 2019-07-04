package com.zzn.swagger.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zengzhangni
 * @date 2019/7/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Element {

    private Integer id;
    private Integer pId;
    private String name;
    private List<Element> childs;

    public Element(Integer id, Integer pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }
}
