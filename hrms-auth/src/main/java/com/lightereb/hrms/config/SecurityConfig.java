package com.lightereb.hrms.config;

import com.lightereb.hrms.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    /**
     * 安全过滤器链配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护
                .csrf(AbstractHttpConfigurer::disable)

                // 配置请求授权
                .authorizeHttpRequests(auth -> auth
                        // 允许认证相关接口匿名访问
                        .requestMatchers("/auth/login", "/auth/register").permitAll()
                        // 允许测试接口匿名访问
                        .requestMatchers("/test/**").permitAll()
                        // 允许Swagger和Druid相关接口匿名访问
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/druid/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/doc.html").permitAll()
                        // 基于角色的访问控制 - 系统管理
                        .requestMatchers("/system/**").hasRole("ADMIN")
                        
                        // 基于角色的访问控制 - 人事管理
                        .requestMatchers("/hr/employee/**").hasAnyRole("ADMIN", "HR_MANAGER")
                        .requestMatchers("/hr/department/**").hasAnyRole("ADMIN", "HR_MANAGER", "DEPT_MANAGER")
                        .requestMatchers("/hr/position/**").hasAnyRole("ADMIN", "HR_MANAGER")
                        
                        // 基于角色的访问控制 - 考勤管理
                        .requestMatchers("/attendance/**").hasAnyRole("ADMIN", "ATTENDANCE_ADMIN")
                        .requestMatchers("/attendance/leave/apply").hasAnyRole("ADMIN", "ATTENDANCE_ADMIN", "HR_MANAGER", "DEPT_MANAGER", "EMPLOYEE")
                        
                        // 基于角色的访问控制 - 薪资管理
                        .requestMatchers("/salary/**").hasAnyRole("ADMIN", "SALARY_ADMIN")
                        
                        // 基于角色的访问控制 - 绩效管理
                        .requestMatchers("/performance/**").hasAnyRole("ADMIN", "PERFORMANCE_ADMIN")
                        
                        // 基于角色的访问控制 - 培训管理
                        .requestMatchers("/training/**").hasAnyRole("ADMIN", "TRAINING_ADMIN")
                        
                        // 基于角色的访问控制 - 组织管理
                        .requestMatchers("/organization/**").hasAnyRole("ADMIN", "HR_MANAGER")
                        .requestMatchers("/organization/structure").hasAnyRole("ADMIN", "HR_MANAGER", "DEPT_MANAGER", "EMPLOYEE")
                        
                        // 基于权限的访问控制 - 系统管理
                        .requestMatchers("/system/user/**").hasAuthority("system:user:list")
                        .requestMatchers("/system/role/**").hasAuthority("system:role:list")
                        .requestMatchers("/system/menu/**").hasAuthority("system:menu:list")
                        
                        // 基于权限的访问控制 - 员工管理
                        .requestMatchers(HttpMethod.GET, "/hr/employee/**").hasAuthority("hr:employee:view")
                        .requestMatchers(HttpMethod.POST, "/hr/employee/**").hasAuthority("hr:employee:add")
                        .requestMatchers(HttpMethod.PUT, "/hr/employee/**").hasAuthority("hr:employee:edit")
                        .requestMatchers(HttpMethod.DELETE, "/hr/employee/**").hasAuthority("hr:employee:delete")
                        
                        // 基于权限的访问控制 - 部门管理
                        .requestMatchers(HttpMethod.GET, "/hr/department/**").hasAuthority("hr:department:view")
                        .requestMatchers(HttpMethod.POST, "/hr/department/**").hasAuthority("hr:department:add")
                        .requestMatchers(HttpMethod.PUT, "/hr/department/**").hasAuthority("hr:department:edit")
                        .requestMatchers(HttpMethod.DELETE, "/hr/department/**").hasAuthority("hr:department:delete")
                        
                        // 基于权限的访问控制 - 考勤管理
                        .requestMatchers(HttpMethod.GET, "/attendance/record/**").hasAuthority("attendance:record:view")
                        .requestMatchers(HttpMethod.POST, "/attendance/leave/apply/**").hasAuthority("attendance:leave:apply")
                        .requestMatchers("/attendance/leave/approve/**").hasAuthority("attendance:leave:approve")
                        
                        // 其他所有请求需要认证
                        .anyRequest().authenticated()
                )

                // 使用无状态会话
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 添加JWT认证过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证提供者
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        // 用户不存在或密码错误时不隐藏错误
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    /**
     * 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
} 