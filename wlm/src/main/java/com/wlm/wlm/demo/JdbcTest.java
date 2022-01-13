package com.wlm.wlm.demo;

import com.wlm.wlm.model.SysUser;

import java.sql.*;

/**
 * @author wuliming
 * @date 2021/12/7 17:40
 */
public class JdbcTest {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wlm?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai", "root", "");
            System.out.println(conn);
            PreparedStatement ps = conn.prepareStatement("select * from sys_user limit 0, 10");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                SysUser user = new SysUser();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setAge(resultSet.getInt("age"));
                user.setDeptNo(resultSet.getString("dept_no"));
                user.setRoleNo(resultSet.getString("role_no"));
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
