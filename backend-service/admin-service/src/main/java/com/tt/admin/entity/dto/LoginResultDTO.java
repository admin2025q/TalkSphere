package com.tt.admin.entity.dto;

import java.util.List;

import com.tt.admin.entity.vo.AdminMenuVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultDTO {

    private String userName;

    private List<AdminMenuVo> menus;
}
