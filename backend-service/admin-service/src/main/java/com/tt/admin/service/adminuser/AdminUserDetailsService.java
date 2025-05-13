package com.tt.admin.service.adminuser;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tt.admin.entity.dao.AdminPermission;
import com.tt.admin.entity.dao.AdminUser;
import com.tt.admin.mapper.AdminUserMapper;
import com.tt.admin.repository.adminuser.AdminPermissionReposity;
import com.tt.admin.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminUserMapper adminUserMapper;

    private final AdminPermissionReposity pReposity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserMapper.getUserByUsername(username);
        if (adminUser == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        List<SimpleGrantedAuthority> authorities = pReposity.selectListByUid(adminUser.getId()).stream()
            .map(AdminPermission::getCode)
            .map(SimpleGrantedAuthority::new)
            .toList();

        log.info("============ {}",JsonUtil.toJson(authorities));
        return new User(adminUser.getUsername(), adminUser.getPassword(), authorities);
    }

}
