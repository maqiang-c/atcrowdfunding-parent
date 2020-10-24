package com.atguigu.security.service;

import com.atguigu.security.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPasswordEncoder implements PasswordEncoder {

    //加密方法
    @Override
    public String encode(CharSequence rawPassword) {

        String digest = MD5Util.digest(rawPassword.toString());
        System.out.println(digest);
        return digest;
    }


    //密文比较方法
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5Util.digest(rawPassword.toString()).equals(encodedPassword);
    }
}
