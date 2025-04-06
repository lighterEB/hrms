package com.lightereb.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lightereb.hrms.common.result.R;
import com.lightereb.hrms.mapper.system.SysUserMapper;
import com.lightereb.hrms.model.entity.system.SysUser;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private SysUserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello, HRMS!";
    }
    
    @GetMapping("/db-check")
    public R<Object> checkDatabase() {
        try {
            SysUser user = userMapper.selectByUsername("admin");
            if (user == null) {
                return R.error("数据库连接成功，但找不到admin用户");
            }
            return R.ok(user, "数据库连接成功，找到admin用户");
        } catch (Exception e) {
            return R.error("数据库连接失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/check-password")
    public R<Object> checkPassword(@RequestParam String rawPassword) {
        try {
            SysUser user = userMapper.selectByUsername("admin");
            if (user == null) {
                return R.error("找不到admin用户");
            }
            
            boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
            if (matches) {
                return R.ok("密码验证成功！");
            } else {
                return R.error("密码验证失败! 数据库中的密码哈希: " + user.getPassword());
            }
        } catch (Exception e) {
            return R.error("密码验证失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/generate-password")
    public R<String> generatePassword(@RequestParam String rawPassword) {
        try {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            return R.ok(encodedPassword, "密码哈希生成成功");
        } catch (Exception e) {
            return R.error("密码哈希生成失败: " + e.getMessage());
        }
    }
} 