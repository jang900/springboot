package com.xia.springbootshiro02.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysUser {

    private int id;
    private String username;
    private String password;
    private String perm;

}
